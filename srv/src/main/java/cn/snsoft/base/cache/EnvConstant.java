package cn.snsoft.base.cache;

import cn.snsoft.util.StrUtils;

/**
 * <p>标题： 当前程序运行环境的常量</p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-11-20 下午2:02:15</p>
 * <p>类全名：cn.snsoft.comm.constant.EnvConstant</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * @version 8.0
 */
/*
 * snsoft.util.Diagnostic
 * Diagnostic.TraceLevel
 */
public class EnvConstant
{
	/*
	 * 测试级别 sn.testEnv
	 * 0 等同于生产环境 全部异常通过日志输出
	 * 1 等同于测试环境 不隐藏异常 不屏蔽数据验证程序 不屏蔽性能测试程序
	 * 2 等同于开发调试环境 数据不再缓存 发布时不再压缩 行为日志不再输出
	 */
	private static int			testEnv		= 0;
	public final static String	TESTNVKEY	= "sn.testEnv";
	static
	{
		try
		{
			String temp = System.getProperty(TESTNVKEY);
			testEnv = StrUtils.obj2int(temp, 0);
		} catch (final Throwable e)
		{
			e.printStackTrace();
		}
	}

	public static boolean isDevelopTestEnv()
	{
		//int _d = DataConfig.getIntConfig(TESTNVKEY, -1);
		///return _d == -1 ? testEnv == 2 : _d == 2;
		return true;
	}

	public static boolean isTestEnv()
	{
		//int _d = DataConfig.getIntConfig(TESTNVKEY, -1);
		//return _d == -1 ? testEnv == 1 : _d == 1;
		return false;
	}

	public static boolean isDebugEnv()
	{
		return isDevelopTestEnv() || isTestEnv();
	}
}
