package com.who.shuk.JavaEmail.model;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Builder
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class HttpResponse {
    protected String timeStamp;
    protected int statusCode;
    protected HttpStatus httpStatus;
    protected String message;
    protected String developerMessage;
    protected String path;
    protected String requestedMethod;

    protected Map<?,?> data;
}
