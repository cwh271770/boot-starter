package cn.swordsmen.exception.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 全局异常自动配置(扫描全局异常处理器所在包)
 *
 * @author caiwanghong
 * @date 2023/8/25 14:14
 * @version 1.0
 */
@Configuration
@ComponentScan("cn.swordsmen.exception")
public class ExceptionAutoConfiguration {

}
