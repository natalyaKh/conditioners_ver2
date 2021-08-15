package com.smilyk.dto;

import lombok.*;

import javax.validation.constraints.Email;

/**
 * ResponseUserDto class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UpdateUserDto {
    private String userUuid;
    private String firstName;
    private String secondName;
    @Email(message = "email should be valid")
    private String userEmail;

}
