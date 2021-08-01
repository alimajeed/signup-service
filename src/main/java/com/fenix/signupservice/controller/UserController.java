package com.fenix.signupservice.controller;

import com.fenix.signupservice.model.SignUpRequest;
import com.fenix.signupservice.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {

    private final AppUserService appUserService;

    @PostMapping(path = "/signup")
    public UserDetails register(@RequestBody SignUpRequest request) {
        return appUserService.signUp(request);
    }

    @GetMapping(path = "users/{userId}")
    @PreAuthorize("authentication.principal.username == #userId")
    public UserDetails get(@PathVariable("userId") String userId){
        return appUserService.loadUserByUsername(userId);
    }
}
