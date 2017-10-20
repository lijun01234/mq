package cn.snsoft.base.cache;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import cn.snsoft.base.cache.counter.CounterCache;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.util.ConfigUtil;
import cn.snsoft.util.RedisCacheUtil;
import cn.snsoft.util.StrUtils;
/**
 * <p>标题： 缓存工厂类</p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午1:21:37</p>
 * <p>类全名：cn.snsoft.component.cache.util.CacheFactory</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		这个对象是其他系统使用缓存的入口
 * 		请使用依赖注入component-CacheFactory
 * 		泛型类型类型:
 * 				EK:Hashtable类型缓存的hash的key(仅在使用HashtableCache时有用,用其他类型缓存的时候,可以写通配符?);
 * 				EV:被缓存的对象的类型
 */
@Component("component-CacheFactory")
public final class CacheFactory<EK extends Serializable,EV extends Serializable>
{
	private CacheType		cacheType;		//单例多线程,该值不可以改动!
	private boolean			bCacheOn;		//单例多线程,该值不可以改动!
	@Resource(name = "component-RedisCacheUtil")
	private RedisCacheUtil	redisCacheUtil;

	CacheFactory()
	{//禁止new对象,使用依赖注入
	}

	public CacheFactory(RedisCacheUtil redisCacheUtil)
	{//禁止new对象,使用依赖注入
		this.redisCacheUtil = redisCacheUtil;
	}

	@PostConstruct
	public void _init()
	{
		final String l2Cache = ConfigUtil.getElementValue("L2Cache");//DataConfig.getConfig("L2Cache", "ON");
		bCacheOn = StrUtils.isStrIn("ON,on,YES,yes,TRUE,true", l2Cache);
		cacheType = bCacheOn ? CacheType.REDIS : CacheType.NO;
	}

	/**
	 * 获取简单对象缓存
	 * @return 简单对象缓存
	 */
	public ValueCache<EV> getValueCache()
	{
		return cacheType.getValueCache(this, getCacheUtil());
	}

	/**
	 * 获取计数器
	 * @param key 计数器id
	 * @return 计数器缓存对象
	 */
	public CounterCache getCounterCache(final String id)
	{
		return cacheType.getCounterCache(id, this, getCacheUtil());
	}

	/**
	 * 获取带有初值的计数器
	 * @param id 计数器id
	 * @param initCount 初值
	 * @return 计数器缓存对象
	 */
	public CounterCache getCounterCache(final String id, final long initCount)
	{
		return cacheType.getCounterCache(id, initCount, this, getCacheUtil());
	}

	/**
	 * 获取计数器组,暂时不传入计数字段
	 * @param id
	 * @return
	 */
	public CounterGroupCache getCounterGroupCache(final String id)
	{
		return getCounterGroupCache(id, (String[]) null);
	}

	/**
	 * 获取计数器组
	 * @param id 计数器组id
	 * @param columns 计数字段名
	 * @return 计数器组
	 */
	public CounterGroupCache getCounterGroupCache(final String id, final String... columns)
	{
		return cacheType.getCounterGroupCache(id, this, getCacheUtil(), columns);
	}

	/**
	 * 获取列表缓存
	 * @param key 列表id
	 * @return 列表缓存
	 */
	public ListCache<EV> getListCache(final String id)
	{
		return cacheType.getListCache(id, this, getCacheUtil());
	}

	/**
	 * 获取队列缓存
	 * @param key 队列id
	 * @return 队列缓存
	 */
	public QueueCache<EV> getQueueCache(final String id)
	{
		return cacheType.getQueueCache(id, this, getCacheUtil());
	}

	/**
	 * 获取集合缓存
	 * @param key 集合id
	 * @return 集合缓存
	 */
	public SetCache<EV> getSetCache(final String id)
	{
		return cacheType.getSetCache(id, this, getCacheUtil());
	}

	/**
	 * 获取按权重排序的集合缓存
	 * @param key 集合id
	 * @return 按权重排序的集合缓存
	 */
	public ZSetCache<EV> getZSetCache(final String id)
	{
		return cacheType.getZSetCache(id, this, getCacheUtil());
	}

	/**
	 * 获取Hash缓存
	 * @param key Hash缓存id
	 * @return Hash缓存
	 */
	public HashtableCache<EK,EV> getHashtableCache(final String id)
	{
		return cacheType.getHashtableCache(id, this, getCacheUtil());
	}

	/**
	 * 获取CacheUtil对象
	 * @return CacheUtil对象
	 */
	public CacheUtil getCacheUtil()
	{
		return bCacheOn ? redisCacheUtil : new CacheUtil();
	}

	public boolean isbCacheOn()
	{
		return bCacheOn;
	}
}
