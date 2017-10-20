package cn.snsoft.db;

import java.sql.Connection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
/**
 * 多数据源链接池
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2017</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2017年10月20日 下午3:51:41</p>
 * <p>类全名：cn.snsoft.db.MultiPool</p>
 * 作者：
 * @version 1.0
 */
public class MultiPool
{
	private static MultiPool					instance	= null;
	private static Map<String,List<Connection>>	pool;
	private static Map<String,List<Connection>>	free		= null;
	static
	{
		free = new Hashtable<String,List<Connection>>();
		pool = new Hashtable<String,List<Connection>>();
	}

	/**
	 * 获取实例
	 * @return
	 */
	public static MultiPool getInstance()
	{
		if (instance == null)
		{
			instance = new MultiPool();
		}
		return instance;
	}

	/**
	 * 获取一个指定库的连接
	 * @param dbType
	 * @return
	 */
	public Connection get(String dbType)
	{
		return first(dbType);
	}

	/**
	 * 返回第一个空闲的连接
	 * @param dbType
	 * @return
	 */
	synchronized Connection first(String dbType)
	{
		Connection ret = free.get(dbType).get(0);
		free.remove(0);
		return ret;
	}

	/**
	 * 释放连接
	 * @param dbType
	 * @param conn
	 */
	public synchronized void release(String dbType, Connection conn)
	{
		free.get(dbType).add(conn);
	}

	/**
	 * 往连接池中增加一个连接
	 * @param dbType
	 * @param conn
	 */
	public synchronized void add(String dbType, Connection conn)
	{
		if (pool.get(dbType) == null)
		{
			List<Connection> list = new Vector<Connection>();
			pool.put(dbType, list);
		}
		if (free.get(dbType) == null)
		{
			List<Connection> fs = new Vector<Connection>();
			free.put(dbType, fs);
		}
		pool.get(dbType).add(conn); //连接池增加一个连接
		free.get(dbType).add(conn); //同时往空闲池增加一个连接
		//System.out.println("add");
	}

	/***
	 * 连接池是否活动
	 * @return
	 */
	public boolean isAlive()
	{
		if (pool.size() == 0)
			return false;
		return true;
	}

	/**
	 * 关闭连接池
	 */
	public void close()
	{
		pool.clear();
		free.clear();
	}
}
