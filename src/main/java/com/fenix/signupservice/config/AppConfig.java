package com.fenix.signupservice.config;

import com.fenix.signupservice.model.AppUser;
import com.fenix.signupservice.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration
@AllArgsConstructor
public class AppConfig {

    private final static Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public AuthenticationManager customAuthenticationManager() {
        return authentication -> {
            String username = ((AppUser)authentication.getPrincipal()).getUsername();
            String password = authentication.getCredentials().toString();

            Optional<AppUser> user = appUserRepository.findByEmail(username);
            if (!user.isPresent()) {
                logger.warn("user not found for user Id : {}", username);
                return new UsernamePasswordAuthenticationToken(username, user.get().getPassword());
            }
            if (!bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
                logger.warn("password not matched for user Id : {}", username);
                return new UsernamePasswordAuthenticationToken(username, user.get().getPassword());
            }
            return new UsernamePasswordAuthenticationToken(username, user.get().getPassword(), user.get().getAuthorities());
        };
    }
}
