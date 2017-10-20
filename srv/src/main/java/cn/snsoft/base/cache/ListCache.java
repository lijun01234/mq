package cn.snsoft.base.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.base.thread.BasThreadFactory;
/**
 * <p>标题： 列表,基类</p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午12:54:44</p>
 * <p>类全名：cn.snsoft.component.search.comm.cache.IListCache</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.NO
 */
public class ListCache<EV extends Serializable> implements List<EV>
{
	protected final String				key;
	protected final CacheUtil<EV>		cacheUtil;	//spring的Redis模板
	protected static ExecutorService	threadPool;

	public ListCache(String id, CacheFactory<?,EV> cacheFactory, CacheUtil<EV> cacheUtil)
	{
		this.key = id;
		this.cacheUtil = cacheUtil;
	}

	public ExecutorService getThreadPool()
	{
		if (threadPool == null)
		{
			threadPool = Executors.newSingleThreadExecutor(new BasThreadFactory("ListCache"));
		}
		return threadPool;
	}

	/**
	 * 将指定的元素追加到list的尾部
	 * @param e 需要加入到尾部的元素
	 * @return	成功返回true
	 * 		Redis->leftPush
	 * @author 王立鹏
	 */
	@Override
	public boolean add(EV element)
	{
		return false;
	}

	/**
	 * 在列表的指定位置插后面入指定元素
	 * @param index	指定位置
	 * @param element 指定元素
	 * @author 王立鹏
	 * 		注意,没有办法将元素插入到第一个元素的前面!
	 */
	@Override
	public void add(int index, EV element)
	{
		return;
	}

	/**
	 * 将指定 collection 中的所有元素都插入到列表中的指定位置
	 * @param index 指定位置(起始位置)
	 * @param c	指定的collection
	 * @return 如果此列表由于调用而发生更改,则返回 true
	 * @author 王立鹏
	 */
	@Override
	public boolean addAll(int index, Collection<? extends EV> c)
	{
		return false;
	}

	/**
	 * 异步方法
	 * 与上面的addAll方法功能相同,但是不阻塞主线程!
	 * @return Future对象
	 * 		Future对象的get()方法将获取实际执行的返回值,但是get()将阻塞线程.
	 * 		需要确认返回结果的逻辑中,不宜使用该方法,应该使用阻塞的方法addAll(index,c),并判断返回值
	 * @author 王立鹏
	 */
	public Future<Boolean> asyncAddAll(final int index, final Collection<? extends EV> c)
	{
		final ExecutorService threadPool = getThreadPool();
		final Future<Boolean> future = threadPool.submit(new Callable<Boolean>()
		{
			@Override
			public Boolean call() throws Exception
			{
				ListCache.this.addAll(c);
				return null;
			}
		});
		return future;
	}

	/**
	 * 添加指定 collection 中的所有元素到此列表的结尾,顺序是指定 collection 的迭代器返回这些元素的顺序
	 * @param c 指定的collection
	 * @return 如果此列表由于调用而发生更改,则返回 true
	 * @author 王立鹏
	 */
	@Override
	public boolean addAll(Collection<? extends EV> c)
	{
		if (c == null || c.isEmpty())
		{
			return true;
		}
		boolean b = false;
		for (final EV element : c)
		{
			b = add(element) || b;
		}
		return b;
	}

	/**
	 * 异步方法
	 * 与上面的addAll方法功能相同,但是不阻塞主线程!
	 * @return Future对象
	 * 		Future对象的get()方法将获取实际执行的返回值,但是get()将阻塞线程.
	 * 		需要确认返回结果的逻辑中,不宜使用该方法,应该使用阻塞的方法addAll(c),并判断返回值
	 * @author 王立鹏
	 */
	public Future<Boolean> asyncAddAll(final Collection<? extends EV> c)
	{
		final ExecutorService threadPool = getThreadPool();
		final Future<Boolean> future = threadPool.submit(new Callable<Boolean>()
		{
			@Override
			public Boolean call() throws Exception
			{
				return ListCache.this.addAll(c);
			}
		});
		return future;
	}

