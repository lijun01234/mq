package cn.snsoft.base.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.base.thread.BasThreadFactory;
/**
 * <p>标题： 队列,基类</p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午12:57:27</p>
 * <p>类全名：cn.snsoft.component.search.comm.cache.IQueueCache</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.NO
 */
public class QueueCache<EV extends Serializable> implements Queue<EV>
{
	protected final String				key;
	protected final CacheUtil<EV>		cacheUtil;	//spring的Redis模板
	protected static ExecutorService	threadPool;

	public QueueCache(String id, CacheFactory<?,EV> cacheFactory, CacheUtil<EV> cacheUtil)
	{
		this.key = id;
		this.cacheUtil = cacheUtil;
	}

	public ExecutorService getThreadPool()
	{
		if (threadPool == null)
		{
			threadPool = Executors.newSingleThreadExecutor(new BasThreadFactory("QueueCache"));
		}
		return threadPool;
	}

	/**
	 * 将指定的元素插入此队列.
	 * 当使用有容量限制的队列时,此方法通常要优于 add(E),后者可能无法插入元素,而只是抛出一个异常.
	 * @param e 要添加的元素
	 * @return 如果该元素已添加到此队列,则返回 true;否则返回 false
	 * @author 王立鹏
	 */
	@Override
	public boolean offer(EV element)
	{
		return false;
	}

	/**
	 * 通offer,批量插入到,不保证插入的元素集合的连续,只保证相对顺序的前后
	 * @param elements 元素集合
	 * @return 队列有改变,则返回true.
	 */
	public boolean offerAll(java.util.Collection<? extends EV> elements)
	{
		boolean b = false;
		for (EV ev : elements)
		{
			b |= offer(ev);
		}
		return b;
	}

	/**
	 * 异步方法
	 * 与上面的offer方法功能相同,但是不阻塞线程!
	 * @return Future对象
	 * 		Future对象的get()方法将获取实际执行的返回值,但是get()将阻塞线程.
	 * 		需要确认返回结果的逻辑中,不宜使用该方法,应该使用阻塞的方法offer(element),并判断返回值
	 * @author 王立鹏
	 */
	public Future<Boolean> asyncOffer(final EV element)
	{
		final ExecutorService threadPool = getThreadPool();
		final Future<Boolean> future = threadPool.submit(new Callable<Boolean>()
		{
			@Override
			public Boolean call() throws Exception
			{
				return QueueCache.this.offer(element);
			}
		});
		return future;
	}

	/**
	 * 获取但不移除此队列的头;如果此队列为空,则返回 null.
	 * @return 此队列的头;如果此队列为空,则返回 null
	 * @author 王立鹏
	 */
	@Override
	public EV peek()
	{
		return null;
	}

	/**
	 * 获取并移除此队列的头,如果此队列为空,则返回 null.
	 * @return 队列的头,如果此队列为空,则返回 null
	 * @author 王立鹏
	 */
	@Override
	public EV poll()
	{
		return null;
	}

	/**
	 * 一次取出多个元素,如果元素你不足,有多少返回多少
	 * @param count 取出的个数
	 * @return 多个元素EV的List
	 */
	public List<EV> multiPoll(int count)
	{
		if (count <= 0)
		{
			return new ArrayList<>(0);
		}
		List<EV> list = new ArrayList<>(count);
		for (int i = 0; i < count; i++)
		{
			EV poll = poll();
			if (poll == null)
			{
				break;
			}
			list.add(poll);
		}
		return list;
	}

	/**
	 * 将指定的元素插入此队列,在成功时返回 true.
	 * 如果当前没有可用的空间,则抛出 IllegalStateException.
	 * @param e 要添加的元素
	 * @return (根据 Collection.add(E) 的规定)
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
	 */
	@Override
	public boolean add(EV element)
	{
		return false;
	}

	/**
	 * 异步方法
	 * 与上面的add方法功能相同,但是不阻塞线程!
	 * @return Future对象
	 * 		Future对象的get()方法将获取实际执行的返回值,但是get()将阻塞线程.
	 * 		需要确认返回结果的逻辑中,不宜使用该方法,应该使用阻塞的方法add(element),并判断返回值
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
	 */
	public Future<Boolean> asyncAdd(final EV element)
	{
		final ExecutorService threadPool = getThreadPool();
		final Future<Boolean> future = threadPool.submit(new Callable<Boolean>()
		{
			@Override
			public Boolean call() throws Exception
			{
				return QueueCache.this.add(element);
			}
		});
		return future;
	}

