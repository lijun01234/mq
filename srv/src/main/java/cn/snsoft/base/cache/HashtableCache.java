package cn.snsoft.base.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.base.thread.BasThreadFactory;
/**
 * <p>标题： 哈希表,基类</p>
 * <p>功能： 支持集群的哈希表缓存</p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午12:53:18</p>
 * <p>类全名：cn.snsoft.component.search.comm.cache.IHashCache</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.NO
 */
public class HashtableCache<EK extends Serializable,EV extends Serializable> implements Map<EK,EV>
{
	protected final String				key;
	protected final CacheUtil<EV>		cacheUtil;	//spring的Redis模板
	protected static ExecutorService	threadPool;

	public HashtableCache(String id, CacheFactory<EK,EV> cacheFactory, CacheUtil<EV> cacheUtil)
	{
		this.key = id;
		this.cacheUtil = cacheUtil;
	}

	public ExecutorService getThreadPool()
	{
		if (threadPool == null)
		{
			threadPool = Executors.newSingleThreadExecutor(new BasThreadFactory("HashtableCache"));
		}
		return threadPool;
	}

	/**
	 * 向Hashtable中塞指定key和value的元素
	 * @param entryKey 指定key
	 * @param entryValue 指定value
	 * @return 塞入的value
	 * @author 王立鹏
	 * 		Redis->put
	 */
	@Override
	public EV put(EK entryKey, EV entryValue)
	{
		return entryValue;
	}

	/**
	 * 将元素塞到Hashtable中,如存在相同的key,则不处理
	 * @param entryKey key
	 * @param entryValue value
	 * @return 是否塞到Hashtable中了
	 * 		Redis->putIfAbsent
	 * Java 1.8 Map接口,增加了putIfAbsent接口方法,这里改成跟Java 1.8一致的返回值形式
	 * Java 1.8 @return the previous value associated with the specified key, or
	 *         {@code null} if there was no mapping for the key.
	 *         (A {@code null} return can also indicate that the map
	 *         previously associated {@code null} with the key,
	 *         if the implementation supports null values.)
	 * @author 王立鹏
	 */
	public EV putIfAbsent(EK entryKey, EV entryValue)
	{
		return null;
	}

	/**
	 * 将指定的Map容器中的所有键值对,全部塞到Hashtable中
	 * @param map 指定的Map
	 * @author 王立鹏
	 * 		Redis->putAll
	 */
	@Override
	public void putAll(Map<? extends EK,? extends EV> map)
	{
		return;
	}

	/**
	 * 异步方法
	 * 与上面的putAll方法功能相同,但是不阻塞主线程!
	 */
	public void asyncPutAll(final Map<? extends EK,? extends EV> map)
	{
		final ExecutorService threadPool = getThreadPool();
		threadPool.submit(new Callable<Object>()
		{
			@Override
			public Object call() throws Exception
			{
				HashtableCache.this.putAll(map);
				return null;
			}
		});
	}

	/**
	 * 取出指定key对应的value
	 * @param entryKey 指定key
	 * @return 对应的value
	 * @author 王立鹏
	 * 		Redis->get
	 */
	@Override
	public EV get(Object entryKey)
	{
		return null;
	}

	/**
	 * 一次从Hashtable中获取多个值
	 * @param entryKeys 包含key的Collection对象
	 * @return 包含Value的List对象,顺序与传入的key的顺序相同
	 * @author 王立鹏
	 * 		Redis->multiGet
	 */
	public List<EV> multiGet(Collection<EK> entryKeys)
	{
		return new ArrayList<EV>(0);
	}

	/**
	 * 一次从Hashtable中获取多个值
	 * @param entryKeys key数组或序列
	 * @return 包含Value的List对象,顺序与传入的key的顺序相同
	 * @author 王立鹏
	 * 		Redis->multiGet
	 */
	public List<EV> multiGet(@SuppressWarnings("unchecked") EK... entryKeys)
	{
		if (entryKeys == null || entryKeys.length == 0)
		{
			return new ArrayList<EV>(0);
		}
		final List<EK> list = new ArrayList<>(entryKeys.length);
		for (final EK ek : entryKeys)
		{
			list.add(ek);
		}
		return multiGet(list);
	}

	/**
	 * 返回Hashtable中的键值对个数
	 * @return Hashtable中的键值对个数
	 * @author 王立鹏
	 * 		Redis->size
	 */
	@Override
	public int size()
	{
		return 0;
	}

	/**
	 * 判断Hashtable中是否包含指定的key
	 * @param entryKey 指定的key
	 * @return 包含返回true
	 * @author 王立鹏
	 * 		Redis->hasKey
	 */
	@Override
	public boolean containsKey(Object entryKey)
	{
		return false;
	}

	/**
	 * 判断Hashtable中是否包含指定的value,包含返回true
	 * @param entryValue 指定的value
	 * @return 包含返回true
	 * @author 王立鹏
	 * 		注意:这个方法不是Redis提供的,有效率问题,使用需要谨慎!
	 * 		Redis未提供
	 */
	@Override
	public boolean containsValue(Object entryValue)
	{
		return values().contains(entryValue);
	}

	/**
	 * 返回Hashtable是否为空
	 * @return 为空返回true
	 * @author 王立鹏
	 * 		Redis未提供
	 */
	@Override
	public boolean isEmpty()
	{
		return size() == 0;
	}

