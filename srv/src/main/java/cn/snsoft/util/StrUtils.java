package cn.snsoft.util;

public class StrUtils
{
	/**
	 * 对字符串进行二次拆分得到一个字符串二维数组
	 * 示例：
	 * String[][] ss = StrUtil.splitString("name=tom;age=18;score=100", ';', '=');
	 * for (String[] s1 : ss)
	 * {
	 * System.out.print(Arrays.toString(s1));
	 * }
	 * 结果：[name, tom][age, 18][score, 100]
	 * @param str 要进行拆分的字符串
	 * @param delimiter1 分隔符1
	 * @param delimiter2 分隔符2
	 * @return 拆分完成的字符串二维数组
	 */
	public static final String[][] splitString(String str, char delimiter1, char delimiter2)
	{
		String[] a1 = splitString(str, delimiter1);
		if (a1 == null)
			return null;
		String a2[][] = new String[a1.length][];
		for (int i = 0; i < a1.length; i++)
		{
			a2[i] = splitString(a1[i], delimiter2);
		}
		return a2;
	}

	/**
	 * 将一个字符串以某字符作为分隔符进行分隔(得到每段作为字符串的字符串数组)
	 * 分隔效果同："123.345.678".split("\\."); 结果：[123, 345, 678]
	 * 示例1:StrUtil.splitString("abc.bcd.cde", '.'); 结果：[abc, bcd, cde]
	 * @param str 被分隔的字符串
	 * @param delimiter 分隔符
	 * @return 分隔的结果
	 */
	public static final String[] splitString(String str, char delimiter)
	{
		if (delimiter == 0)
			return str == null ? null : new String[] { str };
		return splitString(str, 0, delimiter);
	}

	/**
	 * 将一个字符串从某位置开始以某字符作为分隔符进行分隔(得到每段作为字符串的字符串数组).
	 * 示例： String list[] = Utilities.splitString("AAAA,BBBB,CCCC,DDDDD",0,',')
	 * 结果： list 为 { "AAAA","BBBB","CCCC","DDDD" }
	 * @param str 被分隔的字符串
	 * @param istart 开始位置
	 * @param delimiter 分隔符
	 * @return 分隔的结果
	 */
	public static final String[] splitString(String str, int istart, char delimiter)
	{
		if (str == null)
			return null;
		final int sl = str.length();
		int n = 0;
		for (int i = istart; i < sl; i++)
			if (str.charAt(i) == delimiter)
				n++;
		if (n == 0)
			return new String[] { str };
		// if( n==0 )
		// return new String[]{str};
		String[] sa = new String[n + 1];
		int i = istart, j = 0;
		for (; i < sl;)
		{
			int iend = str.indexOf(delimiter, i);
			if (iend < 0)
				break;
			sa[j++] = str.substring(i, iend);
			// System.out.println(sa[j-1]);
			i = iend + 1;
		}
		sa[j++] = str.substring(i);
		// System.out.println(sa[j-1]);
		return sa;
	}

	static public final int obj2int(Object o, int defaultValue)
	{
		if (o instanceof Number)
			return ((Number) o).intValue();
		if (o instanceof String)
		{
			final String s = ((String) o).trim();
			try
			{
				return Integer.parseInt(s);
			} catch (Exception ex)
			{
			}
			{
				try
				{
					return Double.valueOf(s).intValue();
				} catch (Exception ex)
				{
				}
			}
		}
		return defaultValue;
	}
}
