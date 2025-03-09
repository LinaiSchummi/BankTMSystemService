package com.schummi.banktmsystemservice.service;

import com.schummi.banktmsystemservice.bean.UserTMInfoBean;
import com.schummi.banktmsystemservice.exception.TransactionException;

import java.util.List;

/**
 * Transaction service interface
 * 主要用于创建交易、删除交易、修改交易、查询交易
 *
 * @author jiahui
 * @since 2025-03-38
 */
public interface ITMService {
    /**
     * 添加交易
     *
     * @param userTMInfo 用户存款或者取款信息
     * @throws TransactionException TransactionException
     */
    void createTransaction(UserTMInfoBean userTMInfo) throws TransactionException;

    /**
     * 删除交易
     *
     * @param userId userId
     * @throws TransactionException TransactionException
     */
    void deleteTransaction(long userId) throws TransactionException;

    /**
     * 更新交易
     *
     * @param userTMInfo 最新的交易信息
     * @throws TransactionException TransactionException
     */
    void modifyTransaction(UserTMInfoBean userTMInfo) throws TransactionException;

    /**
     * 显示某个用户的交易列表
     *
     * @return 交易列表
     * @throws TransactionException TransactionException
     */
    List<UserTMInfoBean> queryTransactions(long userId) throws TransactionException;
}
