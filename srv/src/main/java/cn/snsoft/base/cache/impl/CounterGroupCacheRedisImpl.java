package cn.snsoft.base.cache.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import cn.snsoft.base.cache.CacheFactory;
import cn.snsoft.base.cache.CounterGroupCache;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.util.RedisCacheUtil;

/**
 * <p>
 * 标题：计数器组
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 所属模块：
 * </p>
 * <p>
 * 版权： Copyright (c) 2013
 * </p>
 * <p>
 * 公司: 北京南北天地科技股份有限公司
 * </p>
 * <p>
 * 创建日期：2013-1-15
 * </p>
 * <p>
 * 类全名：cn.snsoft.component.cache.model.impl.CounterGroupCacheRedisImpl
 * </p>
 * 作者：王立鹏 初审： 复审：
 * 
 * @version 8.0 缓存类型为CacheType.REDIS
 */
public class CounterGroupCacheRedisImpl extends CounterGroupCache {
	private final HashOperations<String, String, Long> opsForHash;

	public CounterGroupCacheRedisImpl(String id,
			CacheFactory<?, ?> cacheFactory, CacheUtil<Long> cacheUtil,
			String... columns) {
		super(id, cacheFactory, cacheUtil, columns);
		final RedisTemplate<String, Long> redisTemplate = ((RedisCacheUtil<Long>) cacheUtil)
				._getRedisTemplate();
		opsForHash = redisTemplate.opsForHash();
	}

	@Override
	public long incr(String column) {
		return opsForHash.increment(key, column, 1);
	}

	public boolean remove(String column) {
		try {
			opsForHash.delete(key, column);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public long decr(String column) {
		return opsForHash.increment(key, column, -1);
	}

	@Override
	public long incr(long count, String column) {
		return opsForHash.increment(key, column, count);
	}

	@Override
	public long nowValue(String column) {
		return opsForHash.increment(key, column, 0);
	}

	@Override
	public Map<String, Long> nowValue() {
		Map<String, Long> map = new HashMap<>();
		try {// 默认情况下,直接用这个就可以
			map = opsForHash.entries(key);
		} catch (Exception ex) {
			final Set<String> columnSet = opsForHash.keys(key);
			if (columnSet == null || columnSet.isEmpty()) {
				return new LinkedHashMap<>(0);
			}
			map = new LinkedHashMap<>(columnSet.size());
			for (String column : columnSet) {
				map.put(column, nowValue(column));
			}
		}
		return map;
	}
}
