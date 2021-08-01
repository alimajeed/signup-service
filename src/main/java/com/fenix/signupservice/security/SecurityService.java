package com.fenix.signupservice.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityService {
    boolean autoLogin(UserDetails userDetails, String password);
}
