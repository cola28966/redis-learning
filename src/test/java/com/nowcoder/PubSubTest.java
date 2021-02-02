package com.nowcoder;

import com.nowcoder.event.PubSubConsumer;
import com.nowcoder.event.PubSubProducer;
import com.nowcoder.util.JedisFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class PubSubTest {

    public static void main(String[] args) throws InterruptedException {
        JedisPool jedisPool = JedisFactory.getInstance().getJedisPool();

        PubSubConsumer consumer1 = new PubSubConsumer(jedisPool.getResource());
        JedisPubSub handler1 = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("consumer : " + Thread.currentThread().getName());
                System.out.println("channel : " + channel);
                System.out.println("message : " + message);
                System.out.println("====================================");
            }
        };
        consumer1.subscribe(handler1,"news:music");
        PubSubConsumer consumer2 = new PubSubConsumer(jedisPool.getResource());
        JedisPubSub handler2 = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("consumer : " + Thread.currentThread().getName());
                System.out.println("channel : " + channel);
                System.out.println("message : " + message);
                System.out.println("====================================");
            }
        };
        consumer2.subscribe(handler1,"news:sport");
        PubSubConsumer consumer3 = new PubSubConsumer(jedisPool.getResource());
        JedisPubSub handler3 = new JedisPubSub() {
            @Override
            public void onPMessage(String pattern, String channel, String message) {
                System.out.println("consumer : " + Thread.currentThread().getName());
                System.out.println("pattern : " + pattern);
                System.out.println("channel : " + channel);
                System.out.println("message : " + message);
                System.out.println("====================================");
        }
        };
        consumer3.subscribeByPattern(handler3,"news:*");
        PubSubProducer producer = new PubSubProducer(jedisPool.getResource());
        producer.publish("news:music","music");
        producer.publish("news:sport","sport");
        producer.publish("news:it","it");
        Thread.sleep(2000);
        /*consumer1.unsubscribe(handler1,"news:music");
        consumer2.unsubscribe(handler2,"news:sport");
        consumer3.unsubscribeByPattern( handler3,"news:*");
        producer.publish("news:music","music");
        producer.publish("news:sport","sport");
        producer.publish("news:it","it");*/

    }
}
