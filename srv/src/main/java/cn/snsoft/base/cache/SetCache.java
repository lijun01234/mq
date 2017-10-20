package cn.snsoft.base.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import cn.snsoft.base.cache.util.CacheUtil;
import cn.snsoft.base.thread.BasThreadFactory;
/**
 * <p>标题： 集合,基类</p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午12:50:50</p>
 * <p>类全名：cn.snsoft.component.search.comm.cache.ISetCache</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.NO
 */
public class SetCache<EV extends Serializable> implements Set<EV>
{
	protected final String				key;
	protected final CacheFactory<?,EV>	cacheFactory;
	protected final CacheUtil<EV>		cacheUtil;
	protected static ExecutorService	threadPool;

	/**
	 * 构造器,仅限工厂类
	 * @param id Redis缓存的id
	 * @param cacheUtil 缓存工具的对象
	 * @param cacheFactory 缓存工厂的对象
	 */
	public SetCache(String id, CacheFactory<?,EV> cacheFactory, CacheUtil<EV> cacheUtil)
	{
		this.key = id;
		this.cacheFactory = cacheFactory;
		this.cacheUtil = cacheUtil;
	}

	public ExecutorService getThreadPool()
	{
		if (threadPool == null)
		{
			threadPool = Executors.newSingleThreadExecutor(new BasThreadFactory("SetCache"));
		}
		return threadPool;
	}

	/**
	 * 获取缓存Id
	 * @return 该集合对应的缓存服务器的key
	 */
	public String getSetCacheId()
	{
		return key;
	}

	/**
	 * 如果 set 中尚未存在指定的元素,则添加此元素.更确切地讲,如果此 set 没有包含满足 (e==null ? e2==null : e.equals(e2)) 的元素 e2,
	 * 则向该 set 中添加指定的元素 e.如果此 set 已经包含该元素,则该调用不改变此 set 并返回 false.结合构造方法上的限制,这就可以确保 set 永远不包含重复的元素.
	 * 上述规定并未暗示 set 必须接受所有元素;set 可以拒绝添加任意特定的元素,包括 null,并抛出异常,这与 Collection.add 规范中所描述的一样.
	 * 每个 set 实现应该明确地记录对其可能包含元素的所有限制.
	 * @param e  要添加到 set 中的元素
	 * @return 如果 set 尚未包含指定的元素,则返回 true
	 * @author 王立鹏
	 */
	@Override
	public boolean add(EV e)
	{
		return false;
	}

	/**
	 * 如果 set 中没有指定 collection 中的所有元素,则将其添加到此 set 中.
	 * 如果指定的 collection 也是一个 set,则 addAll 操作会实际修改此 set,这样其值是两个 set 的一个并集.
	 * 如果操作正在进行的同时修改了指定的 collection,则此操作的行为是不确定的.
	 * @param c 包含要添加到此 set 中的元素的 collection
	 * @return 如果此 set 由于调用而发生更改,则返回 true
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
		for (final EV ev : c)
		{
			b = add(ev) || b;
		}
		return b;
	}

	/**
	 * 异步方法
	 * 与上面的addAll方法功能相同,但是不阻塞主线程!
	 * @return Future对象
	 * 		Future对象的get()方法将获取实际执行的返回值,但是get()将阻塞线程.
	 * 		需要确认返回结果的逻辑中,不宜使用该方法,应该使用阻塞的方法addAll(c),并判断返回值
	 */
	public Future<Boolean> asyncAddAll(final Collection<? extends EV> c)
	{
		final ExecutorService threadPool = getThreadPool();
		final Future<Boolean> future = threadPool.submit(new Callable<Boolean>()
		{
			@Override
			public Boolean call() throws Exception
			{
				return SetCache.this.addAll(c);
			}
		});
		return future;
	}

	/**
	 * 如果 set 中存在指定的元素,则将其移除.更确切地讲,如果此 set 中包含满足 (o==null ? e==null : o.equals(e)) 的元素 e,则移除它.
	 * 如果此 set 包含指定的元素(或者此 set 由于调用而发生更改),则返回 true(一旦调用返回,则此 set 不再包含指定的元素).
	 * @param o 从 set 中移除的对象(如果存在)
	 * @return  如果此 set 包含指定的对象,则返回 true
	 * @author 王立鹏
	 */
	@Override
	public boolean remove(Object o)
	{
		return false;
	}

