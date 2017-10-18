package cn.snsoft.file;

final public class File
{
	public final String		path;	// separatorChar = '/'
	public final boolean	isdir;
	public final long		time;
	public final long		length;
	public final boolean	rdonly, hidden;

	public File(String path, boolean isdir, long time, long length)
	{
		this.path = path;
		this.isdir = isdir;
		this.time = time;
		this.length = length;
		rdonly = false;
		hidden = false;
	}

	public File(String path, boolean isdir, long time, long length, boolean rdonly, boolean hidden)
	{
		this.path = path;
		this.isdir = isdir;
		this.time = time;
		this.length = length;
		this.rdonly = rdonly;
		this.hidden = hidden;
	}

	final public String getPath()
	{
		return path;
	}

	final public String getName()
	{
		if (path == null)
			return null;
		int p = path.lastIndexOf('/');
		return p >= 0 ? path.substring(p + 1) : path;
	}

	static final public String getName(String path)
	{
		int p = path.lastIndexOf('/');
		return p >= 0 ? path.substring(p + 1) : path;
	}

	final public String getParentPath()
	{
		int p = path.lastIndexOf('/');
		return p >= 0 ? path.substring(0, p) : null;
	}

	@Override
	final public String toString()
	{
		return path;
	}

	final public boolean isDir()
	{
		return isdir;
	}

	final public long getTime()
	{
		return time;
	}

	final public long getLength()
	{
		return length;
	}

	final public boolean isReadonly()
	{
		return this.rdonly;
	}

	final public boolean isHidden()
	{
		return this.hidden;
	}

	final public boolean isFile()
	{
		return !this.isdir;
	}

	final public boolean isDirectory()
	{
		return this.isdir;
	}

	final public long length()
	{
		return length;
	}

	@Override
	public boolean equals(Object file)
	{
		return file instanceof File && path.equals(((File) file).path) && isdir == ((File) file).isdir;
	}
}
