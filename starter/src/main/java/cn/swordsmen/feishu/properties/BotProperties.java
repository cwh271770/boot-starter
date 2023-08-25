package cn.swordsmen.feishu.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bot.feishu")
public class BotProperties {
    private String appId;

    private String appSecret;

    private String rootFolderToken;

    private String domain;
}