	/**
	 * 获取,但是不移除此队列的头.此方法与 peek 唯一的不同在于：此队列为空时将抛出一个异常.
	 * @return 队列的头
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
	 */
	@Override
	public EV element()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}
		return peek();
	}

	/**
	 * 获取并移除此队列的头.此方法与 poll 唯一的不同在于：此队列为空时将抛出一个异常.
	 * @return 队列的头
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
	 */
	@Override
	public EV remove()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException();
		}
		return poll();
	}

	/*------------------------------------------从collection继承来的方法----------------------------------------*/
	/**
	 * 返回此 collection 中的元素数.
	 * @return 此 collection 中的元素数
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
	 */
	@Override
	public int size()
	{
		return 0;
	}

	/**
	 * 如果此 collection 不包含元素,则返回 true.
	 * @return 如果此 collection 不包含元素,则返回 true
	 * @author 王立鹏
	 */
	@Override
	public boolean isEmpty()
	{
		return size() == 0;
	}

	/**
	 * 如果此 collection 包含指定的元素,则返回 true.更确切地讲,当且仅当此 collection 至少包含一个满足 (o==null ? e==null : o.equals(e)) 的元素 e 时,返回 true.
	 * @param o 测试在此 collection 中是否存在的元素.
	 * @return 如果此 collection 包含指定的元素,则返回 true
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
	 */
	@Override
	public boolean contains(Object o)
	{
		return getLocalList().contains(o);
	}

	/**
	 * 返回在此 collection 的元素上进行迭代的迭代器.关于元素返回的顺序没有任何保证(除非此 collection 是某个能提供保证顺序的类实例).
	 * @return 在此 collection 的元素上进行迭代的本地 Iterator
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
	 */
	@Override
	public Iterator<EV> iterator()
	{
		return getLocalList().iterator();
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
	public int indexOf(Object o)
	{
		return getLocalList().indexOf(o);
	}

	/**
	 * 返回包含此 collection 中所有元素的数组.如果 collection 对其迭代器返回的元素顺序做出了某些保证,那么此方法必须以相同的顺序返回这些元素.
	 * 返回的数组将是“安全的”,因为此 collection 并不维护对返回数组的任何引用.(换句话说,即使 collection 受到数组的支持,此方法也必须分配一个新的数组).
	 * 因此,调用者可以随意修改返回的数组.
	 * 此方法充当了基于数组的 API 与基于 collection 的 API 之间的桥梁.
	 * @return 包含此 collection 中所有元素的本地数组
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
	 */
	@Override
	public Object[] toArray()
	{
		return getLocalList().toArray();
	}

	/**
	 * 返回包含此 collection 中所有元素的数组;返回数组的运行时类型与指定数组的运行时类型相同.
	 * 如果指定的数组能容纳该 collection,则返回包含此 collection 元素的数组.
	 * 否则,将分配一个具有指定数组的运行时类型和此 collection 大小的新数组.
	 * 如果指定的数组能容纳 collection,并有剩余空间(即数组的元素比 collection 的元素多),那么会将数组中紧接 collection 尾部的元素设置为 null.
	 * (只有 在调用者知道此 collection 没有包含任何 null 元素时才能用此方法确定 collection 的长度.)
	 * 如果此 collection 对其迭代器返回的元素顺序做出了某些保证,那么此方法必须以相同的顺序返回这些元素.
	 * 像 toArray() 方法一样,此方法充当基于数组的 API 与基于 collection 的 API 之间的桥梁.
	 * 更进一步说,此方法允许对输出数组的运行时类型进行精确控制,并且在某些情况下,可以用来节省分配开销.
	 * 假定 x 是只包含字符串的一个已知 collection.以下代码用来将 collection 转储到一个新分配的 String 数组：
	 *      String[] y = x.toArray(new String[0]); 注意,toArray(new Object[0]) 和 toArray() 在功能上是相同的.
	 * @param a 存储此 collection 元素的本地数组(如果其足够大);否则,将为此分配一个具有相同运行时类型的新数组.
	 * @return 包含此 collection 中所有元素的数组,即a
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
	 */
	@Override
	public <T> T[] toArray(T[] a)
	{
		return getLocalList().toArray(a);
	}

	/**
	 * 从此 collection 中移除指定元素的单个实例,如果存在的话(可选操作).
	 * 更确切地讲,如果此 collection 包含一个或多个满足 (o==null ? e==null : o.equals(e)) 的元素 e,则移除这样的元素.
	 * 如果此 collection 包含指定的元素(或者此 collection 由于调用而发生更改),则返回 true .
	 * @param o 要从此 collection 中移除的元素(如果存在).
	 * @return 如果此调用将移除一个元素,则返回 true
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
	 */
	@Override
	public boolean remove(Object o)
	{
		return false;
	}

	/**
	 * 如果此 collection 包含指定 collection 中的所有元素,则返回 true.
	 * @param c 将检查是否包含在此 collection 中的 collection
	 * @return 如果此 collection 包含指定 collection 中的所有元素,则返回 true
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
	 */
	@Override
	public boolean containsAll(Collection<?> c)
	{
		return getLocalList().containsAll(c);
	}

	/**
	 * 将指定 collection 中的所有元素都添加到此 collection 中(可选操作).如果在进行此操作的同时修改指定的 collection,那么此操作行为是不确定的.
	 * (这意味着如果指定的 collection 是此 collection,并且此 collection 为非空,那么此调用的行为是不确定的.)
	 * @param c 包含要添加到此 collection 的元素的 collection
	 * @return 如果此 collection 由于调用而发生更改,则返回 true
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
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
	 * 移除此 collection 中那些也包含在指定 collection 中的所有元素(可选操作).此调用返回后,collection 中将不包含任何与指定 collection 相同的元素.
	 * @param c 办好要从此 collection 移除的元素的 collection
	 * @return 如果此 collection 由于调用而发生更改,则返回 true
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
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
	 * 仅保留此 collection 中那些也包含在指定 collection 的元素(可选操作).换句话说,移除此 collection 中未包含在指定 collection 中的所有元素.
	 * @param c 包含保留在此 collection 中的元素的 collection
	 * @return 如果此 collection 由于调用而发生更改,则返回 true
	 * @author 王立鹏
	 * 		这个方法不推荐使用!
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
	 * 清空队列
	 * 		注意:队列销清空,仍然可以继续使用,只是里面的内容被清空了!
	 * @author 王立鹏
	 */
	@Override
	public void clear()
	{
		destroy();
	}

	/**
	 * 队列销毁
	 * 		注意:队列销毁后,仍然可以继续使用,只是里面的内容被清空了!
	 * @author 王立鹏
	 */
	public void destroy()
	{
		cacheUtil.delete(key);
	}

	//改方法必须被子类覆盖! 宋建中 修改 为 public 2015 02 28
	public List<EV> getLocalList()
	{
		return new ArrayList<EV>();
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
		@SuppressWarnings("rawtypes")
		final QueueCache other = (QueueCache) obj;
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
