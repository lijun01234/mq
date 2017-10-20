package cn.snsoft.base.cache.redis;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;
/**
 * <p>标题： </p>
 * <p>功能：处理Redis密码的通用问题!</p>
 * <p>所属模块： 公共组件</p>
 * <p>版权： Copyright © 2015 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2015年6月6日 下午3:43:12</p>
 * <p>类全名：cn.snsoft.component.cache.factory.SNJedisConnectionFactory</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * 监听使用界面:
 * 		
 * @version 8.0
 */
public class SNJedisConnectionFactory extends JedisConnectionFactory
{
	private boolean	clusterModel	= false;

	public SNJedisConnectionFactory()
	{
		super();
	}

	public SNJedisConnectionFactory(JedisPoolConfig poolConfig)
	{
		super(poolConfig);
	}

	public SNJedisConnectionFactory(RedisClusterConfiguration clusterConfig)
	{
		super(clusterConfig);
	}

	public SNJedisConnectionFactory(RedisClusterConfiguration clusterConfig, JedisPoolConfig poolConfig)
	{
		super(clusterConfig, poolConfig);
	}

	public void setClusterModel(boolean clusterModel)
	{
		this.clusterModel = clusterModel;
	}

	public boolean isClusterModel()
	{
		return clusterModel;
	}
}