	/**
	 * 移除 set 中那些包含在指定 collection 中的元素.
	 * @param c 包含要从此 set 中移除的元素的 collection
	 * @return 如果此 set 由于调用而发生更改,则返回 true
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
		for (final Object ev : c)
		{
			b = remove(ev) || b;
		}
		return b;
	}

	/**
	 * 移除 set 中那些包含在指定的多个元素.
	 * @param elements 需要移除的元素,变长参数
	 * @return 如果此列表由于调用而发生更改,则返回 true
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
				return SetCache.this.removeAll(c);
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
				return SetCache.this.removeAll(elements);
			}
		});
		return future;
	}

	/**
	 * 返回 set 中的元素数(其容量).
	 * @return 此 set 中的元素数(其容量)
	 * @author 王立鹏
	 */
	@Override
	public int size()
	{
		return 0;
	}

	/**
	 * 如果 set 不包含元素,则返回 true.
	 * @return 如果此 set 不包含元素,则返回 true
	 * @author 王立鹏
	 */
	@Override
	public boolean isEmpty()
	{
		return size() == 0;
	}

	/**
	 * 如果 set 包含指定的元素,则返回 true.更确切地讲,当且仅当 set 包含满足 (o==null ? e==null : o.equals(e)) 的元素 e 时返回 true.
	 * @param o 要测试此 set 中是否存在的元素
	 * @return 如果此 set 包含指定的元素,则返回 true
	 * @author 王立鹏
	 */
	@Override
	public boolean contains(Object o)
	{
		return false;
	}

	/**
	 * 从缓存服务器上面销毁这个Set,同destroy()
	 * @see destroy()
	 * @author 王立鹏
	 */
	@Override
	public void clear()
	{
		destroy();
	}

	/**
	 * 从缓存服务器上面销毁这个Set
	 * 销毁后,这个Set还可以继续使用,只是清除了里面的元素
	 * @author 王立鹏
	 */
	public void destroy()
	{
		cacheUtil.delete(key);
	}

	/**
	 * 取出缓存中的Set,克隆生成一个本地的Set对象,对这个本地Set的操作,不会影响缓存服务器上的数据.
	 * @return 生成的本地Set对象
	 * @author 王立鹏
	 */
	public Set<EV> getLocalSet()
	{
		return new HashSet<EV>(0);
	}

	/**
	 * 返回一个包含 set 中所有元素的本地数组.
	 * @return 包含   此 set 中所有元素的本地数组
	 * @author 王立鹏
	 */
	@Override
	public Object[] toArray()
	{
		return getLocalSet().toArray();
	}

	/**
	 * 返回一个包含此 set 中所有元素的本地数组;返回数组的运行时类型是指定数组的类型.如果指定的数组能容纳该 set,则它将在其中返回.
	 * 否则,将分配一个具有指定数组的运行时类型和此 set 大小的新数组.
	 * @param a 存储此 set 中元素的数组(如果其足够大);否则将为此分配一个具有相同运行时类型的新数组.
	 * @return 包含此 set 中所有元素的本地数组
	 * @author 王立鹏
	 */
	@Override
	public <T> T[] toArray(T[] a)
	{
		return getLocalSet().toArray(a);
	}

	/**
	 * 如果此 set 包含指定 collection 的所有元素,则返回 true.如果指定的 collection 也是一个 set,那么当该 collection 是此 set 的子集 时返回 true.
	 * @param c 包含要添加到此 set 中的元素的 collection
	 * @return 如果此 set 包含指定 collection 中的所有元素,则返回 true
	 * 		注意:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 * @author 王立鹏
	 */
	@Override
	public boolean containsAll(Collection<?> c)
	{
		return getLocalSet().containsAll(c);
	}

	/**
	 * 返回在此 set 中的元素上进行迭代的迭代器.返回的元素没有特定的顺序(除非此 set 是某个提供顺序保证的类的实例).
	 * 这个迭代器也是一个本地的迭代器,对迭代器的操作,不会影响缓存中的数据.
	 * @return 在此 set 中的元素上进行迭代的本地迭代器
	 * 		注意:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 * @author 王立鹏
	 */
	@Override
	public Iterator<EV> iterator()
	{
		return getLocalSet().iterator();
	}

