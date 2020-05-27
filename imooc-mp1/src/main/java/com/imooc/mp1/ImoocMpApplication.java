package com.imooc.mp1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.imooc.mp1.dao")
@SpringBootApplication
public class ImoocMpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImoocMpApplication.class, args);
    }

}
