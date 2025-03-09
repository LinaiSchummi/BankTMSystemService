package com.schummi.banktmsystemservice.mapper;

import com.schummi.banktmsystemservice.bean.TransactionErrorEnum;
import com.schummi.banktmsystemservice.bean.UserTMInfoBean;
import com.schummi.banktmsystemservice.exception.TransactionException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户交易数据存储，题目要求用缓存即可，无需持久化存储
 *
 * @author jiahui
 * @since 2024-03-08
 */
@Component
public class TransactionMapper implements ITransactionMapper {
    // 用户交易记录缓存map
    private final Map<Long, List<UserTMInfoBean>> transactions = new ConcurrentHashMap<>();
    // 每个用户记录的上线，当前给到10000
    private static final int MAX_USER_TM_INFO_RECORD = 10000;

    @Override
    public void saveUserTMInfo(UserTMInfoBean userTMInfo) throws TransactionException {
        if (transactions.containsKey(userTMInfo.getUserId())) {
            String errorInfo = "The userId is exist, can not to save.";
            throw new TransactionException(TransactionErrorEnum.PARAM_ERROR.getHttpCode(), errorInfo);
        }
        List<UserTMInfoBean> userInfoList = new ArrayList<>();
        userInfoList.add(userTMInfo);
        transactions.put(userTMInfo.getUserId(), userInfoList);
    }

    @Override
    public void deleteUserTMInfoByUserId(long userId) throws TransactionException {
        if (!transactions.containsKey(userId)) {
            String errorInfo = "The userId is not found";
            throw new TransactionException(TransactionErrorEnum.NOT_FOUND_ERROR.getHttpCode(), errorInfo);
        }
        transactions.remove(userId);
    }

    @Override
    public void updateUserTMInfoByUserId(UserTMInfoBean userTMInfo) throws TransactionException {
        if (!transactions.containsKey(userTMInfo.getUserId())) {
            String errorInfo = "The userId is not found";
            throw new TransactionException(TransactionErrorEnum.NOT_FOUND_ERROR.getHttpCode(), errorInfo);
        }
        if (transactions.size() >= MAX_USER_TM_INFO_RECORD) {
            String errorInfo = "The transaction records of user has reached the upper limit " + MAX_USER_TM_INFO_RECORD + " .";
            throw new TransactionException(TransactionErrorEnum.PARAM_ERROR.getHttpCode(), errorInfo);
        }
        List<UserTMInfoBean> userInfoList = transactions.get(userTMInfo.getUserId());
        userInfoList.add(userTMInfo);
        transactions.put(userTMInfo.getUserId(), userInfoList);
    }

    @Override
    public List<UserTMInfoBean> queryUserTMInfoByUserId(long userId) {
        return transactions.getOrDefault(userId, new ArrayList<>());
    }
}
