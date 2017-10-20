package cn.snsoft.base.cache.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import cn.snsoft.base.cache.CacheFactory;
import cn.snsoft.base.cache.ListCache;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.util.RedisCacheUtil;

/**
 * <p>标题： 列表</p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-30 上午10:50:21</p>
 * <p>类全名：cn.snsoft.component.cache.model.impl.ListCacheRedisImpl</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		删除了原来Redis的部分功能,使得List只能从一个方向进行操作
 * 		缓存类型为CacheType.REDIS
 */
public class ListCacheRedisImpl<EV extends Serializable> extends ListCache<EV>
{
	private final ListOperations<String,EV> opsForList;

	public ListCacheRedisImpl(String id, CacheFactory<?,EV> cacheFactory, CacheUtil cacheUtil)
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
			cacheUtil._logCacheError(e);
			return false;
		}
	}

	@Override
	public void add(int index, EV element)
	{
		final EV ev = get(index);
		opsForList.rightPush(key, ev, element);
	}

	@Override
	public boolean addAll(int index, Collection<? extends EV> c)
	{
		boolean b = true;
		if (c == null || c.isEmpty())
		{
			return b;
		}
		EV ev = get(index);
		for (final EV element : c)
		{
			try
			{
				opsForList.rightPush(key, ev, element);
				ev = element;
			} catch (final Exception e)
			{
				b = false;
				cacheUtil._logCacheError(e);
			}
		}
		return b;
	}

	@Override
	public EV get(int index)
	{
		return opsForList.index(key, index);
	}

	@Override
	public EV set(int index, EV element)
	{
		final EV ev = get(index);
		opsForList.set(key, index, element);
		return ev;
	}

	@Override
	public EV remove(int index)
	{
		final EV element = get(index);
		opsForList.remove(key, 1, element);
		return element;
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
	public int size()
	{
		final Long size = opsForList.size(key);
		return size == null ? 0 : size.intValue();
	}

	@Override
	public List<EV> subLocalList(int fromIndex, int toIndex)
	{
		return opsForList.range(key, fromIndex, toIndex);
	}

	@Override
	public void trim(long start, long end)
	{
		opsForList.trim(key, start, end);
	}

	@Override
	public EV popFirst()
	{
		return opsForList.leftPop(key);
	}

	@Override
	public EV popLast()
	{
		return opsForList.rightPop(key);
	}
}
