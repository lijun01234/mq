package cn.snsoft.base.cache.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import net.sf.json.JSONObject;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;
import cn.snsoft.base.cache.CacheFactory;
import cn.snsoft.base.data.BurlapSerializer;
import cn.snsoft.util.RedisCacheUtil;
import cn.snsoft.util.StrUtils;
/**
 * <p>标题： </p>
 * <p>功能： Redis 连接工厂的工厂!</p>
 * <p>版权： Copyright (c) 2016</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2016年5月25日 下午4:23:20</p>
 * <p>类全名：cn.snsoft.component.cache.factory.SNJedisConnectionFactoryFactory</p>
 * 作者：
 * 初审：
 * 复审：
 * @version 1.0
 * 2016年05月25日	新增,JedisConnectionFactory的工厂,即,工厂的工厂.
 * 					以便兼容配置,支持Redis 3.x的集群模式
 * 2016年06月19日    修改支持多个Redis服务,可以通过配置名字获取连接工厂 
 */
public class SNJedisConnectionFactoryFactory<EK extends Serializable,EV extends Serializable>
{
	private String								clusterHostAndPorts;
	private String								hostName;
	private int									port;
	private String								password;
	//
	private final String						ConfigPrefix		= "Spring.Context.Component.Redis.Factory.";			//非集群前缀
	private final String						ConfigClusterPrefix	= "Spring.Context.Component.Redis.Cluster.Factory.";	//集群前缀
	private static final Map<String,JSONObject>	redisConfigCache	= new ConcurrentHashMap<>();

	//Spring 初始化方法...
	public void init()
	{
		//Default:兼容老配置
		{
			if (!StrUtils.isStrTrimNull(clusterHostAndPorts) && (!clusterHostAndPorts.startsWith("${") && !clusterHostAndPorts.endsWith("}")))
			{
				//Spring.Context.Component.Redis.Cluster.Factory.Default
				JSONObject configJson = new JSONObject();
				configJson.put("HostAndPorts", clusterHostAndPorts);
				configJson.put("PoolMaxIdle", 200);
				configJson.put("PoolMaxTotal", 1000);
				configJson.put("PoolMaxWaitMillis", 10000);
				redisConfigCache.put("Spring.Context.Component.Redis.Cluster.Factory.Default", configJson);
			} else if (hostName != null && port > 0)
			{
				//Spring.Context.Component.Redis.Factory.Default
				JSONObject configJson = new JSONObject();
				configJson.put("HostAndPorts", hostName + ":" + port);
				configJson.put("Password", password);
				configJson.put("PoolMaxIdle", 200);
				configJson.put("PoolMaxTotal", 1000);
				configJson.put("PoolMaxWaitMillis", 10000);
				redisConfigCache.put("Spring.Context.Component.Redis.Factory.Default", configJson);
			}
		}
		//Config里面的Spring.Context.开头的配置,已经全被塞到了Properties里面,不用单独取Config了
		{
			Properties properties = System.getProperties();
			if (properties != null && !properties.isEmpty())
			{
				for (final Map.Entry<Object,Object> enery : properties.entrySet())
				{
					final String key = StrUtils.obj2str(enery.getKey());
					if (key != null && key.startsWith("Spring.Context.Component.Redis."))
					{
						final String value = StrUtils.obj2str(enery.getValue());
						if (value != null)
						{
							redisConfigCache.put(key, JSONObject.fromObject(value));
						}
					}
				}
			}
		}
	}

	//装载配置:
	public String getClusterHostAndPorts()
	{
		return clusterHostAndPorts;
	}

	public void setClusterHostAndPorts(String clusterHostAndPorts)
	{
		this.clusterHostAndPorts = clusterHostAndPorts;
	}

	public String getHostName()
	{
		return hostName;
	}

	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isClusterModel(String name)
	{
		boolean bClusterModel = redisConfigCache.containsKey(ConfigClusterPrefix + name);
		if (bClusterModel)
		{
			return bClusterModel;
		} else
		{
			if ("Default".equalsIgnoreCase(name))
			{//旧版集群配置:
				return !StrUtils.isStrTrimNull(clusterHostAndPorts) && !clusterHostAndPorts.startsWith("${") && !clusterHostAndPorts.endsWith("}");
			} else
			{
				return false;
			}
		}
	}

