package com.nixsolutions.ppp.config;

import com.nixsolutions.ppp.model.AuthUser;
import com.nixsolutions.ppp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/logout", "/register").permitAll()
                .antMatchers("/users/**").hasAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .csrf().disable().cors()
                .and()
                .httpBasic().authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement().disable();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> new AuthUser(userService.findUserByUsername(username));
    }

    @Bean
    public PasswordEncoder encoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
