package cn.snsoft.base.cache.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import cn.snsoft.base.cache.CacheFactory;
import cn.snsoft.base.cache.ValueCache;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.util.RedisCacheUtil;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午3:10:36</p>
 * <p>类全名：cn.snsoft.component.cache.model.impl.ValueCacheRedisImpl</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.REDIS
 */
public final class ValueCacheRedisImpl<EV extends Serializable> extends ValueCache<EV>
{
	private final ValueOperations<String,EV>	opsForValue;

	public ValueCacheRedisImpl(CacheFactory cacheFactory, CacheUtil cacheUtil)
	{
		super(cacheFactory, cacheUtil);
		final RedisTemplate<String,EV> redisTemplate = ((RedisCacheUtil) cacheUtil)._getRedisTemplate();
		this.opsForValue = redisTemplate.opsForValue();
	}
 
	@Override
	public boolean put(String key, EV value)
	{
		try
		{
			opsForValue.set(key, value);
			return true;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return false;
		}
	}

	@Override
	public boolean putIfAbsent(String key, EV value)
	{
		return opsForValue.setIfAbsent(key, value);
	}

	@Override
	public boolean put(String key, EV value, long timeout, TimeUnit unit)
	{
		try
		{
			opsForValue.set(key, value, timeout, unit);
			return true;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return false;
		}
	}

	@Override
	public void multiPut(Map<String,? extends EV> m)
	{
		opsForValue.multiSet(m);
	}

	@Override
	public void multiPutIfAbsent(Map<String,? extends EV> m)
	{
		opsForValue.multiSetIfAbsent(m);
	}

	@Override
	public EV getAndPut(String key, EV value)
	{
		return opsForValue.getAndSet(key, value);
	}

	@Override
	public EV get(String key)
	{
		return opsForValue.get(key);
	}

	@Override
	public List<EV> multiGet(Collection<String> keys)
	{
		return opsForValue.multiGet(keys);
	}
}
