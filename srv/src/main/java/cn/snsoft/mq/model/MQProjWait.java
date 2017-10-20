package cn.snsoft.mq.model;

import java.util.Date;
import java.util.List;
import cn.snsoft.mq.constant.MQConstant;
import cn.snsoft.mq.inter.IMQBaseMessageData;
import cn.snsoft.mq.model.BaseModel.Column;
import cn.snsoft.util.DateUtil;
import cn.snsoft.util.StrUtils;
/**
 * <p>标题：消息通知表 此对象不缓存 </p>
 * <p>功能： </p>
 * <p>所属模块： MQ MODEL</p>
 * <p>版权： Copyright © 2013 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2013年11月26日 下午7:18:57</p>
 * <p>类全名：snsoft.api.mq.model.MQProjWait</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
@BaseModel.Table(tableName = "mq_proj_wait", primaryKeys = "projwaitid")
public class MQProjWait extends MQRunModel implements IMQBaseMessageData
{
	private String	projwaitid;		//PWID
	private String	projtype;			//工程类型编号
	private String	syscode;			//当前系统请求方
	private String	syscode_response;	//响应方
	private String	busitype;			//业务类型
	private String	servid;			//工程创建服务ID 
	private Date	odate;				//业务发生时间
	private int		flags;				//flags *
	private int		discardflag;		//1 无数据时废弃 
	//先预留  7 个参数
	private String	param01;			//短参数32/01
	private String	param02;			//短参数32/02
	private String	param03;			//短参数32/03
	private String	param21;			//中参数64/01
	private String	param22;			//中参数64/02
	private String	param41;			//长参数128/01
	private String	param51;			//超长参数512/01
	private Date	param60;			//日期参数01
	private Date	param61;			//日期参数02
	private Date	queryfromtime;		//查询起始时间
	private Date	querytotime;		//查询终止时间 		
	private String	queryaddfilter;	//查询增加过滤条件
	private String	projid;			//PID
	private String	outercode;			//单据外码(用于方便查询)
	private String	innercode;			//主键
	private String	statusfrom;		//单据起始状态 
	private String	statusto;			//单据终止状态 
	private Date	receipttime;		//回执完成时间
	private int		receiptflags;		//回执标识
	private int		nerepflag;			//需回执
	private int		endflag;			//完结标识
	private int		orderflag;			//顺序执行
	private String	filepath;			//文件保存路径 
	private String	recfilepath;		//回执报文路径
	private String	inouttype;			//请求响应
	private Date	modifydate;		//最近修改日期
	private int		sleepseconds;		//休眠时间(秒),某些特殊的消息通知需要等待若干秒再执行  add by 王江平 2015-10-04
	private String	opertype;			//			操作类型
	private boolean	isBuff;			//是否为缓冲消息
	private String	sheetcode;			//单据号
	private int		exeftpfileflag;	//工程已执行

	public MQProjWait()
	{
		super();
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

	@Override
	@Column("syscode")
	public String getSenderID()
	{
		return syscode;//syscode_request  	请求方  
	}

	@Override
	@Column("syscode")
	public void setSenderID(String syscode)
	{
		this.syscode = syscode;
		setUpdateValue("syscode", this.syscode);
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
		setUpdateValue("odate.[sqltype]", 93);
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

	public String getServid()
	{
		return servid;
	}

	@Column
	public void setServid(String servid)
	{
		this.servid = servid;
		setUpdateValue("servid", this.servid);
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
	@Column("statusfrom")
	public String getStatusFrom()
	{
		return getStatusfrom();
	}

	public String getStatusfrom()
	{
		return statusfrom;
	}

	@Override
	@Column("statusfrom")
	public void setStatusFrom(String statusFrom)
	{
		setStatusfrom(statusFrom);
	}

	public void setStatusfrom(String statusFrom)
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

	public Date getReceipttime()
	{
		return receipttime;
	}

	@Column
	public void setReceipttime(Date receipttime)
	{
		this.receipttime = receipttime;
		setUpdateValue("receipttime", this.receipttime);
		setUpdateValue("receipttime.[sqltype]", 93);
	}

	public int getReceiptflags()
	{
		return receiptflags;
	}

	@Column
	public void setReceiptflags(int receiptflags)
	{
		this.receiptflags = receiptflags;
		setUpdateValue("receiptflags", this.receiptflags);
	}

	public void setReceipt()
	{
		setReceiptflags(1);
	}

	public int getExeftpfileflag()
	{
		return exeftpfileflag;
	}

	@Column
	public void setExeftpfileflag(int exeftpfileflag)
	{
		this.exeftpfileflag = exeftpfileflag;
		setUpdateValue("exeftpfileflag", this.exeftpfileflag);
	}

	public void setExeftpfile()
	{
		setExeftpfileflag(1);
	}

	public int getDiscardflag()
	{
		return discardflag;
	}

	@Column
	public void setDiscardflag(int flag)
	{
		if ((this.discardflag & flag) != flag)
		{
			this.discardflag = this.discardflag + flag;
			setUpdateValue("discardflag", this.discardflag);
		}
	}

	public void setDiscard()
	{
		setDiscardflag(1);
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
		setUpdateValue("param60.[sqltype]", 93);
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
		setUpdateValue("param61.[sqltype]", 93);
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
		setUpdateValue("queryfromtime.[sqltype]", 93);
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
		setUpdateValue("querytotime.[sqltype]", 93);
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
	}

	@Override
	public boolean isExecuteStatus()
	{
		return super.isExecuteStatus();
	}

	@Override
	public String getMessageStr()
	{
		String projwaitid = getProjWaitID();
		String projtype = getProjType(); //工程类型编号
		String syscode_response = getReceiverID();
		Date executeDate = getExecutedate();//本次执行开始时间
		Date endDate = getEnddate();//执行结束时间
		String mess = "定时执行消息通知出错,消息通知信息如下:";
		mess += "\n工程类型编号:" + projtype + "    消息通知ID:" + projwaitid + "    响应方系统号:" + syscode_response;
		mess += "\n本次开始时间:" + executeDate + "    结束时间:" + endDate;
		return mess;
	}

	@Override
	public String getQueueLockKey()
	{
		return "MQProjWait" + projtype;
	}

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
		setUpdateValue(MQConstant.EXCRESULTDESC_ORIGINAL, excresultdesc);//用于记录完整的信息，记录到日志表中
		this.excresultdesc = trimDescInfo(excresultdesc);
		setUpdateValue("excresultdesc", this.excresultdesc);
	}

	@Override
	public String getPrimaryValues()
	{
		return this.getProjWaitID();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projwaitid == null) ? 0 : projwaitid.hashCode());
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
		MQProjWait other = (MQProjWait) obj;
		if (projwaitid == null)
		{
			if (other.projwaitid != null)
			{
				return false;
			}
		} else if (!projwaitid.equals(other.projwaitid))
		{
			return false;
		}
		return true;
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
		setUpdateValue("filepath", this.filepath);
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

	public int getNerepflag()
	{
		return nerepflag;
	}

	/**
	 * 需回执
	 * isNerep
	 * @author shuquan
	 * @return
	 */
	public boolean isNerep()
	{
		return (getNerepflag() & 1) != 0;
	}

	@Column
	public void setNerepflag(int nerepflag)
	{
		this.nerepflag = nerepflag;
		setUpdateValue("nerepflag", this.nerepflag);
	}

	@Override
	public String toString()
	{
		return "消息 ： " + this.getProjWaitID();
	}

	public int getEndflag()
	{
		return endflag;
	}

	/**
	 * 已完结
	 * isEnd
	 * @author shuquan
	 * @return
	 */
	public boolean isEnd()
	{
		return (getEndflag() & 1) != 0;
	}

	@Column
	public void setEndflag(int endflag)
	{
		this.endflag = endflag;
		setUpdateValue("endflag", this.endflag);
	}

	public void setEnd()
	{
		setEndflag(1);
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

	public int getOrderflag()
	{
		return orderflag;
	}

	/**
	 * 是否需要顺序执行
	 * isInOrder
	 * @author shuquan
	 * @return
	 */
	@Override
	public boolean isInOrder()
	{
		return (getOrderflag() & 1) != 0;
	}

	@Column
	public void setOrderflag(int orderflag)
	{
		this.orderflag = orderflag;
		setUpdateValue("orderflag", this.orderflag);
	}

	private List<MQProjWait>	prevProjWait;	// 此消息通知之前的消息

	public List<MQProjWait> getPrevProjWait()
	{
		return prevProjWait;
	}

	public void setPrevProjWait(List<MQProjWait> prevProjWait)
	{
		this.prevProjWait = prevProjWait;
	}

	/**
	 * 是否执行失败
	 * isFail
	 * @author shuquan
	 * @return
	 */
	public boolean isFail()
	{
		return IMQBaseMessageData.RECEIPT_EXCRESULT_FAILURE.equals(getExcresult());
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

	public int getSleepseconds()
	{
		return sleepseconds;
	}

	@Column
	public void setSleepseconds(int sleepseconds)
	{
		this.sleepseconds = sleepseconds;
	}

	public String getOpertype()
	{
		return opertype;
	}

	@Column
	public void setOpertype(String opertype)
	{
		this.opertype = opertype;
	}

	public String getSheetcode()
	{
		return sheetcode;
	}

	@Column
	public void setSheetcode(String sheetcode)
	{
		this.sheetcode = sheetcode;
	}

	public boolean isBuff()
	{
		return isBuff;
	}

	public void setBuff(boolean isBuff)
	{
		this.isBuff = isBuff;
	}

	@Override
	public String getExcresulttime()
	{
		return null;
	}

	@Override
	public void setExcresulttime(String excresulttime)
	{
		if (StrUtils.isNotNull(excresulttime))
		{
			Date date = DateUtil.getServerDate();
			try
			{
				date = DateUtil.parseDate(excresulttime, DateUtil.DATE_FORMAT_01);
			} finally
			{
				this.receipttime = date;
				setUpdateValue("receipttime", this.receipttime);
			}
		}
	}
}
