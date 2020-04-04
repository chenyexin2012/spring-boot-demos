package com.holmes.springboot.dubbo.exception;

public class NullInputException extends Exception{

    private final static String MESSAGE = "input is null";

    public NullInputException() {
        super(MESSAGE);
    }
    public NullInputException(String message) {
        super(message);
    }
}
