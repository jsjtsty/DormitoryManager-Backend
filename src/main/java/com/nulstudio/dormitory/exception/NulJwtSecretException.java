package com.nulstudio.dormitory.exception;

/**
 * Exception for JWT Secret not found.
 * @author nullsty
 * @since 2.0
 */
public class NulJwtSecretException extends NulException {
    /**
     * Initialize a new {@code NulJwtSecretException}.
     */
    public NulJwtSecretException() {
        super(NulExceptionConstants.JWT_SECRET_MISSING);
    }
}
