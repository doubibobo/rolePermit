package com.example.redisUtils;

import com.example.configuration.RedisConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class MainRedisUtil {
    private static Logger logger = LoggerFactory.getLogger(MainRedisUtil.class);

    public static String getValue(String key) {
        Jedis jedis = PoolRedisUtil.getJedis();
        if (jedis == null) {
            logger.info("jedis == null");
            return null;
        }
        try {
            if (!jedis.exists(key)) {
                return null;
            } else {
                return jedis.get(key);
            }
        } catch (Exception e ) {
            logger.info("获取异常 {}",e.getMessage());
        } finally {
            try {
                jedis.close();
            } catch (Exception e) {
                logger.info("关闭异常 {}",e.getMessage());
            }

        }
        return null;
    }

    /**
     * Redis数据库中存入String类型的变量
     * @param key 键
     * @param value 值
     */
    public static void setValue(String key, String value) {
        Jedis jedis = PoolRedisUtil.getJedis();
        if (jedis != null) {
            try {
                jedis.setex(key, RedisConfiguration.REDIS_SECONDS, value);
                logger.info("设置成功 {}", key, RedisConfiguration.REDIS_SECONDS, value);
            } catch (Exception e) {
                logger.info("设置异常 {}", e.getMessage());
            } finally {
                try {
                    jedis.close();
                } catch (Exception e) {
                    logger.info("关闭异常 {}",e.getMessage());
                }
            }
        }
    }

    // 设置key的过期时间
    public static void doExpire(String key) {
        Jedis jedis = PoolRedisUtil.getJedis();
        if (jedis != null) {
            try {
                jedis.expire(key, RedisConfiguration.REDIS_SECONDS);
                logger.info("过期时间延长一小时");
            } catch (Exception e) {
                logger.info("时间设置异常 {}",e.getMessage());
            } finally {
                try {
                    jedis.close();
                } catch (Exception e) {
                    logger.info("关闭异常 {}",e.getMessage());
                }
            }
        }
    }

    // 删除一个键值对
    static boolean doDelete(String key) {
        Jedis jedis = PoolRedisUtil.getJedis();
        if (jedis != null) {
            try {
                jedis.del(key);
                logger.info("删除成功");

            } catch (Exception e) {
                logger.info("删除异常 {}",e.getMessage());
                return false;
            } finally {
                try {
                    jedis.close();
                } catch (Exception e) {
                    logger.info("关闭异常 {}",e.getMessage());
                }
            }
        }
        return true;
    }

    // 设置一个key永不过期
    static void doPersist(String key) {
        Jedis jedis = PoolRedisUtil.getJedis();
        if (jedis != null) {
            try {
                jedis.persist(key);
                logger.info("设置成功！");

            } catch (Exception e) {
                logger.info("设置异常{}",e.getMessage());
            } finally {
                try {
                    jedis.close();
                } catch (Exception e) {
                    logger.info("关闭异常 {}",e.getMessage());
                }
            }
        }
    }
}
