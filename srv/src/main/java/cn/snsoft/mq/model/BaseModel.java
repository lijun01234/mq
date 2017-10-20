package cn.snsoft.mq.model;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import cn.snsoft.util.StrUtils;
/**
 * <p>标题： 所有Model的基类</p>
 * <p>功能：</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-10-29 下午8:23:06</p>
 * <p>类全名：cn.snsoft.comm.model.BaseModel</p>
 * 作者：王立鹏
 * @version 8.0
 *          所有类上面需要写标注@Table(tableName="数据库表名"),如果需要缓存,还应该写cache="缓存Key2"
 *          所有对应数据库表字段的属性,必须有public的set方法,且方法需要加标注@Column,如果方法名截去set以外的部分与数据库字段名不一致,应写!Column("数据库字段名")
 *          注意:所有Model,应该重写hashCode()和equals()方法
 *          	修改类中的注解，重启后生效！
 */
public abstract class BaseModel implements Serializable, JsonStreamable
{
	/**
	 * <p>标题： </p> 
	 * <p>功能：用来定义表名和缓存 </p>
	 * <p>版权： Copyright (c) 2012</p>
	 * <p>公司: 北京南北天地科技股份有限公司</p>
	 * <p>创建日期：2012-10-31 下午11:30:59</p>
	 * <p>类全名：cn.snsoft.comm.model.Table</p>
	 * 作者：王立鹏
	 * @version 8.0
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE })
	public static @interface Table
	{
		String tableName() default "";

		String filter() default "";//基本不再使用

		String cacheColumns() default "";//add by 郑明 2013-09-09 缓存关联字段

		String order() default "";//基本不再使用

		String cache() default "";

		//String cacheType() default "";//基本不再使用
		String primaryKeys() default "";//add by 宋建中 2013-11-25  用于标识物理表主键
	}

	/**
	 * <p>标题： </p>
	 * <p>功能： 用来定义字段名</p>
	 * <p>版权： Copyright (c) 2012</p>
	 * <p>公司: 北京南北天地科技股份有限公司</p>
	 * <p>创建日期：2012-10-31 下午11:32:17</p>
	 * <p>类全名：cn.snsoft.comm.model.Column</p>
	 * 作者：王立鹏
	 * @version 8.0
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD })
	public @interface Column
	{
		String value() default "";
	}

	@Override
	public Object toJson()
	{
		return null;
		//	//return DomainUtil.domainToJSONObject(this);
	}

	/**
	 * 设置参数的值
	 * @param model
	 * @param fieldName
	 * @param value
	 */
	public void setModelParams(String fieldName, Object value)
	{
		if (StrUtils.isNull(fieldName))
		{
			throw new java.lang.RuntimeException("参数字段名为空！");
		}
		Method m = null;
		String methodName = fieldName.toLowerCase();
		Class<? extends BaseModel> c = this.getClass();
		try
		{
			// Edit by 杨斌 
			// 原BUG：字段名'innercode'，那么会按照'setInnercode'名称查找方法，
			//        而实际方法名是'setInnerCode'，导致无法找到正确的方法。
			// 先在使用 Column 注解的方法中搜索，
			Method[] methods = c.getMethods();
			for (Method method : methods)
			{
				final String mname = method.getName();
				// 只查找set方法
				if (!mname.startsWith("set"))
				{
					continue;
				}
				Column column = method.getAnnotation(Column.class);
				if (null == column)
				{
					continue;
				}
				String columnName = column.value();
				if ("".equals(columnName))
				{
					columnName = mname.substring(3);
				}
				// 列名忽略要大小写
				if (fieldName.equalsIgnoreCase(columnName))
				{
					// 找到方法则返回方法
					m = method;
					break;
				}
			}
			if (null == m)
			{
				methodName = "set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
				m = c.getMethod(methodName, value.getClass());
			}
			m.invoke(this, value);
		} catch (final Exception e)
		{
			throw new java.lang.RuntimeException(c.getName() + " 未找到方法:" + methodName + "！");
		}
	}

	/**
	 * 根据参数字段名获取参数值
	 * @param paramfield
	 * @return
	 */
	public Object getModelParams(String paramfield)
	{
		if (StrUtils.isNull(paramfield))
		{
			throw new java.lang.RuntimeException("参数字段名为空！");
		}
		Method m = null;
		String methodName = paramfield.toLowerCase();
		Class<? extends BaseModel> c = this.getClass();
		try
		{
			// Edit by 杨斌 
			// 原BUG：字段名'innercode'，那么会按照'setInnercode'名称查找方法，
			//        而实际方法名是'setInnerCode'，导致无法找到正确的方法。
			// 先在使用 Column 注解的方法中搜索，
			Method[] methods = c.getMethods();
			for (Method method : methods)
			{
				// 只查找set方法
				if (!method.getName().startsWith("get"))
				{
					continue;
				}
				Column column = method.getAnnotation(Column.class);
				if (null == column)
				{
					continue;
				}
				// 列名忽略要大小写
				if (paramfield.equalsIgnoreCase(column.value()))
				{
					// 找到方法则返回方法
					m = method;
					break;
				}
			}
			if (null == m)
			{
				methodName = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
				m = c.getMethod(methodName);
			}
			Object value = m.invoke(this);
			return value;
		} catch (final Exception e)
		{
			throw new java.lang.RuntimeException(c.getName() + " 未找到方法:" + methodName + "！");
		}
	}
}
