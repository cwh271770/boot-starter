package cn.swordsmen.lock.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;

/**
 * lock工具类
 *
 * @author caiwanghong
 * @date 2023/8/25 15:40
 * @version 1.0
 */
public abstract class LockUtil {
    private static final LockTemplate LOCK_TEMPLATE = SpringUtil.getBean(LockTemplate.class);

    public static LockInfo lock(String lockName) {
        return LOCK_TEMPLATE.lock(lockName, -1, 10000);
    }

    public static LockInfo lock(String lockName, long acquireTimeout) {
        return LOCK_TEMPLATE.lock(lockName, -1, acquireTimeout);
    }

    public static boolean unlock(LockInfo lock) {
        return LOCK_TEMPLATE.releaseLock(lock);
    }
}
