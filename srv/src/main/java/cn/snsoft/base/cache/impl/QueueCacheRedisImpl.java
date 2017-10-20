package cn.snsoft.base.cache.impl;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import cn.snsoft.base.cache.CacheFactory;
import cn.snsoft.base.cache.QueueCache;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.util.RedisCacheUtil;
/**
 * <p>标题： 队列</p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-12-4 下午1:23:31</p>
 * <p>类全名：cn.snsoft.component.cache.model.impl.QueueCacheRedisImpl</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.REDIS
 */
public class QueueCacheRedisImpl<EV extends Serializable> extends QueueCache<EV>
{
	private final ListOperations<String,EV>	opsForList;

	public QueueCacheRedisImpl(String id, CacheFactory<?,EV> cacheFactory, CacheUtil cacheUtil)
	{
		super(id, cacheFactory, cacheUtil);
		final RedisTemplate<String,EV> redisTemplate = ((RedisCacheUtil) cacheUtil)._getRedisTemplate();
		opsForList = redisTemplate.opsForList();
	}

	@Override
	public boolean add(EV element)
	{
		try
		{
			opsForList.rightPush(key, element);
			return true;
		} catch (final Exception e)
		{
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean offer(EV element)
	{
		try
		{
			long leftPush = opsForList.rightPush(key, element);
			return leftPush > 0;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return false;
		}
	}

	@Override
	public EV poll()
	{
		return opsForList.leftPop(key);
	}

	@Override
	public EV peek()
	{
		return opsForList.index(key, size() - 1);
	}

	@Override
	public int size()
	{
		final Long size = opsForList.size(key);
		return size == null ? 0 : size.intValue();
	}

	@Override
	public boolean remove(Object o)
	{
		try
		{
			opsForList.remove(key, 1, o);
			return true;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return false;
		}
	}

	@Override
	public List<EV> getLocalList()
	{
		return opsForList.range(key, 0, -1);
	}
}
