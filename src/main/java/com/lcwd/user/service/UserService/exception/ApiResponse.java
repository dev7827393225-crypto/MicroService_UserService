package com.lcwd.user.service.UserService.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private boolean success;
    private HttpStatus status;
}
