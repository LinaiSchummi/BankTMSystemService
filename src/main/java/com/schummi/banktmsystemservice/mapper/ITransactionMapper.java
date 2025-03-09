package com.schummi.banktmsystemservice.mapper;

import com.schummi.banktmsystemservice.bean.UserTMInfoBean;
import com.schummi.banktmsystemservice.exception.TransactionException;

import java.util.List;

/**
 * 交易数据库操作
 *
 * @author jiahui
 * @since 2025-03-08
 */
public interface ITransactionMapper {
    /**
     * 保存用户交易信息到数据库
     *
     * @param userTMInfo userTMInfo
     */
    void saveUserTMInfo(UserTMInfoBean userTMInfo) throws TransactionException;

    /**
     * 删除用户交易信息到数据库
     *
     * @param userId 用户id
     */
    void deleteUserTMInfoByUserId(long userId) throws TransactionException;

    /**
     * 更新用户信息到数据库
     *
     * @param userTMInfo 用户交易信息
     */
    void updateUserTMInfoByUserId(UserTMInfoBean userTMInfo) throws TransactionException;

    /**
     * 查询用户交易列表
     *
     * @param userId 用户id
     * @return 用户列表
     */
    List<UserTMInfoBean> queryUserTMInfoByUserId(long userId);
}
