package cn.swordsmen.user.utils;

import cn.swordsmen.user.entity.User;
import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 用户上下文 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/8/22 14:39
 */
public class UserContext {
    private static class UserHolder {
        private static final TransmittableThreadLocal<User> USER_HOLDER = new TransmittableThreadLocal<>();
    }

    public static void set(User user) {
        UserHolder.USER_HOLDER.set(user);
    }

    public static User get() {
        return UserHolder.USER_HOLDER.get();
    }

    public static void clear() {
        UserHolder.USER_HOLDER.remove();
    }
}
