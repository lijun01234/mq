package cn.snsoft.base.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import cn.snsoft.base.cache.util.CacheUtil;
/**
 * <p>标题： 按权重排序(自动排序)的集合,基类</p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午12:51:16</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.NO
 * 		排序按照权重从小到大
 */
public class ZSetCache<EV extends Serializable> implements Iterable<EV>
{
	protected final CacheFactory<?,EV>	cacheFactory;
	protected final CacheUtil<EV>		cacheUtil;
	protected final String				key;

	/**
	 * 构造器,仅限工厂类
	 * @param id Redis缓存的id
	 * @param cacheUtil 缓存工具的对象
	 * @param cacheFactory 缓存工厂的对象
	 */
	public ZSetCache(String id, CacheFactory<?,EV> cacheFactory, CacheUtil<EV> cacheUtil)
	{
		this.key = id;
		this.cacheFactory = cacheFactory;
		this.cacheUtil = cacheUtil;
	}

	/**
	 * 获取缓存Id
	 * @return 该集合对应的缓存服务器的key
	 */
	public String getZSetCacheId()
	{
		return key;
	}

	/**
	 * 如果 ZSet 中尚未存在指定的元素,则添加此元素(可选操作).更确切地讲,如果此 ZSet 没有包含满足 (e==null ? e2==null : e.equals(e2)) 的元素 e2,
	 * 则向该 ZSet 中添加指定的元素 e.如果此 ZSet 已经包含该元素,则该调用不改变此 ZSet 并返回 false.结合构造方法上的限制,这就可以确保 set 永远不包含重复的元素.
	 * 上述规定并未暗示 ZSet 必须接受所有元素;ZSet 可以拒绝添加任意特定的元素,包括 null,并抛出异常,这与 Collection.add 规范中所描述的一样.
	 * 每个 ZSet 实现应该明确地记录对其可能包含元素的所有限制(应该使用泛型).
	 * ZSet中的顺序按照score从小到大排序
	 * @param e 要添加到 set 中的元素
	 * @param score 排序权重 权重码的范围是0.0~8388607.2147483647
	 * @return 如果 set 尚未包含指定的元素,则返回 true
	 * @author 王立鹏
	 */
	public boolean add(EV e, double score)
	{
		return false;
	}

	/**
	 * 取符合权重范围的元素个数.
	 * @param min 权重范围的下线
	 * @param max 权重范围的上线
	 * @return 符合条件的元素个数
	 * 		Redis:count
	 */
	public long size(double min, double max)
	{
		return 0;
	}

	/**
	 * 返回 set 中的元素数(其容量).
	 * @return 此 ZSet 中的元素总数(其容量)
	 * @author 王立鹏
	 */
	public long size()
	{
		return 0;
	}

	/**
	 * 将指定的元素,增加相应的权重增量!增量为正数表示增加,为负数表示减少
	 * @param value 指定的元素
	 * @param delta 权重增量值 
	 * @return 增加后的权重
	 * @author 王立鹏
	 * 		权重码的范围是0.0~8388607.2147483647
	 */
	public double incScore(EV value, double delta)
	{
		return 0.0;
	}

	/**
	 * 返回指定位置的元素的Set,0为第一个元素，-1为最后一个元素
	 * 返回的Set仍然具有顺序!
	 * @param start 指定的起始位置
	 * @param end 指定的结束位置
	 * @return 指定位置之间的元素构成的本地集合
	 * @author 王立鹏
	 * 		Redis:range
	 * 		[start,end]闭区间
	 */
	public Set<EV> subLocalSet(long start, long end)
	{
		return new HashSet<EV>(0);
	}

	/**
	 * 返回指定位置的元素的Set,0为第一个元素，-1为最后一个元素,按照反序排序
	 * 返回的Set仍然具有顺序!
	 * @param start 指定的起始位置
	 * @param end 指定的结束位置
	 * @return 指定位置之间的元素构成的本地集合
	 * @author 王立鹏
	 * 		Redis:reverseRange+
	 * 		[start,end]闭区间
	 */
	public Set<EV> subLocalSetDesc(long start, long end)
	{
		return new HashSet<EV>(0);
	}

