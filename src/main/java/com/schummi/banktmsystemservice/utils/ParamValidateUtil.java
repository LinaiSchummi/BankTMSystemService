package com.schummi.banktmsystemservice.utils;

import com.schummi.banktmsystemservice.bean.TransactionErrorEnum;
import com.schummi.banktmsystemservice.exception.TransactionException;

import java.math.BigDecimal;

/**
 * 参数校验类
 *
 * @author jiahui
 * @since 2025-03-08
 */
public class ParamValidateUtil {
    /**
     * 用户id不能为空，
     *
     * @param userId 用户Id
     * @return 是否校验通过
     */
    public static void assertUserId(long userId) throws TransactionException {
        if (userId <= 0) {
            String errorInfo = "The userId is invalid.";
            throw new TransactionException(TransactionErrorEnum.PARAM_ERROR.getHttpCode(), errorInfo);
        }
    }

    public static void assertUserAmount(BigDecimal userAmount) throws TransactionException {
        if (userAmount.compareTo(BigDecimal.valueOf(100)) < 0 && (userAmount.compareTo(BigDecimal.valueOf(50000)) > 0)) {
            String errorInfo = "The userAmount is invalid.";
            throw new TransactionException(TransactionErrorEnum.PARAM_ERROR.getHttpCode(), errorInfo);
        }
    }
}
