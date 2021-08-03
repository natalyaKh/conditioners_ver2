package com.smilyk.cond.dto;

import lombok.*;

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
