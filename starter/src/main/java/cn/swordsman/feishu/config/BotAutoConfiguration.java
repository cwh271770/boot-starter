package cn.swordsman.feishu.config;

import cn.swordsman.feishu.properties.BotProperties;
import cn.swordsman.feishu.service.BotProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 飞书机器人自动装配
 *
 * @author caiwanghong
 * @date 2023/8/25 15:22
 * @version 1.0
 */
@AutoConfiguration
@ConditionalOnClass(BotProxy.class)
@EnableConfigurationProperties(value = BotProperties.class)
public class BotAutoConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean(BotProxy.class)
    @ConditionalOnBean(StringRedisTemplate.class)
    public BotProxy botProxy() {
        return applicationContext.getBean(BotProxy.class);
    }
}
