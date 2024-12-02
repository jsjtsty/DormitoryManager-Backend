package com.nulstudio.dormitory.exception;

import org.springframework.lang.NonNull;

/**
 * Exception for all fail responses in this application.
 * @author nullsty
 * @since 2.0
 */
public class NulException extends RuntimeException {

    /**
     * Error code.
     */
    protected final int code;

    /**
     * Create a new exception with code 0 and empty error message.
     */
    public NulException() {
        this.code = 0;
    }

    /**
     * Create a new exception with the specified code and empty error message.
     * @param code error code
     */
    public NulException(int code) {
        this.code = code;
    }

    /**
     * Create a new exception with the specified code and error message.
     * @param code error code
     * @param message error message, must be non-null
     */
    public NulException(int code, @NonNull String message) {
        super(message);
        this.code = code;
    }

    /**
     * Initialize an exception by exception constant.
     * @param exceptionConstants exception constant, must be non-null
     */
    public NulException(@NonNull NulExceptionConstants exceptionConstants) {
        super(exceptionConstants.getMessage());
        this.code = exceptionConstants.getCode();
    }

    /**
     * Initialize an exception by exception constant and message.
     * @param exceptionConstants exception constant, must be non-null
     * @param message error message, must be non-null
     */
    public NulException(@NonNull NulExceptionConstants exceptionConstants, @NonNull String message) {
        super(message);
        this.code = exceptionConstants.getCode();
    }

    /**
     * Get code of the exception.
     * @return code of the exception
     */
    public int getCode() {
        return code;
    }
}
