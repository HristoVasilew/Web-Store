package io.pliant.webstore.model;

import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {
    private static final long serialVersionUID = 1L;
    public CustomUser(UserEntity user) {
        super(user.getEmail(), user.getPassword(), user.getGrantedAuthoritiesList());
    }
}