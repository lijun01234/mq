package cn.snsoft.base.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * <p>标题： 线程创建工程，用于给线程指定名称，便于查找堆栈</p>
 * <p>功能： </p>
 * <p>所属模块： ROOTBAS</p>
 * <p>版权： Copyright © 2017 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2017年8月24日 下午2:23:42</p>
 * <p>类全名：snsoft.rootbas.tools.thread.pub.BasThreadFactory</p>
 * 作者：PengXS
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
public class BasThreadFactory implements ThreadFactory
{
	private static final AtomicInteger	poolNumber		= new AtomicInteger(1);
	private final ThreadGroup			group;
	private final AtomicInteger			threadNumber	= new AtomicInteger(1);
	private final String				namePrefix;

	public BasThreadFactory(String poolName)//这儿指定名称，便于查看线程堆栈
	{
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = poolName + "-" + poolNumber.getAndIncrement() + "-thread-";
	}

	@Override
	public Thread newThread(Runnable r)
	{
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		if (t.isDaemon())
		{
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY)
		{
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}
}
