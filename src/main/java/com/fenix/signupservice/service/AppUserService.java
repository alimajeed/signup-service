package com.fenix.signupservice.service;

import com.fenix.signupservice.config.PropertyConfig;
import com.fenix.signupservice.model.AppUser;
import com.fenix.signupservice.model.AppUserRole;
import com.fenix.signupservice.model.SignUpRequest;
import com.fenix.signupservice.repository.AppUserRepository;
import com.fenix.signupservice.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(PropertyConfig.USER_NOT_FOUND_MSG, email)));
    }

    public UserDetails signUpUser(AppUser appUser) {
        Optional<AppUser> dbUser = appUserRepository
                .findByEmail(appUser.getEmail());
        boolean userExists = dbUser.isPresent();

        if (userExists) {
            securityService.autoLogin(appUser, appUser.getPassword());
            return dbUser.get();
        }

        String plainPassword = appUser.getPassword();
        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);
        securityService.autoLogin(appUser, plainPassword);
        return appUser;
    }

    public UserDetails signUp(SignUpRequest request) {
        return signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER

                )
        );
    }
}
