package com.shy.tools.redis;


import com.shy.tools.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * Redis管理器
 * ClassName: RedisManager <br/>
 * Date: 2017/5/16 15:07 <br/>
 * Version: 1.0 <br/>
 */
public class RedisManager {

    private static final Logger LOG = LoggerFactory.getLogger(RedisManager.class);

    private static JedisPool pool = null;
    private static String HOST = PropertiesUtil.getProperty("redis.host"); // 地址
    private static String PORT  = PropertiesUtil.getProperty("redis.port");; // 端口号
    private static String PASSWORD = PropertiesUtil.getProperty("redis.password"); // 密码

    private static final int TIMEOUT = 5000; // 读取超时等待时间

    //连接Redis数据库
    static {
        if (LOG.isInfoEnabled()) {
            LOG.info("========redis connect star========");
        }
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(300);
            config.setMaxTotal(600);
            config.setMaxWaitMillis(1000);
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, HOST, Integer.parseInt(PORT), TIMEOUT, PASSWORD);
        } catch (Exception e) {
            LOG.error("========redis connect error========", e);
        }
        if (LOG.isInfoEnabled()) {
            LOG.info("========redis connect success!========");
        }
    }

    /**
     * 追加，当key存在时，将value追加到当前value之后
     * @param key        键
     * @param value      值
     * @param expireTime 失效时间
     */
    public static void append(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.append(key, value);
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            LOG.error("========redis append error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 新增，若数据库中存在则覆盖
     * @param key        键
     * @param value      值
     * @param expireTime 失效时间
     */
    public static void add(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            //先删除
            delete(key);
            //再追加
            jedis.append(key, value);
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            LOG.error("========redis add error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 刷新失效时间
     * @param key        键
     * @param expireTime 失效时间
     */
    public static void expire(String key, int expireTime){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            LOG.error("========redis expire error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    /**
     * 根据key获取value
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            LOG.error("========redis get error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * 根据key模糊查找
     * @param key 键
     * @return 值集合
     */
    public static Set<String> findKey(String key) {
        Set<String> set = null;
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            set = jedis.keys("*" + key + "*");
        } catch (Exception e) {
            LOG.error("========redis findKey error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
        return set;
    }

    /**
     * 删除
     * @param key 键
     */
    public static void delete(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            LOG.error("========redis delete error========", e);
        } finally {
            // jedis实例使用完毕，返还连接池，若有异常，则释放此实例
            if (null != jedis) {
                jedis.close();
            }
        }
    }
}
