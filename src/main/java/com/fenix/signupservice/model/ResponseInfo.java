package com.fenix.signupservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResponseInfo<T extends UserDetails> {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private T responsePayload;
}
