package com.fenix.signupservice.controller;

import com.fenix.signupservice.model.ResponseInfo;
import com.fenix.signupservice.model.SignUpRequest;
import com.fenix.signupservice.service.AppUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final AppUserService appUserService;

    @PostMapping(path = "/signup")
    public ResponseInfo register(@RequestBody SignUpRequest request, HttpServletResponse response) {
        logger.info("Going to signup for user : {}", request.getEmail());
        ResponseInfo responseInfo = appUserService.signUp(request);
        response.setStatus(responseInfo.getStatus());
        return responseInfo;
    }

    @GetMapping(path = "users/{userId}")
    @PreAuthorize("authentication.principal.username == #userId")
    public ResponseInfo get(@PathVariable("userId") String userId, HttpServletResponse response){
        logger.info("Going to get details for user : {}", userId);
        ResponseInfo responseInfo = appUserService.getUser(userId);
        response.setStatus(responseInfo.getStatus());
        return responseInfo;
    }
}
