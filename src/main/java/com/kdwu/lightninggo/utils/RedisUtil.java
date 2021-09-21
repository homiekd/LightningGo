package com.kdwu.lightninggo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis工具類別
 */
@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 向redis 存值
     * @param key   主鍵
     * @param value 值
     * @return 是否儲存成功
     */
    public boolean setValue(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("redis設定緩存時發生異常---> ", e.getMessage());
            return false;
        }
    }

    /**
     * 向redis 存值，並設定過期時間
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setValueTime(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
            } else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("redis設定緩存並設置時間時發生異常---> ", e.getMessage());
            return false;
        }
    }

    /**
     * 根據key獲取redis中的值
     * @param key
     * @return
     */
    public Object getValue(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 根據keys 刪除redis中的緩存
     * @param keys
     */
    public void deleteKey(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                for (int i = 0; i < keys.length; i++) {
                    redisTemplate.delete(keys[i]);
                }
            }
        }
    }

    /**
     * 判斷值是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis值不存在---> ", e.getMessage());
            return false;
        }
    }

    /**
     * 獲取redis主鍵的過期時間
     * 0: 表示永久有效
     * 大於0: 剩下多少分鐘失效
     * @param key
     * @return
     */
    public Long isExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.MINUTES);
    }

    /**
     * 給key加過期時間
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.MINUTES);
            }
            return true;
        } catch (Exception e) {
            log.error("緩存設置新的過期時間異常--> ", e.getMessage());
            return false;
        }


    }
}
