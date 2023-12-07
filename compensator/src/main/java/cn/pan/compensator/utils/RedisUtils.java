package cn.pan.compensator.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisUtils<T> {

    private RedisTemplate redisTemplate;

    public RedisUtils(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public T getValue(String key) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        try {
            boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey) {
                T t = operations.get(key);
                return t;
            }
        } catch (Exception e) {
            log.info("connect redis error:{}", e);
            try {
                Thread.sleep(10);
                boolean hasKey = redisTemplate.hasKey(key);
                if (hasKey) {
                    T t = operations.get(key);
                    return t;
                }
            } catch (Exception e1) {
                log.error("connect redis error:{}", e1);
            }
        }
        return null;
    }

    public void setValue(String key, T t, int time, TimeUnit timeUnit) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, t, time, timeUnit);
        } catch (Exception e) {
            log.info("connect redis error:{}", e);
            try {
                Thread.sleep(10);
                operations.set(key, t, time, timeUnit);
            } catch (Exception e1) {
                log.error("connect redis error:{}", e1);
            }
        }
    }

    public void setValue(String key, T t) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, t);
        } catch (Exception e) {
            log.info("connect redis error:{}", e);
            try {
                operations.set(key, t);
            } catch (Exception e1) {
                log.error("connect redis error:{}", e1);
            }
        }
    }

    public boolean setNxValue(String key, T t) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        boolean result = false;
        try {
            result = operations.setIfAbsent(key, t);
        } catch (Exception e) {
            log.info("connect redis error:{}", e);
            try {
                result = operations.setIfAbsent(key, t);
            } catch (Exception e1) {
                log.error("connect redis error:{}", e1);
            }
        }
        return result;
    }

    public boolean hasKey(String key) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        try {
            boolean hasKey = redisTemplate.hasKey(key);
            return hasKey;
        } catch (Exception e) {
            log.info("connect redis error:{}", e);
            try {
                boolean hasKey = redisTemplate.hasKey(key);
                return hasKey;
            } catch (Exception e1) {
                log.error("connect redis error:{}", e1);
            }
        }
        return false;
    }

    //spring boot 2的delete方法有改动，和spring boot 1不兼容
//    public void delete(String key) {
//        try {
//            redisTemplate.delete(key);
//        } catch (Exception e) {
//            log.info("connect redis error:{}", e);
//            try {
//                redisTemplate.delete(key);
//            } catch (Exception e1) {
//                log.error("connect redis error:{}", e1);
//            }
//        }
//    }

    public void expire(String key, Long time) {
        try {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("connect redis error:{}", e);
        }
    }

    public boolean lock(String key, T t, int time, TimeUnit timeUnit) {
        Boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                Object obj = connection.execute("set", keySerializer.serialize(key), valueSerializer.serialize(t),
                        "NX".getBytes(StandardCharsets.UTF_8), "EX".getBytes(StandardCharsets.UTF_8),
                        String.valueOf(timeUnit.toSeconds(time)).getBytes(StandardCharsets.UTF_8));
                return obj != null;
            }
        });
        return result;
    }

    public Jedis getJedisObject() {
        RedisConnection redisConnection = redisTemplate.getConnectionFactory().getConnection();
        return (Jedis) redisConnection.getNativeConnection();
    }
}
