package com.lcwd.user.service.UserService.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource not found of server");
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
