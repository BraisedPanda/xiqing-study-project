package com.xiqing.project.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiqing.project.redis.properties.RedisProperties;
import lombok.Getter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * Redis初始化器
 * @author : XI.QING 
 * @date : 2021/12/21       
 */
public class RedisInitializer {

    @Getter
    private RedisProperties redisProperties;

    /**
     * 自定义Redis序列化方式
     * @param :
     * @author : XI.QING
     * @date : 2021/12/20
     */
    public RedisTemplate<String, Object> initRedisTemplate(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(createConnectionFactory());
        setRedisSerializer(template);

        return template;
    }
    /**
     * 自定义序列化方式
     * @param template:
     * @author : XI.QING
     * @date : 2021/12/20
     */
    public void setRedisSerializer(RedisTemplate<String, Object> template){
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
    }

    /**
     * Redis工程类
     * @param :
     * @author : XI.QING
     * @date : 2021/12/20
     */
    public RedisConnectionFactory createConnectionFactory() {

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(redisProperties.getTimeout()))
                .poolConfig(setRedisConfig())
                .build();

        if ("standalone".equals(redisProperties.getMode())) {
            // 单机版配置
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setDatabase(redisProperties.getStandalone().getDatabase());
            redisStandaloneConfiguration.setHostName(redisProperties.getStandalone().getHost());
            redisStandaloneConfiguration.setPort(redisProperties.getStandalone().getPort());
            redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));

            LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
            factory.afterPropertiesSet();
            return factory;
        } else {
            // 集群版配置
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
            Set<RedisNode> nodes = redisProperties.getCluster().getNodes().stream()
                    .map(s -> {
                        String[] ipAndPort = s.split(":");
                        return new RedisNode(ipAndPort[0].trim(), Integer.parseInt(ipAndPort[1]));
                    }).collect(Collectors.toSet());
            redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
            redisClusterConfiguration.setClusterNodes(nodes);
            redisClusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());

            LettuceConnectionFactory factory = new LettuceConnectionFactory(redisClusterConfiguration, clientConfig);
            factory.afterPropertiesSet();
            return factory;
        }
    }
    /**
     * 配置连接参数
     * @param :
     * @author : XI.QING
     * @date : 2021/12/20
     */
    public GenericObjectPoolConfig<?> setRedisConfig() {
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxIdle(redisProperties.getPool().getMaxIdle());
        genericObjectPoolConfig.setMinIdle(redisProperties.getPool().getMinIdle());
        genericObjectPoolConfig.setMaxTotal(redisProperties.getPool().getMaxActive());
        genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
        return genericObjectPoolConfig;
    }


}
