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
		return null;//splitString(str, 0, delimiter);
	}
}
