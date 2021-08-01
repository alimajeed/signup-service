package com.fenix.signupservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfig {

    public static String USER_NOT_FOUND_MSG;
    public static String BAD_CREDENTIALS_CODE;

    @Value("${user.notfound}")
    public void setUserNotFoundMsg(String userNotFoundMsg){
        USER_NOT_FOUND_MSG = userNotFoundMsg;
    }

    @Value("${user.badcreds}")
    public void setBadCredentialsCode(String badCredentialsCode){
        BAD_CREDENTIALS_CODE = badCredentialsCode;
    }
}
