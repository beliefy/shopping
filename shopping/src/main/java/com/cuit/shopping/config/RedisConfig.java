package com.cuit.shopping.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.text.SimpleDateFormat;

/**
 * 定义RedisConfig配置类实现JSON格式自定义
 */
@Configuration
public class RedisConfig
{
    @Bean
    public RedisTemplate <String, Object> redisTemplate(LettuceConnectionFactory factory)
    {
        RedisTemplate <String, Object> redisTemplate               = new RedisTemplate <>();
        Jackson2JsonRedisSerializer    jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer( Object.class );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ) );
        jackson2JsonRedisSerializer.setObjectMapper( objectMapper );

        redisTemplate.setKeySerializer( new StringRedisSerializer() );
        redisTemplate.setValueSerializer( jackson2JsonRedisSerializer );
        redisTemplate.setConnectionFactory( factory );
        return redisTemplate;
    }
}

