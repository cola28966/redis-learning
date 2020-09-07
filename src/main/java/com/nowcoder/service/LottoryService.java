package com.nowcoder.service;

import com.nowcoder.util.JedisFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class LottoryService {

    private JedisPool jedisPool = JedisFactory.getInstance().getJedisPool();

    private String lottoryKey = "user:lottory";

    //添加用户
    public void addLottoryPool(String ... users) {
        if(users == null || users.length == 0 ) {
            throw new RuntimeException("至少传入一个用户!");
        }
        try(Jedis jedis = jedisPool.getResource()){
            jedis.sadd(lottoryKey, users);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //清空奖池
    public void clearLottryPool() {
        try(Jedis jedis = jedisPool.getResource()){
            jedis.del(lottoryKey);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //抽取用户
    public Set<String> getLottryUsers(int n) {
        if(n < 0 ) {
            throw new RuntimeException("参数必须大于0!");
        }
        try(Jedis jedis = jedisPool.getResource()){
            long count = jedis.scard(lottoryKey);
            if( n > count) {
                throw new RuntimeException("参数必须小于用户总数!");
            }
            return jedis.spop(lottoryKey,n);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
