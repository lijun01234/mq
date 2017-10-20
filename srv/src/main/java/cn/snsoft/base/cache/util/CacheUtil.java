package cn.snsoft.base.cache.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.springframework.data.redis.connection.DataType;
/**
 * <p>标题： 缓存工具,基类</p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-29 下午1:49:34</p>
 * <p>类全名：cn.snsoft.component.cache.util.CacheUtil</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.NO
 */
public class CacheUtil<EV extends Serializable>
{
	public CacheUtil()
	{//禁止new对象,使用依赖注入CacheFactory,通过CacheFactory获取工具
	}

	/**
	 * 清空全部缓存
	 * 危险方法,请慎用!
	 * 正式环境下,任何人禁止使用!!!!!!
	 */
	@Deprecated
	public boolean $__cleanAllCacheData()
	{
		return false;
	}

	/**
	 * 判断Redis缓存中是否存在指定的key
	 * @param id
	 * @return
	 */
	public boolean hasKey(String id)
	{
		return false;
	}

	/**
	 * 将Redis缓存中指定的key重名呢,如果新key存在,则覆盖之
	 * @param oldKey key旧名称
	 * @param newKey key新名称
	 * @return 执行成功返回true
	 */
	public boolean rename(String oldKey, String newKey)
	{
		return false;
	}

	/**
	 * 将Redis缓存中指定的key重名呢,仅当新key不存在的时候才执行
	 * @param oldKey key旧名称
	 * @param newKey key新名称
	 * @return 执行成功返回true
	 */
	public boolean renameIfAbsent(String oldKey, String newKey)
	{
		return false;
	}

	/**
	 * 从Redis服务器上删除指定的key
	 * @param key 指定的key
	 */
	public boolean delete(String key)
	{
		return false;
	}

	/**
	 * 从Redis服务器上删除Collection中的所有key
	 * @param keys 包含待删除key的Collection
	 */
	public void deleteAll(Collection<String> keys)
	{
	}

	/**
	 * 从服务器上获取指定key对应的存储内容的数据类型
	 * @param key 指定的key
	 * @return 数据类型,DataType的枚举
	 */
	@Deprecated
	public DataType getType(String key)
	{
		return DataType.NONE;
	}

	/**
	 * 重置Redis服务器中有时效性的缓存的缓存有效时间 
	 * @param key 指定的缓存
	 * @param timeout 指定的时间值
	 * @param unit 时间的单位TimeUnit的枚举
	 * @return 成功返回true
	 */
	public boolean updateTimeout(String key, long timeout, TimeUnit unit)
	{
		return false;
	}

	/**
	 * 返回Redis缓存对象的剩余生存时间
	 * @param key 指定的缓存Key
	 * @return 剩余时间,单位是秒. 如果key不存在或者未设置有效期,返回-1
	 */
	public long getTimeout(final String key)
	{
		return -2;
	}

	/**
	 * 取出符合特定格式的key
	 * @param pattern 格式
	 * @return key的一个Set(集合)
	 * 		仅返回String类型的key,其他类型的key不能用这个方法统配获取
	 * 		key里面有"[","]"等符号的时候,这些符号需要转意!->"\\[","\\]"
	 */
	public Set<String> getKeys(String pattern)
	{
		return new HashSet<String>(0);
	}

	/*--------------------------------以下方法为内部方法,其他人不要使用---------------------------------------*/
	/**
	 * 记录缓存错误日志
	 * @param Message 日志信息
	 * 		内部方法,其他人严禁调用!
	 */
	public void _logCacheError(Object m)
	{
		if (m instanceof Throwable)
		{
			m = ((Throwable) m).getMessage();
		}
		//TODO 记录日志
		//LogAdapter logAdapter = LogTools.newLogAdapter(LogType.EXCEPTION);
		//logAdapter.log(Level.ERROR, m, true);
	}
}
