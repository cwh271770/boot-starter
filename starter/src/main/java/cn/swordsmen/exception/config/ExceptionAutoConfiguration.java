package cn.swordsmen.exception.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 全局异常自动配置(扫描全局异常处理器所在包) [蔡旺鸿]
 *
 * @author caiwanghong
 * @version 1.0
 * @apiNote
 * @date 2023/8/23 16:51
 */
@Configuration
@ComponentScan("cn.swordsmen.exception")
public class ExceptionAutoConfiguration {

}
