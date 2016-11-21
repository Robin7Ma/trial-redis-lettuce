package io.github.robin7ma.trial.redis.lettuce;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TryRedisWithLettuce {


    private static final String REDIS_SERVER = "redis://localhost:6379/0";


    private RedisClient redisClient;
    private StatefulRedisConnection<String, String> connection;
    private RedisCommands<String, String> syncCommands;

    @Before
    public void init() {
        redisClient = RedisClient.create(REDIS_SERVER);
        connection = redisClient.connect();
        syncCommands = connection.sync();
    }


    @After
    public void destory() {
        if (connection != null)
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (redisClient != null)
            try {
                redisClient.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Test
    public void testMset() {
        long st = System.currentTimeMillis();
        int count = 100000;

        Map<String, String> ss = new HashMap<>();
        for (int i = 0; i < count; i++) {
            ss.put("test" + i, "Hello 你好!" + i);
        }
        syncCommands.mset(ss);

        System.out.println("mset("+ count +")总耗时：" + (System.currentTimeMillis() - st));
    }


    @Test
    public void testMget() {
        long st = System.currentTimeMillis();
        int count = 100000;

        String[] ks = new String[count];
        for (int i = 0; i < count; i++) {
            ks[i] = "test" + i;
        }
        syncCommands.mget(ks);
        System.out.println("mget("+ count +")总耗时：" + (System.currentTimeMillis() - st));
    }
}
