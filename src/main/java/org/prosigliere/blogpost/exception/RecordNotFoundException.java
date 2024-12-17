package org.prosigliere.blogpost.exception;

public class RecordNotFoundException extends Exception{
    public RecordNotFoundException(String commentNotFound) {
        super(commentNotFound);
    }
}
