package com.xfpay.cache.customize;

import com.xfpay.cache.BaseCacheService;
import com.xfpay.cache.RedisKeys;
import com.xfpay.entity.SimpleEntity;
import com.xfpay.mapper.SimpleEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 参考例子-缓存
 *
 * 创建缓存类时，以下信息需要同步设置或增加：
 *  1-创建类似于SimpleCache的类
 *  2-com.xfpay.cache.BizCacheFactory#setApplicationContext(org.springframework.context.ApplicationContext)
 *  3-com.xfpay.cache.BizCacheFactory#getSimpleCache()
 * @author android
 */
public class SimpleCache {

    @Autowired
    private BaseCacheService baseCache;

    private SimpleEntityMapper simpleEntityMapper;

    public SimpleCache(BaseCacheService baseCache) {
        this.baseCache = baseCache;
    }

    //注入mapper
    public void setApplicationContext(ApplicationContext applicationContext) {
        simpleEntityMapper = applicationContext.getBean(SimpleEntityMapper.class);
        this.initBean();
    }



    public void setSimpleInfo(SimpleEntity entity) {
        baseCache.setJson(RedisKeys.SimpleInfo.getSingleKey(entity.getId()), entity);   //key -value
    }


    public SimpleEntity getSimpleInfo(Integer userId) {
        SimpleEntity info = null;
        info = baseCache.getJson(RedisKeys.SimpleInfo.getSingleKey(userId),
                SimpleEntity.class);
        if (null != info) {
            return info;
        }
        info = simpleEntityMapper.selectByPrimaryKey(userId);
        if (null != info) {
            setSimpleInfo(info);
        }
        return info;
    }



    /**
     * 初始化缓存。
     * 想要再系统启动时将重要信息缓存起来，则再这里提前进行初始化
     */
    private void initBean() {
        Integer version = baseCache.getInt(RedisKeys.SimpleInfo.PREFIX_KEY);
        if (version == null || version != 1) {
            baseCache.setInt(RedisKeys.VersionUpdateSpace.VERSION_UPDATE_KEY, 1);
            List<SimpleEntity> list = simpleEntityMapper.selectAll();
            for (SimpleEntity entity : list) {
                this.addVersionUpdateDescription(entity);
            }
        }
    }

    //向缓存添加
    public void addVersionUpdateDescription(SimpleEntity entity) {
//		 baseCache.setJson(RedisKeys.SimpleInfo.getSingleKey(entity.getUserId()),entity.getTgwNo());//用户id为key的缓存
        baseCache.setJson(RedisKeys.SimpleInfo.getSingleKey2(entity.getId(), entity.getKey()), entity);//媒体id_广告id为key的缓存
    }


}
