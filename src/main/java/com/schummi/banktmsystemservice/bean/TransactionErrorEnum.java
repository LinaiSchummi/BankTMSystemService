package com.schummi.banktmsystemservice.bean;

import lombok.Getter;

/**
 * 交易异常错误码的枚举
 *
 * @author jiahui
 * @since 2025-03-08
 */
public enum TransactionErrorEnum {
    PARAM_ERROR(400, "CLIENT PARAM ERROR"),
    NOT_FOUND_ERROR(404, "NOT FOUND ERROR"),
    INTERNAL_ERROR(500, "SERVICE INTERNAL ERROR");

    @Getter
    private final int httpCode;

    @Getter
    private final String errorInfo;

    TransactionErrorEnum(int httpCode, String errorInfo) {
        this.httpCode = httpCode;
        this.errorInfo = errorInfo;
    }
}
