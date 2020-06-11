package com.imooc.mp2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.imooc.mp2.dao")
public class ImoocMp2Application {

    public static void main(String[] args) {
        SpringApplication.run(ImoocMp2Application.class, args);
    }

}
