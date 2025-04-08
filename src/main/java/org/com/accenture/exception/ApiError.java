package org.com.accenture.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ApiError {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private List<String> details;

    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.details = new ArrayList<>();
    }

    public void addDetail(String defaultMessage) {
    }
}