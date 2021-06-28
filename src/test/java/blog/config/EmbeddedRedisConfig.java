package blog.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

//@Component
//public class EmbeddedRedisConfig {
//
//    @Value("${spring.redis.port}")
//    private int redisPort;
//
//    private RedisServer redisServer;
//
//    @PostConstruct
//    public void startRedis() throws IOException {
//        redisServer = new RedisServer(redisPort);
//        redisServer.start();
//    }
//
//    @PreDestroy
//    public void stopRedis() {
//        redisServer.stop();
//    }
//}

@Configuration
public class EmbeddedRedisConfig {

    private final redis.embedded.RedisServer redisServer;

    public EmbeddedRedisConfig(@Value("${redis.port}") final int redisPort) throws IOException {
        this.redisServer = new redis.embedded.RedisServer(redisPort);
    }

    @PostConstruct
    public void startRedis() {
        this.redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        this.redisServer.stop();
    }
}