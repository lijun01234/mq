package cn.snsoft.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * <p>标题：COMM 层日期操作工具 </p>
 * <p>功能： </p>
 * <p>所属模块： COMM</p>
 * <p>版权： Copyright © 2013 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2013年8月16日 下午3:19:54</p>
 * <p>类全名：cn.snsoft.comm.util.DateUtil</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
public class DateUtil
{
	/**
	 * y  年 
	 * M  年中的月份 
	 * m  小时中的分钟数
	 * d  月份中的天数   
	 * D  年中的天数  
	 * H  一天中的小时数（0-23 )
	 * h  am/pm 中的小时数（1-12） 
	 * s  分钟中的秒数
	 * S  毫秒数 
	 * z  时区
	 * Z  时区  
	 * w  年中的周数  
	 * W  月份中的周数 
	 * F  月份中的星期
	 * E  星期中的天数
	 * a  Am/pm 标记
	 * k  一天中的小时数（1-24 )
	 * K  am/pm 中的小时数（0-11）     
	 * @author zjg
	 */
	/**
	 * 24小时制格式化日期	      年-月-日 时:分:秒
	 */
	public static final String	DATE_FORMAT_01	= "yyyy-MM-dd HH:mm:ss";
	/**
	 * 年-月-日 
	 */
	public static final String	DATE_FORMAT_02	= "yyyy-MM-dd";
	/**
	 * 24小时制格式化日期	      年月日时分秒
	 */
	public static final String	DATE_FORMAT_03	= "YYYYMMDDHHMISS";
	/**
	 * 年月日 
	 */
	public static final String	DATE_FORMAT_04	= "YYYYMMDD";
	/**
	 * 年月日 时分秒
	 */
	public static final String	DATE_FORMAT_05	= "YYYYMMDDHHMMSS";
	/**
	 * 24小时制格式化日期	      年-月-日 时:分:秒
	 */
	public static final String	DATE_FORMAT_06	= "YYYY-MM-DD HH:MM:SS";
	/**
	 * 年月日 时分秒
	 */
	public static final String	DATE_FORMAT_07	= "yyyyMMddHHmmss";
	/**
	 * 24小时制格式化日期	      年-月-日 时:分
	 */
	public static final String	DATE_FORMAT_08	= "yyyy-MM-dd HH:mm";

	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return 返回单位是毫秒(ms)
	 */
	public static long getDiffDate(Date date1, Date date2)
	{
		return date1.getTime() - date2.getTime();
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 返回（endDate - startDate）时间相差的秒数(s)
	 * @author zjg
	 */
	public static long getDiffDate_sec(Date endDate, Date startDate)
	{
		return getDiffDate(endDate, startDate) / 1000;
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 返回（endDate - startDate）时间相差的分钟数(s)
	 * @author zjg
	 */
	public static long getDiffDate_min(Date endDate, Date startDate)
	{
		return getDiffDate_sec(endDate, startDate) / 60;
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return 返回（endDate - startDate）时间相差的小时数(s)
	 * @author zjg
	 */
	public static long getDiffDate_hour(Date endDate, Date startDate)
	{
		return getDiffDate_min(endDate, startDate) / 60;
	}

	/**
	 * 获得当前年的结束时间，即2013-12-31 23:59:59
	 * @return
	 * @author zhaojg
	 */
	public static Date getYearEndTime()
	{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date endTime = null;
		try
		{
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DATE, 31);
			endTime = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return endTime;
	}

	/**
	 * 获取指定年的最后一天
	 * @param year
	 * @return
	 * 杨伟光
	 */
	public static Date getYearEndTime(int year)
	{
		Calendar c = Calendar.getInstance();
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date endTime = null;
		try
		{
			c.set(Calendar.YEAR, year);
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DATE, 31);
			endTime = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return endTime;
	}

	/**
	 * 被检查日期为空默认已经失效
	 * 检查日期是否在有效期内,
	 * 生效日期为空,认为比检查的日期小,
	 * 失效日期为空,认为比检查的日期大
	 * @param checkDate 被检查的日期
	 * @param bedate 生效日期
	 * @param ledate 失效日期
	 * @return
	 */
	public static boolean isExpiryDate(Date checkDate, Date bedate, Date ledate)
	{
		if (checkDate == null)
		{
			return false;
		}
		boolean isExpiryDate = true;
		if (bedate != null && bedate.compareTo(checkDate) > 0)
		{
			isExpiryDate = false;
		}
		if (ledate != null && ledate.compareTo(checkDate) < 0)
		{
			isExpiryDate = false;
		}
		return isExpiryDate;
	}

	public final static String dateToString(Date date, String format)
	{
		if (date == null || format == null)
			return null;
		synchronized (staticCal)
		{
			Calendar cal = getStaticCalendars(date);
			int ymd[] = new int[6];
			ymd[0] = cal.get(Calendar.YEAR);//((java.sql.Timestamp)value).getYear() + 1900;
			ymd[1] = cal.get(Calendar.MONTH) + 1;//((java.sql.Timestamp)value).getMonth() + 1;
			ymd[2] = cal.get(Calendar.DAY_OF_MONTH);//((java.sql.Timestamp)value).getDate();
			ymd[3] = cal.get(Calendar.HOUR_OF_DAY);
			ymd[4] = cal.get(Calendar.MINUTE);
			ymd[5] = cal.get(Calendar.SECOND);
			format = format.toUpperCase();
			int n = format.length();
			StringBuilder sb = new StringBuilder();
			boolean hasHour = false;
			for (int i = 0; i < n;)
			{
				char c = format.charAt(i++);
				int nd = 1;
				for (; i < n && format.charAt(i) == c; i++)
					nd++;
				int j = "YMDHMS".indexOf(c);
				if (j >= 0)
				{
					if (j == 3)
						hasHour = true;
					else if (j == 1 && hasHour)
						j = 4;
					String s = Integer.toString(j == 0 && nd == 2 ? ymd[0] % 100 : ymd[j]);
					for (; nd > s.length(); nd--)
					{
						sb.append('0');
					}
					sb.append(s);
				} else
				{
					for (; nd > 0; nd--)
					{
						sb.append(c);
					}
				}
			}
			return sb.toString();
		}
	}

	final static protected Calendar	staticCal	= newGregorianCalendar();

	protected static Calendar newGregorianCalendar()
	{
		String ignoreDST = System.getProperty("Timezone.IgnoreDST");
		/*
		if( ignoreDST==null && Config.isOnServerSide() )
			ignoreDST = DataConfig.getConfig("Timezone.IgnoreDST"); // 2016-04-07 : 先去掉  Timezone.IgnoreDST 判断 
		*/
		if (ignoreDST != null && "false".equalsIgnoreCase(ignoreDST)) // 2015-04-03
			return Calendar.getInstance();
		/*
		 * TimeZone.getTimeZone("GMT+8:00")
		 */
		final int hOffset = TimeZone.getDefault().getRawOffset() / (60 * 60 * 1000);
		/*
		 * 对于北京 时区， hOffset = +8
		 *    Calendar.getInstance()
		 *  改为
		 *    Calendar.getInstance(TimeZone.getTimeZone("GMT"+(hOffset>0?"+"+hOffset:""+hOffset)+":00"));
		 *      为了避免  1988-1991 使用夏令时的问题   Daylight Saving Time：DST
		 *   因为  
		 *     toLongTime(year,month,day,....)
		 *     
		 */
		System.out.println("时区偏移 = " + hOffset);
		return Calendar.getInstance(TimeZone.getTimeZone("GMT" + (hOffset > 0 ? "+" + hOffset : "" + hOffset) + ":00"));
		//        return new GregorianCalendar(TimeZone.getTimeZone("GMT"));
	}

	/**
	 *  从一个<code>java.util.Date</code>对象得到一个表示该日期的临时<code>Calendar</code>对象.
	 *  该对象只作为临时使用。
	 *  @param  date <code>java.util.Date</code>对象
	 *  @return 表示 date 的 <code>Calendar</code>对象.
	 */
	protected static Calendar getStaticCalendars(java.util.Date date)
	{
		// if(staticCal==null) staticCal = newGregorianCalendar();//TimeZone.getDefault());
		if (date != null)
			staticCal.setTime(date);
		else
			staticCal.setTimeInMillis(System.currentTimeMillis()); // 2011-3-26 : 加
		return staticCal;
		//utcCal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		//defaultCenturyStart = staticCal.get(Calendar.YEAR) - 80;
	}

	/**
	 * 服务端无环境变量情况下获取服务器时间
	 * @param env
	 * @return
	 */
	public static Date getServerDate()
	{
		return getServerDate(false);
	}

	public static Date getServerDate(final boolean filterTime)
	{
		final long lTime = DateUtil.getServerTime(null);
		if (filterTime)
		{
			return new Date(lTime - (lTime + TimeZone.getDefault().getRawOffset()) % 86400000);
		} else
		{
			return new Date(lTime);
		}
	}

	@Deprecated
	public static long getServerTime(java.util.Map env)
	{
		return getServerTime();
	}

	final public static long getServerTime()
	{
		//System.err.println("getServerTime.deltaFromServer="+deltaFromServer+",ServerURL="+env.getValue("ServerURL"));
		//	       java.util.Date date = new java.util.Date();
		//if (BasConfig.isOnside(3))
		return System.currentTimeMillis();
		//final UserSession userSession = AppContext.getUserSession(false);
		//   final String serverURL;
		//if (userSession == null || (userSession.getServerURL()) == null)
		//return System.currentTimeMillis();
		//if (deltaFromServer == Long.MIN_VALUE)
		//{
		//final Long serverTime = (Long) RemoteInvoke.rmInvoke("snsoft.commons.util.DateUtils.todayTime");
		//deltaFromServer = serverTime.longValue() - System.currentTimeMillis();
		//}
		//System.err.println("deltaFromServer="+deltaFromServer);
		//return System.currentTimeMillis() + deltaFromServer;
	}

	public final static Date parseDate(String text, String format)
	{
		SimpleDateFormat f = new SimpleDateFormat(format);
		try
		{
			return f.parse(text);
		} catch (ParseException e)
		{
			e.printStackTrace();
			throw new RuntimeException("日期格式化异常！" + text + "---" + format);
		}
	}
}
