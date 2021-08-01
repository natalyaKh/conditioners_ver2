package com.smilyk.cond.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidUserException extends  RuntimeException{
    public InvalidUserException(String message)
    {
        super(message);
    }
}

