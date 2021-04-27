package com.nixsolutions.ppp.config;

import com.nixsolutions.ppp.service.UserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }
}
