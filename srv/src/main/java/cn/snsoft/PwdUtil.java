package cn.snsoft;

import java.io.IOException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
/**
 * 
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2017</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2017年10月9日 下午4:39:22</p>
 * <p>类全名：cn.snsoft.PwdUtil</p>
 * 
 * 作者：
 * @version 1.0
 */
public class PwdUtil
{
	public static void main(String[] args)
	{
		System.err.println(decode("123123"));
		System.err.println(encode(decode("123123")));
	}

	/** 
	 * @param bytes 
	 * @return 
	 */
	public static String decode(String str)
	{
		return new sun.misc.BASE64Encoder().encode(str.getBytes());
	}

	/** 
	 * 二进制数据编码为BASE64字符串 
	 * 
	 * @param bytes 
	 * @return 
	 * @throws Exception 
	 */
	public static String encode(String str)
	{
		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		byte[] bytes = null;
		try
		{
			bytes = decoder.decodeBuffer(str);
			return new String(bytes);
		} catch (IOException e)
		{
			e.printStackTrace();
			return str;
		}
	}
}
