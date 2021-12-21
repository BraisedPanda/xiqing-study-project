package com.xiqing.project.redis.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XI.QING
 */
@Data
@ConfigurationProperties("xiqing.redis")
@Component
public class RedisProperties {

    private String mode = "standalone";
    // Redis单机
    private Standalone standalone;
    // Redis服务器连接密码（默认为空）
    private String password = "";
    // 连接超时时间（毫秒）,默认1000毫秒
    private long timeout = 1000;

    @Data
    public static class Standalone {
        // Redis数据库索引（默认为0）
        private int database = 0;
        // Redis服务器地址
        private String host = "localhost";
        // Redis服务器连接端口
        private int port = 6379;

    }

    // Redis集群
    private Cluster cluster;

    @Data
    public static class Cluster {
        // redis集群节点集合(单个元素格式："host:port")
        private List<String> nodes = new ArrayList<>();
        //redis集群最大重定向次数，默认为5
        private int maxRedirects = 5;
    }


    // Redis连接池
    private Pool pool;

    @Data
    public static class Pool {
        // redis连接池中的最大空闲连接,默认为10
        private int maxIdle = 10;
        // redis连接池中的最小空闲连接,默认为0
        private int minIdle = 0;
        // redis连接池最大连接数（使用负值表示没有限制）,默认200
        private int maxActive = 200;
        // redis连接池最大阻塞等待时间（使用负值表示没有限制）,默认-1
        private long maxWait = -1;
    }

}
