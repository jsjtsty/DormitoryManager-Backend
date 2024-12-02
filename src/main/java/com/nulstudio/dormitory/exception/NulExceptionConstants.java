package com.nulstudio.dormitory.exception;

import org.springframework.lang.NonNull;

/**
 * Enumeration for exception code and message.
 * @author nullsty
 * @since 2.0
 */
public enum NulExceptionConstants {
    INVALID_TOKEN(1, "Invalid Token"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    INVALID_EMAIL(1001, "Invalid email"),
    INVALID_PHONE(1002, "Invalid phone"),
    INVALID_MONEY(1003, "Invalid money syntax"),
    JWT_SECRET_MISSING(10002, "JWT Secret Missing"),
    ROLE_NOT_EXIST(10003, "Role does not exist"),
    USER_NOT_EXIST(10004, "User does not exist"),
    WRONG_PASSWORD(10005, "Wrong password"),
    INVALID_INVITE_CODE(10006, "Invalid invite code"),
    EXPIRED_INVITE_CODE(10007, "Invite code has expired"),
    INVITE_CODE_USED_UP(10008, "Invite code used up"),
    INVITE_CODE_BLOCKED(10009, "Invite code has been blocked"),
    ROLE_BLOCKED(10010, "Role blocked"),
    USER_ALREADY_EXIST(10011, "User already exist"),
    INVALID_GENDER(10012, "Invalid gender"),
    INVALID_EXPIRE_DATE(10013, "Invalid expire date"),
    DELETING_SELF(10014, "Deleting self is not allowed"),
    SUPPLIER_NOT_EXIST(10100, "Supplier does not exist"),
    WAREHOUSE_NOT_EXIST(10200, "Warehouse does not exist"),
    WAREHOUSE_INVALID_TYPE(10201, "Invalid warehouse type"),
    COMMODITY_NOT_EXIST(10300, "Commodity does not exist"),
    INVENTORY_NOT_EXIST(10400, "Inventory does not exist"),
    INVALID_QUANTITY(10401, "Invalid quantity")
    ;

    /**
     * Error code.
     */
    private final int code;

    /**
     * Error message, cannot be null.
     */
    @NonNull
    private final String message;

    /**
     * Inner initializer for this enumeration.
     * @param code error code
     * @param message error message, must not be null
     */
    NulExceptionConstants(int code, @NonNull String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Get error code.
     * @return error code
     */
    public int getCode() {
        return code;
    }

    /**
     * Get error message.
     * @return error message, not null.
     */
    @NonNull
    public String getMessage() {
        return message;
    }
}
