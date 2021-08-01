package com.fenix.signupservice.config;

import com.fenix.signupservice.model.AppUser;
import com.fenix.signupservice.repository.AppUserRepository;
import lombok.AllArgsConstructor;
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

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public AuthenticationManager customAuthenticationManager() {
        return authentication -> {
            String username = ((AppUser)authentication.getPrincipal()).getUsername() + "";
            String password = authentication.getCredentials() + "";

            Optional<AppUser> user = appUserRepository.findByEmail(username);
            if (!user.isPresent()) {
                throw new BadCredentialsException("1000");
            }
            if (!bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
                throw new BadCredentialsException("1000");
            }
            return new UsernamePasswordAuthenticationToken(username, null, user.get().getAuthorities());
        };
    }
}
