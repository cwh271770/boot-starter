package cn.swordsmen.user.utils;

import cn.hutool.core.util.StrUtil;
import cn.swordsmen.user.entity.User;

/**
 * 用户工具类 [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/8/22 15:58
 */
public abstract class UserUtil {
    /***
     * 获取当前用户信息 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param
     * @return User
     * @date 2023/8/22 16:01
     */
    public static User currentUser() {
        return UserContext.get();
    }

    /***
     * 设置当前用户 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param currentUser
     * @return void
     * @date 2023/8/22 16:15
     */
    public static void setCurrentUser(User currentUser) {
        UserContext.set(currentUser);
    }

    /***
     * 根据key值获取用户扩展属性 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param key
     * @return Object
     * @date 2023/8/22 16:03
     */
    public static Object getProperty(String key) {
        return currentUser().getProperties().get(key);
    }

    /***
     * 构建系统用户信息 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param
     * @return User
     * @date 2023/8/22 16:07
     */
    public static User buildSystemUser() {
        return User.builder().userCode("system").userId("system").userName("system")
            .userRole("system").build();
    }

    /***
     * 根据输入构建默认用户信息 [蔡旺鸿]
     *
     * @author caiwanghong
     * @apiNote
     * @param text
     * @return User
     * @date 2023/8/22 16:07
     */
    public static User buildDefaultUser(String text) {
        if(StrUtil.isBlank(text)) {
            return buildSystemUser();
        }

        return User.builder().userCode(text).userId(text).userName(text)
            .userRole(text).build();
    }


}
