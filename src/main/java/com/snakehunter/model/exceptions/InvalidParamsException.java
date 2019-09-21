package com.snakehunter.model.exceptions;

/**
 * @author WeiYi Yu
 * @date 2019-09-21
 */
public class InvalidParamsException
        extends Exception {

    private static final String ERROR_MESSAGE = "Invalid parameters passed.";

    public InvalidParamsException() {
        super(ERROR_MESSAGE);
    }
}
