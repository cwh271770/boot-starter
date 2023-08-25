package cn.test.startertest;

import cn.swordsmen.exception.annotation.EnableGlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableGlobalExceptionHandler
@SpringBootApplication
public class StarterTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterTestApplication.class, args);
    }

}
