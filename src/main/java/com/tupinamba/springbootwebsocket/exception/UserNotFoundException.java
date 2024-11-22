package com.tupinamba.springbootwebsocket.exception;
import com.tupinamba.springbootwebsocket.exception.UserNotFoundException;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}