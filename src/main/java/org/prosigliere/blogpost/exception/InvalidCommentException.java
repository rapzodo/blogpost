package org.prosigliere.blogpost.exception;

public class InvalidCommentException extends RuntimeException {
    public InvalidCommentException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