	/**
	 * 返回指定位置的元素,0为第一个元素，-1为最后一个元素
	 * @param i 位置
	 * @return 指定位置的元素
	 */
	public final EV get(long i)
	{
		Set<EV> subLocalSet = subLocalSet(i, i);
		Iterator<EV> iterator = subLocalSet.iterator();
		EV ev = iterator.next();
		return ev;
	}

	/**
	 * 返回指定位置的元素的Set,包含权重信息!,0为第一个元素，-1为最后一个元素
	 * 返回的Set仍然具有顺序!
	 * Set中的TypedTuple是Spring-data框架中的类,里面包含getScore()和getValue()方法,用于获取权重和元素值
	 * @param start 指定的起始位置
	 * @param end 指定的结束位置
	 * @return 指定位置之间的元素构成的本地TypedTuple的集合
	 * @author 王立鹏
	 * 		Redis:rangeWithScores
	 */
	public Set<TypedTuple<EV>> subLocalSetWithScores(long start, long end)
	{
		return new HashSet<TypedTuple<EV>>(0);
	}

	/**
	 * 返回指定位置的元素的Set,包含权重信息!,0为第一个元素，-1为最后一个元素,按照反序排序!
	 * @param start 指定的起始位置
	 * @param end 指定的结束位置
	 * @return	指定位置之间的元素构成的本地TypedTuple的集合
	 * @author 王立鹏
	 * 		Redis:reverseRangeWithScores
	 */
	public Set<TypedTuple<EV>> subLocalSetWithScoresDesc(long start, long end)
	{
		return new HashSet<TypedTuple<EV>>(0);
	}

	/**
	 * 返回指定权重范围的元素的Set
	 * 返回的Set仍然具有顺序!
	 * @param min 权重下线值
	 * @param max 权重上线值
	 * @return 指定位置之间的元素构成的本地集合
	 * @author 王立鹏
	 * 		Redis:rangeByScore
	 */
	public Set<EV> subLocalSetByScore(double min, double max)
	{
		return new HashSet<EV>(0);
	}

	/**
	 * 返回指定权重范围的元素的Set,按照反序排序!
	 * 返回的Set仍然具有顺序!
	 * @param min 权重下线值
	 * @param max 权重上线值
	 * @return 指定位置之间的元素构成的本地集合
	 * @author 王立鹏
	 * 		Redis:reverseRangeByScore
	 */
	public Set<EV> subLocalSetByScoreDesc(double min, double max)
	{
		return new HashSet<EV>(0);
	}

	/**
	 * 返回指定位置的元素的Set,包含权重信息!0为第一个元素，-1为最后一个元素
	 * 返回的Set仍然具有顺序!
	 * Set中的TypedTuple是Spring-data框架中的类,里面包含getScore()和getValue()方法,用于获取权重和元素值
	 * @param start 指定的起始位置
	 * @param end 指定的结束位置
	 * @return 指定位置之间的元素构成的本地集合Set<TypedTuple<EV>>
	 * @author 王立鹏
	 * 		Redis:rangeByScoreWithScores
	 */
	public Set<TypedTuple<EV>> subLocalSetByScoreWithScores(double min, double max)
	{
		return new HashSet<TypedTuple<EV>>(0);
	}

	/**
	 * 返回指定位置的元素的Set,包含权重信息!0为第一个元素，-1为最后一个元素,按照反序排序!
	 * 返回的Set仍然具有顺序!
	 * Set中的TypedTuple是Spring-data框架中的类,里面包含getScore()和getValue()方法,用于获取权重和元素值
	 * @param min 权重下线值
	 * @param max 权重上线值
	 * @return 指定位置之间的元素构成的本地集合Set<TypedTuple<EV>>
	 * @author 王立鹏
	 * 		Redis:reverseRangeByScoreWithScores
	 */
	public Set<TypedTuple<EV>> subLocalSetByScoreWithScoresDesc(double min, double max)
	{
		return new HashSet<TypedTuple<EV>>(0);
	}

