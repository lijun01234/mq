package cn.snsoft.file.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public class IOStreamUtils
{
	protected IOStreamUtils()
	{
	}

	final static public byte[] toByteArray(InputStream in) throws IOException
	{
		if (in == null)
			return null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		copy(in, out);
		return out.toByteArray();
	}

	final static public int copy(InputStream in, OutputStream out) throws IOException
	{
		int n = 0;
		//       ByteArrayOutputStream
		//final byte[] buffer = new byte[64*1024];
		final byte[] buffer = new byte[1024 * 1024];
		for (;;)
		{
			final int count = in.read(buffer);
			if (count < 0)
				break;
			out.write(buffer, 0, count);
			n += count;
		}
		return n;
	}
}
