package com.smilyk.cond.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorDto {
    String error;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime date;
    int status;
}
