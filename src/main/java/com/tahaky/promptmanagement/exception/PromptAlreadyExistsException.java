package com.tahaky.promptmanagement.exception;

public class PromptAlreadyExistsException extends RuntimeException {
    
    public PromptAlreadyExistsException(String message) {
        super(message);
    }
}
