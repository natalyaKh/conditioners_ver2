package com.smilyk.cond.dto;

import lombok.*;

/**
 * ResponseDeleteBlockedUserDto class
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseDeleteBlockedUserDto {
    private String firstName;
    private String secondName;
}
