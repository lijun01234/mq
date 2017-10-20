package cn.snsoft.base.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import cn.snsoft.util.ConfigUtil;
import cn.snsoft.util.StrUtils;
import com.caucho.burlap.io.BurlapInput;
import com.caucho.burlap.io.BurlapOutput;
/**
 * <p>标题： Burlap序列化</p>
 * <p>功能： </p>
 * <p>所属模块： 二级缓存(Level2Cache)</p>
 * <p>版权： Copyright (c) 2012</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2012-12-5 上午9:46:35</p>
 * 作者：王立鹏
 * 初审：
 * 复审：
 * @version 8.0
 * 		一种基于可识别字符编码的序列化,适用于对缓存Key序列化
 */
public class BurlapSerializer<T> implements RedisSerializer<T>
{
	@Override
	public byte[] serialize(T t) throws SerializationException
	{
		if (t == null)
		{
			return new byte[0];
		}
		int iMajorization = StrUtils.obj2int(ConfigUtil.getElementValue("Component.RedisKeyMajorization"), 0);//DataConfig.getConfig("Component.RedisKeyMajorization"), 0);
		if (iMajorization > 0 && t instanceof String)
		{
			byte[] bytes = ((String) t).getBytes();
			if (iMajorization == 1)
			{//使用字符串的字节码
				return bytes;
			} else
			{//完全转化为SHA1
				try
				{
					MessageDigest md = MessageDigest.getInstance("SHA1");
					md.reset();
					md.update(bytes);
					byte[] digest = md.digest();
					//Base64Encode.encode(digest);
					String base64 = Base64Encode.encode(digest);
					return base64.getBytes();
				} catch (NoSuchAlgorithmException | IOException e)
				{
					throw new RuntimeException(e);
				}
			}
		}
		BurlapOutput oos = null;
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream())
		{
			oos = new BurlapOutput(bos);
			oos.writeObject(t);
			oos.flush();
			final byte[] b = bos.toByteArray();
			return b;
		} catch (final IOException e)
		{
			throw new SerializationException(e.getMessage());
		} finally
		{
			if (oos != null)
			{
				try
				{
					oos.close();
				} catch (final IOException e)
				{
					throw new SerializationException(e.getMessage());
				}
			}
		}
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException
	{
		if (bytes == null || bytes.length == 0)
		{
			return null;
		}
		int iMajorization = StrUtils.obj2int(ConfigUtil.getElementValue("Component.RedisKeyMajorization"), 0);//DataConfig.getConfig("Component.RedisKeyMajorization"), 0);
		if (iMajorization > 0)
		{
			if (iMajorization == 1)
			{
				T t = (T) new String(bytes);
				return t;
			} else
			{
				throw new RuntimeException("更高级别的优化配置,即[Component.RedisKeyMajorization]的值>1,不再支持获取key!");
			}
		}
		BurlapInput ois = null;
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes))
		{
			ois = new BurlapInput(bis);
			T t = (T) ois.readObject();
			return t;
		} catch (IOException e)
		{
			throw new SerializationException(e.getMessage());
		} finally
		{
			if (ois != null)
			{
				try
				{
					ois.close();
				} catch (IOException e)
				{
					throw new SerializationException(e.getMessage());
				}
			}
		}
	}
}
