package com.xfpay.cache;

/**
 * Created by fei on 2019/6/4.
 */

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

    protected Logger LOG = Logger.getLogger("RedisConfig");

    @Autowired
    RedisConnectionFactory connectionFactory;

//    /**
//     * 定义 StringRedisTemplate ，指定序列化和反序列化的处理类
//     *
//     * @param connectionFactory
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
//        StringRedisTemplate template = new StringRedisTemplate(connectionFactory);
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
//                Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        //序列化 值时使用此序列化方法
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }


    @Bean
    public RedisTemplate<Object, Object> getRedisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public StringRedisTemplate getStringRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean(name = "bizCacheFactory")
    public BizCacheFactory getBaseCacheService() {
        BizCacheFactory cacheFactory = new BizCacheFactory(getRedisTemplate(), getStringRedisTemplate());
        if (cacheFactory != null) {
            LOG.info("《《《《《《《《《《《redis cache init success~~》》》》》》》》》》》》》》");
        } else {
            LOG.warn("《《《《《《《《《《《redis cache init fail~~》》》》》》》》》》》》》》");
        }
        return cacheFactory;
    }
}
