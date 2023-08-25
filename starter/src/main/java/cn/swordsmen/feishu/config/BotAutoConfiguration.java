package cn.swordsmen.feishu.config;

import cn.swordsmen.feishu.properties.BotProperties;
import cn.swordsmen.feishu.service.BotProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

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