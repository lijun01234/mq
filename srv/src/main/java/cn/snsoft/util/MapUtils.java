package cn.snsoft.util;

import java.util.HashMap;
public class MapUtils
{
	/**
	 * 将参数字符串解析并将解析结果装载到一个Map中；
	 * 算法：如果参数不为空，则将参数以’&’切割为数组并遍历，如果切割后的小字符串可以满足key=value的形式，并将key和value作为键值对装入to中，将to返回；
	 * @param paramText参数文本
	 * @param to解析后参数装载到的Map
	 * @return 装载后的to
	 */
	final static public java.util.Map<String,String> parseParameterTo(String paramText, java.util.Map<String,String> to)
	{
		if (paramText == null)
			return to;
		String a[] = StrUtils.splitString(paramText, '&');
		for (int i = 0; i < a.length; i++)
		{
			final String si = a[i].trim();
			int p = si.indexOf('=');
			if (p < 0)
				continue;
			if (to == null)
				to = new HashMap<String,String>();
			to.put(si.substring(0, p).trim(), si.substring(p + 1));
		}
		return to;
	}
}
