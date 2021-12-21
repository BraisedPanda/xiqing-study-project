package com.xiqing.project.redis.config;

import com.xiqing.project.redis.properties.RedisProperties;
import com.xiqing.project.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("com.xiqing.project.redis.properties")
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedisUtil redisUtil(RedisProperties redisProperties){
        return new RedisUtil(redisProperties);
    }


}