	/**
	 * 从列表中移除指定 collection 中包含的其所有元素.
	 * @param c 包含从此列表中移除的元素的 collection
	 * @return 如果此列表由于调用而发生更改,则返回 true
	 * @author 王立鹏
	 */
	@Override
	public boolean removeAll(Collection<?> c)
	{
		boolean b = false;
		if (c == null || c.isEmpty())
		{
			return b;
		}
		for (final Object element : c)
		{
			try
			{
				b = remove(element) || b;
			} catch (final Exception e)
			{
				cacheUtil._logCacheError(e);
			}
		}
		return b;
	}

	/**
	 * 从列表中移除指定的其所有元素.
	 * @param elements 需要移除的elements,变长参数
	 * @return 如果此列表由于调用而发生更改,则返回 true
	 * @author 王立鹏
	 */
	public boolean removeAll(Object... elements)
	{
		if (elements == null || elements.length == 0)
		{
			return false;
		}
		final List<Object> list = new ArrayList<>(elements.length);
		for (final Object ek : elements)
		{
			list.add(ek);
		}
		return removeAll(list);
	}

	/**
	 * 异步方法
	 * 与上面的removeAll方法功能相同,但是不阻塞主线程!
	 * @param c 包含从此列表中移除的元素的 collection
	 * @return Future对象
	 * 		Future对象的get()方法将获取实际执行的返回值,但是get()将阻塞线程.
	 * @author 王立鹏
	 */
	public Future<Boolean> asyncRemoveAll(final Collection<?> c)
	{
		final ExecutorService threadPool = getThreadPool();
		final Future<Boolean> future = threadPool.submit(new Callable<Boolean>()
		{
			@Override
			public Boolean call() throws Exception
			{
				return ListCache.this.removeAll(c);
			}
		});
		return future;
	}

	/**
	 * 异步方法
	 * 与上面的removeAll方法功能相同,但是不阻塞主线程!
	 * @param elements elements的变长参
	 * @return Future对象
	 * 		Future对象的get()方法将获取实际执行的返回值,但是get()将阻塞线程.
	 * @author 王立鹏
	 */
	public Future<Boolean> asyncRemoveAll(final Object... elements)
	{
		final ExecutorService threadPool = getThreadPool();
		final Future<Boolean> future = threadPool.submit(new Callable<Boolean>()
		{
			@Override
			public Boolean call() throws Exception
			{
				return ListCache.this.removeAll(elements);
			}
		});
		return future;
	}

	/**
	 * 仅在列表中保留指定 collection 中所包含的元素(可选操作).换句话说,该方法从列表中移除未包含在指定 collection 中的所有元素.
	 * @param c 包含将保留在此列表中的元素的 collection
	 * @return 如果此列表由于调用而发生更改,则返回 true
	 * @author 王立鹏
	 */
	@Override
	public boolean retainAll(Collection<?> c)
	{
		try
		{
			final List<EV> localList = this.getLocalList();
			localList.retainAll(c);
			this.clear();
			return this.addAll(localList);
		} catch (final Exception e)
		{
			cacheUtil._logCacheError(e);
			return false;
		}
	}

	/**
	 * 返回列表中指定位置的元素.
	 * @param index  要返回的元素的索引
	 * @return 列表中指定位置的元素
	 * @author 王立鹏
	 */
	@Override
	public EV get(int index)
	{
		return null;
	}

	/**
	 * 用指定元素替换列表中指定位置的元素
	 * @param index 要替换的元素的索引
	 * @param element 要在指定位置存储的元素
	 * @return 以前在指定位置的元素
	 * @author 王立鹏
	 */
	@Override
	public EV set(int index, EV element)
	{
		return null;
	}

