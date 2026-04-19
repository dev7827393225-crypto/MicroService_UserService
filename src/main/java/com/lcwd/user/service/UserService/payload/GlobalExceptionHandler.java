package com.lcwd.user.service.UserService.payload;

import com.lcwd.user.service.UserService.exception.ApiResponse;
import com.lcwd.user.service.UserService.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFound(ResourceNotFoundException ex) {
      String message= ex.getMessage();
       ApiResponse build = ApiResponse.builder()
               .message(message)
               .success(true)
               .status(HttpStatus.NOT_FOUND)
               .build();

       return new  ResponseEntity<ApiResponse>(build, HttpStatus.NOT_FOUND);
    }

}
