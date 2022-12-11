package com.due.oldmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NoSuchProductExistsException extends RuntimeException {
    private String message;

    public NoSuchProductExistsException() {
    }

    public NoSuchProductExistsException(String msg) {
        super(msg);
        this.message = msg;
    }
}
