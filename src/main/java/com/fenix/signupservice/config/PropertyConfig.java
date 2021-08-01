package com.fenix.signupservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfig {

    public static String USER_NOT_FOUND_MSG;

    @Value("${user.notfound}")
    public void setUserNotFoundMsg(String userNotFoundMsg){
        USER_NOT_FOUND_MSG = userNotFoundMsg;
    }

}
