package cn.snsoft.mq.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import cn.snsoft.base.data.Comp3Interface;
import cn.snsoft.mq.constant.MQConstant;
import cn.snsoft.mq.util.MQUtil;
import cn.snsoft.util.DateUtil;
import cn.snsoft.util.StrUtils;
/**
 * <p>标题： 系统间交互定义</p>
 * <p>功能： </p>
 * <p>所属模块：  MQ</p>
 * <p>版权： Copyright © 2014 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2014年3月20日 下午1:43:14</p>
 * <p>类全名：cn.snsoft.component.mq.model.MQSysConn</p>
 * 作者：李楠
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
@BaseModel.Table(tableName = "mq_sys_conn", cache = "SQL-MQSysConn.[mq_sys_conn]|MQSysConn")
public class MQSysConn extends BaseModel implements Comp3Interface
{
	private String						id;									//ID
	private String						syscode;								//当前系统 
	private String						syscode_mutual;						//交互系统
	private String						conntype;								//交互方式
	private String						projtype;								//工程类型编号 
	private String						inouttype;								//请求/响应 
	private String						fsmacro;								//文件服务器地址宏
	private String						bfsmacro;								//备份服务器地址宏 	add by wuxiaohai 2014年9月15日
	private int							startflags;							//启用标识
	private int							scanflags;								//需扫描
	private int							flags;									// 标识
	private String						projcode;								//工程模版号
	private String						ord;									//序号
	/** 请求时作为生成文件的前缀,响应扫描时作为过滤的文件前缀,此时可用正则匹配或者逗号分隔的枚举*/
	private String						fileprefix;							//文件名称前缀
	private String						custfilepath;							//自定义文件夹
	private String						busitype;								//业务类别
	private String						dsid;									//生成工程的数据库地址
	private String						filecheckmode;							//文件校验模式
	private int							sortmode;								//报文排序方式  0 时间升序   1 时间降序 
	private String						mq_dataSource;
	private final Map<String,Object>	datas	= new HashMap<String,Object>();
	private boolean						isMkdir	= false;
	private int							threadcount;							//报文扫描的最大分线程数  
	private int							threadfilecount;						//单线程报文数
	private String						dirpath;								//文件夹路径定义
	private String						xsdpath;								//XSD 验证文件地址

	/**
	 * 获取备份服务器地址
	 * add by wuxiaohai 2014年9月15日
	 * @return
	 */
	public String getServerBackPath(Map<String,Object> envParams)
	{
		String cfgBackPath = "";
		String fsmacro = getFsmacro();
		String bfsmacro = getBfsmacro();
		//如果备份服务器地址不为空
		if (StrUtils.isNotStrTrimNull(bfsmacro))
		{
			//获取备份地址宏配置
			cfgBackPath = MQUtil.getMQFtpPath(envParams, bfsmacro);
			if (StrUtils.isStrTrimNull(cfgBackPath))
			{
				throw new RuntimeException("备份服务器路径宏【" + bfsmacro + "】未配置具体的FTP地址!");
			}
		} else
		{
			//文件服务器配置
			cfgBackPath = MQUtil.getMQFtpPath(envParams, fsmacro);
			if (StrUtils.isStrTrimNull(cfgBackPath))
			{
				throw new RuntimeException("服务器路径宏【" + fsmacro + "】未配置具体的FTP地址!");
			}
		}
		return cfgBackPath;
	}

	/**
	 * 获取备份文件全路径
	 * @return
	 * @author zjg
	 */
	public String getRealBackFullPath(Map<String,Object> envParams, Date date)
	{
		return getServerBackPath(envParams) + getBackFilePath(date);
	}

	/**
	 * 备份文件的文件路径
	 * getBackFilePath
	 * @author shuquan
	 * @return
	 */
	public String getBackFilePath(Date date)
	{
		StringBuffer buffer = new StringBuffer("snsoft-mq-backups");
		//15 11 12 宋建中修改为 日期 + 原路径  备份方式
		buffer.append("/").append(DateUtil.dateToString(date, "yyyy-MM-dd"));
		String filePath = getFilePath();
		if (StrUtils.isNotNull(filePath))
		{
			buffer.append("/").append(filePath);
		}
		String backFilePath = buffer.toString();
		if (!backFilePath.endsWith("/"))
		{
			backFilePath += '/';
		}
		return backFilePath;
	}

	public String getValidateFilePath(Date date)
	{
		StringBuffer buffer = new StringBuffer("snsoft-mq-validate");
		buffer.append("/").append(DateUtil.dateToString(date, "yyyy-MM-dd"));
		String filePath = getFilePath();
		if (StrUtils.isNotStrTrimNull(filePath))
		{
			buffer.append("/").append(filePath);
		}
		String validateFilePath = buffer.toString();
		if (!validateFilePath.endsWith("/"))
		{
			validateFilePath += '/';
		}
		return validateFilePath;
	}

	/**
	 * 获得报文文件路径 格式为{服务器路径宏1}/工程类型编号/(in/out);
	 * @return
	 */
	public String getPath()
	{
		String path = "{" + MQUtil.getWholeFsmacro(fsmacro) + "}/" + getFilePath();
		return path;
	}

	/**
	 * 根据交互类型判断为in还是out
	 * @return
	 */
	public String getInOrOut()
	{
		return isRequest() ? "in" : "out";
	}

	/**
	 * 是否为请求方
	 * @return
	 */
	private boolean isRequest()
	{
		return MQConstant.SYSCONN_REQUEST.equals(this.inouttype);
	}

	/**
	 * 获取请求方编码
	 * @return
	 */
	public String getRequest()
	{
		return syscode;
	}

	/**
	 * 获取响应方编码
	 * @return
	 */
	public String getResponse()
	{
		return syscode_mutual;
	}

	/**
	 * 获得报文真实文件路径 
	 * @return
	 */
	public String getRealPath(Map<String,Object> envParams)
	{
		String realPath = getRealRootPath(envParams) + getFilePath();
		return realPath;
	}

	public String getRealRootPath(Map<String,Object> envParams)
	{
		return getRealRootPath(envParams, true);
	}

	public String getRealRootPath(Map<String,Object> envParams, boolean isThrow)
	{
		String url = MQUtil.getMQFtpPath(envParams, getFsmacro());
		if (StrUtils.isStrTrimNull(url))
		{
			if (isThrow)
			{
				throw new RuntimeException("服务器路径宏【" + getFsmacro() + "】未配置具体的FTP地址!");
			} else
			{
				return null;
			}
		}
		if (!url.endsWith("/"))
		{
			url += '/';
		}
		return url;
	}

	/**
	 * 宏下的文件路径
	 * @return
	 */
	public String getFilePath()
	{
		String dirpath = getDirpath();
		if (dirpath != null && !dirpath.endsWith("/"))
		{
			dirpath += '/';
		}
		if (isNotCreateDir())
		{
			return dirpath == null ? "" : dirpath;
		} else if (StrUtils.isNotNull(getCustfilepath()))
		{
			return (StrUtils.isNotNull(dirpath)) ? dirpath + getCustfilepath() : getCustfilepath();
		}
		return (StrUtils.isNotNull(dirpath)) ? dirpath + getProjtype() + '/' + getInOrOut() : getProjtype() + '/' + getInOrOut();
	}

	public String getDirpath()
	{
		return dirpath;
	}

	@Column
	public void setDirpath(String dirpath)
	{
		this.dirpath = dirpath;
	}

	public String getXsdpath()
	{
		return xsdpath;
	}

	@Column
	public void setXsdpath(String xsdpath)
	{
		this.xsdpath = xsdpath;
	}

	/**
	 * 是否需要创建文件夹
	 * @return
	 */
	public boolean isNotCreateDir()
	{
		return (flags & 1) == 1;
	}

	/**
	 * 是否为密文
	 */
	public boolean isSecre()
	{
		return (flags & 2) == 2;
	}

	/**
	 * 是否为压缩报文
	 * @return
	 */
	public boolean isZip()
	{
		return (flags & 4) == 4;
	}

	/**
	 * 是否需要备份报文，现在只用于消息创建报文
	 * @return
	 */
	public boolean isBackups()
	{
		return (flags & 8) == 8;
	}

	/**
	 * 是否为永久降级
	 * @return
	 */
	public boolean isPerpetualDegrade()
	{
		return (flags & 16) == 16;
	}

	/**
	 * 是否先创建临时文件再改名称的方式处理报文
	 * @return
	 */
	public boolean isCreateTempFile()
	{
		return (flags & 32) == 32;
	}

	/**
	 * WS 工程是否异步
	 * @return
	 * PengXS
	 */
	public boolean isWSAsync()
	{
		return (flags & 64) == 64;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((syscode == null) ? 0 : syscode.hashCode());
		result = prime * result + ((syscode_mutual == null) ? 0 : syscode_mutual.hashCode());
		result = prime * result + ((projtype == null) ? 0 : projtype.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		MQSysConn other = (MQSysConn) obj;
		if (syscode == null)
		{
			if (other.syscode != null)
			{
				return false;
			}
		} else if (!syscode.equals(other.syscode))
		{
			return false;
		}
		if (syscode_mutual == null)
		{
			if (other.syscode_mutual != null)
			{
				return false;
			}
		} else if (!syscode_mutual.equals(other.syscode_mutual))
		{
			return false;
		}
		if (projtype == null)
		{
			if (other.projtype != null)
			{
				return false;
			}
		} else if (!projtype.equals(other.projtype))
		{
			return false;
		}
		return true;
	}

	public String getSyscode()
	{
		return syscode;
	}

	@Column
	public void setSyscode(String syscode)
	{
		this.syscode = syscode;
		datas.put("syscode", syscode);
	}

	public String getSyscode_mutual()
	{
		return syscode_mutual;
	}

	@Column
	public void setSyscode_mutual(String syscode_mutual)
	{
		this.syscode_mutual = syscode_mutual;
		datas.put("syscode_mutual", syscode_mutual);
	}

	public String getConntype()
	{
		return conntype;
	}

	public String getPostfix()
	{
		if ("1010".equals(conntype))
		{
			return ".xml";
		} else if ("1020".equals(conntype))
		{
			return ".txt";
		} else
		{
			return "";
		}
	}

	@Column
	public void setConntype(String conntype)
	{
		this.conntype = conntype;
		datas.put("conntype", conntype);
	}

	public String getProjtype()
	{
		return projtype;
	}

	@Column
	public void setProjtype(String projtype)
	{
		this.projtype = projtype;
		datas.put("projtype", projtype);
	}

	public String getInouttype()
	{
		return inouttype;
	}

	@Column
	public void setInouttype(String inouttype)
	{
		this.inouttype = inouttype;
		datas.put("inouttype", inouttype);
	}

	public String getFsmacro()
	{
		return fsmacro;
	}

	public boolean isSameFtp()
	{
		if (StrUtils.isNotNull(getFsmacro()))
		{
			if (StrUtils.isNotNull(getBfsmacro()))
			{
				return getFsmacro().equals(getBfsmacro());
			} else
			{
				return true;
			}
		}
		return false;
	}

	@Column
	public void setFsmacro(String fsmacro)
	{
		this.fsmacro = fsmacro;
		if (this.fsmacro != null)
		{
			this.fsmacro = this.fsmacro.trim();
		}
		datas.put("fsmacro", fsmacro);
	}

	public int getStartflags()
	{
		return startflags;
	}

	@Column
	public void setStartflags(int startflags)
	{
		this.startflags = startflags;
		datas.put("startflags", startflags);
	}

	public int getScanflags()
	{
		return scanflags;
	}

	@Column
	public void setScanflags(int scanflags)
	{
		this.scanflags = scanflags;
		datas.put("scanflags", scanflags);
	}

	public boolean isScan()
	{
		return getScanflags() == 1;
	}

	public int getFlags()
	{
		return flags;
	}

	@Column
	public void setFlags(int flags)
	{
		this.flags = flags;
		datas.put("flags", flags);
	}

	public String getId()
	{
		return id;
	}

	@Column
	public void setId(String id)
	{
		this.id = id;
		datas.put("id", id);
	}

	public String getProjcode()
	{
		return projcode;
	}

	@Column
	public void setProjcode(String projcode)
	{
		this.projcode = projcode;
		datas.put("projcode", projcode);
	}

	public String getOrd()
	{
		return ord;
	}

	@Column
	public void setOrd(String ord)
	{
		this.ord = ord;
		datas.put("ord", ord);
	}

	public int getThreadcount()
	{
		return threadcount;
	}

	@Column
	public void setThreadcount(int threadcount)
	{
		this.threadcount = threadcount;
	}

	public int getThreadfilecount()
	{
		/*if (threadfilecount > 0)
		{
			return threadfilecount;
		}
		return 20;*/
		return threadfilecount;
	}

	@Column
	public void setThreadfilecount(int threadfilecount)
	{
		this.threadfilecount = threadfilecount;
	}

	@Override
	public Object getComp1Key()
	{
		return getSyscode();
	}

	@Override
	public Object getComp2Key()
	{
		return getProjtype();
	}

	@Override
	public Object getComp3Key()
	{
		return getSyscode_mutual();
	}

	public String getCustfilepath()
	{
		return custfilepath;
	}

	@Column
	public void setCustfilepath(String custfilepath)
	{
		this.custfilepath = custfilepath;
		if (this.custfilepath != null)
		{
			this.custfilepath = this.custfilepath.trim();
		}
		datas.put("custfilepath", custfilepath);
	}

	public String getFileprefix()
	{
		return fileprefix;
	}

	@Column
	public void setFileprefix(String fileprefix)
	{
		this.fileprefix = fileprefix;
		if (this.fileprefix != null)
		{
			this.fileprefix = this.fileprefix.trim();
		}
		datas.put("fileprefix", fileprefix);
	}

	public String getBfsmacro()
	{
		return bfsmacro;
	}

	@Column
	public void setBfsmacro(String bfsmacro)
	{
		this.bfsmacro = bfsmacro;
		if (this.bfsmacro != null)
		{
			this.bfsmacro = this.bfsmacro.trim();
		}
		datas.put("bfsmacro", bfsmacro);
	}

	public String getBusitype()
	{
		return busitype;
	}

	public void setBusitype(String busitype)
	{
		this.busitype = busitype;
		datas.put("busitype", busitype);
	}

	public int getSortmode()
	{
		return sortmode;
	}

	@Column
	public void setSortmode(int sortmode)
	{
		this.sortmode = sortmode;
		datas.put("sortmode", sortmode);
	}

	public String getFilecheckmode()
	{
		return filecheckmode;
	}

	@Column
	public void setFilecheckmode(String filecheckmode)
	{
		this.filecheckmode = filecheckmode;
		datas.put("filecheckmode", filecheckmode);
	}

	public Object getValue(String name)
	{
		return datas.get(name);
	}

	/**
	 * 判断是否已生成目录,确保生成文件夹的程序只执行一次，减少FTP指令
	 * @return
	 */
	public boolean isMkdir()
	{
		return isMkdir;
	}

	public void setMkdir(boolean isMkdir)
	{
		this.isMkdir = isMkdir;
	}

	@Override
	public String toString()
	{
		return getProjtype() + "|" + getSyscode() + "|" + getSyscode_mutual();
	}

	/**
	 * 是为交互定义的FTP宏配置具体的FTP地址
	 * @param envParams
	 * @return
	 */
	public boolean isNoFsmacroPath(Map<String,Object> envParams)
	{
		boolean isNoFsmacroPath = false;
		try
		{
			getRealRootPath(envParams, true);
		} catch (Exception e)
		{
			isNoFsmacroPath = true;
		}
		return isNoFsmacroPath;
	}
}
