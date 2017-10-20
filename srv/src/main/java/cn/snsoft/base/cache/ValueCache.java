package cn.snsoft.base.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.base.thread.BasThreadFactory;
/**
 * <p>标题： 简单对象,基类</p>
 * <p>功能： 支持集群的简单对象缓存</p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午12:50:15</p>
 * <p>类全名：cn.snsoft.component.search.comm.cache.IValueCache</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.NO
 */
public class ValueCache<EV extends Serializable>
{
	protected final CacheUtil<EV>		cacheUtil;
	protected static ExecutorService	threadPool;

	public ValueCache(CacheFactory<?,EV> cacheFactory, CacheUtil<EV> cacheUtil)
	{
		this.cacheUtil = cacheUtil;
	}

	public ExecutorService getThreadPool()
	{
		if (threadPool == null)
		{
			threadPool = Executors.newSingleThreadExecutor(new BasThreadFactory("ValueCache"));
		}
		return threadPool;
	}

	/**
	 * 向缓存中塞一个基本对象
	 * @param key 缓存key
	 * @param value 需要缓存的对象
	 * @return 执行成功返回true
	 * @author 王立鹏
	 */
	public boolean put(String key, EV value)
	{
		return false;
	}

	/**
	 * 向缓存中塞一个基本对象,如果Key存在则不覆盖
	 * @param key 缓存key
	 * @param value 需要缓存的对象
	 * @return 是否塞进缓存了
	 * @author 王立鹏
	 */
	public boolean putIfAbsent(String key, EV value)
	{
		return false;
	}

	/**
	 * 向缓存中塞一个会自动过期的缓存对象
	 * @param key 缓存key
	 * @param value 需要缓存的对象
	 * @param timeout 超时时间
	 * @param unit 时间单位,TimeUnit类型
	 * @author 王立鹏
	 */
	public boolean put(String key, EV value, long timeout, TimeUnit unit)
	{
		return false;
	}

	/**
	 * 更新缓存中的对象的过期时间!
	 * @param key 缓存key
	 * @param timeout 超时时间
	 * @param unit 时间单位,TimeUnit类型
	 * @return 是否更新成功
	 */
	public boolean updateTimeout(String key, long timeout, TimeUnit unit)
	{
		return cacheUtil.updateTimeout(key, timeout, unit);
	}

	/**
	 * 一起向缓存中塞多个元素
	 * @param m 需要塞到缓存中全部对象的map
	 */
	public void multiPut(Map<String,? extends EV> m)
	{
	}

	/**
	 * 异步方法
	 * 与上面的multiPut方法功能相同,但是不阻塞主线程!
	 */
	public void asyncMultiPut(final Map<String,? extends EV> m)
	{
		final ExecutorService threadPool = getThreadPool();
		threadPool.submit(new Callable<Boolean>()
		{
			@Override
			public Boolean call() throws Exception
			{
				ValueCache.this.multiPut(m);
				return null;
			}
		});
	}

	/**
	 * 一起向缓存中塞多个元素,如果存在则不更新
	 */
	public void multiPutIfAbsent(Map<String,? extends EV> m)
	{
	}

	/**
	 * 异步方法
	 * 与上面的multiPutIfAbsent方法功能相同,但是不阻塞主线程!
	 */
	public void asyncMultiPutIfAbsent(final Map<String,? extends EV> m)
	{
		final ExecutorService threadPool = getThreadPool();
		threadPool.submit(new Callable<Boolean>()
		{
			@Override
			public Boolean call() throws Exception
			{
				ValueCache.this.multiPutIfAbsent(m);
				return null;
			}
		});
	}

	/**
	 * 从缓存中取出旧的值,同时放入新的值
	 * @param key 缓存key
	 * @param value 新的待缓存的对象
	 * @return 缓存中原有的对象
	 */
	public EV getAndPut(String key, EV value)
	{
		return null;
	}

	/**
	 * 获取缓存的对象
	 * @param key 缓存key
	 * @return 缓存的对象
	 */
	public EV get(String key)
	{
		return null;
	}

	/**
	 * 批量获取缓存对象
	 * @param keys 缓存key,需要装到Collection中
	 * @return 缓存的对象List,按照key的顺序排序
	 */
	public List<EV> multiGet(Collection<String> keys)
	{
		return new ArrayList<EV>();
	}

	/**
	 * 批量获取缓存对象
	 * @param keys 缓存key数组
	 * @return 缓存的对象List,按照key的顺序排序
	 */
	public List<EV> multiGet(String... keys)
	{
		if (keys == null || keys.length == 0)
		{
			return null;
		}
		final List<String> list = new ArrayList<>(keys.length);
		for (final String k : keys)
		{
			list.add(k);
		}
		return multiGet(list);
	}

	/**
	 * 删除指定的缓存
	 * @param k
	 */
	public boolean delete(String key)
	{
		try
		{
			cacheUtil.delete(key);
			return true;
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return false;
		}
	}
}
