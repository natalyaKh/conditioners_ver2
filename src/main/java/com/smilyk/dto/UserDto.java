package com.smilyk.dto;

import com.smilyk.enums.Roles;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * UserDto class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {

    private String userUuid;
    @NotNull(message = "first name can not be empty")
    private String firstName;
    @NotNull(message = "second name can not be empty")
    private String secondName;
    @Email(message = "email should be valid")
    @NotNull(message = "email of user can not be empty")
    private String userEmail;
    @Min(value = 2, message = "password should contains minimum 3 symbol")
    @NotNull(message = "password can not be empty")
    private String password;
    @NotNull(message = "field Roles can not be empty")
    private Roles roles;
}
