package com.synalogik.wordtest.exception;

public class IOException extends RuntimeException{
    private final String message;
    public IOException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }


}