package com.nowcoder.event;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.io.Closeable;
import java.io.IOException;

public class PubSubConsumer implements Closeable {

    private Jedis jedis;

    public PubSubConsumer(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public void close() throws IOException {
        if(jedis != null) {
            jedis.close();
        }
    }

    public void subscribe(final JedisPubSub handler, final String ... channels) {
        if(handler == null || channels == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                jedis.subscribe(handler,channels);
            }
        }).start();
    }

    public void unsubscribe(JedisPubSub handler, String ... channels) {
        handler.unsubscribe(channels);
    }

    public void subscribeByPattern(final JedisPubSub handler, final String ... patterns) {
        if(handler == null || patterns == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                jedis.psubscribe(handler,patterns);
            }
        }).start();
    }

    public void unsubscribeByPattern(JedisPubSub handler, String ... patterns) {
        handler.punsubscribe(patterns);
    }


}