	/**
	 * 返回包含所有Key的Set
	 * @return 包含所有Key的Set
	 * @author 王立鹏
	 * 		Redis->keys
	 */
	@Override
	public Set<EK> keySet()
	{
		return new HashSet<EK>(0);
	}

	/**
	 * 删除Hashtable中指定Key的键值对
	 * @param entryKey
	 * @return 返回删除的元素的值
	 * @author 王立鹏
	 * 		注意:非原子操作!返回值可能有延迟(小概率事件)
	 * 		Redis->delete
	 */
	@Override
	public EV remove(Object entryKey)
	{
		return null;
	}

	/**
	 * 批量删除Hashtable中的指定的多个key
	 * @param entryKeys entryKey的集合
	 * @return 返回删除了的key的集合对应的值,返回的list中值的顺序与传入的key的顺序相同,
	 * 		返回的list的size与传入的key的个数相同,如果key在缓存的hash中不存在,则在list中该位置为null.
	 * @author 王立鹏
	 */
	public List<EV> removeAll(Collection<?> entryKeys)
	{
		if (entryKeys == null || entryKeys.isEmpty())
		{
			return new ArrayList<EV>(0);
		}
		final List<EV> list = new ArrayList<>(entryKeys.size());
		for (final Object ek : entryKeys)
		{
			list.add(remove(ek));
		}
		return list;
	}

	/**
	 * 批量删除Hashtable中的指定的多个key
	 * @param entryKeys entryKey的变长参
	 * @return  返回删除了的key的集合对应的值,返回的list中值的顺序与传入的key的顺序相同,
	 * 		返回的list的size与传入的key的个数相同,如果key在缓存的hash中不存在,则在list中该位置为null.
	 * @author 王立鹏
	 */
	public List<EV> removeAll(Object... entryKeys)
	{
		if (entryKeys == null || entryKeys.length == 0)
		{
			return new ArrayList<EV>(0);
		}
		final List<EV> list = new ArrayList<>(entryKeys.length);
		for (final Object ek : entryKeys)
		{
			list.add(remove(ek));
		}
		return list;
	}

	/**
	 * 与上面的multiRemove方法功能相同,但是不阻塞主线程!
	 * @param entryKeys entryKey的集合
	 * @return Future对象
	 * 		Future对象的get()方法将获取实际执行的返回值,但是get()将阻塞线程.
	 * @author 王立鹏
	 */
	public Future<List<EV>> asyncRemoveAll(final Collection<?> entryKeys)
	{
		final ExecutorService threadPool = getThreadPool();
		final Future<List<EV>> future = threadPool.submit(new Callable<List<EV>>()
		{
			@Override
			public List<EV> call() throws Exception
			{
				return HashtableCache.this.removeAll(entryKeys);
			}
		});
		return future;
	}

	/**
	 * 与上面的multiRemove方法功能相同,但是不阻塞主线程!
	 * @param entryKeys entryKey的变长参
	 * @return Future对象
	 * 		Future对象的get()方法将获取实际执行的返回值,但是get()将阻塞线程.
	 * @author 王立鹏
	 */
	public Future<List<EV>> asyncRemoveAll(final Object... entryKeys)
	{
		final ExecutorService threadPool = getThreadPool();
		final Future<List<EV>> future = threadPool.submit(new Callable<List<EV>>()
		{
			@Override
			public List<EV> call() throws Exception
			{
				return HashtableCache.this.removeAll(entryKeys);
			}
		});
		return future;
	}

	/**
	 * 返回包含所有Value的List
	 * @return 包含所有Value的List
	 * @author 王立鹏
	 * 		Redis->values
	 */
	@Override
	public List<EV> values()
	{
		return new ArrayList<EV>(0);
	}

	/**
	 * 将缓存全部取出,装到一个本地的HashMap中
	 * @return 返回一个HashMap对象
	 * @author 王立鹏
	 * 		返回的对象是HashMap的对象,如果需要同步化,请用map=java.util.Collections.synchronizedMap(map)方法转换
	 * @author 王立鹏
	 * 		Redis->entries
	 */
	public Map<EK,EV> getLocalMap()
	{
		return new HashMap<EK,EV>(0);
	}

	/**
	 * @see destroy()
	 * 为了适配Map添加的
	 * @author 王立鹏
	 * 		Redis未提供
	 */
	@Override
	public void clear()
	{
		destroy();
	}

	/**
	 * 销毁这个Hashtable,,销毁后仍然可以继续使用,只是里面的内容变为空
	 * @author 王立鹏
	 * 		Redis未提供
	 */
	public void destroy()
	{
		cacheUtil.delete(key);
	}

	/**
	 * 取出Hashtable对应的MapEntry
	 * @return MapEntry的对象
	 * @author 王立鹏
	 * 		暂时性的:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 * 		并且数据与缓存不是实时同步的,容器,并不会修改缓存
	 * 		注意:该方法为适配Map接口,暂未实现,先不要用!实现之后遍历效率也不会提高
	 * 		Redis未提供
	 */
	@Override
	public Set<Map.Entry<EK,EV>> entrySet()
	{
		return getLocalMap().entrySet();
	}

	/**
	 * 这里不提供这个方法了.以避免混乱,以后如果需要,另行提供两个key的计数器
	 * @author 王立鹏
	 * 		Redis->increment
	 */
	//long incr(EK entryKey, long delta);
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
		@SuppressWarnings("rawtypes")
		final HashtableCache other = (HashtableCache) obj;
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
