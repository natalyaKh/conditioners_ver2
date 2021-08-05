package com.smilyk.cond.exceptions;

import org.springframework.http.HttpStatus;

/**
 * ObjectNotFoundException
 * This exception means that searching object not found in DB
 */
public class ObjectNotFoundException extends  RuntimeException{
    public ObjectNotFoundException(String message)
    {
        super(message);
    }
}
