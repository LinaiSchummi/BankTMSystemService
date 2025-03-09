package com.schummi.banktmsystemservice.exception;

import com.schummi.banktmsystemservice.bean.TransactionErrorEnum;
import lombok.Getter;

/**
 * 交易异常
 *
 * @author jiahui
 * @since 2025-03-08
 */
public class TransactionException extends Exception {
    @Getter
    private int httpCode;

    @Getter
    private String errorInfo;

    /**
     * 交易异常
     *
     * @param httpCode  错误码
     * @param errorInfo 错误信息
     */
    public TransactionException(int httpCode, String errorInfo) {
        this.httpCode = httpCode;
        this.errorInfo = errorInfo;
    }
}
