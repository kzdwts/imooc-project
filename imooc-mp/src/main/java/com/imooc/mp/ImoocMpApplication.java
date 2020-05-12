package com.imooc.mp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.imooc.mp.dao")
@SpringBootApplication
public class ImoocMpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImoocMpApplication.class, args);
    }

}
