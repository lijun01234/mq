package cn.snsoft.base.cache.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import cn.snsoft.base.cache.CacheFactory;
import cn.snsoft.base.cache.counter.CounterCache;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.util.RedisCacheUtil;
/**
 * <p>标题： 计数器</p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午7:37:22</p>
 * <p>类全名：cn.snsoft.component.cache.model.impl.CounterCacheRedisImpl</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.REDIS
 */
public class CounterCacheRedisImpl extends CounterCache
{
	private final ValueOperations<String,Long>	opsForValue;

	public CounterCacheRedisImpl(String id, CacheFactory<?,?> cacheFactory, CacheUtil cacheUtil)
	{
		super(id, cacheFactory, cacheUtil);
		final RedisTemplate<String,Long> redisTemplate = ((RedisCacheUtil) cacheUtil)._getRedisTemplate();
		opsForValue = redisTemplate.opsForValue();
	}

	@Override
	public long incr()
	{
		return opsForValue.increment(key, 1);
	}

	@Override
	public long decr()
	{
		return opsForValue.increment(key, -1);
	}

	@Override
	public long incr(long count)
	{
		return opsForValue.increment(key, count);
	}

	@Override
	public long nowValue()
	{
		//[add] by wlp 2015-02-12 如果计数器不存在,返回0,不增加Redis的内容
		if (!cacheUtil.hasKey(key))
		{
			return 0L;
		}
		//[end add]
		return opsForValue.increment(key, 0);
	}
}
