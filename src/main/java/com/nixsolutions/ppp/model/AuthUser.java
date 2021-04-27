package com.nixsolutions.ppp.model;

public class AuthUser extends org.springframework.security.core.userdetails.User{
    private User user;

    public AuthUser(User user) {
        super(user.getUsername(), user.getPassword(), user.getRoles());
        this.user = user;
    }
}
