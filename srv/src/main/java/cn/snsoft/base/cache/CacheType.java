package cn.snsoft.base.cache;

import java.io.Serializable;
import cn.snsoft.base.cache.counter.CounterCache;
import cn.snsoft.base.cache.impl.CounterCacheRedisImpl;
import cn.snsoft.base.cache.impl.CounterGroupCacheRedisImpl;
import cn.snsoft.base.cache.impl.HashtableCacheRedisImpl;
import cn.snsoft.base.cache.impl.ListCacheRedisImpl;
import cn.snsoft.base.cache.impl.QueueCacheRedisImpl;
import cn.snsoft.base.cache.impl.SetCacheRedisImpl;
import cn.snsoft.base.cache.impl.ValueCacheRedisImpl;
import cn.snsoft.base.cache.impl.ZSetCacheRedisImpl;
import cn.snsoft.base.cache.util.CacheUtil;
/**
 * <p>标题： 缓存类型</p>
 * <p>功能： 同时用于连接不同类型的缓存</p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-12-4 下午4:22:52</p>
 * <p>类全名：cn.snsoft.component.cache.connection.CacheType</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 */
public enum CacheType
{
	REDIS("REDIS")
	{
		@Override
		public <EK extends Serializable,EV extends Serializable> ValueCache<EV> getValueCache(CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new ValueCacheRedisImpl<EV>(cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> CounterCache getCounterCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new CounterCacheRedisImpl(id, cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> CounterCache getCounterCache(String id, long initCount, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			final CounterCache counterCache = getCounterCache(id, cacheFactory, cacheUtil);
			if (!cacheUtil.hasKey(id))
			{//新的计数器的时候,才执行增量!已有的计数器,直接返回
				counterCache.incr(initCount);
			}
			return counterCache;
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> CounterGroupCache getCounterGroupCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil, String... columns)
		{
			return new CounterGroupCacheRedisImpl(id, cacheFactory, cacheUtil, columns);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> ListCache<EV> getListCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new ListCacheRedisImpl<EV>(id, cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> QueueCache<EV> getQueueCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new QueueCacheRedisImpl<EV>(id, cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> SetCache<EV> getSetCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new SetCacheRedisImpl<EV>(id, cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> ZSetCache<EV> getZSetCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new ZSetCacheRedisImpl<EV>(id, cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> HashtableCache<EK,EV> getHashtableCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new HashtableCacheRedisImpl<EK,EV>(id, cacheFactory, cacheUtil);
		}
	},
	NO("NO")
	{
		@Override
		public <EK extends Serializable,EV extends Serializable> ValueCache<EV> getValueCache(CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new ValueCache<EV>(cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> CounterCache getCounterCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new CounterCache(id, cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> CounterCache getCounterCache(String id, long initCount, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			final CounterCache counterCache = getCounterCache(id, cacheFactory, cacheUtil);
			if (!cacheUtil.hasKey(id))
			{//新的计数器的时候,才执行增量!已有的计数器,直接返回
				counterCache.incr(initCount);
			}
			return counterCache;
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> CounterGroupCache getCounterGroupCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil, String... columns)
		{
			return new CounterGroupCache(id, cacheFactory, cacheUtil, columns);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> ListCache<EV> getListCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new ListCache<EV>(id, cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> QueueCache<EV> getQueueCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new QueueCache<EV>(id, cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> SetCache<EV> getSetCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new SetCache<EV>(id, cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> ZSetCache<EV> getZSetCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new ZSetCache<EV>(id, cacheFactory, cacheUtil);
		}

		@Override
		public <EK extends Serializable,EV extends Serializable> HashtableCache<EK,EV> getHashtableCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
		{
			return new HashtableCache<EK,EV>(id, cacheFactory, cacheUtil);
		}
	};
	//code
	private final String	code;

	/**
	 * 构造函数
	 * @param code 缓存类型编码
	 * @param cacheUtil 对应类型的缓存工具
	 */
	CacheType(String code)
	{
		this.code = code;
	}

	/**
	 * 获取缓存编码
	 * @return
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * 获取基础缓存对象
	 * @param cacheFactory 缓存工厂
	 * @return
	 */
	public <EK extends Serializable,EV extends Serializable> ValueCache<EV> getValueCache(CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
	{
		throw new AbstractMethodError();
	}

	/**
	 * 获取缓存计数器对象
	 * @param id 缓存ID
	 * @return 缓存的计数器对象
	 */
	public <EK extends Serializable,EV extends Serializable> CounterCache getCounterCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
	{
		throw new AbstractMethodError();
	}

	/**
	 * 获取带初值的缓存计数器对象
	 * @param id 缓存ID
	 * @param initCount 初值
	 * @return 缓存的计数器对象
	 */
	public <EK extends Serializable,EV extends Serializable> CounterCache getCounterCache(String id, long initCount, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
	{
		throw new AbstractMethodError();
	}

	/**
	 * 获取缓存计数器组对象
	 * @param id 计数器组id
	 * @param columns 字段列表
	 * @return 缓存的计数器组对象
	 */
	public <EK extends Serializable,EV extends Serializable> CounterGroupCache getCounterGroupCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil, String... columns)
	{
		throw new AbstractMethodError();
	}

	/**
	 * 获取缓存的List
	 * @param id 缓存ID
	 * @return 缓存的List对象
	 */
	public <EK extends Serializable,EV extends Serializable> ListCache<EV> getListCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
	{
		throw new AbstractMethodError();
	}

	/**
	 * 获取缓存的队列 Queue
	 * @param id 缓存ID
	 * @return 缓存的Queue对象
	 */
	public <EK extends Serializable,EV extends Serializable> QueueCache<EV> getQueueCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
	{
		throw new AbstractMethodError();
	}

	/**
	 * 获取缓存的Set对象
	 * @param id 缓存的ID
	 * @param cacheFactory 缓存工厂对象
	 * @return
	 */
	public <EK extends Serializable,EV extends Serializable> SetCache<EV> getSetCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
	{
		throw new AbstractMethodError();
	}

	/**
	 * 获取缓存ZSet
	 * @param id 缓存ID
	 * @param cacheFactory 缓存工厂对象
	 * @return 缓存的ZSet对象
	 */
	public <EK extends Serializable,EV extends Serializable> ZSetCache<EV> getZSetCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
	{
		throw new AbstractMethodError();
	}

	/**
	 * 获取缓存的Hashtable
	 * @param id 缓存id
	 * @return 缓存的Hashtable对象
	 */
	public <EK extends Serializable,EV extends Serializable> HashtableCache<EK,EV> getHashtableCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil cacheUtil)
	{
		throw new AbstractMethodError();
	}
	/*private static final Map<String,CacheType>	codeLookup	= new ConcurrentHashMap<String,CacheType>(3);
	static
	{
		for (final CacheType type : EnumSet.allOf(CacheType.class))
		{
			CacheType.codeLookup.put(type.getCode(), type);
		}
	}*/
	/*public static CacheType fromCode(String code)
	{
		final CacheType cacheType = StrUtil.isStrTrimNull(code) ? CacheType.REDIS : CacheType.codeLookup.get(code); //默认是REDIS
		if (cacheType == null)
		{
			throw new IllegalArgumentException("未知的缓存类型");
		}
		return cacheType;
	}*/
}
