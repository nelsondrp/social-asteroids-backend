package com.ndr.socialasteroids.infra.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ErrorDetails
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;

    private ErrorDetails()
    {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorDetails(HttpStatus status)
    {
        this();
        this.status = status;
    }

    public ErrorDetails(HttpStatus status, String message)
    {
        this(status);
        this.message = message;
    }
    
}
