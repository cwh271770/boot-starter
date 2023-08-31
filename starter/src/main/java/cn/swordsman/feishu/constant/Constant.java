package cn.swordsman.feishu.constant;

/**
 * 飞书机器人常量类
 *
 * @author caiwanghong
 * @date 2023/8/25 15:23
 * @version 1.0
 */
public abstract class Constant {
    public interface Redis_Key {
        /**
         * file文件夹下文件数量统计key
         */
        String CLOUD_FILE_COUNT = "cloud_folder:file_count";

        /**
         * file文件夹-key
         */
        String CLOUD_FOLDER_FILE = "cloud_folder:file_folder";

        /**
         * 月文件夹-key
         */
        String CLOUD_FOLDER_MONTH = "cloud_folder:month_folder";

        /**
         * redis-key文件夹token前缀
         */
        String LOCK_CLOUD_FOLDER_PREFIX = "cloud_folder:lock:";
    }

    public interface Feishu_Card_Template {
        /**
         * 分享消息卡片模板
         */
        String SHARE_TEMPLATE = "{\"config\":{\"wide_screen_mode\":true},\"header\":{\"template\":"
                + "\"red\",\"title\":{\"content\":\"CRM问题报告\",\"tag\":\"plain_text\"}},\"elements\":[{\"tag\":\"markdown\","
                + "\"content\":\"您被分享了一个CRM问题跟进文档，[点此查看详情](%s)，%s。\"}]}";

        /**
         * @我消息卡片模板
         */
        String AT_USER_TEMPLATE = "{\"config\":{\"wide_screen_mode\":true},\"header\":{\"template\":"
                + "\"blue\",\"title\":{\"content\":\"%s@了你\",\"tag\":\"plain_text\"}},\"elements\":[{\"tag\":\"markdown\","
                + "\"content\":\"您收到一个CRM问题跟进文档，[点此查看详情](%s)，%s。\"}]}";
    }
}
