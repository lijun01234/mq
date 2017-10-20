package cn.snsoft.base.cache.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import cn.snsoft.base.cache.CacheFactory;
import cn.snsoft.base.cache.SetCache;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.util.RedisCacheUtil;

/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-30 下午4:30:49</p>
 * <p>类全名：cn.snsoft.component.cache.model.impl.SetCacheRedisImpl</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.REDIS
 */
public class SetCacheRedisImpl<EV extends Serializable> extends SetCache<EV>
{
	private final SetOperations<String,EV> opsForSet;

	/**
	 * 构造器,仅限工厂类
	 * @param id Redis缓存的id
	 * @param cacheUtil 缓存工具的对象
	 * @param cacheFactory 缓存工厂的对象
	 */
	public SetCacheRedisImpl(String id, CacheFactory<?,EV> cacheFactory,CacheUtil cacheUtil)
	{
		super(id, cacheFactory,cacheUtil);
		final RedisTemplate<String,EV> redisTemplate = ((RedisCacheUtil) cacheUtil)._getRedisTemplate();
		opsForSet = redisTemplate.opsForSet();
	}

	@Override
	public int size()
	{
		final Long size = opsForSet.size(key);
		return size == null ? 0 : size.intValue();
	}

	@Override
	public boolean contains(Object o)
	{
		return opsForSet.isMember(key, o);
	}

	@Override
	public boolean add(EV e)
	{
		return opsForSet.add(key, e)==1;
	}

	@Override
	public boolean remove(Object o)
	{
		return opsForSet.remove(key, o)==1;
	}

	@Override
	public Set<EV> getLocalSet()
	{
		return opsForSet.members(key);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		// TODO Auto-generated method stub
		return getLocalSet().retainAll(c);
	}

	@Override
	public EV randompPop()
	{
		return opsForSet.pop(key);
	}

	@Override
	public EV randomMember()
	{
		return opsForSet.randomMember(key);
	}

	@Override
	public boolean move(EV value, String destId)
	{
		return opsForSet.move(key, value, destId);
	}

	@Override
	public java.util.Set<EV> intersect(String otherId)
	{
		return opsForSet.intersect(key, otherId);
	}

	@Override
	public Set<EV> intersect(SetCache<EV> otherSetCache)
	{
		return opsForSet.intersect(key, otherSetCache.getSetCacheId());
	}

	@Override
	public SetCache<EV> intersectAndCache(String otherId, String destId)
	{
		try
		{
			opsForSet.intersectAndStore(key, otherId, destId);
			return cacheFactory.getSetCache(destId);
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return null;
		}
	}

	@Override
	public SetCache<EV> intersectAndCache(SetCache<EV> otherSetCache, SetCache<EV> destSetCache)
	{
		try
		{
			destSetCache.clear();
			opsForSet.intersectAndStore(key, otherSetCache.getSetCacheId(), destSetCache.getSetCacheId());
			return destSetCache;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return null;
		}
	}

	@Override
	public Set<EV> union(String otherId)
	{
		return opsForSet.union(key, otherId);
	}

	@Override
	public Set<EV> union(SetCache<EV> otherSetCache)
	{
		return opsForSet.union(key, otherSetCache.getSetCacheId());
	}

	@Override
	public SetCache<EV> unionAndCache(String otherId, String destId)
	{
		try
		{
			opsForSet.unionAndStore(key, otherId, destId);
			return cacheFactory.getSetCache(destId);
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return null;
		}
	}

	@Override
	public SetCache<EV> unionAndCache(SetCache<EV> otherSetCache, SetCache<EV> destSetCache)
	{
		try
		{
			destSetCache.clear();
			opsForSet.unionAndStore(key, otherSetCache.getSetCacheId(), destSetCache.getSetCacheId());
			return destSetCache;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return null;
		}
	}

	@Override
	public Set<EV> difference(String otherId)
	{
		return opsForSet.difference(key, otherId);
	}

	@Override
	public Set<EV> difference(SetCache<EV> otherSetCache)
	{
		return opsForSet.difference(key, otherSetCache.getSetCacheId());
	}

	@Override
	public SetCache<EV> differenceAndStore(String otherId, String destId)
	{
		try
		{
			opsForSet.differenceAndStore(key, otherId, destId);
			return cacheFactory.getSetCache(destId);
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return null;
		}
	}

	@Override
	public SetCache<EV> differenceAndStore(SetCache<EV> otherSetCache, SetCache<EV> destSetCache)
	{
		try
		{
			destSetCache.clear();
			opsForSet.differenceAndStore(key, otherSetCache.getSetCacheId(), destSetCache.getSetCacheId());
			return destSetCache;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return null;
		}
	}
}
