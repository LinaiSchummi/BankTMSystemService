package com.schummi.banktmsystemservice.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程安全，锁处理类
 *
 * @author jiahui
 * @since 2025-03-08
 */
public class LockUtils {
    private static final long LOCK_TIME_OUT = 10000;
    /**
     * 锁过期时间
     */
    private static final ConcurrentHashMap<String, Long> lockExpireTimeMap = new ConcurrentHashMap<>();

    /**
     * 获取锁
     *
     * @param key     key
     * @return 是否成功获取锁
     */
    public static boolean isObtainLock(String key) {
        long currentTime = System.currentTimeMillis();
        Long previousExpireTime = lockExpireTimeMap.putIfAbsent(key, currentTime + LOCK_TIME_OUT);
        if (previousExpireTime == null) {
            return true;
        }
        if (currentTime > previousExpireTime) {
            synchronized (LockUtils.class) {
                Long latestExpireTime = lockExpireTimeMap.get(key);
                if (latestExpireTime != null && currentTime > latestExpireTime) {
                    lockExpireTimeMap.put(key, currentTime + LOCK_TIME_OUT);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Release the lock.
     *
     * @param key The key of the lock.
     */
    public static void unlock(String key) {
        lockExpireTimeMap.remove(key);
    }
}
