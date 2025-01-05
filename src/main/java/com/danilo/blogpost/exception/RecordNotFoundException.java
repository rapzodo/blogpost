package com.danilo.blogpost.exception;

public class RecordNotFoundException extends Exception{
    public RecordNotFoundException(String commentNotFound) {
        super(commentNotFound);
    }
}
