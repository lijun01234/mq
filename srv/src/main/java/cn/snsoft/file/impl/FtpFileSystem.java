package cn.snsoft.file.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import cn.snsoft.file.FtpClientI;
import cn.snsoft.util.ConfigUtil;
import cn.snsoft.util.MapUtils;
public class FtpFileSystem extends AbstractFileSystem
{
	protected final String	host;
	protected final int		port;
	protected final String	user, password;
	protected final String	rootPath;
	protected FtpClientI	ftpClient;
	protected String		charSet;

	public FtpFileSystem(String host, int port, String user, String password, String rootPath, String ftpType) throws IOException
	{
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.rootPath = rootPath;
		ftpClient = getFtpClient(host, port, user, password, ftpType);
	}

	public static FtpClientI getFtpClient(String host, int port, String user, String password) throws IOException
	{
		return getFtpClient(host, port, user, password, "FTP");
	}

	public static FtpClientI getFtpClient(String host, int port, String user, String password, String protocol) throws IOException
	{
		assert "FTP".equalsIgnoreCase(protocol) || "SFTP".equalsIgnoreCase(protocol);
		try
		{
			String ftpClientClass = "SFTP".equals(protocol) ? "cn.snsoft.file.impl.JSchSFtpClient" : "cn.snsoft.file.impl.ApacheFtpClient";
			String str = ConfigUtil.getElementValue("FTP.ATTRIBUTES");
			final Map xparams = MapUtils.parseParameterTo(str, null);
			return (FtpClientI) Class.forName(ftpClientClass).getConstructor(String.class, Integer.TYPE, String.class, String.class, java.util.Map.class)
					.newInstance(host, port, user, password, xparams);
		} catch (Throwable ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String toURL()
	{
		return null;
	}

	@Override
	public boolean deleteFile(String path, boolean deleteNonEmptyDir) throws IOException
	{
		return false;
	}

	@Override
	public boolean mkdir(String path) throws IOException
	{
		return false;
	}

	@Override
	public InputStream openFile(String fileName) throws IOException
	{
		return null;
	}

	@Override
	public void setFileTime(String fileName, long time) throws IOException
	{
	}

	@Override
	public void writeFile(String fileName, InputStream data, long time) throws IOException
	{
	}

	@Override
	public OutputStream openWrite(String fileName, boolean gzip) throws IOException
	{
		return null;
	}

	@Override
	public void close() throws IOException
	{
		try
		{
			if (ftpClient != null)
				ftpClient.close();
			ftpClient = null;
		} catch (Throwable ex)
		{
			ex.printStackTrace();
		}
	}
}
