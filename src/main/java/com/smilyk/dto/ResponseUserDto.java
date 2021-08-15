package com.smilyk.dto;

import lombok.*;

import java.util.List;

/**
 * ResponseUserDto class
 */
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
    private List<String> roles;
}
