package cn.snsoft.mq.scan;

import java.util.Map;
import cn.snsoft.base.cache.redis.RedisCompLock;
import cn.snsoft.mq.model.MQSysConn;
import cn.snsoft.mq.model.MqScanConf;
import cn.snsoft.mq.model.MqScanConfMatch;
/**
 * <p>标题：文件目录扫描锁 </p>
 * <p>功能： </p>
 * <p>所属模块： MQ</p>
 * <p>版权： Copyright © 2015 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2015年10月23日 下午3:53:30</p>
 * <p>类全名：cn.snsoft.component.mq.scan.MQScanFilesLock</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
public class MQScanFilesLock extends RedisCompLock
{
	private MQScanFilesLock()
	{
		super("MQScanFilesLock");
	}

	/**目录扫描定义 相关锁定操作**/
	//目录锁定
	public boolean scanFilesLock(MqScanConf scanConf, String className)
	{
		if (isScanFilesStop(className, scanConf.getScid()))
		{
			return true;
		}
		String key = className + ':' + scanConf.getScid();
		boolean isLock = this.lock(key);
		if (isLock)
		{
			long lock = this.decr(key);
			if (lock <= 1)
			{
				this.destroy(key);
				return false;
			}
		}
		return isLock;
	}

	//目录扫描 交互锁定
	public void scanFilesLock(MqScanConfMatch scanConfMatch, String className)
	{
		String key = className + ':' + scanConfMatch.getScid();
		this.lock(key);
	}

	//目录扫描 交互解锁，全部交互解锁完后目录自动解锁
	public void scanFilesUnLock(MqScanConfMatch scanConfMatch, String className)
	{
		String key = className + ':' + scanConfMatch.getScid();
		long lock = this.decr(key);
		if (lock <= 1)
		{
			this.destroy(key);
		}
	}

	//目录解锁    只在异常、初始化或手工时调用
	public void scanFilesUnLock(MqScanConf scanConf, String className)
	{
		String key = className + ':' + scanConf.getScid();
		this.destroy(key);
	}

	/**交互定义 相关锁定操作**/
	//交互锁操作
	public boolean scanFilesLock(MQSysConn conn, String className)
	{
		if (isScanFilesStop(className, conn.getId()))
		{
			return true;
		}
		String key = className + ':' + conn.getId();
		return this.lock(key);
	}

	//交互解锁
	public void scanFilesUnLock(MQSysConn conn, String className)
	{
		scanFilesUnLock(className, conn.getId());
	}

	public boolean isScanFilesLock(String className, String id)
	{
		String key = className + ':' + id;
		return this.isLock(key);
	}

	public void scanFilesUnLock(String className, String id)
	{
		String key = className + ':' + id;
		this.destroy(key);
	}

	/**暂停相关操作**/
	public boolean isScanFilesStop(String className, String id)
	{
		String key = "STOP:" + className + ':' + id;
		return this.isLock(key);
	}

	public void stopScanFiles(String className, String id)
	{
		String key = "STOP:" + className + ':' + id;
		this.lock(key);
	}

	public void startScanFiles(String className, String id)
	{
		String key = "STOP:" + className + ':' + id;
		this.destroy(key);
	}

	//以下为相关的静态方法
	public static MQScanFilesLock	lock	= new MQScanFilesLock();

	static public void scanFilesUnLock(Map envParams, Map parameter)
	{
		String srctype = (String) parameter.get("srctype");
		String srcid = (String) parameter.get("srcid");
		lock.scanFilesUnLock(srctype, srcid);
	}

	//@snsoft.annotation.Remoteable
	static public void startScanFiles(Map envParams, Map parameter)
	{
		String srctype = (String) parameter.get("srctype");
		String srcid = (String) parameter.get("srcid");
		lock.startScanFiles(srctype, srcid);
	}

	//@snsoft.annotation.Remoteable
	static public void stopScanFiles(Map envParams, Map parameter)
	{
		String srctype = (String) parameter.get("srctype");
		String srcid = (String) parameter.get("srcid");
		lock.stopScanFiles(srctype, srcid);
	}
}
