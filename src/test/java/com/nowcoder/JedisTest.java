package com.nowcoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nowcoder.entity.User;
import com.nowcoder.util.JedisFactory;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {

    private JedisPool jedisPool = JedisFactory.getInstance().getJedisPool();

    @Test
    public void testJedis() {
        try(Jedis jedis = new Jedis("124.70.75.113", 6379)){
            jedis.auth("aa123456");
            jedis.set("hello","123");
            System.out.println(jedis.get("hello"));
        }catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Test
    public void testJedisPool() {
        try(Jedis jedis = jedisPool.getResource()) {
            jedis.set("hello","123");
            System.out.println(jedis.get("hello"));
        }
    }

    @Test
    public void testJedisApi() {
        User user = new User(2,"tom","123456","tom@qq.com"
        ,"1233212");
        String key = null;
        try(Jedis jedis = jedisPool.getResource()) {
            key = "user:" + user.getId();
            jedis.set(key, JSON.toJSONString(user));
            String value = jedis.get(key);
            System.out.println(value);
            System.out.println(JSON.parseObject(value, User.class));
        }
    }

}
