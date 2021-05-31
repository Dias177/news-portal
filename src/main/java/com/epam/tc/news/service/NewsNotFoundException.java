package com.epam.tc.news.service;

public class NewsNotFoundException extends RuntimeException {
    public NewsNotFoundException() {
        super();
    }

    public NewsNotFoundException(String message) {
        super(message);
    }

    public NewsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
