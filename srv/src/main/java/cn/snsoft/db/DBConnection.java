package cn.snsoft.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
public class DBConnection
{
	private static Properties	props	= null;
	static
	{//加载配置
		props = new Properties();
		try
		{
			props.load(new FileInputStream("./src/main/resources/config/jdbc.properties"));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 获取驱动类名
	 */
	private String buildDriverClassName(String dbType)
	{
		String key = new StringBuilder().append(dbType).append(".").append("driverClassName").toString();
		return props.getProperty(key);
	}

	/**
	 * 拼接url
	 */
	private String buildUrl(String dbType, String ip, String port, String db)
	{
		String key = new StringBuilder().append(dbType).append(".").append("url").toString();
		String value = props.getProperty(key);
		return urlReplace(value, new String[] { ip, port, db });
	}

	/**
	 * 替换url
	 */
	private String urlReplace(String url, String[] args)
	{
		url = url.replace("[ip]", args[0]);
		url = url.replace("[port]", args[1]);
		url = url.replace("[db]", args[2]);
		return url;
	}

	public DBConnection(String dbType, String driverClassName, String url, String username, String password)
	{
		super();
		this.dbType = dbType;
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public DBConnection(String dbType, String ip, String port, String db, String username, String password)
	{
		super();
		this.dbType = dbType;
		this.driverClassName = buildDriverClassName(dbType);
		this.url = buildUrl(dbType, ip, port, db);
		this.username = username;
		this.password = password;
	}

	public String getDriverClassName()
	{
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName)
	{
		this.driverClassName = driverClassName;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getMinIdle()
	{
		return minIdle;
	}

	public void setMinIdle(int minIdle)
	{
		this.minIdle = minIdle;
	}

	public int getMaxIdle()
	{
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle)
	{
		this.maxIdle = maxIdle;
	}

	public int getInitialSize()
	{
		return initialSize;
	}

	public void setInitialSize(int initialSize)
	{
		this.initialSize = initialSize;
	}

	public int getMaxActive()
	{
		return maxActive;
	}

	public void setMaxActive(int maxActive)
	{
		this.maxActive = maxActive;
	}

	public int getMaxWait()
	{
		return maxWait;
	}

	public void setMaxWait(int maxWait)
	{
		this.maxWait = maxWait;
	}

	public String getDbType()
	{
		return dbType;
	}

	public void setDbType(String dbType)
	{
		this.dbType = dbType;
	}

	private String	dbType;
	private String	driverClassName;
	private String	url;
	private String	username;
	private String	password;
	private int		minIdle		= 1;		//最小个数
	private int		maxIdle		= 10;		//最大个数
	private int		initialSize	= 3;		//初始化大小
	private int		maxActive	= 5;		//最大活跃数
	private int		maxWait		= 3000000;	//最大等待时间, 毫秒
}
