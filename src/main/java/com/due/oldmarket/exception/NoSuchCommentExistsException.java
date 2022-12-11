package com.due.oldmarket.exception;

public class NoSuchCommentExistsException extends RuntimeException{
    private String message;

    public NoSuchCommentExistsException() {
    }

    public NoSuchCommentExistsException(String message) {
        this.message = message;
    }
}
