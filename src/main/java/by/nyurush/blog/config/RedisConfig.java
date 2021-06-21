package by.nyurush.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;


@Configuration
@ComponentScan("by.nyurush.blog")
public class RedisConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }


    @Bean
    public RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }

}

//todo
//@Configuration
//public class RedisConfig {
//
//    @Value("${redis.host}")
//    private String redisHost;
//
//    @Value("${redis.port}")
//    private Integer redisPort;
//
//    @Value("${redis.pass}")
//    private String redisPass;
//
//    @Bean
//    @Primary
//    JedisConnectionFactory jedisConnectionFactory() throws Exception {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setHostName(redisHost);
//        factory.setPort(redisPort);
//        if (redisPass != null) {
//            factory.setPassword(redisPass);
//        }
//        factory.setUsePool(true);
//
//        return factory;
//    }
//
//    @Bean
//    RedisTemplate<String, Object> redisTemplate() throws Exception {
//        final RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        template.setKeySerializer(new StringRedisSerializer());
//
//        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
//        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
//        return template;
//    }
//}
