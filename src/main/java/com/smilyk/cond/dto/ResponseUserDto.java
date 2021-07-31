package com.smilyk.cond.dto;

import com.smilyk.cond.enums.Roles;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseUserDto {

    private String userUuid;
    private String firstName;
    private String secondName;
    private String roles;
}
