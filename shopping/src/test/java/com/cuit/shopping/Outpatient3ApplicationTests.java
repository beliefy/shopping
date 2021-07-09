package com.cuit.shopping;

import com.cuit.shopping.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class Outpatient3ApplicationTests {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private Admin admin;

    @Test
    void contextLoads() {
    }
    @Test
    void test(){
        redisTemplate.opsForValue().set( "sf", 2 );
        System.out.println( "数据已经添加到Redis数据库中，请确认！" );




    }
}
