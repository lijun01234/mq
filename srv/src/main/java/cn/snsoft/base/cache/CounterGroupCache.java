package cn.snsoft.base.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import cn.snsoft.base.cache.util.CacheUtil;
/**
 * <p>
 * 标题：计数器组,基类
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 所属模块：
 * </p>
 * <p>
 * 版权： Copyright (c) 2013
 * </p>
 * <p>
 * 公司: 北京南北天地科技股份有限公司
 * </p>
 * <p>
 * 创建日期：2013-1-15
 * </p>
 * <p>
 * 类全名：cn.snsoft.component.cache.model.impl.CounterGroupCache
 * </p>
 * 作者：王立鹏 初审： 复审：
 * 
 * @version 8.0 缓存类型为CacheType.NO
 */
public class CounterGroupCache
{
	protected final CacheUtil<?>		cacheUtil;
	protected final CacheFactory<?,?>	cacheFactory;
	protected final String				key;

	public CounterGroupCache(String id, CacheFactory<?,?> cacheFactory, CacheUtil<?> cacheUtil, String... column)
	{
		key = id;
		this.cacheUtil = cacheUtil;
		this.cacheFactory = cacheFactory;
	}

	/**
	 * 对指定字段的计数器加1并返回加1后的结果
	 * 
	 * @param column
	 *            指定的字段
	 * @return 计数器数值
	 */
	public long incr(String column)
	{
		return 1;
	}

	public boolean remove(String column)
	{
		return false;
	}

	/**
	 * 对指定字段的计数器加1并返回加1后的结果
	 * 
	 * @param columns
	 *            指定的字段(变长参数)
	 * @return 计数器数值List,顺序同传入参数的顺序
	 */
	public List<Long> incr(String... columns)
	{
		if (columns == null || columns.length == 0)
		{
			return new ArrayList<Long>(0);
		}
		final List<Long> list = new ArrayList<>();
		for (int i = 0; i < columns.length; i++)
		{
			list.add(incr(columns[i]));
		}
		return list;
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
	 * 对指定字段的计数器减1并返回减1后的结果
	 * 
	 * @param column
	 *            指定的字段
	 * @return 计数器数值 先天原子操作,不需要加同步锁
	 */
	public long decr(String column)
	{
		return -1;
	}

	/**
	 * 对指定字段的计数器减1并返回减1后的结果
	 * 
	 * @param columns
	 *            指定的字段(变长参数)
	 * @return 计数器数值 先天原子操作,不需要加同步锁
	 */
	public List<Long> decr(String... columns)
	{
		if (columns == null || columns.length == 0)
		{
			return new ArrayList<Long>(0);
		}
		final List<Long> list = new ArrayList<>();
		for (int i = 0; i < columns.length; i++)
		{
			list.add(decr(columns[i]));
		}
		return list;
	}

	/**
	 * 在指定字段的计数器加上指定的值并返回运算后的结果
	 * 
	 * @param delta
	 *            计数器的增减量,正数表示加,负数表示减
	 * @param column
	 *            指定的字段
	 * @return 计数器数值
	 */
	public long incr(long count, String column)
	{
		return count;
	}

	/**
	 * 在指定字段的计数器加上指定的值并返回运算后的结果
	 * 
	 * @param delta
	 *            计数器的增减量,正数表示加,负数表示减
	 * @param columns
	 *            指定的字段(变长参数)
	 * @return 计数器数值List,顺序与 传入的参数相同
	 */
	public List<Long> incr(long count, String... columns)
	{
		if (columns == null || columns.length == 0)
		{
			return new ArrayList<Long>(0);
		}
		final List<Long> list = new ArrayList<>();
		for (int i = 0; i < columns.length; i++)
		{
			list.add(incr(count, columns[i]));
		}
		return list;
	}

	/**
	 * 在指定字段的计数器加上指定的值并返回运算后的结果
	 * 
	 * @param columnMap
	 *            key为指定的字段,value为计数器的增减量,正数表示加,负数表示减
	 * @return 计数器计数之后的各字段的值,Map类型,key是字段名,Value是计数处理后的值. Map中键值对的顺序与传入的Map相同.
	 */
	public Map<String,Long> incr(Map<String,Long> columnMap)
	{
		if (columnMap == null || columnMap.size() == 0)
		{
			return new LinkedHashMap<String,Long>(0);
		}
		final Map<String,Long> map = new LinkedHashMap<String,Long>(columnMap.size());
		for (final Map.Entry<String,Long> entry : columnMap.entrySet())
		{// Map这种办法遍历是速度最快的!
			final String column = entry.getKey();
			final long incr = entry.getValue();
			map.put(column, incr(incr, column));
		}
		return map;
	}

	/**
	 * 获取计数器指定字段的当前值
	 * 
	 * @param column
	 *            指定的字段
	 * @return 计数器当前值
	 */
	public long nowValue(String column)
	{
		return 0;
	}

	/**
	 * 获取计数器指定字段的当前值
	 * 
	 * @param columns
	 *            指定的字段(变长参数)
	 * @return 计数器当前值List,顺序与传入的字段顺序相同 2016年05月03日
	 *         修改了一下执行过程,一次获取整个组的数值,在从里面筛选列需要的.
	 */
	public List<Long> nowValue(String... columns)
	{
		if (columns == null || columns.length == 0)
		{
			return new ArrayList<Long>(0);
		}
		Map<String,Long> map = nowValue();// [add] by wlp 2016_05_03
		final List<Long> list = new ArrayList<>(columns.length);
		for (int i = 0; i < columns.length; i++)
		{
			// [modify] by wlp 2016_05_03
			// list.add(nowValue(columns[i]));
			list.add(map.get(columns[i]));
			// [end add]
		}
		return list;
	}

	/**
	 * 取计数器全部字段的值
	 * 
	 * @return Map,key是字段名,value是计数器的值
	 */
	public Map<String,Long> nowValue()
	{
		return new HashMap<String,Long>(0);
	}

	/**
	 * 计数器销毁 注意:计数器销毁后,销毁后仍然可以继续使用,继续使用会从0开始重新计数!
	 */
	public void destroy()
	{
		cacheUtil.delete(key);
	}

	/**
	 * 一次去多组计数器的值!
	 * 
	 * @param ids
	 *            计数器IDs(变长参)
	 * @return 计数器值 由于计数器的值的序列化与对象不同,所以不能批量取出,这里其实是有效率问题的,这个问题后面想办法解决!
	 */
	public List<Map<String,Long>> getNowValuesById(String... ids)
	{
		if (ids == null || ids.length == 0)
		{
			ids = new String[] { this.key };
		}
		List<Map<String,Long>> list = new ArrayList<>(ids.length);
		for (String id : ids)
		{
			list.add(cacheFactory.getCounterGroupCache(id).nowValue());
		}
		return list;
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
		final CounterGroupCache other = (CounterGroupCache) obj;
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
