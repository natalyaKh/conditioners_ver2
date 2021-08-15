package com.smilyk.security.modelAndDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class LoginRequestDto {
    private String userEmail;
    private String password;
}

