package cn.org.shelly.edu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.org.shelly.edu.mapper")
public class EduApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(EduApplication.class, args);
    }
}
