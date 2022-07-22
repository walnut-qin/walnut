package com.walnut.core.type;

import java.time.Duration;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;

import org.springframework.core.convert.converter.Converter;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class Cache<K, V> {
    /**
     * 缓存实体
     */
    LoadingCache<K, Optional<V>> loadingCache;

    /**
     * 后初始化
     * 
     * @param size
     * @param converter
     */
    protected Cache(Integer size, Converter<K, V> converter, Duration refresh, Duration expire) {
        // 构造缓存实体
        this.loadingCache = CacheBuilder.newBuilder()
                .maximumSize(size)
                .refreshAfterWrite(refresh)
                .expireAfterAccess(expire)
                .recordStats()
                .build(new CacheLoader<K, Optional<V>>() {
                    @Override
                    public Optional<V> load(K key) throws Exception {
                        return Optional.fromNullable(converter.convert(key));
                    }
                });
    }

    /**
     * 后初始化
     * 
     * @param size
     * @param converter
     */
    protected Cache(Integer size, Converter<K, V> converter) {
        this(size, converter, Duration.ofMinutes(1), Duration.ofMinutes(10));
    }

    /**
     * 后初始化
     * 
     * @param size
     * @param converter
     */
    protected Cache(Converter<K, V> converter) {
        this(200, converter);
    }

    /**
     * 从缓存中获取值
     * 
     * @param key
     * @return
     */
    public V get(K key) {
        // 不支持空索引
        if (key == null) {
            log.warn("缓存不支持空索引");
            return null;
        }

        // 检索缓存
        try {
            return loadingCache.get(key).orNull();
        } catch (Exception e) {
            log.warn(String.format("检索缓存出现异常(%s)", e.getMessage()));
            return null;
        }
    }

    /**
     * 刷新缓存的值，仅刷新缓存中存在的值
     * 
     * @param key
     */
    public void refresh(K key) {
        // 检索缓存
        if (loadingCache.getIfPresent(key) != null) {
            loadingCache.refresh(key);
        }
    }

    /**
     * 展示缓存内容
     * 
     * @return
     */
    public CacheStats getStats() {
        return loadingCache.stats();
    }
}
