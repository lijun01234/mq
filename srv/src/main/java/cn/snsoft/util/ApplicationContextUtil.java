/**
 * 
 */
package cn.snsoft.util;

import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * <p>标题： 获取 ApplicationContext 工具</p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-9-21 下午8:31:57</p>
 * <p>类全名：cn.snsoft.comm.context.ApplicationContextUtil</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 编辑：宋建中
 * @version 1.0
 */
public class ApplicationContextUtil implements ApplicationContextAware
{
	private static volatile ApplicationContext	context;

	public static ApplicationContext getContext()
	{
		checkApplicationContext();
		return context;
	}

	public static <T> T getBean(String beanId)
	{
		checkApplicationContext();
		T bean = (T) context.getBean(beanId);
		if (bean == null)
		{
			throw new java.lang.RuntimeException("未找到 BEAN：ID=" + beanId + ", 请检查 applicationContext-xxx.xml 定义！");
		}
		return bean;
	}

	public static <T> Map<String,T> getBean(Class<T> clazz)
	{
		checkApplicationContext();
		Map<String,T> beanMap = context.getBeansOfType(clazz);
		if (beanMap == null)
		{
			throw new java.lang.RuntimeException("未找到 BEAN：Class=" + clazz.getName() + ", 请检查 applicationContext-xxx.xml 定义！");
		}
		return beanMap;
	}

	public static <T> T getBean(String beanId, Class<T> clazz)
	{
		checkApplicationContext();
		T bean = context.getBean(beanId, clazz);
		if (bean == null)
		{
			throw new java.lang.RuntimeException("未找到 BEAN：ID=" + beanId + ", & Class=" + clazz.getName() + "  请检查 applicationContext-xxx.xml 定义！");
		}
		return bean;
	}

	public static void setContext(ApplicationContext ctx)
	{
		context = ctx;
	}

	private static synchronized void checkApplicationContext()
	{
		if (context == null)
		{
			throw new IllegalStateException("applicaitonContext未注入!");
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)
	{
		ApplicationContextUtil.context = ctx;
	}
}
