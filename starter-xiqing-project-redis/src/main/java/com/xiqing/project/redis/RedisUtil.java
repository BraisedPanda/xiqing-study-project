package com.xiqing.project.redis;


import com.xiqing.project.redis.properties.RedisProperties;

import lombok.Getter;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Component;


import java.util.List;

import java.util.concurrent.TimeUnit;

/**
 * RedisUtil工具类
 * @author : XI.QING
 * @date : 2021/11/9
 */
@Component
@SuppressWarnings("unused")
public class RedisUtil {


    @Getter
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisProperties redisProperties) {
        RedisInitializer redisInitializer = new RedisInitializer();
        this.redisTemplate = redisInitializer.initRedisTemplate(redisProperties);
    }

    /**
     * 设置过期时间
     *
     * @param key:  key
     * @param time: 秒数
     * @author : XI.QING
     * @date : 2021/11/9
     */
    public boolean setExpireTime(String key, Long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取key的过期时间
     *
     * @param key: key
     * @author : XI.QING
     * @date : 2021/11/9
     */
    public Long getExpireTime(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断Redis中是否有Key
     *
     * @param key: key
     * @author : XI.QING
     * @date : 2021/11/9
     */
    public boolean hasKey(String key) {
        try {
            redisTemplate.hasKey(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key: 单个Key
     * @author : XI.QING
     * @date : 2021/11/9
     */
    public boolean deleteKey(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param keyList: KeyList
     * @author : XI.QING
     * @date : 2021/11/9
     */
    public boolean deleteKey(List<String> keyList) {
        try {
            redisTemplate.delete(keyList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================String=============================

    /**
     * 获取缓存
     *
     * @param key: key
     * @author : XI.QING
     * @date : 2021/11/9
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置缓存
     *
     * @param key:   key
     * @param value: value
     * @author : XI.QING
     * @date : 2021/11/9
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置缓存以及过期时间
     *
     * @param key:   key
     * @param value: value
     * @author : XI.QING
     * @date : 2021/11/9
     */
    public boolean set(String key, Object value, Long time) {
        try {
            if (time > 0L) {
                this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                this.redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
