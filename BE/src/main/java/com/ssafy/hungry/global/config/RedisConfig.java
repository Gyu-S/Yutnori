package com.ssafy.hungry.global.config;

import com.ssafy.hungry.global.service.RedisListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public ChannelTopic roomTopic(){
        return new ChannelTopic("room");
    }

    @Bean
    public ChannelTopic gameTopic(){
        return new ChannelTopic("game");
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    // redis Listener 커스텀 어댑터 등록 (RedisListener)
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   RedisListener RedisListener,
                                                   ChannelTopic roomTopic,
                                                   ChannelTopic gameTopic){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(RedisListener, roomTopic);
        container.addMessageListener(RedisListener, gameTopic);
        return container;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        // 객체를 위한 직렬화 방식 설정
        Jackson2JsonRedisSerializer<Object> jsonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setHashKeySerializer(jsonSerializer);
        redisTemplate.setHashValueSerializer(jsonSerializer);
        redisTemplate.setKeySerializer(jsonSerializer);
        redisTemplate.setValueSerializer(jsonSerializer);

        //모든 경우
        redisTemplate.setDefaultSerializer(jsonSerializer);

        return redisTemplate;
    }
}