	public SNJedisConnectionFactory createJedisConnectionFactory(String name)
	{
		//创建连接池配置
		if (!isClusterModel(name))
		{//非Redis3.0集群
			JSONObject configJson = redisConfigCache.get(ConfigPrefix + name);
			if (configJson == null || configJson.isEmpty() || !configJson.containsKey("HostAndPorts"))
			{
				throw new RuntimeException("Redis配置[" + ConfigPrefix + name + "]有错误,或者未找到相应的HostAndPorts配置.");
			}
			String hostAndPorts = StrUtils.obj2str(configJson.get("HostAndPorts"));
			String[] hostAndPortSplit = StrUtils.splitString(hostAndPorts, ':');
			if (hostAndPortSplit.length < 1)
			{
				throw new RuntimeException("Redis配置[" + ConfigPrefix + name + "]有错误,HostAndPorts格式有误,HostAndPor[" + hostAndPorts + "],期望格式[Host:<Port>].");
			}
			//Log
			SNJedisConnectionFactory jedisConnectionFactory = new SNJedisConnectionFactory();
			jedisConnectionFactory.setHostName(hostAndPortSplit[0].trim());
			jedisConnectionFactory.setPort(hostAndPortSplit.length >= 2 ? StrUtils.obj2int(hostAndPortSplit[1], 0) : 6379);//默认端口6379
			String password = StrUtils.obj2str(configJson.get("Password"));
			if (!StrUtils.isStrTrimNull(password))
			{
				jedisConnectionFactory.setPassword(password.trim());
			}
			jedisConnectionFactory.setPoolConfig(getJedisPoolConfig(configJson));
			jedisConnectionFactory.setClusterModel(false);
			return jedisConnectionFactory;
		} else
		{//Redis3.0集群 Redis集群 暂不支持密码!请通过bind配置,来限制访问权限.
			JSONObject configJson = redisConfigCache.get(ConfigClusterPrefix + name);
			if (configJson == null || configJson.isEmpty() || !configJson.containsKey("HostAndPorts"))
			{
				throw new RuntimeException("Redis配置[" + ConfigClusterPrefix + name + "]有错误,或者未找到相应的HostAndPorts配置.");
			}
			String hostAndPorts = StrUtils.obj2str(configJson.get("HostAndPorts"));
			List<String> nodeList = new ArrayList<>();
			String[] nodeArr = StrUtils.splitString(hostAndPorts, ',');
			if (nodeArr == null || nodeArr.length == 0)
			{
				throw new RuntimeException("Redis集群连接地址配置错误!.");
			}
			for (String node : nodeArr)
			{
				if (node.indexOf('-') > 0)
				{//配置有端口范围
					String[] split = StrUtils.splitString(node, ':');
					if (split.length != 2)
					{
						throw new RuntimeException("Redis集群连接地址配置错误!.");
					}
					String host = split[0];
					String ports = split[1];
					String[] portSplit = StrUtils.splitString(ports, '-');
					int portFrom = StrUtils.obj2int(portSplit[0],0);
					int portTo = StrUtils.obj2int(portSplit[1],0);
					for (int i = portFrom; i <= portTo; i++)
					{
						nodeList.add(host + ":" + i);
					}
				} else
				{
					nodeList.add(node);
				}
			}
			RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(nodeList);
			SNJedisConnectionFactory jedisConnectionFactory = new SNJedisConnectionFactory(redisClusterConfiguration, getJedisPoolConfig(configJson));
			jedisConnectionFactory.setClusterModel(true);
			return jedisConnectionFactory;
		}
	}

	private JedisPoolConfig getJedisPoolConfig(JSONObject configJson)
	{
		if (configJson == null || configJson.isEmpty())
		{
			JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setMaxIdle(200);
			jedisPoolConfig.setMaxTotal(1000);
			jedisPoolConfig.setMaxWaitMillis(10000);
			return jedisPoolConfig;
		}
		int maxIdle = StrUtils.obj2int(configJson.get("PoolMaxIdle"), 200);
		int maxTotal = StrUtils.obj2int(configJson.get("PoolMaxTotal"), 1000);
		int maxWaitMillis = StrUtils.obj2int(configJson.get("PoolMaxWaitMillis"), 10000);
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxTotal(maxTotal);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		return jedisPoolConfig;
	}

	public SNJedisConnectionFactory createDefaultJedisConnectionFactory()
	{
		return createJedisConnectionFactory("Default");
	}

	private final Map<String,CacheFactory<EK,EV>>	redisConnectionFactoryCache	= new ConcurrentHashMap<>();

	public CacheFactory<EK,EV> getCacheFactory(String name)
	{
		CacheFactory<EK,EV> cacheFactory = redisConnectionFactoryCache.get(name);
		if (cacheFactory != null)
		{
			return cacheFactory;
		}
		synchronized (name.intern())
		{
			cacheFactory = redisConnectionFactoryCache.get(name);
			if (cacheFactory != null)
			{
				return cacheFactory;
			}
			SNJedisConnectionFactory connectionFactory = createJedisConnectionFactory(name);
			connectionFactory.afterPropertiesSet();
			RedisTemplate<String,EV> redisTpl = new RedisTemplate<>();
			redisTpl.setConnectionFactory(connectionFactory);
			redisTpl.afterPropertiesSet();
			redisTpl.setKeySerializer(new BurlapSerializer<String>());
			RedisCacheUtil<EV> redisCacheUtil = new RedisCacheUtil<>(redisTpl);
			cacheFactory = new CacheFactory<>(redisCacheUtil);
			cacheFactory._init();
			redisConnectionFactoryCache.put(name, cacheFactory);//缓存
			return cacheFactory;
		}
	}
}
