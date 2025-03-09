package com.schummi.banktmsystemservice.service;


import com.schummi.banktmsystemservice.bean.TransactionErrorEnum;
import com.schummi.banktmsystemservice.bean.UserTMInfoBean;
import com.schummi.banktmsystemservice.exception.TransactionException;
import com.schummi.banktmsystemservice.mapper.ITransactionMapper;
import com.schummi.banktmsystemservice.utils.LockUtils;
import com.schummi.banktmsystemservice.utils.ParamValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 银行交易系统的实现类
 *
 * @author jiahui
 * @since 2025-03-08
 */
@Component
public class TMService implements ITMService {
    @Autowired
    private ITransactionMapper transactionMapper;

    @Override
    public void createTransaction(UserTMInfoBean userTMInfo) throws TransactionException {
        ParamValidateUtil.assertUserId(userTMInfo.getUserId());
        ParamValidateUtil.assertUserAmount(userTMInfo.getUserAmount());
        boolean isObtainLock = LockUtils.isObtainLock(String.valueOf(userTMInfo.getUserId()));
        if (!isObtainLock) {
            throw new TransactionException(TransactionErrorEnum.INTERNAL_ERROR.getHttpCode(), "The transaction has an exception.");
        }
        try {
            transactionMapper.saveUserTMInfo(userTMInfo);
        } finally {
            LockUtils.unlock(String.valueOf(userTMInfo.getUserId()));
        }
    }

    @Override
    public void deleteTransaction(long userId) throws TransactionException {
        ParamValidateUtil.assertUserId(userId);
        try {
            boolean isObtainLock = LockUtils.isObtainLock(String.valueOf(userId));
            if (!isObtainLock) {
                throw new TransactionException(TransactionErrorEnum.INTERNAL_ERROR.getHttpCode(), "The transaction has an exception.");
            }
            transactionMapper.deleteUserTMInfoByUserId(userId);
        } finally {
            LockUtils.unlock(String.valueOf(userId));
        }
    }

    @Override
    public void modifyTransaction(UserTMInfoBean userTMInfo) throws TransactionException {
        ParamValidateUtil.assertUserId(userTMInfo.getUserId());
        ParamValidateUtil.assertUserAmount(userTMInfo.getUserAmount());
        try {
            boolean isObtainLock = LockUtils.isObtainLock(String.valueOf(userTMInfo.getUserId()));
            if (!isObtainLock) {
                throw new TransactionException(TransactionErrorEnum.INTERNAL_ERROR.getHttpCode(), "The transaction has an exception.");
            }
            transactionMapper.updateUserTMInfoByUserId(userTMInfo);
        } finally {
            LockUtils.unlock(String.valueOf(userTMInfo.getUserId()));
        }
    }

    @Override
    public List<UserTMInfoBean> queryTransactions(long userId) throws TransactionException {
        ParamValidateUtil.assertUserId(userId);
        try {
            boolean isObtainLock = LockUtils.isObtainLock(String.valueOf(userId));
            if (!isObtainLock) {
                throw new TransactionException(TransactionErrorEnum.INTERNAL_ERROR.getHttpCode(), "The transaction has an exception.");
            }
            return transactionMapper.queryUserTMInfoByUserId(userId);
        } finally {
            LockUtils.unlock(String.valueOf(userId));
        }
    }
}
