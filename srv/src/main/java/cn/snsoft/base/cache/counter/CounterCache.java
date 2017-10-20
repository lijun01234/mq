package cn.snsoft.base.cache.counter;

import java.util.concurrent.TimeUnit;
import cn.snsoft.base.cache.CacheFactory;
import cn.snsoft.base.cache.util.CacheUtil;
/**
 * <p>标题： 计数器,基类</p>
 * <p>功能： 支持集群的计数器</p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午1:11:53</p>
 * <p>类全名：cn.snsoft.component.search.comm.cache.ICounterCache</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.NO
 */
public class CounterCache
{
	protected final String		key;
	protected final CacheUtil	cacheUtil;	//spring的Redis模板

	public CounterCache(String id, CacheFactory<?,?> cacheFactory, CacheUtil cacheUtil)
	{
		key = id;
		this.cacheUtil = cacheUtil;
	}

	/**
	 * 设置超时时间
	 * @param timeout
	 * @param unit
	 */
	public void updateTimeout(long timeout, TimeUnit unit)
	{
		this.cacheUtil.updateTimeout(key, timeout, unit);
	}

	/**
	 * 计数器加1并返回加1后的结果
	 * @return 计数器数值
	 * 		先天原子操作,不需要加同步锁
	 */
	public long incr()
	{
		return 1;
	}

	/**
	 * 计数器减1并返回减1后的结果
	 * @return 计数器数值
	 * 		先天原子操作,不需要加同步锁
	 */
	public long decr()
	{
		return -1;
	}

	/**
	 * 计数器加上指定的值并返回运算后的结果
	 * @param delta 计数器的增减量,正数表示加,负数表示减
	 * @return 计数器数值
	 * 		先天原子操作,不需要加同步锁
	 */
	public long incr(long count)
	{
		return count;
	}

	/**
	 * 获取计数器的当前值
	 * @return 计数器当前值
	 */
	public long nowValue()
	{
		return 0;
	}

	/**
	 * 计数器销毁
	 * 		注意:计数器销毁后,销毁后仍然可以继续使用,继续使用会从0开始重新计数!
	 * 		此时,Redis中,已经没有这个计数器了,但是当调用计数器的其他方法(nowValue除外)的时候,还会重新生成该计数器.
	 */
	public void destroy()
	{
		cacheUtil.delete(key);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (key == null ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final CounterCache other = (CounterCache) obj;
		if (key == null)
		{
			if (other.key != null)
			{
				return false;
			}
		} else if (!key.equals(other.key))
		{
			return false;
		}
		return true;
	}
}
