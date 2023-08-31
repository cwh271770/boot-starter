package cn.swordsman.user.utils;

import cn.swordsman.user.entity.User;
import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 用户上下文
 *
 * @author caiwanghong
 * @date 2023/8/25 15:47
 * @version 1.0
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
