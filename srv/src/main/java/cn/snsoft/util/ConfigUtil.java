package cn.snsoft.util;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import cn.snsoft.db.WorkSpace;
public class ConfigUtil
{
	public static void main(String[] args)
	{
		getProperties();
	}

	public static String getElementValue(String attrName)
	{
		Map map = getProperties();
		if (map != null)
		{
			return (String) map.get(attrName);
		}
		return null;
	}

	public static Map getProperties()
	{
		try
		{
			InputStream inputStream = WorkSpace.class.getResourceAsStream("../../../config/Config.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
