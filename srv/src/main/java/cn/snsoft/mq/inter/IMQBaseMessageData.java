package cn.snsoft.mq.inter;

import java.util.Date;
/**
 * <p>标题： MQ 标准报文的接口</p>
 * <p>功能： </p>
 * <p>所属模块： MQ SERV</p>
 * <p>版权： Copyright © 2014 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2014年3月29日 下午9:57:13</p>
 * <p>类全名：snsoft.api.mq.model.IMQBaseMessageData</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
public interface IMQBaseMessageData
{
	public static final String	DATE_FORMAT					= "yyyy-mm-dd hh:mm:ss";	//报文日期格式
	//*******************************消息中心XML报文Head元素常量(基础)*********************************************
	public final static String	HEAD_TABLENAME				= "Head";					//报文头表名
	public final static String	HEAD_PROJWAITID				= "ProjWaitID";			//消息通知号
	public final static String	HEAD_PROJID					= "ProjID";				//工程任务号
	public final static String	HEAD_PROJTYPE				= "ProjType";				//消息类型
	public final static String	HEAD_SENDERDI				= "SenderID";				//发送方ID
	public final static String	HEAD_RECEIVERID				= "ReceiverID";			//接收方ID
	public final static String	HEAD_SENDTIME				= "SendTime";				//发送时间
	public final static String	HEAD_VERSION				= "Version";				//版本号
	//*******************************从xml配置其他属性相关属性*********************************************
	public final static String	HEAD_PARAMS					= "HeadParams";			//从xml导出配置其他属性里向head元素赋值(格式：K-V,K-V) 
	public final static String	INNERCODE_BY_OTHERCODE		= "InnercodeByOthercode";	//xml导入配置其他属性，数据内码来源其他字段(格式：表名1-o1~o2,表名2-o3~o4)
	public final static String	BMAKEINNERCODE_ALWAYS		= "BMakeInnercodeAlways";	// 总是生成内码,不根据关联码查找内码的值,也不根据其他字段查找INNERCODE_BY_OTHERCODE无效
	//*******************************消息中心XML报文Head元素常量(回执)*********************************************
	public final static String	HEAD_EXCRESULT				= "Excresult";				//回执结果
	public final static String	HEAD_EXCRESULTDESC			= "Excresultdesc";			//回执结果描述
	public final static String	HEAD_EXCRESULTTIME			= "Excresulttime";			//回执结果时间
	//*******************************消息中心XML报文Head元素常量(单据)*********************************************
	public final static String	HEAD_INNERCODE				= "InnerCode";				//单据内码
	public final static String	HEAD_OUTERCODE				= "Outercode";				//单据外码
	public final static String	HEAD_ODATE					= "Odate";					//业务日期
	public final static String	HEAD_STATUSFROM				= "StatusFrom";			//起始状态
	public final static String	HEAD_STATUSTO				= "StatusTo";				//目标状态
	public final static String	HEAD_FILEPATH				= "Filepath";				//文件路径	
	public final static String	HEAD_FILENAME				= "Filename";				//文件名称	
	public final static String	HEAD_INOUTTYPE				= "Inouttype";				//请求响应
	//*******************************消息中心XML报文回执相关*********************************************
	public final static String	RECEIPT_EXCRESULT_SUCCESS	= "10";					//成功回执结果类型
	public final static String	RECEIPT_EXCRESULT_WAITCHECK	= "15";					//查验
	public final static String	RECEIPT_EXCRESULT_FAILURE	= "20";					//失败回执结果类型
	public final static String	HEAD_REQUEST				= "10";					//请求
	public final static String	HEAD_RESPONSE				= "20";					//响应
	/** 消息ID*/
	public final static String	OrgMessageID				= "OrgMessageID";
	/** 消息类型*/
	public final static String	OrgMessageType				= "OrgMessageType";
	/** 发送方*/
	public final static String	OrgSenderID					= "OrgSenderID";
	/** 接收方*/
	public final static String	OrgReceiverID				= "OrgReceiverID";
	/** 原发送时间*/
	public final static String	OrgRecTime					= "OrgRecTime";

	public String getProjID();

	public void setProjID(String projID);

	public String getProjWaitID();

	public void setProjWaitID(String projWaitID);

	public String getProjType();

	public void setProjType(String projType);

	public String getSenderID();

	public void setSenderID(String senderID);

	public String getReceiverID();

	public void setReceiverID(String receiverID);

	public Date getSendTime();

	public void setSendTime(Date sendTime);

	public String getExcresult();

	public void setExcresult(String excresult);

	public String getExcresultdesc();

	public void setExcresultdesc(String excresultdesc);

	public String getInnerCode();

	public void setOuterCode(String outercode);

	public String getOuterCode();

	public void setInnerCode(String outercode);

	public String getStatusFrom();

	public void setStatusFrom(String statusFrom);

	public String getStatusTo();

	public void setStatusTo(String statusTo);

	public Date getOdate();

	public void setOdate(Date odate);

	public String getFilepath();

	public void setFilepath(String filepath);

	public String getInouttype();

	public void setInouttype(String inouttype);

	public String getExcresulttime();

	public void setExcresulttime(String excresulttime);
}
