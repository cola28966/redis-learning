package com.nowcoder;

import com.alibaba.fastjson.JSON;
import com.nowcoder.event.StreamConsumer;
import com.nowcoder.event.StreamConsumerHandler;
import com.nowcoder.event.StreamProducer;
import com.nowcoder.util.JedisFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.StreamEntry;
import redis.clients.jedis.StreamEntryID;

import java.util.List;
import java.util.Map;

public class StreamTest {

    public static void main(String[] args) throws InterruptedException {
        JedisPool jedisPool = JedisFactory.getInstance().getJedisPool();



        //创建消费组
        try(Jedis jedis = jedisPool.getResource()){
            jedis.xgroupCreate("s1", "g1", new StreamEntryID(), true);
        }catch (Exception e) {
            e.printStackTrace();
        }

        StreamConsumerHandler handler = new StreamConsumerHandler() {
            @Override
            public void handle(List<StreamEntry> list) {
                for(StreamEntry entry : list) {
                    System.out.println("consumer: " + Thread.currentThread().getName());
                    System.out.println(entry.getID());
                    System.out.println(entry.getFields());
                    System.out.println("=====================================");
                }
            }
        };
        //消费组消费
        StreamConsumer sc1 = new StreamConsumer(jedisPool.getResource());
        sc1.consumeGroup("s1", "g1","c1", handler);
        StreamConsumer sc2 = new StreamConsumer(jedisPool.getResource());
        sc2.consumeGroup("s1", "g1","c2", handler);
        StreamConsumer sc3 = new StreamConsumer(jedisPool.getResource());
        sc3.consumeGroup("s1", "g1","c3", handler);

        //生产
        StreamProducer sp = new StreamProducer(jedisPool.getResource());
        for (int i = 0, n = 0; i < 3; i++) {
            sp.xadd("s1", StreamEntryID.NEW_ENTRY, JSON.parseObject("{'num':'" + ++n + "'}", Map.class));
            sp.xadd("s1", StreamEntryID.NEW_ENTRY, JSON.parseObject("{'num':'" + ++n + "'}", Map.class));
            sp.xadd("s1", StreamEntryID.NEW_ENTRY, JSON.parseObject("{'num':'" + ++n + "'}", Map.class));
            sp.xadd("s1", StreamEntryID.NEW_ENTRY, JSON.parseObject("{'num':'" + ++n + "'}", Map.class));
            sp.xadd("s1", StreamEntryID.NEW_ENTRY, JSON.parseObject("{'num':'" + ++n + "'}", Map.class));
            Thread.sleep(3000);
        }
    }
}
