package cn.swordsman.feishu.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 飞书机器人配置类
 *
 * @author caiwanghong
 * @date 2023/8/25 15:31
 * @version 1.0
 */
@Data
@ConfigurationProperties(prefix = "bot.feishu")
public class BotProperties {
    /**
     * 应用id
     */
    private String appId;

    /**
     * 应用秘钥
     */
    private String appSecret;

    /**
     * 文件夹token
     */
    private String rootFolderToken;

    /**
     * 飞书请求地址域名（如：https://open.feishu.com）
     */
    private String domain;
}
