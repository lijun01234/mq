package cn.snsoft.base.cache.redis;

import java.util.concurrent.TimeUnit;
/**
 * <p>标题：通过REDIS记录某个操作是否完成 </p>
 * <p>所属模块： REDIS 缓存</p>
 * <p>版权： Copyright © 2015 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2015年3月16日 下午6:26:54</p>
 * <p>类全名：cn.snsoft.component.cache.util.InitCompLock</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
public class RedisCompLock extends RedisCount
{
	public RedisCompLock(String name)
	{
		super(name);
	}

	public RedisCompLock(String name, String redisServName)
	{
		super(name, redisServName);
	}

	/**
	 * 判断是否锁定状态
	 * @return
	 */
	public boolean isLock()
	{
		return isLock(null);
	}

	public boolean isLock(String key)
	{
		long lockValue = query(key);
		boolean isLock = lockValue > 0;
		return isLock;
	}

	/**
	 * 锁定的同时判断是否已锁定   lock = isLock + addLock  根据逻辑尽量使用此方法  完全的同步操作
	 * @return
	 */
	public boolean lock()
	{
		long lockValue = addLock();
		return lockValue > 1;
	}

	/**
	 * 锁定的同时判断是否已锁定   lock = isLock + addLock  根据逻辑尽量使用此方法  完全的同步操作
	 * @return
	 */
	public boolean lock(String key)
	{
		long lockValue = addLock(key);
		return lockValue > 1;
	}

	/**
	 * 锁定的同时判断是否已锁定   lock = isLock + addLock  根据逻辑尽量使用此方法  完全的同步操作
	 * @return
	 */
	public boolean lock(String key, long days)
	{
		return lock(key, days, TimeUnit.DAYS);
	}

	public boolean lock(String key, long days, TimeUnit unit)
	{
		long lockValue = addLock(key, days, unit);
		return lockValue > 1;
	}

	/**
	 * 锁定  此方法不建议使用，优先使用 lock 方法
	 * @return
	 */
	public long addLock()
	{
		return incr(null);
	}

	public long addLock(String key)
	{
		return incr(key);
	}

	public long addLock(String key, long days)
	{
		return addLock(key, days, TimeUnit.DAYS);
	}

	public long addLock(String key, long days, TimeUnit unit)
	{
		return incr(key, days, unit);
	}

	/**
	 * 解锁操作
	 * @param key
	 */
	public void unLock(String key)
	{
		destroy(key);
	}

	public void unLock()
	{
		destroy(null);
	}
}
