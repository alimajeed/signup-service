package com.fenix.signupservice.service;

import com.fenix.signupservice.config.PropertyConfig;
import com.fenix.signupservice.model.AppUser;
import com.fenix.signupservice.model.AppUserRole;
import com.fenix.signupservice.model.ResponseInfo;
import com.fenix.signupservice.model.SignUpRequest;
import com.fenix.signupservice.repository.AppUserRepository;
import com.fenix.signupservice.security.SecurityService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(AppUserService.class);

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

    public ResponseInfo getUser (String userId){
        UserDetails dbUser = loadUserByUsername(userId);
        return new ResponseInfo(LocalDateTime.now(), HttpStatus.OK.value(), null, HttpStatus.OK.getReasonPhrase(), null, dbUser);
    }

    public ResponseInfo signUpUser(AppUser appUser) {
        Optional<AppUser> dbUser = appUserRepository
                .findByEmail(appUser.getEmail());
        boolean userExists = dbUser.isPresent();

        if (userExists) {
            logger.info("User already exists, Going to login user : {} ", appUser.getEmail());
            boolean authenticated = securityService.autoLogin(appUser, appUser.getPassword());
            if (!authenticated){
                return new ResponseInfo(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), null, null);
            } else {
                return new ResponseInfo(LocalDateTime.now(), HttpStatus.OK.value(), null, HttpStatus.OK.getReasonPhrase(), null, appUser);
            }
        }

        String plainPassword = appUser.getPassword();
        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);
        securityService.autoLogin(appUser, plainPassword);
        logger.info("user signed up for userId : {}", appUser.getEmail());
        return new ResponseInfo(LocalDateTime.now(), HttpStatus.OK.value(), null, HttpStatus.OK.getReasonPhrase(), null, appUser);
    }

    public ResponseInfo signUp(SignUpRequest request) {
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
