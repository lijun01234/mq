package cn.snsoft.file;

import java.io.*;
public interface FileSystemI extends Closeable
{
	abstract public String toURL();
    abstract public boolean  deleteFile(String path,boolean deleteNonEmptyDir) throws java.io.IOException;
    abstract public boolean mkdir(String path) throws IOException;
    abstract public java.io.InputStream openFile(String fileName)  throws java.io.IOException;
    //abstract public java.io.InputStream openFile(snsoft.file.File file)  throws java.io.IOException;
    abstract public void setFileTime(String fileName,long time)  throws java.io.IOException;
    abstract public void  writeFile(String fileName,java.io.InputStream data,long time)throws java.io.IOException;
    abstract public java.io.OutputStream openWrite(String fileName,boolean gzip)throws java.io.IOException;

}