	/**
	 * 异步方法
	 * 与上面的set方法功能相同,但是不阻塞主线程!
	 * @return Future对象
	 * 		Future对象的get()方法将获取实际执行的返回值,但是get()将阻塞线程.
	 * 		需要确认返回结果的逻辑中,不宜使用该方法,应该使用阻塞的方法set(index,element),并判断返回值
	 */
	public Future<EV> asyncSet(final int index, final EV element)
	{
		final ExecutorService threadPool = getThreadPool();
		final Future<EV> future = threadPool.submit(new Callable<EV>()
		{
			@Override
			public EV call() throws Exception
			{
				return ListCache.this.set(index, element);
			}
		});
		return future;
	}

	/**
	 * 从此列表中移除第一次出现的指定元素(如果存在).如果列表不包含元素,则不更改列表.
	 * 更确切地讲,移除满足 (o==null ? get(i)==null : o.equals(get(i))) 的最低索引 i 的元素(如果存在这样的元素).
	 * 如果此列表已包含指定元素(或者此列表由于调用而发生更改),则返回 true.
	 * @param o 要从该列表中移除的元素,如果存在的话
	 * @return 如果列表包含指定的元素,则返回 true
	 * @author 王立鹏
	 */
	@Override
	public boolean remove(Object o)
	{
		return false;
	}

	/**
	 * 从列表中移除所有元素(可选操作).此调用返回后该列表将是空的.
	 * @author 王立鹏
	 */
	@Override
	public void clear()
	{
		destroy();
	}

	/**
	 * 如果列表不包含元素,则返回 true.
	 * @return 如果列表不包含元素,则返回 true
	 * @author 王立鹏
	 */
	@Override
	public boolean isEmpty()
	{
		return size() == 0;
	}

	/**
	 * 返回列表中的元素数.
	 * @return 列表中的元素数
	 */
	@Override
	public int size()
	{
		return 0;
	}

	/**
	 * 截取缓存的list,将缓存的list截为从start到end的子list
	 * @param start 起始位置(包含)
	 * @param end 结束位置,-1表示最后一个,-2,-3以此类推(包含)
	 */
	public void trim(long start, long end)
	{
	}

	/**
	 * 将最早塞到list中的元素,从list中移出
	 * @return 移出的元素
	 */
	public EV popFirst()
	{
		return null;
	}

	/**
	 * 将最后塞到list中的元素,从list中移出
	 * @return 移出的元素
	 */
	public EV popLast()
	{
		return null;
	}

	/**
	 * 返回列表中指定的 fromIndex(包括 )和 toIndex(不包括)之间的部分视图.
	 * (如果 fromIndex 和 toIndex 相等,则返回的列表为空).返回的列表由此列表支持,因此返回列表中的非结构性更改将反映在此列表中,反之亦然.
	 * 返回的列表支持此列表支持的所有可选列表操作.此方法省去了显式范围操作(此操作通常针对数组存在).
	 * 通过传递 subList 视图而非整个列表,期望列表的任何操作可用作范围操作.例如,下面的语句从列表中移除了元素的范围：
	 *             list.subList(from, to).clear();
	 * 可以对 indexOf 和 lastIndexOf 构造类似的语句,而且 Collections 类中的所有算法都可以应用于 subList.
	 * 如果支持列表(即此列表)通过任何其他方式(而不是通过返回的列表)从结构上修改,则此方法返回的列表语义将变为未定义(从结构上修改是指更改列表的大小,或者以其他方式打乱列表,使正在进行的迭代产生错误的结果).
	 * @param fromIndex list的低端(包括)
	 * @param toIndex list的高端(不包括)
	 * @return 列表中指定范围的本地拷贝
	 * @author 王立鹏
	 * 		返回的list子集是本地list,对其操作不再影响缓存的结果
	 */
	public List<EV> subLocalList(int fromIndex, int toIndex)
	{
		return new ArrayList<EV>(0);
	}

	/**
	 * 与上面方法相同,只是返回的是缓存的list的完整本地化版本
	 * @return 将缓存的list全部取回本地
	 * 		返回的list子集是本地list,对其操作不再影响缓存的结果
	 */
	public List<EV> getLocalList()
	{
		return subLocalList(0, -1);
	}

