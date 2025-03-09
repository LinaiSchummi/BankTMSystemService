package com.schummi.banktmsystemservice.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户交易信息
 *
 * @author jiahui
 * @since 2025-03-08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTMInfoBean {
    /**
     * 用户Id，具有唯一性
     */
    private long userId;
    /**
     * 用户存款和取款,每次交易最少允许100最多允许50000
     */
    private BigDecimal userAmount;
    /**
     * 用户交易时间,单位为毫秒
     */
    private long userTransactionTime;
    /**
     * 用户交易业务是否为存款
     */
    private String isSaveMoney;
}
