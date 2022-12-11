package com.due.oldmarket.exception;

public class ProductAlreadyExistsException  extends RuntimeException{
    private String message;
    public ProductAlreadyExistsException() {}

    public ProductAlreadyExistsException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}