	/**
	 * 返回按适当顺序包含列表中的所有元素的本地数组(从第一个元素到最后一个元素).
	 * 由于此列表不维护对返回数组的任何引用,因而它将是“安全的”.(换句话说,即使数组支持此列表,此方法也必须分配一个新数组).因此,调用者可以随意修改返回的数组.
	 * 此方法充当基于数组的 API 与基于 collection 的 API 之间的桥梁.
	 * @return 按适当顺序包含该列表中所有元素的本地数组
	 * @author 王立鹏
	 */
	@Override
	public Object[] toArray()
	{
		return getLocalList().toArray();
	}

	/**
	 * 返回按适当顺序(从第一个元素到最后一个元素)包含列表中所有元素的本地数组；返回数组的运行时类型是指定数组的运行时类型.
	 * 如果指定数组能容纳列表,则在其中返回该列表.否则,分配具有指定数组的运行时类型和此列表大小的新数组.
	 * 如果指定数组能容纳列表,并剩余空间(即数组的元素比列表的多),那么会将数组中紧随列表尾部的元素设置为 null.
	 * (只有 在调用者知道列表不包含任何 null 元素时此方法才能用于确定列表的长度).
	 * 像 toArray() 方法一样,此方法充当基于数组的 API 与基于 collection 的 API 之间的桥梁.
	 * 更进一步说,此方法允许对输出数组的运行时类型进行精确控制,在某些情况下,可以用来节省分配开销.
	 * 假定 x 是只包含字符串的一个已知列表.以下代码用来将该列表转储到一个新分配的 String 数组：
	 *      String[] y = x.toArray(new String[0]);注意,toArray(new Object[0]) 和 toArray() 在功能上是相同的.
	 * @param a 要存储列表中元素的数组,如果它足够大的话；否则为此目的分配一个运行时类型相同的新数组.
	 * @return 包含列表中元素的本地数组
	 * @author 王立鹏
	 */
	@Override
	public <T> T[] toArray(T[] a)
	{
		return getLocalList().toArray(a);
	}

	/**
	 * 销毁这个list,,销毁后仍然可以继续使用,只是里面的内容变为空
	 * @author 王立鹏
	 * 		Redis未提供
	 */
	public void destroy()
	{
		cacheUtil.delete(key);
	}

	/**
	 * 返回按适当顺序在列表的元素上进行迭代的迭代器.
	 * @return 按适当顺序在列表的元素上进行迭代的迭代器
	 * @author 王立鹏
	 * 		暂时性的:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 * 		并且数据与缓存不是实时同步的,修改这个迭代器,并不会修改缓存
	 */
	@Override
	public Iterator<EV> iterator()
	{
		return getLocalList().iterator();
	}

	/**
	 * @return 返回此列表元素的列表迭代器(按适当顺序).
	 * @author 王立鹏
	 * 		暂时性的:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 * 		并且数据与缓存不是实时同步的,修改这个迭代器,并不会修改缓存
	 */
	@Override
	public ListIterator<EV> listIterator()
	{
		return getLocalList().listIterator();
	}

	/**
	 * 返回列表中元素的列表迭代器(按适当顺序),从列表的指定位置开始.指定的索引表示 next 的初始调用所返回的第一个元素.
	 * previous 方法的初始调用将返回索引比指定索引少 1 的元素.
	 * @param index 从列表迭代器返回的第一个元素的索引(通过调用 next 方法)
	 * @return 此列表中元素的列表迭代器(按适当顺序),从列表中的指定位置开始
	 * @author 王立鹏
	 * 		暂时性的:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 * 		并且数据与缓存不是实时同步的,修改这个迭代器,并不会修改缓存
	 */
	@Override
	public ListIterator<EV> listIterator(int index)
	{
		return getLocalList().listIterator(index);
	}

