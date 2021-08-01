package com.smilyk.cond.exceptions;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends  RuntimeException{
    public ObjectNotFoundException(String message)
    {
        super(message);
    }
}
