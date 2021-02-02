package com.nowcoder.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;

public class JedisFactory {

    private static JedisFactory jedisFactory;

    private JedisPool jedisPool;

    private JedisFactory() {
        super();
    }

    public static JedisFactory getInstance() {
        if(jedisFactory == null) {
            return new JedisFactory();
        }
        return jedisFactory;
    }

    public JedisPool getJedisPool() {
        if(jedisPool == null) {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            jedisPool = new JedisPool(config,"47.116.142.117",6379,3000,"aa123456");
        }
        return jedisPool;
    }
}
