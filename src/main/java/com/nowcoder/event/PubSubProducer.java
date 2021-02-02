package com.nowcoder.event;

import redis.clients.jedis.Jedis;

import java.io.Closeable;
import java.io.IOException;

public class PubSubProducer implements Closeable {

    private Jedis jedis;

    public PubSubProducer(Jedis jedis) {
        this.jedis = jedis;
    }

    public Long publish(String channel, String message) {
        return jedis.publish(channel,message);
    }

    @Override
    public void close() throws IOException {
        if(jedis != null) {
            jedis.close();
        }
    }
}
