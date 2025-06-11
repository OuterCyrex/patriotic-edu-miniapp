package cn.org.shelly.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("cn.org.shelly.edu.mapper")
@EnableAsync
public class EduApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(EduApplication.class, args);
    }
}
