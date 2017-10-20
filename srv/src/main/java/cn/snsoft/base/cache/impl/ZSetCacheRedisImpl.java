package cn.snsoft.base.cache.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import cn.snsoft.base.cache.CacheFactory;
import cn.snsoft.base.cache.ZSetCache;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.util.RedisCacheUtil;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-12-3 下午7:14:07</p>
 * <p>类全名：cn.snsoft.component.cache.model.impl.ZSetCacheRedisImpl</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.REDIS
 */
public class ZSetCacheRedisImpl<EV extends Serializable> extends ZSetCache<EV>
{
	private final ZSetOperations<String,EV>	opsForZSet;

	/**
	 * 构造器,仅限工厂类
	 * @param id Redis缓存的id
	 * @param cacheUtil 缓存工具的对象
	 * @param cacheFactory 缓存工厂的对象
	 */
	public ZSetCacheRedisImpl(String id, CacheFactory<?,EV> cacheFactory, CacheUtil cacheUtil)
	{
		super(id, cacheFactory, cacheUtil);
		final RedisTemplate<String,EV> redisTemplate = ((RedisCacheUtil) cacheUtil)._getRedisTemplate();
		opsForZSet = redisTemplate.opsForZSet();
	}

	@Override
	public boolean add(EV e, double score)
	{
		return opsForZSet.add(key, e, score);
	}

	@Override
	public long size(double min, double max)
	{
		return opsForZSet.count(key, min, max);
	}

	@Override
	public long size()
	{
		return opsForZSet.size(key);
	}

	@Override
	public double incScore(EV value, double delta)
	{
		return opsForZSet.incrementScore(key, value, delta);
	}

	@Override
	public Set<EV> subLocalSet(long start, long end)
	{
		return opsForZSet.range(key, start, end);
	}

	@Override
	public Set<EV> subLocalSetDesc(long start, long end)
	{
		return opsForZSet.reverseRange(key, start, end);
	}

	@Override
	public Set<TypedTuple<EV>> subLocalSetWithScores(long start, long end)
	{
		return opsForZSet.rangeWithScores(key, start, end);
	}

	@Override
	public Set<TypedTuple<EV>> subLocalSetWithScoresDesc(long start, long end)
	{
		return opsForZSet.reverseRangeWithScores(key, start, end);
	}

	@Override
	public Set<EV> subLocalSetByScore(double min, double max)
	{
		return opsForZSet.rangeByScore(key, min, max);
	}

	@Override
	public Set<EV> subLocalSetByScoreDesc(double min, double max)
	{
		return opsForZSet.reverseRangeByScore(key, min, max);
	}

	@Override
	public Set<TypedTuple<EV>> subLocalSetByScoreWithScores(double min, double max)
	{
		return opsForZSet.rangeByScoreWithScores(key, min, max);
	}

	@Override
	public Set<TypedTuple<EV>> subLocalSetByScoreWithScoresDesc(double min, double max)
	{
		return opsForZSet.reverseRangeByScoreWithScores(key, min, max);
	}

	@Override
	public long indexOf(Object value)
	{
		return opsForZSet.rank(key, value);
	}

	@Override
	public long indexOfDesc(EV value)
	{
		return opsForZSet.reverseRank(key, value);
	}

	@Override
	public boolean remove(Object value)
	{
		return opsForZSet.remove(key, value)>0;
	}

	@Override
	public boolean removeAll(long start, long end)
	{
		try
		{
			opsForZSet.removeRange(key, start, end);
			return true;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return false;
		}
	}

	@Override
	public boolean removeAllByScore(double min, double max)
	{
		try
		{
			opsForZSet.removeRangeByScore(key, min, max);
			return true;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return false;
		}
	}

	@Override
	public double getScore(Object value)
	{
		return opsForZSet.score(key, value);
	}

	@Override
	public ZSetCache<EV> intersectAndCache(String otherId, String destId)
	{
		try
		{
			opsForZSet.intersectAndStore(key, otherId, destId);
			return cacheFactory.getZSetCache(destId);
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return null;
		}
	}

	@Override
	public ZSetCache<EV> intersectAndCache(ZSetCache<EV> otherZSetCache, ZSetCache<EV> destZSetCache)
	{
		try
		{
			destZSetCache.clear();
			opsForZSet.intersectAndStore(key, otherZSetCache.getZSetCacheId(), destZSetCache.getZSetCacheId());
			return destZSetCache;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return null;
		}
	}

	@Override
	public ZSetCache<EV> unionAndCache(String otherId, String destId)
	{
		try
		{
			opsForZSet.unionAndStore(key, otherId, destId);
			return cacheFactory.getZSetCache(destId);
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return null;
		}
	}

	@Override
	public ZSetCache<EV> unionAndCache(ZSetCache<EV> otherZSetCache, ZSetCache<EV> destZSetCache)
	{
		try
		{
			destZSetCache.clear();
			opsForZSet.unionAndStore(key, otherZSetCache.getZSetCacheId(), destZSetCache.getZSetCacheId());
			return destZSetCache;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return null;
		}
	}

	@Override
	public Set<EV> getLocalSet()
	{
		return opsForZSet.range(key, 0, -1);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		//TODO: 
		return getLocalSet().retainAll(c);
	}
}
