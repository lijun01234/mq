package cn.snsoft.file.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import cn.snsoft.file.File;
import cn.snsoft.file.FtpClientI;
/**
 * 
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2017</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2017年10月18日 下午2:35:09</p>
 * <p>类全名：cn.snsoft.file.impl.JSchSFtpClient</p>
 * 
 * 作者：
 * @version 1.0
 */
public class JSchSFtpClient implements FtpClientI
{
	@Override
	public void close() throws Exception
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void changeDir(String remoteDirectory) throws IOException
	{
		// TODO Auto-generated method stub
	}

	@Override
	public String getWorkingDirectory() throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File[] listFiles(String dir, String charSet) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void makeDir(String dir) throws IOException
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean deleteFile(String name) throws IOException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteDir(String dir) throws IOException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InputStream openRead(String fileName) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutputStream openWrite(String fileName) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setFileTime(String name, long date) throws IOException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean renameTo(String from, String to, boolean makeDir) throws IOException
	{
		// TODO Auto-generated method stub
		return false;
	}
}
