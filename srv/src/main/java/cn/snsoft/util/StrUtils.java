package cn.snsoft.util;

import java.nio.charset.Charset;
import java.util.Iterator;
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

	static public boolean isStrIn(final String strList, final String s)
	{
		return StrUtils.isStrIn(strList, s, ',');
	}

	/**
	 * 判断字段串 s是否在字段串strList中
	 * @param strList 可理解为源字符串
	 * @param s 想要在源字符串中查找的字符串
	 * @param delimiter 分隔符
	 * @return 如果存在返回true,如果不存在返回false
	 */
	public static boolean isStrIn(final String strList, final String s, final char delimiter)
	{
		return StrUtils.indexOf(strList, delimiter, s, false, false) >= 0;
	}

	/**
	 * 返回子串在源字符串中的索引，如果找到则返回非负的索引，否则返回-1
	 * @param str 源字符串
	 * @param delimiter 分隔符
	 * @param sub 子串
	 * @param trim 删除前后导空白字符
	 * @param ignoreCase 忽略大小写
	 * @return
	 */
	public static final int indexOf(String str, char delimiter, String sub, boolean trim, boolean ignoreCase)
	{
		if (str == null || sub == null)
			return -1;
		int p0 = 0;
		final int n = str.length();
		final int cmpLen = sub.length();
		int j = 0;
		for (int i = 0; i <= n; i++)
		{
			if (i == n || str.charAt(i) == delimiter)
			{
				if (trim)
				{
					if (ignoreCase ? str.substring(p0, i).trim().equalsIgnoreCase(sub) : str.substring(p0, i).trim().equals(sub))
						return j;
					// str.regionMatches(p0,);
				} else
				{
					if (cmpLen == i - p0 && str.regionMatches(ignoreCase, p0, sub, 0, cmpLen))
						return j;
				}
				p0 = i + 1;
				j++;
			}
		}
		return -1;
	}

	/**
	 * 将一个对象转换为一个长整型
	 * 示例：StrUtil.obj2long("1024",100); 结果：1024
	 * @param o 要进行转换的对象
	 * @param defaultValue 默认值，对象不符合转换条件时返回
	 * @return 返回的长整型
	 */
	static public final long obj2long(Object o, long defaultValue)
	{
		if (o instanceof Number)
			return ((Number) o).longValue();
		if (o instanceof String)
		{
			String s = ((String) o).trim();
			//[add] by wlp 2014-01-23
			if (s.endsWith("L") || s.endsWith("l"))
			{
				s = s.substring(0, s.length() - 1);
			}
			//[end add]
			try
			{
				return Long.parseLong(s);
			} catch (Exception ex)
			{
			}
			// if( s.indexOf('.')>=0 )
			{
				try
				{
					return Double.valueOf(s).longValue();
				} catch (Exception ex)
				{
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return 空返回true
	 */
	static public boolean isStrTrimNull(final String str)
	{
		return str == null || str.trim().length() == 0 || str.equalsIgnoreCase("null");
	}

	static public final String obj2str(final Object text, final String defaultvalue)
	{
		return text != null ? obj2str(text) : defaultvalue;
	}

	/**
	 * 把对象转换为字符串输出，该对象建议实现toString()方法
	 * 示例：StrUtil.obj2str("abc"); 结果：abc
	 * @param text 要转换的对象
	 * @return 转换为的字符串
	 */
	static public final String obj2str(Object text)
	{
		return text != null ? text.toString() : null;
	}

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return 空返回true
	 */
	static public boolean isNull(final String str)
	{
		return str == null || str.length() == 0 || "null".equalsIgnoreCase(str);
	}

	/**
	 * 判断一个字符串是否与一含通配符(*,?)的模式串通配(大小写敏感).
	 * 例如 like("123A45","1*A??") 为true, like("123A45","1*A???") 为 false
	 * @param text 被判断的字符串
	 * @param pattern 含通配符(*,?)的模式串
	 * @return 是否通配, 如通配,返回 true, 否则 false
	 */
	final public static boolean like(String text, String pattern)
	{
		return like(text, 0, pattern, 0, false);
	}

	/**
	 * 判断一个字符串(从某位置起)是否与一含通配符(*,?)的模式串(从某位置起)通配.
	 * 例如 like("123A45","1*A??",true) 为true, like("123A45","1*A???",true) 为 false
	 * StrUtil.like("01002","010%02%%") = true
	 * StrUtil.like("01002","010%01%%") = false
	 * StrUtil.like("123A45","1*A??")
	 * StrUtil.like("123A45","1*A???")
	 * @param text 被判断的字符串
	 * @param oText text的起始位置
	 * @param pattern 含通配符(*,?)的模式串
	 * @param oPattern pattern的起始位置
	 * @param ignoreCase 是否忽略字母的大小写
	 * @return 是否通配, 如通配,返回 true, 否则 false
	 */
	final public static boolean like(String text, int oText, String pattern, int oPattern, boolean ignoreCase)
	{
		if (text == null)
			return pattern == null; // 2002-11-08
		// Message.out.println(text.substring(oText)+" like "+pattern.substring(oPattern)+"  : ");
		/*
		 * if( snsoft.TestConfig.test )
		 * {
		 * System.err.println("text="+text.substring(oText)+",pattern="+pattern.substring(oPattern));
		 * }
		 */
		// System.err.println("text="+text+",pattern="+pattern);
		final int ltext = text.length() - oText;
		final int lpattern = pattern.length() - oPattern;
		// if( lpattern==0 )
		// return ltext==0;
		for (int i = 0; i < lpattern /* && i<ltext */; i++)
		{
			char c = pattern.charAt(oPattern + i);
			if (c == '*' || c == '%')
			{
				if (i == lpattern - 1 || endsWithStarsPattern(pattern, oPattern + i))
					return true;
				// i<lpattern-1
				for (int iText = oText + i; iText < ltext + oText; iText++)
					// for(int iText=oText;iText<ltext+oText;iText++)
					if (like(text, iText, pattern, oPattern + i + 1, ignoreCase))
						return true;
				return false;
			}
			if (i >= ltext)
				return false;
			if (c == '?' || c == '_')
			{
				continue;
			}
			if (ignoreCase)
			{
				if (Character.toUpperCase(c) != Character.toUpperCase(text.charAt(oText + i)))
					return false;
			} else
			{
				if (c != text.charAt(oText + i))
					return false;
			}
		}
		return ltext == lpattern || (lpattern >= ltext + 1 && endsWithStarsPattern(pattern, ltext));
		// || (lpattern==ltext+1&&(pattern.charAt(lpattern+oPattern-1)=='*'||pattern.charAt(lpattern+oPattern-1)=='%'));
	}

	final static private boolean endsWithStarsPattern(String text, int from)
	{
		if (from < 0)
			from = 0;
		for (; from < text.length(); from++)
		{
			char c = text.charAt(from);
			if (c != '*' && c != '%')
				return false;
		}
		return true;
	}

	/**
	 * 将一个对象数组用连接字符串拼接成一个字符串
	 * @author 杨斌
	 * 
	 * @param obj 对象数组
	 * @param separator 连接字符串
	 * @return 拼接好的字符串
	 */
	public static String join(final Object[] obj, final String separator)
	{
		return StrUtils.join(new ArrayIterator<Object>(obj), separator);
	}

	/**
	 * 将一个对象数组用连接字符串拼接成一个字符串
	 * @author 杨斌
	 * 
	 * @param obj 可迭代对象
	 * @param separator 连接字符串
	 * @return 拼接好的字符串
	 */
	public static String join(final Iterable obj, final String separator)
	{
		return null == obj ? null : StrUtils.join(obj.iterator(), separator);
	}

	/**
	 * 将一个迭代器用连接字符串拼接成一个字符串
	 * @author 杨斌
	 * 
	 * @param it 迭代器
	 * @param separator 连接字符串
	 * @return 拼接好的字符串
	 */
	public static String join(final Iterator it, final String separator)
	{
		if (null == it)
		{
			return null;
		}
		final StringBuffer sbr = new StringBuffer();
		if (it.hasNext())
		{
			if (StrUtils.isStrTrimNull(separator))
			{
				do
				{
					final Object o = it.next();
					if (null == o)
					{
						continue;
					}
					sbr.append(o);
				} while (it.hasNext());
			} else
			{
				do
				{
					final Object o = it.next();
					if (null == o)
					{
						continue;
					}
					sbr.append(o).append(separator);
				} while (it.hasNext());
				if (sbr.length() > 0)
				{
					sbr.delete(sbr.length() - separator.length(), sbr.length());
				}
			}
		}
		return sbr.toString();
	}

	/**
	 * 判断字符串是否不为空
	 * @param str
	 * @return 空返回true
	 */
	static public boolean isNotNull(final String str)
	{
		return !isNull(str);
	}

	/**
	 * 判断字符串是否不为为空
	 * @param str
	 * @return 不为空返回true
	 */
	static public boolean isNotStrTrimNull(final String str)
	{
		return !isStrTrimNull(str);
	}
	/**
	 * 根据编码格式串按字节截取字符串结果会根据字符串取得完整内容
	 * 例如（"你好abc世界"）按照"utf-8" 截取10字节,则结果:"你好abc";截取8字节,则结果:"你好ab"
	 * author: 张连宝
	 * @param str
	 * @param charsetname
	 * @param culength
	 * @return
	 */
	public static String getSubValue(String str, String charsetname, int culength)
	{
		return getSubValue(str, Charset.forName(charsetname), culength);
	}

	/**
	 * 根据编码格式按字节截取字符串
	 * author: 张连宝
	 * @param str
	 * @param encoding
	 * @param culength
	 * @return
	 */
	public static String getSubValue(String str, Charset charset, int culength)
	{
		// edit by drakice @ 2016-1-27: getBytes 必须指明编码方式，否则判断长队会存在问题。
		if (str == null || str.length() == 0 || str.getBytes(charset).length < culength)
		{
			return str;
		}
		if (!charset.canEncode())
		{
			throw new RuntimeException(String.format("no supported charset: %s.", charset));
		}
		culength = culength <= 0 ? 0 : culength;
		char[] cs = str.toCharArray();
		return new String(cs, 0, cutlength(cs, charset, culength));
	}

	private static int cutlength(char[] cs, Charset charset, int cutlength)
	{
		int count = 0;
		for (int i = 0; i < cs.length; i++)
		{
			count += String.valueOf(cs[i]).getBytes(charset).length;
			if (count >= cutlength)
			{
				return count > cutlength ? i : i + 1;
			}
		}
		return cs.length;
	}
}
