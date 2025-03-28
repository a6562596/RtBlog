package com.rt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.rt.mapper")
public class RtBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(RtBlogApplication.class,args);
    }
}
