package cn.swordsman.user.utils;

import cn.hutool.core.util.StrUtil;
import cn.swordsman.user.entity.User;

/**
 * 用户工具类
 *
 * @author caiwanghong
 * @date 2023/8/25 15:45
 * @version 1.0
 */
public abstract class UserUtil {
    /**
     * 获取当前用户信息
     *
     * @author caiwanghong
     * @date 2023/8/25 15:45
     * @return User
     */
    public static User currentUser() {
        return UserContext.get();
    }

    /**
     * 设置当前用户
     *
     * @author caiwanghong
     * @param currentUser 当前用户信息
     * @date 2023/8/25 15:45
     */
    public static void setCurrentUser(User currentUser) {
        UserContext.set(currentUser);
    }

    /**
     * 根据key值获取用户扩展属性
     *
     * @author caiwanghong
     * @param key 属性键值
     * @date 2023/8/25 15:46
     * @return Object
     */
    public static Object getProperty(String key) {
        return currentUser().getProperties().get(key);
    }

    /**
     * 构建系统用户信息
     *
     * @author caiwanghong
     * @date 2023/8/25 15:46
     * @return User
     */
    public static User buildSystemUser() {
        return User.builder().userCode("system").userId("system").userName("system")
            .userRole("system").build();
    }

    /**
     * 根据输入构建默认用户信息
     *
     * @author caiwanghong
     * @param text 输入内容
     * @date 2023/8/25 15:46
     * @return User
     */
    public static User buildDefaultUser(String text) {
        if(StrUtil.isBlank(text)) {
            return buildSystemUser();
        }

        return User.builder().userCode(text).userId(text).userName(text)
            .userRole(text).build();
    }


}
