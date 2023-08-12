package com.lyq.activiti7Service;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-02 21:31
 * @version: 1.0
 */
@SpringBootApplication
@EnableSwagger2Doc
@MapperScan("com.lyq.activiti7Service.mapper")
public class Activiti7ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(Activiti7ServiceApplication.class, args);
    }
}
