package cn.snsoft.base.cache.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import cn.snsoft.base.cache.CacheFactory;
import cn.snsoft.base.cache.HashtableCache;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.util.RedisCacheUtil;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午9:18:08</p>
 * <p>类全名：cn.snsoft.component.cache.model.impl.HashtableCacheRedisImpl</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.REDIS
 */
public class HashtableCacheRedisImpl<EK extends Serializable,EV extends Serializable> extends HashtableCache<EK,EV>
{
	private final HashOperations<String,EK,EV>	opsForHash;

	public HashtableCacheRedisImpl(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
	{
		super(id, cacheFactory, cacheUtil);
		final RedisTemplate<String,EV> redisTemplate = ((RedisCacheUtil) cacheUtil)._getRedisTemplate();
		this.opsForHash = redisTemplate.opsForHash();
	}

	@Override
	public EV put(EK entryKey, EV entryValue)
	{
		opsForHash.put(key, entryKey, entryValue);
		return entryValue;
	}

	@Override
	public EV putIfAbsent(EK entryKey, EV entryValue)
	{
		boolean putIfAbsent = opsForHash.putIfAbsent(key, entryKey, entryValue);
		return putIfAbsent ? entryValue : opsForHash.get(key, entryKey);
	}

	@Override
	public void putAll(Map<? extends EK,? extends EV> map)
	{
		opsForHash.putAll(key, map);
	}

	@Override
	public EV get(Object entryKey)
	{
		return opsForHash.get(key, entryKey);
	}

	@Override
	public List<EV> multiGet(Collection<EK> entryKeys)
	{
		return opsForHash.multiGet(key, entryKeys);
	}

	@Override
	public int size()
	{
		final Long size = opsForHash.size(key);
		return size == null ? 0 : size.intValue();
	}

	@Override
	public boolean containsKey(Object entryKey)
	{
		return opsForHash.hasKey(key, entryKey);
	}

	@Override
	public Set<EK> keySet()
	{
		return opsForHash.keys(key);
	}

	@Override
	public EV remove(Object entryKey)
	{
		final EV ev = get(entryKey);
		opsForHash.delete(key, entryKey);
		return ev;
	}

	@Override
	public List<EV> values()
	{
		return opsForHash.values(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<EK,EV> getLocalMap()
	{
		Map<EK,EV> map = new HashMap<>();
		try
		{// 默认情况下,直接用这个就可以
			map = opsForHash.entries(key);
		} catch (Exception e)
		{
			Set<EK> keySet = keySet();
			int size = keySet.size();
			map = new HashMap<>(size);
			for (EK ek : keySet)
			{
				try
				{// 计数器Map,需要走这里.
					Long increment = opsForHash.increment(key, ek, 0);
					map.put(ek, (EV) increment);
				} catch (Exception e1)
				{
					get(ek);// 这样会报直接的错误,错误容易找;
				}
			}
		}
		return map;
	}
}