	/**
	 * 获取指定值在集合中的位置,集合排序从低到高
	 * @param value 指定的元素
	 * @return 排列的位置
	 * @author 王立鹏
	 * 		Redis:rank
	 * 		注意,这个方法可能存在bug
	 */
	public long indexOf(Object value)
	{
		return -1;
	}

	/**
	 * 获取指定值在集合中的位置,集合排序从高到低
	 * @param value 指定的元素
	 * @return 排列的位置
	 * @author 王立鹏
	 * 		Redis:reverseRank
	 * 		注意,这个方法可能存在bug
	 */
	public long indexOfDesc(EV value)
	{
		return -1;
	}

	/**
	 * 如果 ZSet 中存在指定的元素,则将其移除(可选操作).更确切地讲,如果此 ZSet 中包含满足 (o==null ? e==null : o.equals(e)) 的元素 e,则移除它.
	 * 如果此 set 包含指定的元素(或者此 ZSet 由于调用而发生更改),则返回 true(一旦调用返回,则此 ZSet 不再包含指定的元素).
	 * @param o 从 ZSet 中移除的对象(如果存在)
	 * @return  如果此 ZSet 包含指定的对象,则返回 true
	 * @author 王立鹏
	 * 		Redis:remove
	 */
	public boolean remove(Object value)
	{
		return false;
	}

	/**
	 * 移除 ZSet 中那些包含在指定 collection 中的元素(可选操作).
	 * 如果指定的 collection 也是一个 ZSet,则此操作会实际修改此 ZSet,这样其值是两个 ZSet 的一个不对称差集.
	 * @param c 包含要从此 ZSet 中移除的元素的 collection
	 * @return 如果此 ZSet 由于调用而发生更改,则返回 true
	 * @author 王立鹏
	 */
	public final boolean removeAll(Collection<?> c)
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
	 * 移除 ZSet 中从start起到end之间的元素
	 * @param start 起始位置
	 * @param end 结束位置
	 * @return 如果此 ZSet 由于调用而发生更改,则返回 true
	 * @author 王立鹏
	 * 		Redis:removeRange
	 */
	public boolean removeAll(long start, long end)
	{
		return false;
	}

	/**
	 * 移除 ZSet 中权重从min起到max之间的元素
	 * @param min 权重下线
	 * @param max 权重上线
	 * @return 如果此 ZSet 由于调用而发生更改,则返回 true
	 * @author 王立鹏
	 * 		Redis:removeRangeByScore
	 */
	public boolean removeAllByScore(double min, double max)
	{
		return false;
	}

	/**
	 * 获取指定元素的权重值!
	 * @param value 指定的元素
	 * @return 权重
	 * @author 王立鹏
	 * 		Redis:score
	 */
	public double getScore(Object value)
	{
		return 0.0;
	}

	/*----------------------------------------以下方法为跨缓存对象执行的方法--------------------------------------------------*/
	/**
	* 求当前集合与指定集合id对应的集合的交集,交集的结果为缓存集合
	* 交集是对等运算,即C(A)∩C(B)=C(B)∩C(A)
	* 交集计算时,结果集合中的元素的权重,为两个原集合中该元素权重的和!
	* @param otherId 指定的需要与当前集合计算交集的集合id
	* @param destId 计算结果的缓存集合id
	* @return 返回计算结果的缓存集合
	*/
	public ZSetCache<EV> intersectAndCache(String otherId, String destId)
	{
		return cacheFactory.getZSetCache(destId);
	}

	/**
	 * 求当前集合与指定集合对应的集合的交集,交集的结果为缓存集合
	 * 交集是对等运算,即C(A)∩C(B)=C(B)∩C(A)
	 * 交集计算时,结果集合中的元素的权重,为两个原集合中该元素权重的和!
	 * @param otherSetCache 指定的需要与当前集合计算交集的集合
	 * @param destSetCache 计算结果的缓存集合
	 * @return 返回计算结果的缓存集合,即destSetCache
	 */
	public ZSetCache<EV> intersectAndCache(ZSetCache<EV> otherZSetCache, ZSetCache<EV> destZSetCache)
	{
		return destZSetCache;
	}

