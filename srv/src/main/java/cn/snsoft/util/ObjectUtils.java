package cn.snsoft.util;

public class ObjectUtils
{
	//	public static void set(Object obj, String namePrefix, java.util.Map<String,Object> values, boolean ignoreCase, boolean throwErrIfNoMethod)
	//	{
	//		if (values == null)
	//			return;
	//		final Class clazz = obj.getClass();
	//		final int lP = namePrefix == null ? 0 : namePrefix.length();
	//		for (final String name : values.keySet())
	//		{
	//			if (lP == 0 || name.startsWith(namePrefix))
	//				set(obj, clazz, name.substring(lP), values.get(name), ignoreCase, throwErrIfNoMethod);
	//		}
	//	}
	//
	//	private static void set(Object obj, Class clazz, String name, Object v, boolean ignoreCase, boolean throwErrIfNoMethod)
	//	{
	//		final java.lang.reflect.Method[] ms = clazz.getMethods();
	//		char c0 = name.charAt(0);
	//		if (c0 >= 'a' && c0 <= 'z')
	//			c0 = (char) (c0 - 'a' + 'A');
	//		final String methodName = "set" + c0 + name.substring(1);
	//		for (java.lang.reflect.Method m : ms)
	//		{
	//			if (!(ignoreCase ? m.getName().equalsIgnoreCase(methodName) : m.getName().equals(methodName)))
	//				continue;
	//			Class pramTypes[] = m.getParameterTypes();
	//			if (pramTypes.length != 1)
	//				continue;
	//			v = DataUtils.toCompatibleObject(v, pramTypes[0]);
	//			try
	//			{
	//				m.invoke(obj, new Object[] { v });
	//			} catch (Throwable ex)
	//			{
	//				throw new RuntimeException(ex);
	//			}
	//			return;
	//		}
	//		if (throwErrIfNoMethod)
	//			throw new java.lang.IllegalArgumentException("No method " + methodName);
	//	}
}
