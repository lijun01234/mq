package cn.snsoft.base.cache.redis;

import java.util.concurrent.TimeUnit;
import cn.snsoft.base.cache.CacheFactory;
import cn.snsoft.base.cache.counter.CounterCache;
import cn.snsoft.util.ApplicationContextUtil;
import cn.snsoft.util.ConfigUtil;
import cn.snsoft.util.StrUtils;
/**
 * <p>标题： Redis 计数器</p>
 * <p>功能： </p>
 * <p>所属模块： COMPONENT</p>
 * <p>版权： Copyright © 2015 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2015年7月14日 上午10:34:22</p>
 * <p>类全名：cn.snsoft.component.cache.util.RedisCount</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
public class RedisCount
{
	private final String	name;
	private final String	workspc;
	private final String	redisServName;

	public RedisCount(String name)
	{
		this(name, null);
	}

	public RedisCount(String name, String redisServName)
	{
		this.name = name.toLowerCase();
		this.workspc = ConfigUtil.getElementValue("SYSDEF.WORKSPACE").toLowerCase();//CommDatabaseManager.getWorkSpace().toLowerCase();
		this.redisServName = redisServName;
	}

	/**
	 * 查询现在计数数据  
	 */
	public long query()
	{
		return query(null);
	}

	public long query(String key)
	{
		CounterCache counterCache = getCounterCache(key);
		long count = StrUtils.obj2long(counterCache.nowValue(), 0);
		return count;
	}

	/**
	 * count 计数  
	 */
	public long count(long count)
	{
		return count(null, count);
	}

	public long count(String key, long count)
	{
		CounterCache counterCache = getCounterCache(key);
		return counterCache.incr(count);
	}

	/**
	 * 加一计数 days 为保留天数
	 */
	public long incr()
	{
		return incr(null);
	}

	public long incr(String key)
	{
		CounterCache counterCache = getCounterCache(key);
		return counterCache.incr();
	}

	public long incr(String key, long days)
	{
		return incr(key, days, TimeUnit.DAYS);
	}

	public long incr(String key, long timeout, TimeUnit unit)
	{
		CounterCache counterCache = getCounterCache(key);
		long count = counterCache.incr();
		counterCache.updateTimeout(timeout, unit);
		return count;
	}

	/**
	 * 更新缓存保存时间
	 * @param timeout
	 * @param unit
	 */
	public void updateTimeout(long timeout, TimeUnit unit)
	{
		updateTimeout(null, timeout, unit);
	}

	public void updateTimeout(String key, long timeout, TimeUnit unit)
	{
		CounterCache counterCache = getCounterCache(key);
		counterCache.updateTimeout(timeout, unit);
	}

	/**
	 * 减一计数
	 */
	public long decr()
	{
		return decr(null);
	}

	public long decr(String key)
	{
		CounterCache counterCache = getCounterCache(key);
		return counterCache.decr();
	}

	/**
	 * 销毁
	 */
	public void destroy()
	{
		destroy(null);
	}

	public void destroy(String key)
	{
		CounterCache counterCache = getCounterCache(key);
		counterCache.destroy();
	}

	private CacheFactory<?,?>	cacheFactory;

	private CounterCache getCounterCache(String key)
	{
		return getCounterCache(key, 0);
	}

	private CounterCache getCounterCache(String key, int times)
	{
		try
		{
			times++;
			if (this.cacheFactory == null)
			{
				if (redisServName == null)
				{
					this.cacheFactory = ApplicationContextUtil.getBean("component-CacheFactory");
				} else
				{
					SNJedisConnectionFactoryFactory connectionFactoryFactory = ApplicationContextUtil.getBean("component-SNJedisConnectionFactoryFactory");
					this.cacheFactory = connectionFactoryFactory.getCacheFactory(redisServName);
				}
			}
			CounterCache counterCache = this.cacheFactory.getCounterCache(getKey(key));
			if (counterCache == null)
			{
				throw new java.lang.RuntimeException();
			}
			return counterCache;
		} catch (Exception e)
		{
			if (times > 2)
			{
				return null;
			}
			runSleep(10000);
			return getCounterCache(key, times++);
		}
	}

	/**
	 * 线程等待
	 * @param space
	 */
	private void runSleep(long space)
	{
		try
		{
			Thread.sleep(space);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private String getKey(String key)
	{
		String lockKey = workspc + '|' + this.name;
		if (key != null && key.trim().length() > 0)
		{
			lockKey += '|' + key.toLowerCase();
		}
		return lockKey;
	}
}
