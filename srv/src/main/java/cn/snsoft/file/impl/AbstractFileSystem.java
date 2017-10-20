package cn.snsoft.file.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import cn.snsoft.file.FileSystemI;
import cn.snsoft.util.StrUtils;
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

	static public String pathNormalize(String path)
	{
		if (path == null || path.length() == 0)
			return path;
		path = path.replace('\\', '/');
		String pathA[] = StrUtils.splitString(path, '/');
		java.util.List<String> list = new java.util.ArrayList();
		for (int i = 0; i < pathA.length; i++)
		{
			if (pathA[i].length() == 0 || pathA[i].equals("."))
				continue;
			if (pathA[i].equals(".."))
			{
				if (list.size() == 0)
					throw new java.lang.IllegalArgumentException("无效文件路径:" + path);
				list.remove(list.size() - 1);
			} else
			{
				list.add(pathA[i]);
			}
		}
		if (list.size() == 0)
			return null;
		return StrUtils.join(list.toArray(), "/");
	}

	public static FileSystemI getFileSystem(String url) throws java.io.IOException
	{
		if (url == null)
		{
			return null;
			//new LocalFileSystem("file:///"+(new java.io.File(snsoft.Config.ConfigPath)).getCanonicalPath());
		}
		//if (url.startsWith("file:"))
			//return new LocalFileSystem(url);
		//else if (url.startsWith("ftp:") || url.startsWith("sftp:"))
			return null;//new FtpFileSystem(url);
		//else if (url.startsWith("dx:"))
		//	return new snsoft.file.dx.DxFileSystem(url);
		//else if (url.startsWith("dxr:"))
		//	return new snsoft.file.dx.DxrFileSystem(url);
		//throw new IllegalArgumentException(url);
	}
}
