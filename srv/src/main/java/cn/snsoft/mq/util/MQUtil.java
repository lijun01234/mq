package cn.snsoft.mq.util;

import java.util.Map;
import cn.snsoft.mq.constant.MQConstant;
import cn.snsoft.util.ConfigUtil;
public class MQUtil
{
	/**
	 * 从config中获取FTP的真实地址
	 * @param envParams
	 * @param fsmacro
	 * @return
	 */
	public static String getMQFtpPath(Map<String,Object> envParams, String fsmacro)
	{
		//MQUtil.getMQFtpPath(envParams, fsmacro);
		//String key = getMQFtpConfigKey(fsmacro);
		//String path = StrUtil.obj2str(DataConfig.getDataConfig(envParams, key));
		String path = ConfigUtil.getElementValue(fsmacro);
		// modify by pxs 20160314  这儿统一将跟路径加上/结尾  防止后面的程序有的加有的不加，导致出现连接池中有两个链接的情况
		return path == null ? path : path.endsWith("/") ? path : path + "/";
	}

	/**
	 * 获得FTP全部宏
	 * @return
	 */
	public static String getWholeFsmacro(String fsmacro)
	{
		//{FS.MQSERVICE_FS001}/4200.4260.0010.0010/in/20131217203815003629.XML
		//MQUtil.getWholeFsmacro(getFsmacro());
		return MQConstant.SERVICEMACROPRE + fsmacro;
	}
}
