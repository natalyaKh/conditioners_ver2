package com.smilyk.cond.exceptions;

import org.springframework.http.HttpStatus;

/**
 * InvalidUserException class
 * This exception means that saving object exists in DB
 */
public class InvalidUserException extends  RuntimeException{
    public InvalidUserException(String message)
    {
        super(message);
    }
}

