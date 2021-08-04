package com.smilyk.cond.dto;

import com.smilyk.cond.enums.Roles;
import com.smilyk.cond.model.RoleEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

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
