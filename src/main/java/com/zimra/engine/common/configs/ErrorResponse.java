package com.zimra.engine.common.configs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Data
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss", timezone = "Central Africa Time/Harare")
    private HttpStatus status;
    private Date timeStamp;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String message;
    private List<String> errors;

    public ErrorResponse(HttpStatus status, List<String> errors, String message, int httpStatusCode) {
        this.timeStamp = new Date();
        this.message = message;
        this.status = status;
        this.errors = errors;
        this.httpStatusCode = httpStatusCode;
    }


}