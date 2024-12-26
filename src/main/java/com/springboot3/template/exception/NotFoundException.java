package com.springboot3.template.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    public NotFoundException(Object body) {
        super(body);
    }
}
