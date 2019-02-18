package com.example.redisUtils;

import com.example.configuration.RedisConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

final class PoolRedisUtil {
    private static final Logger logger = LoggerFactory.getLogger(PoolRedisUtil.class);

    private static JedisPool jedisPool = null;

    /*
      静态块，初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            /*注意：
                在高版本的jedis jar包，比如本版本2.9.0，JedisPoolConfig没有setMaxActive和setMaxWait属性了
                这是因为高版本中官方废弃了此方法，用以下两个属性替换。
                maxActive  ==>  maxTotal
                maxWait==>  maxWaitMillis
            */
            config.setMaxTotal(RedisConfiguration.REDIS_MAX_TOTAL);
            config.setMaxIdle(RedisConfiguration.REDIS_MAX_IDLE);
            config.setMaxWaitMillis(RedisConfiguration.REDIS_MAX_WAIT_MILLIS);
            config.setTestOnBorrow(RedisConfiguration.REDIS_TEST_ON_BORROW);
            jedisPool = new JedisPool(config, RedisConfiguration.REDIS_ADDRESS, RedisConfiguration.REDIS_PORT, RedisConfiguration.REDIS_TIMEOUT);
            logger.info("Redis连接池初始化成功！");
        } catch (Exception e) {
            logger.info("Redis连接池初始化失败！");
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     * @return jedis实例操作
     */
    synchronized static Jedis getJedis() {
        Jedis jedis = null;
        try {
            if(jedisPool != null) {
                logger.info("Redis实例成功获取！");
                jedis = jedisPool.getResource();
                return jedisPool.getResource();
            } else {
                logger.info("Redis实例获取失败，已无可用Redis实例！");
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    static void returnResource(final Jedis jedis){
        //方法参数被声明为final，表示它是只读的。
        if(jedis!=null){
            jedis.close();
        }
    }
}

