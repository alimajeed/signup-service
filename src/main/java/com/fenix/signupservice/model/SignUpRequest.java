package com.fenix.signupservice.model;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignUpRequest {
    private final String firstName;
    private final String lastName;
    @NonNull
    private final String email;
    @NonNull
    private final String password;
}
