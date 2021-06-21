package by.nyurush.blog.service.impl;

import by.nyurush.blog.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void addCode(String key, String value) {
        redisTemplate.opsForValue().set(key, value, 24, TimeUnit.HOURS);
    }

    @Override
    public boolean isValidCode(String key, String value) {
        return value.equals(redisTemplate.opsForValue().get(key));
    }

    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean isCodeExist(String code) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(code));
    }
}
