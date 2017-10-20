package cn.snsoft.mq.model;

import java.io.Serializable;
import java.util.Date;
import cn.snsoft.mq.inter.IMQBaseMessageData;
/**
 * <p>标题： 工程任务 此对象不缓存</p>
 * <p>功能： </p>
 * <p>所属模块： MQ</p>
 * <p>版权： Copyright © 2013 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2013年11月14日 下午5:52:31</p>
 * <p>类全名：snsoft.api.mq.model.MQProj</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
@BaseModel.Table(tableName = "mq_proj", primaryKeys = "projid")
public class MQProj extends MQRunModel implements IMQBaseMessageData, Serializable
{
	private MQTask[]	tasks;				//任务列表
	private String		projid;			//PID 
	private String		projcode;			//工程编号
	private String		projtype;			//工程类型编号
	private String		busitype;			//业务类型
	private Date		odate;				//业务发生时间
	private String		syscode_request;	//请求方
	private String		syscode_response;	//响应方
	private String		projwaitid;		//PWID
	private int			stepnum_max;		//总步骤  
	private int			stepnum_cur;		//当前步骤 *
	private int			flags;				//flags *
	private String		innercode;			//内码
	private String		outercode;			//外码
	private String		statusfrom;		//单据起始状态 
	private String		statusto;			//单据终止状态 
	//先预留  7 个参数
	private String		param01;			//短参数32/01
	private String		param02;			//短参数32/02
	private String		param03;			//短参数32/03
	private String		param21;			//中参数64/01
	private String		param22;			//中参数64/02
	private String		param41;			//长参数128/01
	private String		param51;			//超长参数512/01
	private Date		param60;			//日期参数01
	private Date		param61;			//日期参数02
	private Date		queryfromtime;		//查询起始时间
	private Date		querytotime;		//查询终止时间 
	private String		queryaddfilter;	//查询增加过滤条件
	private String		inouttype;			//请求响应
	private Date		modifydate;		//最近修改日期
	private String		filepath;			//文件保存路径 
	private String		filename;			//保存文件名称 
	private String		recfilepath;		//回执报文保存路径
	private int			discardflag;		//废弃标识

	public MQProj()
	{
		super();
	}

	public MQTask[] getTasks()
	{
		return tasks;
	}

	private int[]	nums;

	public void addTasks(MQTask[] tasks)
	{
		this.tasks = tasks;
		int len = this.tasks.length;
		this.nums = new int[len];
		for (int i = 0; i < len; i++)
		{
			this.nums[i] = this.tasks[i].getStepnum();
		}
	}

	public int[] taskNums()
	{
		return nums;
	}

	@Override
	@Column("projid")
	public String getProjID()
	{
		return projid;
	}

	@Override
	@Column("projid")
	public void setProjID(String projid)
	{
		this.projid = projid;
		setUpdateValue("projid", this.projid);
	}

	public String getProjcode()
	{
		return projcode;
	}

	@Column
	public void setProjcode(String projcode)
	{
		this.projcode = projcode;
		setUpdateValue("projcode", this.projcode);
	}

	@Override
	@Column("syscode_request")
	public String getSenderID()
	{
		return syscode_request;//syscode_request  	请求方  
	}

	@Override
	@Column("syscode_request")
	public void setSenderID(String syscode_request)
	{
		this.syscode_request = syscode_request;
		setUpdateValue("syscode_request", this.syscode_request);
	}

	@Override
	@Column("syscode_response")
	public String getReceiverID()
	{
		return syscode_response;
	}

	@Override
	@Column("syscode_response")
	public void setReceiverID(String syscode_response)
	{
		this.syscode_response = syscode_response;
		setUpdateValue("syscode_response", this.syscode_response);
	}

	@Override
	@Column("projwaitid")
	public String getProjWaitID()//标准的报文数据
	{
		return projwaitid;
	}

	@Override
	@Column("projwaitid")
	public void setProjWaitID(String projwaitid)//标准的报文数据
	{
		this.projwaitid = projwaitid;
		setUpdateValue("projwaitid", this.projwaitid);
	}

	public int getStepnum_max()
	{
		return stepnum_max;
	}

	@Column
	public void setStepnum_max(int stepnum_max)
	{
		this.stepnum_max = stepnum_max;
		setUpdateValue("stepnum_max", this.stepnum_max);
	}

	public int getStepnum_cur()
	{
		return stepnum_cur;
	}

	@Column
	public void setStepnum_cur(int stepnum_cur)
	{
		this.stepnum_cur = stepnum_cur;
		setUpdateValue("stepnum_cur", this.stepnum_cur);
	}

	public int getFlags()
	{
		return flags;
	}

	@Column
	public void setFlags(int flags)
	{
		this.flags = flags;
		setUpdateValue("flags", this.flags);
	}

	@Override
	@Column("innercode")
	public String getInnerCode()
	{
		return innercode;
	}

	@Override
	@Column("innercode")
	public void setInnerCode(String innerCode)
	{
		this.innercode = innerCode;
		setUpdateValue("innercode", this.innercode);
	}

	@Override
	@Column("outercode")
	public String getOuterCode()
	{
		return outercode;
	}

	@Override
	@Column("outercode")
	public void setOuterCode(String outercode)
	{
		this.outercode = outercode;
		setUpdateValue("outercode", this.outercode);
	}

	@Override
	@Column("statusfrom")
	public String getStatusFrom()
	{
		return statusfrom;
	}

	@Override
	@Column("statusfrom")
	public void setStatusFrom(String statusFrom)
	{
		this.statusfrom = statusFrom;
		setUpdateValue("statusfrom", this.statusfrom);
	}

	@Override
	@Column("statusto")
	public String getStatusTo()
	{
		return statusto;
	}

	@Override
	@Column("statusto")
	public void setStatusTo(String statusTo)
	{
		this.statusto = statusTo;
		setUpdateValue("statusto", this.statusto);
	}

	@Override
	@Column("projtype")
	public String getProjType()//标准报文数据
	{
		return projtype;
	}

	@Override
	@Column("projtype")
	public void setProjType(String projtype)//标准报文数据
	{
		this.projtype = projtype;
		setUpdateValue("projtype", this.projtype);
	}

	@Override
	public Date getOdate()
	{
		return odate;
	}

	@Override
	@Column
	public void setOdate(Date odate)
	{
		this.odate = odate;
		setUpdateValue("odate", this.odate);
	}

	public String getParam01()
	{
		return param01;
	}

	@Column
	public void setParam01(String param01)
	{
		this.param01 = param01;
		setUpdateValue("param01", this.param01);
	}

	public String getParam02()
	{
		return param02;
	}

	@Column
	public void setParam02(String param02)
	{
		this.param02 = param02;
		setUpdateValue("param02", this.param02);
	}

	public String getParam03()
	{
		return param03;
	}

	@Column
	public void setParam03(String param03)
	{
		this.param03 = param03;
		setUpdateValue("param03", this.param03);
	}

	public String getParam21()
	{
		return param21;
	}

	@Column
	public void setParam21(String param21)
	{
		this.param21 = param21;
		setUpdateValue("param21", this.param21);
	}

	public String getParam22()
	{
		return param22;
	}

	@Column
	public void setParam22(String param22)
	{
		this.param22 = param22;
		setUpdateValue("param22", this.param22);
	}

	public String getParam41()
	{
		return param41;
	}

	@Column
	public void setParam41(String param41)
	{
		this.param41 = param41;
		setUpdateValue("param41", this.param41);
	}

	public String getParam51()
	{
		return param51;
	}

	@Column
	public void setParam51(String param51)
	{
		this.param51 = param51;
		setUpdateValue("param51", this.param51);
	}

	public Date getParam60()
	{
		return param60;
	}

	@Column
	public void setParam60(Date param60)
	{
		this.param60 = param60;
		setUpdateValue("param60", this.param60);
	}

	public Date getParam61()
	{
		return param61;
	}

	@Column
	public void setParam61(Date param61)
	{
		this.param61 = param61;
		setUpdateValue("param61", this.param61);
	}

	public Date getQueryfromtime()
	{
		return queryfromtime;
	}

	@Column
	public void setQueryfromtime(Date queryfromtime)
	{
		this.queryfromtime = queryfromtime;
		setUpdateValue("queryfromtime", this.queryfromtime);
	}

	public Date getQuerytotime()
	{
		return querytotime;
	}

	@Column
	public void setQuerytotime(Date querytotime)
	{
		this.querytotime = querytotime;
		setUpdateValue("querytotime", this.querytotime);
	}

	public String getQueryaddfilter()
	{
		return queryaddfilter;
	}

	@Column
	public void setQueryaddfilter(String queryaddfilter)
	{
		this.queryaddfilter = queryaddfilter;
		setUpdateValue("queryaddfilter", this.queryaddfilter);
	}

	@Override
	public String getFilepath()
	{
		return filepath;
	}

	@Override
	@Column
	public void setFilepath(String filepath)
	{
		this.filepath = filepath;
		if (this.filepath != null)
		{
			this.filepath = this.filepath.trim();
		}
		setUpdateValue("filepath", this.filepath);
	}

	public String getFilename()
	{
		return filename;
	}

	@Column
	public void setFilename(String filename)
	{
		this.filename = filename;
		setUpdateValue("filename", this.filename);
	}

	public String getRecfilepath()
	{
		return recfilepath;
	}

	@Column
	public void setRecfilepath(String recfilepath)
	{
		this.recfilepath = recfilepath;
		if (this.recfilepath != null)
		{
			this.recfilepath = this.recfilepath.trim();
		}
		setUpdateValue("recfilepath", this.recfilepath);
	}

	@Override
	public String getInouttype()
	{
		return inouttype;
	}

	@Override
	@Column
	public void setInouttype(String inouttype)
	{
		this.inouttype = inouttype;
		setUpdateValue("inouttype", this.inouttype);
	}

	@Override
	public String getMessageStr()
	{
		String projid = getProjID(); //PID
		String projcode = getProjcode(); //工程编号
		String projtype = getProjType(); //工程类型编号
		String syscode_request = getSenderID(); //请求方
		String syscode_response = getReceiverID(); //响应方
		int stepnum_cur = getStepnum_cur(); //当前步骤 
		Date executeDate = getExecutedate();//本次执行开始时间
		Date endDate = getEnddate();//执行结束时间
		String mess = "消息中心定时执行任务出错,工程信息如下:";
		mess += "\n工程类型编号:" + projtype + "    工程编号:" + projcode + "    工程ID:" + projid + "    当前步骤:" + (stepnum_cur + 1);
		mess += "\n请求方系统号:" + syscode_request + "    响应方系统号:" + syscode_response + "    本次开始时间:" + executeDate + "    结束时间:" + endDate;
		mess += "\n";
		return mess;
	}

	@Override
	public String getQueueLockKey()
	{
		return "MQProj" + projtype;
	}

	//
	/********************************标准的报文数据***************************************/
	private Date	sendTime;
	private String	excresult;
	private String	excresultdesc;

	@Override
	public Date getSendTime()
	{
		return sendTime;
	}

	@Override
	public void setSendTime(Date sendTime)
	{
		this.sendTime = sendTime;
	}

	@Override
	public String getExcresult()
	{
		return excresult;
	}

	@Override
	public void setExcresult(String excresult)
	{
		this.excresult = excresult;
		setUpdateValue("excresult", this.excresult);
	}

	@Override
	public String getExcresultdesc()
	{
		return excresultdesc;
	}

	@Override
	public void setExcresultdesc(String excresultdesc)
	{
		this.excresultdesc = trimDescInfo(excresultdesc);
		setUpdateValue("excresultdesc", this.excresultdesc);
	}

	public Date getModifydate()
	{
		return modifydate;
	}

	@Column
	public void setModifydate(Date modifydate)
	{
		this.modifydate = modifydate;
	}

	public String getBusitype()
	{
		return busitype;
	}

	@Column
	public void setBusitype(String busitype)
	{
		this.busitype = busitype;
	}

	public int getDiscardflag()
	{
		return discardflag;
	}

	@Column
	public void setDiscardflag(int discardflag)
	{
		this.discardflag = discardflag;
		setUpdateValue("discardflag", this.discardflag);
	}

	@Override
	public String getExcresulttime()
	{
		return null;
	}

	@Override
	public void setExcresulttime(String excresulttime)
	{
	}

	@Override
	public String getPrimaryValues()
	{
		return this.getProjID();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projid == null) ? 0 : projid.hashCode());
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
		MQProj other = (MQProj) obj;
		if (projid == null)
		{
			if (other.projid != null)
			{
				return false;
			}
		} else if (!projid.equals(other.projid))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "工程：" + this.getProjID();
	}
}
