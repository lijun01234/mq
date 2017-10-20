package cn.snsoft.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import cn.snsoft.base.cache.EnvConstant;
import cn.snsoft.base.cache.util.CacheUtil;
/**
 * <p>标题： Redis缓存工具</p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-12-4 下午3:52:24</p> 
 * <p>类全名：cn.snsoft.component.cache.util.RedisCacheUtil</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		缓存类型为CacheType.REDIS
 */
@Component("component-RedisCacheUtil")
public class RedisCacheUtil<EV extends Serializable> extends CacheUtil<EV>
{
	@Resource(name = "component-RedisTemplate")
	protected RedisTemplate<String,EV>	redisTemplate;	//spring的Redis模板

	RedisCacheUtil()
	{//禁止new对象,使用依赖注入CacheFactory,通过CacheFactory获取工具
	}

	public RedisCacheUtil(RedisTemplate<String,EV> redisTemplate)
	{
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 情况全部缓存
	 * 危险方法,请慎用!
	 */
	@Override
	@Deprecated
	public boolean $__cleanAllCacheData()
	{
		if (!EnvConstant.isDevelopTestEnv())
		{
			//throw new RuntimeException("该方法仅限于开发阶段测试调用!");
		}
		try
		{
			_getRedisTemplate().getConnectionFactory().getConnection().flushDb();
			//LogTools.log.info("\n\n您的操作已经将Redis缓存已经被全部清空,请注意!!\n\n");
			return true;
		} catch (final Exception e)
		{
			_logCacheError(e);
			return false;
		}
	}

	/**
	 * 判断Redis缓存中是否存在指定的key
	 * @param key
	 * @return
	 */
	@Override
	public boolean hasKey(final String key)
	{
		return _getRedisTemplate().hasKey(key);
	}

	/**
	 * 将Redis缓存中指定的key重名呢,如果新key存在,则覆盖之
	 * @param oldKey key旧名称
	 * @param newKey key新名称
	 * @return 执行成功返回true
	 */
	@Override
	public boolean rename(final String oldKey, final String newKey)
	{
		try
		{
			_getRedisTemplate().rename(oldKey, newKey);
			return true;
		} catch (final Exception e)
		{
			_logCacheError(e);
			return false;
		}
	}

	/**
	 * 将Redis缓存中指定的key重名呢,仅当新key不存在的时候才执行
	 * @param oldKey key旧名称
	 * @param newKey key新名称
	 * @return 执行成功返回true
	 */
	@Override
	public boolean renameIfAbsent(final String oldKey, final String newKey)
	{
		try
		{
			return _getRedisTemplate().renameIfAbsent(oldKey, newKey);
		} catch (final Exception e)
		{
			_logCacheError(e);
			return false;
		}
	}

	/**
	 * 从Redis服务器上删除指定的key
	 * @param key 指定的key
	 */
	@Override
	public boolean delete(final String key)
	{
		try
		{
			_getRedisTemplate().delete(key);
			//modify by pxs,20170103 日志太多，调为dubug级别
			//LogTools.log.info("Redis缓存key=" + key + "删除!");
			//LogTools.log.debug("Redis缓存key=" + key + "删除!");
			return true;
		} catch (final Exception e)
		{
			_logCacheError(e);
			return false;
		}
	}

	/**
	 * 从Redis服务器上删除Collection中的所有key
	 * @param keys 包含待删除key的Collection
	 */
	@Override
	public void deleteAll(final Collection<String> keys)
	{
		_getRedisTemplate().delete(keys);
		//		LogTools.log.info("Redis缓存keys=" + keys + "全部删除!");
		//LogTools.log.debug("Redis缓存keys=" + keys + "全部删除!");
	}

	/**
	 * 从Redis服务器上获取指定key对应的存储内容的Redis数据类型
	 * @param key 指定的key
	 * @return 数据类型,DataType的枚举
	 */
	@Override
	public DataType getType(final String key)
	{
		final DataType type = _getRedisTemplate().type(key);
		return type;
	}

	/**
	 * 重置Redis服务器中有时效性的缓存的缓存有效时间
	 * @param key 指定的缓存
	 * @param timeout 指定的时间值
	 * @param unit 时间的单位TimeUnit的枚举
	 * @return 成功返回true
	 */
	@Override
	public boolean updateTimeout(final String key, final long timeout, final TimeUnit unit)
	{
		if (key == null)
		{
			return false;
		}
		return _getRedisTemplate().expire(key, timeout, unit);
	}

	/**
	 * 返回Redis缓存对象的剩余生存时间
	 * @param key 指定的缓存Key
	 * @return 剩余时间,单位是秒. 如果key不存在或者未设置有效期,返回-1
	 */
	@Override
	public long getTimeout(final String key)
	{
		if (key == null)
		{
			return -2L;
		}
		return _getRedisTemplate().getExpire(key);
	}

	/**
	 * 取出符合特定格式的key
	 * @param pattern 格式
	 * @return key的一个Set(集合)
	 *		key里面有"[","]"等符号的时候,这些符号需要转意!->"\\[","\\]"
	 */
	@Override
	public Set<String> getKeys(final String pattern)
	{
		final Set<String> keys = _getRedisTemplate().keys(pattern);
		return keys;
	}

	/**
	 * 获取Spring的Redis模板
	 * @return
	 */
	public RedisTemplate<String,EV> _getRedisTemplate()
	{
		return redisTemplate;
	}
}