	/*----------------------------一下方法可能存在bug或者不容易理解的地方,使用请仔细看说明----------------------------------*/
	/**
	 * 返回此列表中第一次出现的指定元素的索引；如果此列表不包含该元素,则返回 -1.
	 * 更确切地讲,返回满足 (o==null ? get(i)==null : o.equals(get(i))) 的最低索引 i；如果没有这样的索引,则返回 -1.
	 * @param o 要搜索的元素
	 * @return 此列表中第一次出现的指定元素的索引,如果列表不包含该元素,则返回 -1
	 * @author 王立鹏
	 * 		注意:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 */
	@Override
	public int indexOf(Object o)
	{
		return getLocalList().indexOf(o);
	}

	/**
	 * 返回此列表中最后出现的指定元素的索引；如果列表不包含此元素,则返回 -1.
	 * 更确切地讲,返回满足 (o==null ? get(i)==null : o.equals(get(i))) 的最高索引 i；如果没有这样的索引,则返回 -1.
	 * @param o 要搜索的元素
	 * @return 列表中最后出现的指定元素的索引；如果列表不包含此元素,则返回 -1
	 * @author 王立鹏
	 * 		注意:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 */
	@Override
	public int lastIndexOf(Object o)
	{
		return getLocalList().lastIndexOf(o);
	}

	/**
	 * 如果列表包含指定的元素,则返回 true.更确切地讲,当且仅当列表包含满足 (o==null ? e==null : o.equals(e)) 的元素 e 时才返回 true.
	 * @param o 要测试列表中是否存在的元素
	 * @return 如果列表包含指定的元素,则返回 true
	 * @author 王立鹏
	 * 		注意:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 */
	@Override
	public boolean contains(Object o)
	{
		return getLocalList().contains(o);
	}

	/**
	 * 如果列表包含指定 collection 的所有元素,则返回 true.
	 * @param c 要在列表中检查其包含性的 collection
	 * @return 如果列表包含指定 collection 的所有元素,则返回 true
	 * @author 王立鹏
	 * 		注意:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 */
	@Override
	public boolean containsAll(Collection<?> c)
	{
		return getLocalList().containsAll(c);
	}

	/**
	 * 移除列表中指定位置的元素,其实是删除了与指定位置元素相同的第一个元素,将所有的后续元素向左移动(将其索引减 1).返回从列表中移除的元素
	 * @param index 要移除的元素的索引
	 * @return 以前在指定位置的元素
	 * @author 王立鹏
	 * 		注意:这个方法跟List的具体效果可能有差异,请谨慎使用!
	 * 		这个方法实际上删除的是与指定位置相同的第一个元素,不一定是从指定位置上删的
	 */
	@Override
	public EV remove(int index)
	{
		return null;
	}

	@Override
	public List<EV> subList(int fromIndex, int toIndex)
	{
		return subLocalList(fromIndex, toIndex);
	}

	/**
	 * 返回列表的哈希码值.列表的哈希码定义为以下计算的结果：
	 * int hashCode = 1;
	 * Iterator<E> i = list.iterator();
	 * while (i.hasNext()) {
	 *		E obj = i.next();
	 *		hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
	 * }
	 * 这确保了 list1.equals(list2) 意味着对于任何两个列表 list1 和 list2 而言,可实现 list1.hashCode()==list2.hashCode(),
	 * 正如 Object.hashCode() 的常规协定所要求的.
	 * @return 此列表的哈希码值
	 * @author 王立鹏
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (key == null ? 0 : key.hashCode());
		return result;
	}

	/**
	 * 比较指定的对象与列表是否相等.当且仅当指定的对象也是一个列表、两个列表有相同的大小,
	 * 并且两个列表中的所有相应的元素对相等 时才返回 true( 如果 (e1==null ? e2==null :e1.equals(e2)),则两个元素 e1 和 e2 是相等 的).
	 * 换句话说,如果所定义的两个列表以相同的顺序包含相同的元素,那么它们是相等的.该定义确保了 equals 方法在 List 接口的不同实现间正常工作.
	 * @param o 要与此列表进行相等性比较的对象
	 * @return 如果指定对象与此列表相等,则返回 true
	 * @author 王立鹏
	 */
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
		final ListCache other = (ListCache) obj;
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
