package cn.snsoft.util;

public class ArrayUtils
{
	final public static <T> T[] addElement(T oldArray[], T value, boolean nonDupable)
	{
		return (T[]) addArrayElement((Class<T>) null, oldArray, value, nonDupable);
	}

	static protected final Object[] addArrayElement(Class elementClass, Object oldArray[], Object value, boolean nonDupable)
	{
		int n = oldArray == null ? 0 : java.lang.reflect.Array.getLength(oldArray);
		if (nonDupable)
			for (int i = 0; i < n; i++)
			{
				Object x = oldArray[i];//java.lang.reflect.Array.get(oldArray,i);
				if (x == value || (x != null && x.equals(value)))
					return oldArray;
			}
		if (elementClass == null && value != null)
			elementClass = value.getClass();
		Object newArray[] = (Object[]) java.lang.reflect.Array.newInstance(elementClass, n + 1);//new ?[n+1];
		if (n > 0)
			System.arraycopy(oldArray, 0, newArray, 0, n);
		newArray[n] = value;//java.lang.reflect.Array.set(newArray,n,value);
		return newArray;
	}
}
