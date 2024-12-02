package com.nulstudio.dormitory.common;

import com.nulstudio.dormitory.exception.NulException;
import com.nulstudio.dormitory.exception.NulExceptionConstants;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;

/**
 * Common result of this application. <br/>
 * Format: {@code { "version": 1, "code": [code], "message": "[message]", "result": [result] } }
 * @param <T> type of result, {@code void} if the object holds no result
 * @author nullsty
 * @since 2.0
 */
public final class NulResult<T> {

    /**
     * Current version of response.
     */
    private static final int NUL_RESULT_VERSION = 1;

    /**
     * Version of the response.
     */
    private final int version;

    /**
     * Code of the result, set the field to {@code 0} if operations are successfully done.
     */
    private final int code;

    /**
     * Message of the result, must not be null.
     */
    @NonNull
    private final String message;

    /**
     * Result of the result, can be null.
     */
    @Nullable
    private final T result;

    /**
     * Type of result, containing the paged query result.
     * @param data paged data entries to return to the user
     * @param total count of all objects to query
     * @param <T> type of data entry in {@code PageInfo}
     */
    public record PageInfoResult<T>(List<T> data, long total) {}

    /**
     * Initialize a new {@code NulResult}.
     * @param code code of the response
     * @param message message of the response, must not be null
     * @param result result of the response, can be null
     */
    private NulResult(int code, @NonNull String message, @Nullable T result) {
        this.version = NUL_RESULT_VERSION;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    /**
     * Initialize a new {@code NulResult}, with {@code code == 0} and {@code message == ""}.
     * @param result result of the response, can be null
     */
    private NulResult(@Nullable T result) {
        this.version = NUL_RESULT_VERSION;
        this.code = 0;
        this.message = "";
        this.result = result;
    }

    /**
     * Generate a non-null response with specified result.
     * @param result result of the response
     * @return a normal state response with the specified result
     * @param <T> type of the result
     */
    @NonNull
    public static <T> NulResult<T> response(T result) {
        return new NulResult<>(result);
    }

    /**
     * Generate a non-null empty response.
     * @return a normal state response with result {@code null}
     */
    @NonNull
    public static NulResult<Void> response() {
        return new NulResult<>(null);
    }

    /**
     * Generate a non-null response with error information and result.
     * @param code error code of the response
     * @param message error message of the response, must not be null
     * @param result result of the response
     * @return a response with error information and result
     * @param <T> type of result
     */
    @NonNull
    public static <T> NulResult<T> fail(int code, @NonNull String message, @Nullable T result) {
        return new NulResult<>(code, message, result);
    }

    /**
     * Generate a non-null response with error information.
     * @param code error code of the response
     * @param message error message of the response, must be non-null
     * @return a response with error information
     * @param <T> type of result
     */
    @NonNull
    public static <T> NulResult<T> fail(int code, @NonNull String message) {
        return new NulResult<>(code, message, null);
    }

    /**
     * Generate a non-null response with error information by specified exception.
     * @param exception a {@code NulException} object containing error information, must be non-null
     * @return a response with error information
     */
    @NonNull
    public static NulResult<Void> fail(@NonNull NulException exception) {
        return new NulResult<>(exception.getCode(), exception.getMessage(), null);
    }

    /**
     * Generate a non-null response with error information by error enumeration.
     * @param exceptionCode a {@code NulExceptionConstants} enumeration containing error code and message,
     *                      must be non-null
     * @return a response with error information
     */
    @NonNull
    public static NulResult<Void> fail(@NonNull NulExceptionConstants exceptionCode) {
        return new NulResult<>(exceptionCode.getCode(), exceptionCode.getMessage(), null);
    }

    /**
     * Generate a non-null response with error information by error enumeration and message.
     * @param exceptionCode a {@code NulExceptionConstants} enumeration containing error code, must be non-null
     * @param message error message, must be non-null
     * @return a response with error information
     */
    @NonNull
    public static NulResult<Void> fail(@NonNull NulExceptionConstants exceptionCode, @NonNull String message) {
        return new NulResult<>(exceptionCode.getCode(), message, null);
    }

    /**
     * Get version of the result.
     * @return version of the result
     */
    public int getVersion() {
        return version;
    }

    /**
     * Get code of the result.
     * @return code of the result
     */
    public int getCode() {
        return code;
    }

    /**
     * Get message of the result.
     * @return message of the result
     */
    @NonNull
    public String getMessage() {
        return message;
    }

    /**
     * Get the result of the object.
     * @return result of the object, can be null
     */
    @Nullable
    public T getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final NulResult<?> nulResult1 = (NulResult<?>) o;
        return getVersion() == nulResult1.getVersion() && getCode() == nulResult1.getCode()
                && Objects.equals(getMessage(), nulResult1.getMessage())
                && Objects.equals(getResult(), nulResult1.getResult());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVersion(), getCode(), getMessage(), getResult());
    }
}
