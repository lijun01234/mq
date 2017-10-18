package cn.snsoft.file.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import cn.snsoft.file.FileSystemI;

public abstract class AbstractFileSystem implements FileSystemI
{
	@Override
	public void close() throws IOException
	{
		// TODO Auto-generated method stub
	}

	@Override
	public String toURL()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteFile(String path, boolean deleteNonEmptyDir) throws IOException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mkdir(String path) throws IOException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InputStream openFile(String fileName) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFileTime(String fileName, long time) throws IOException
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void writeFile(String fileName, InputStream data, long time) throws IOException
	{
		// TODO Auto-generated method stub
	}

	@Override
	public OutputStream openWrite(String fileName, boolean gzip) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
