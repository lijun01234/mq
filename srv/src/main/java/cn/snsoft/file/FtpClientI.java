package cn.snsoft.file;

import java.io.IOException;
public interface FtpClientI extends java.lang.AutoCloseable
{
	public void changeDir(String remoteDirectory) throws IOException;

	public String getWorkingDirectory() throws java.io.IOException;

	public File[] listFiles(String dir, String charSet) throws IOException;

	public void makeDir(String dir) throws IOException;

	public boolean deleteFile(String name) throws IOException;

	public boolean deleteDir(String dir) throws IOException;

	public java.io.InputStream openRead(String fileName) throws IOException;

	public java.io.OutputStream openWrite(String fileName) throws IOException;

	public boolean setFileTime(String name, long date) throws IOException;

	public boolean renameTo(String from, String to, boolean makeDir) throws IOException;
}
