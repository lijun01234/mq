package cn.snsoft.file.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import cn.snsoft.file.File;
import cn.snsoft.file.FtpClientI;
import cn.snsoft.util.ObjectUtils;
import cn.snsoft.util.StrUtils;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2017</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2017年10月18日 下午2:35:44</p>
 * <p>类全名：cn.snsoft.file.impl.ApacheFtpClient</p>
 * 作者：
 * @version 1.0
 */
public class ApacheFtpClient extends FTPClient implements FtpClientI
{
	private String	encoding	= "GB18030";

	public ApacheFtpClient(String host, int port, String user, String password, java.util.Map<String,Object> xparams) throws IOException
	{
		super.setConnectTimeout(60000);
		String defEncoding = xparams == null ? null : (String) xparams.get("Encoding");
		if (defEncoding != null)
			encoding = defEncoding;
		super.setControlEncoding(encoding);
		if (xparams != null)
		{
			//TODO ObjectUtils.set(this, "Ftp.", xparams, true, true);
		}
		super.connect(host, port);
		super.login(user, password);
		ApacheFtpClient.this.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
		final int dataConnectionMode = xparams == null ? 0 : StrUtils.obj2int(xparams.get("DataConnectionMode"), 0);
		if (dataConnectionMode == 2)
			this.enterLocalPassiveMode();
		//add by wlp 2016年12月10日 Ftp主动模式的时候,客户端的数据端口范围:
		final int minPort = xparams == null ? 0 : StrUtils.obj2int(xparams.get("MinPort"), 0);
		final int maxPort = xparams == null ? 0 : StrUtils.obj2int(xparams.get("MaxPort"), 0);
		if (minPort > 0 && maxPort >= minPort)
		{
			this.setActivePortRange(minPort, minPort);
		}
	}

	@Override
	public void close() throws Exception
	{
		try
		{
			super.disconnect();
		} catch (Throwable ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void changeDir(String remoteDirectory) throws IOException
	{
	}

	@Override
	public String getWorkingDirectory() throws IOException
	{
		return null;
	}

	@Override
	public File[] listFiles(String dir, String charSet) throws IOException
	{
		if (charSet != null) // 2016-08-02 
			setControlEncoding(charSet);
		for (; dir != null && dir.endsWith("/.");)
		{
			dir = dir.substring(0, dir.length() - 2);
		}
		this.changeDir(dir);//=FileSystem.pathNormalize(dir));
		final String workDir;
		if (!dir.equals(workDir = this.getWorkingDirectory()))
		{
			throw new java.io.IOException("Can not change dir " + dir + "(workDir=" + workDir + ")");
		}
		/*
		if( snsoft.TestConfig.test )
		{
			// ACTIVE_LOCAL_DATA_CONNECTION_MODE = 0
			enterLocalPassiveMode();
			System.out.println( "__dataConnectionMode="+this.getDataConnectionMode());
		} */
		org.apache.commons.net.ftp.FTPFile ftpFiles[] = super.listFiles();
		if (ftpFiles == null)
			return null;
		//snsoft.file.File[] files = new snsoft.file.File[ftpFiles.length];
		java.util.List<File> v = new java.util.ArrayList();
		for (int i = 0; i < ftpFiles.length; i++)
		{
			org.apache.commons.net.ftp.FTPFile ftpFile = ftpFiles[i];
			//ftpFile.isDirectory() 
			java.util.Calendar ftpDate = ftpFile.getTimestamp();
			String name = ftpFile.getName();
			if (".".equals(name) || "..".equals(name))
				continue;
			v.add(new File(name, ftpFile.isDirectory(), ftpDate == null ? 0 : ftpDate.getTimeInMillis(), ftpFile.getSize()));
			//snsoft.file.File file
		}
		return v.toArray(new File[v.size()]);
	}

	@Override
	public void makeDir(String dir) throws IOException
	{
		// TODO Auto-generated method stub
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
