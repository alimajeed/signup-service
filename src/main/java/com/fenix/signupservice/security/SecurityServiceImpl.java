package com.fenix.signupservice.security;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService{

    private final static Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private AuthenticationManager authenticationManager;

    @Override
    public boolean autoLogin(UserDetails userDetails, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (authenticate.isAuthenticated()) {
            logger.info("user authenticated for user Id : {}", userDetails.getUsername());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        return authenticate.isAuthenticated();
    }
}
