package com.cuit.shopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author 五组
 */
@EnableCaching
@SpringBootApplication
public class Outpatient3Application {

    public static void main(String[] args) {
        SpringApplication.run(Outpatient3Application.class, args);
    }

}
