package com.xfpay.cache;

import com.xfpay.cache.customize.SimpleCache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * 业务缓存配置工厂
 */
public class BizCacheFactory implements ApplicationContextAware {
    private RedisTemplate<Object, Object> redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    public BizCacheFactory(RedisTemplate<Object, Object> redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private BaseCacheService basecache;

    private SimpleCache simpleCache;


    /**
     * 默认缓存处理器
     *
     * @return
     */
    public BaseCacheService getDefaultCache() {
        if (basecache == null)
            basecache = new BaseCacheService(redisTemplate, stringRedisTemplate);
        return basecache;
    }

    /**
     * 获取缓存的实例
     *
     * @return
     */
    public SimpleCache getSimpleCache() {
        if (simpleCache == null) {
            simpleCache = new SimpleCache(getDefaultCache());
        }
        return simpleCache;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        getSimpleCache().setApplicationContext(applicationContext);
    }
}