	/**
	 * 求当前集合与指定集合id对应的集合的并集,并集的结果为缓存集合
	 * 并集是对等运算,即C(A)∪C(B)=C(B)∪C(A)
	 * 并集计算时,结果集合中的元素的权重,为两个原集合中该元素权重的和!
	 * @param otherId 指定的需要与当前集合计算并集的集合id
	 * @param destId 计算结果的缓存集合id
	 * @return 返回计算结果的缓存集合
	 */
	public ZSetCache<EV> unionAndCache(String otherId, String destId)
	{
		return cacheFactory.getZSetCache(destId);
	}

	/**
	 * 求当前集合与指定集合id对应的集合的并集,并集的结果为缓存集合
	 * 并集是对等运算,即C(A)∪C(B)=C(B)∪C(A)
	 * 并集计算时,结果集合中的元素的权重,为两个原集合中该元素权重的和!
	 * @param otherSetCache 指定的需要与当前集合计算并集的集合
	 * @param destSetCache 计算结果的缓存集合
	 * @return 返回计算结果的缓存集合,即destSetCache
	 */
	public ZSetCache<EV> unionAndCache(ZSetCache<EV> otherZSetCache, ZSetCache<EV> destZSetCache)
	{
		return destZSetCache;
	}

	/**
	 * 如果 set 不包含元素,则返回 true.
	 * @return 如果此 set 不包含元素,则返回 true
	 * @author 王立鹏
	 */
	public final boolean isEmpty()
	{
		return size() == 0;
	}

	/**
	 * 从缓存服务器上面销毁这个ZSet,同destroy()
	 * @see destroy()
	 * @author 王立鹏
	 */
	public final void clear()
	{
		destroy();
	}

	/**
	 * 从缓存服务器上面销毁这个ZSet
	 * 销毁后,这个Set还可以继续使用,只是清除了里面的元素
	 * @author 王立鹏
	 */
	public final void destroy()
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
		return new HashSet<EV>();
	}

	/**
	 * 返回一个包含 set 中所有元素的本地数组.如果此 set 对其迭代器返回的元素的顺序作出了某些保证,那么此方法也必须按相同的顺序返回这些元素.
	 * @return 包含   此 set 中所有元素的本地数组
	 * @author 王立鹏
	 */
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
	public <T> T[] toArray(T[] a)
	{
		return getLocalSet().toArray(a);
	}

	/**
	 * 如果 set 包含指定的元素,则返回 true.更确切地讲,当且仅当 set 包含满足 (o==null ? e==null : o.equals(e)) 的元素 e 时返回 true.
	 * @param o 要测试此 set 中是否存在的元素
	 * @return 如果此 set 包含指定的元素,则返回 true
	 * @author 王立鹏
	 */
	public boolean contains(Object value)
	{
		return indexOf(value) != -1;
	}

	/**
	 * 如果此 set 包含指定 collection 的所有元素,则返回 true.如果指定的 collection 也是一个 set,那么当该 collection 是此 set 的子集 时返回 true.
	 * @param c 包含要添加到此 set 中的元素的 collection
	 * @return 如果此 set 包含指定 collection 中的所有元素,则返回 true
	 * 		注意:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 * @author 王立鹏
	 */
	public boolean containsAll(Collection<?> c)
	{
		return getLocalSet().containsAll(c);
	}

	/**
	 * 仅保留 set 中那些包含在指定 collection 中的元素(可选操作).换句话说,移除此 set 中所有未包含在指定 collection 中的元素.
	 * 如果指定的 collection 也是一个 set,则此操作会实际修改此 set,这样其值是两个 set 的一个交集.
	 * 该方法是取得缓存服务器中Set中的全部
	 *
	 * @param c 包含要保留到此 set 中的元素的 collection
	 * @return 如果此 set 由于调用而发生更改,则返回 true
	 * 		注意:这个方法需要将缓存的列表取回本地进行查找,会慢点
	 * @author 王立鹏
	 */
	public boolean retainAll(Collection<?> c)
	{
		return false;
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
		final ZSetCache other = (ZSetCache) obj;
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