	/**
	 * 仅保留 set 中那些包含在指定 collection 中的元素.换句话说,移除此 set 中所有未包含在指定 collection 中的元素.
	 * 如果指定的 collection 也是一个 set,则此操作会实际修改此 set,这样其值是两个 set 的一个交集.
	 * 该方法是取得缓存服务器中Set中的全部
	 *
	 * @param c 包含要保留到此 set 中的元素的 collection
	 * @return 如果此 set 由于调用而发生更改,则返回 true
	 * 		注意:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 * @author 王立鹏
	 */
	@Override
	public boolean retainAll(Collection<?> c)
	{
		return false;
	}

	/**
	 * 从当前集合中随机抽出一个元素,抽出后,该元素直接从集合中删除,集合元素减少一个
	 * @return 抽出的元素
	 */
	public EV randompPop()
	{
		return null;
	}

	/**
	 * 从当前集合中随机获取一个元素,获取后,该元素还在集合中,集合不变
	 * @return 获取的元素
	 */
	public EV randomMember()
	{
		return null;
	}

	/*----------------------------------------以下方法为跨缓存对象执行的方法--------------------------------------------------*/
	/**
	 * 将指定元素从当前集合移出放入目标集合
	 * 如果当前集合不存在或不包哈指定元素，不进行任何操作，返回false
	 * 否则该成员从源集合上删除，并添加到目标集合，如果目标集合中成员已存在，则只在源集合进行删除
	 * @param value 指定的成员
	 * @param destId 目标集合的Id
	 * @return 执行成功返回true
	 * @author 王立鹏
	 */
	public boolean move(EV value, String destId)
	{
		return false;
	}

	/**
	 * 异步方法
	 * 与上面的move方法功能相同,但是不阻塞主线程!
	 * @return Future对象
	 * 		Future对象的get()方法将获取实际执行的返回值,但是get()将阻塞线程.
	 * 		需要确认返回结果的逻辑中,不宜使用该方法,应该使用阻塞的方法move(value,destId),并判断返回值
	 */
	public Future<Boolean> asyncMove(final EV value, final String destId)
	{
		final ExecutorService threadPool = getThreadPool();
		final Future<Boolean> future = threadPool.submit(new Callable<Boolean>()
		{
			@Override
			public Boolean call() throws Exception
			{
				return SetCache.this.move(value, destId);
			}
		});
		return future;
	}

	/**
	 * 同上
	 * @param value 指定的成员
	 * @param SetCache 目标集合对象
	 * @return 执行成功返回true
	 * @see move(EV value, K tarKey)
	 * @author 王立鹏
	 */
	public boolean move(EV value, SetCache<EV> destSetCache)
	{
		if (destSetCache == null)
		{
			return false;
		}
		final String tarKey = destSetCache.getSetCacheId();
		if (tarKey != null)
		{
			return move(value, tarKey);
		}
		return false;
	}

	/**
	 * 求当前集合与指定集合id对应的集合的交集,交集结果为本地集合
	 * 交集是对等运算,即C(A)∩C(B)=C(B)∩C(A)
	 * @param otherId 指定的集合id
	 * @return 返回交集的本地Set
	 */
	public Set<EV> intersect(String otherId)
	{
		return new HashSet<EV>(0);
	}

	/**
	 * 求当前集合与指定集合对应的集合的交集,交集结果为本地集合
	 * 交集是对等运算,即C(A)∩C(B)=C(B)∩C(A)
	 * @param otherSetCache 指定的集合
	 * @return 返回交集的本地Set
	 */
	public Set<EV> intersect(SetCache<EV> otherSetCache)
	{
		return new HashSet<EV>(0);
	}

	/**
	 * 求当前集合与指定集合id对应的集合的交集,交集的结果为缓存集合
	 * 交集是对等运算,即C(A)∩C(B)=C(B)∩C(A)
	 * @param otherId 指定的需要与当前集合计算交集的集合id
	 * @param destId 计算结果的缓存集合id
	 * @return 返回计算结果的缓存集合
	 */
	public SetCache<EV> intersectAndCache(String otherId, String destId)
	{
		return cacheFactory.getSetCache(destId);
	}

	/**
	 * 求当前集合与指定集合对应的集合的交集,交集的结果为缓存集合
	 * 交集是对等运算,即C(A)∩C(B)=C(B)∩C(A)
	 * @param otherSetCache 指定的需要与当前集合计算交集的集合
	 * @param destSetCache 计算结果的缓存集合
	 * @return 返回计算结果的缓存集合,即destSetCache
	 */
	public SetCache<EV> intersectAndCache(SetCache<EV> otherSetCache, SetCache<EV> destSetCache)
	{
		return destSetCache;
	}

	/**
	 * 求当前集合与指定集合id对应的集合的并集,并集结果为本地集合
	 * 并集是对等运算,即C(A)∪C(B)=C(B)∪C(A)
	 * @param otherId 指定的集合id
	 * @return 返回并集的本地Set
	 */
	public Set<EV> union(String otherId)
	{
		return new HashSet<EV>(0);
	}

	/**
	 * 求当前集合与指定集合id对应的集合的并集,并集结果为本地集合
	 * 并集是对等运算,即C(A)∪C(B)=C(B)∪C(A)
	 * @param otherSetCache otherSetCache 指定的集合
	 * @return 返回并集的本地Set
	 */
	public Set<EV> union(SetCache<EV> otherSetCache)
	{
		return new HashSet<EV>(0);
	}

	/**
	 * 求当前集合与指定集合id对应的集合的并集,并集的结果为缓存集合
	 * 并集是对等运算,即C(A)∪C(B)=C(B)∪C(A)
	 * @param otherId 指定的需要与当前集合计算并集的集合id
	 * @param destId 计算结果的缓存集合id
	 * @return 返回计算结果的缓存集合
	 */
	public SetCache<EV> unionAndCache(String otherId, String destId)
	{
		return cacheFactory.getSetCache(destId);
	}

	/**
	 * 求当前集合与指定集合id对应的集合的并集,并集的结果为缓存集合
	 * 并集是对等运算,即C(A)∪C(B)=C(B)∪C(A)
	 * @param otherSetCache 指定的需要与当前集合计算并集的集合
	 * @param destSetCache 计算结果的缓存集合
	 * @return 返回计算结果的缓存集合,即destSetCache
	 */
	public SetCache<EV> unionAndCache(SetCache<EV> otherSetCache, SetCache<EV> destSetCache)
	{
		return destSetCache;
	}

	/**
	 * 求当前集合与指定集合id对应的集合的差集,差集的结果为本地集合
	 * 差集是非对等运算,即C(A)-C(B)≠C(B)-C(A)
	 * @param otherId 指定的集合Id(被差的那个集合)
	 * @return 返回差集的本地Set
	 */
	public Set<EV> difference(String otherId)
	{
		return new HashSet<EV>(0);
	}

	/**
	 * 求当前集合与指定集合id对应的集合的差集,差集的结果为本地集合
	 * 差集是非对等运算,即C(A)-C(B)≠C(B)-C(A)
	 * @param otherSetCache 指定的集合(被差的那个集合)
	 * @return 返回差集的本地Set
	 */
	public Set<EV> difference(SetCache<EV> otherSetCache)
	{
		return new HashSet<EV>(0);
	}

	/**
	 * 求当前集合与指定集合id对应的集合的差集,差集的结果为缓存集合
	 * 差集是非对等运算,即C(A)-C(B)≠C(B)-C(A)
	 * @param otherId 指定的需要与当前集合计算交集的集合Id(被差的那个集合)
	 * @param destId 计算结果的缓存集合Id
	 * @return 返回计算结果的缓存集合
	 */
	public SetCache<EV> differenceAndStore(String otherId, String destId)
	{
		return cacheFactory.getSetCache(destId);
	}

	/**
	 * 求当前集合与指定集合id对应的集合的差集,差集的结果为缓存集合
	 * 差集是非对等运算,即C(A)-C(B)≠C(B)-C(A)
	 * @param otherSetCache 指定的需要与当前集合计算差集的集合(被差的那个集合)
	 * @param destSetCache 计算结果的缓存集合
	 * @return 返回计算结果的缓存集合,即destSetCache
	 */
	public SetCache<EV> differenceAndStore(SetCache<EV> otherSetCache, SetCache<EV> destSetCache)
	{
		return destSetCache;
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
		final SetCache other = (SetCache) obj;
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
