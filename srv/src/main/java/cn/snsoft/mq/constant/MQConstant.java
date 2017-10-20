package cn.snsoft.mq.constant;

/**
 * <p>标题： 消息中心使用的常量</p>
 * <p>功能： </p>
 * <p>所属模块： MQ</p>
 * <p>版权： Copyright © 2013 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2013年11月14日 下午4:47:51</p>
 * <p>类全名：snsoft.api.mq.constant.MQConstant</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
public class MQConstant
{
	/**1 创建 待处理工程 **/
	//1 MQUtil.createMQProjWait
	//mq_proj_create				MQProjCreate		消息通知定义	
	//---mq_proj_create_param  		MQProjCreateParam	消息通知参数
	//mq_proj_wait					MQProjWait			消息通知表
	//---mq_proj_wait_log								消息通知日志表
	/*************************************************************************/
	//---mq_serv_conf  				MQServConf			系统服务配置定义	
	//------mq_serv_conf_param  	MQServConfParam		系统服务参数配置定义
	//---mq_sys_conf  				MQSysConf			接入系统定义		
	//---mq_conn_conf  				MQConnConf			系统交互方式
	/*************************************************************************/
	//mq_proj  						MQProj				工程
	//---mq_task  					MQTask				工程任务
	/*************************************************************************/
	//mq_proj_temp  				MQProjTemp			工程模板定义				
	//---mq_proj_temp_param  		MQProjTempParam		工程模板参数				
	//---mq_task_temp  				MQTaskTemp			任务模板定义	
	/*************************************************************************/
	//**************************MQ 系统  MAP KEY 常量****************************************
	public final static String	MQSYS_PROCESS_RESULT_KEY		= "MQSYS_PROCESS_RESULT_KEY";									//MQ 系统 过程中的结果 KEY
	public final static String	MQSYS_PROJ_PARAMS_KEY			= "MQSYS_PROJ_PARAMS_KEY";										//工程参数KEY
	public final static String	MQSYS_RESULT_KEY				= "MQSYS_RESULT_KEY";											//返回结果KEY
	//**************************MQResult MQ 执行结果 对象*****************************
	//成功
	public final static String	RESULT_SUCCEED					= "SUCCEED";													//succeed
	//失败
	public final static String	RESULT_FAILURE					= "FAILURE";
	//等待
	//public final static String	RESULT_WAIT						= "WAIT";
	//**************************MQConnConf MQ 系统交互方式 对象************************
	public final static String	CONNCONF_NATIVE					= "1010";														//1010 NATIVE 本地代码  
	public final static String	CONNCONF_SERVLET_REMOTEINVOKE	= "2010";														//2010 SERVLET REMOTEINVOKE	
	public final static String	CONNCONF_SERVLET_CMS			= "2020";														//2020 SERVLET CMS	
	public final static String	CONNCONF_WEBSERVICE_CXF			= "3010";														//3010 WEBSERVICE CXF
	//服务注册方式
	public final static String	CONNCONF_SERVICE_REG			= "6010";
	//**************************MQ 任务状态*****************************************
	//10"未激活" 20"等待中" 30"执行中" 70"执行成功"  80"执行失败"  81"消息异常" 82"无效异常"  90"执行超次"
	public final static String	STATUS_NOACTIVATE				= "10";														//"未激活"
	//public final static String	STATUS_WAIT						= "20";														//"等待中"
	public final static String	STATUS_EXECUTE					= "30";														//"执行中"
	public final static String	STATUS_SUCCEED					= "70";														//"执行成功"
	public final static String	STATUS_FAILURE					= "80";														//应用层bug	执行失败
	public final static String	STATUS_MQFAILURE				= "81";														//MQ bug  消息异常
	public final static String	STATUS_INVALID					= "82";														//对方的异常
	public final static String	STATUS_NUMOVER					= "90";														//延次执行失败  执行超次
	//**************************MQ 标准工程+任务*****************************************	
	public final static String	PROJ_TASK_XXX_KEY				= "";															//
	//**************************MQ Task异常类型*****************************************************
	public final static String	TASKEXC_EXECUTE_CONDITION		= "1010";														//1010	执行条件异常
	public final static String	TASKEXC_NET_CONNECTION			= "2010";														//2010	网络通信异常			
	public final static String	TASKEXC_RUNTIME					= "3010";														//3010	程序执行异常
	public final static String	TASKEXC_INVALID					= "4010";														//4010	第三方异常
	//**************************MQ 任务模板输入/输出方*************************************************
	public final static String	ACCESS_REQUEST					= "10";														//请求方
	public final static String	ACCESS_RESPONSE					= "20";														//响应方
	//**************************MQ 系统间交互定义请求/响应 **********************************************
	public final static String	SYSCONN_REQUEST					= "10";														//请求
	public final static String	SYSCONN_RESPONSE				= "20";														//响应
	//**************************当前系统编码**********************************************************
	public final static String	CURSYSCODE						= "MQ.MQCURSYSCODE";
	/**当前系统消息通知,工程,扫描报文是需要过滤的系统编码(为了解决测试时,多个检管区共用一个服务使用)*/
	public final static String	CURFILTERSYSCODES				= "MQ.MQCURFILTERSYSCODES";
	//数据交互中心所属系统
	public final static String	MQ_MQCENTERSYSCODE				= "MQ.MQCENTERSYSCODE";
	//**************************MQ 系统间交互定义服务地址宏前缀**********************************************************
	public final static String	SERVICEMACROPRE					= "MQSERVICE_";												//服务地址宏前缀
	//**************************文件类型*************************************************************
	public final static String	FILETYPE_XML					= "10";														//XML文件
	public final static String	FILETYPE_DE						= "11";														//大师导入导出
	public final static String	FILETYPE_BEAN					= "12";														//BEAN对象导入导出
	public final static String	FILETYPE_TXT					= "20";														//TXT文件
	public final static String	FILETYPE_RESULTXML				= "30";														//标准XML回执报文
	public final static String	FILETYPE_RESULTTXT				= "40";														//标准TXT回执文件
	//**************************消息常规参数名*************************************************************
	public final static String	BEANCODE_IN						= "beancode_in";												//导入bean定义编号
	public final static String	BEANCODE						= "beancode";													//导入bean定义编号
	public final static String	XMLCODE_OUT						= "xmlcode_out";												//导出XML格式定义编号
	public final static String	XMLCODE_IN						= "xmlcode_in";												//导入XML格式定义编号
	public final static String	FILEDEFCODE						= "filedefcode";												//文件定义编码
	public final static String	XMLCODE							= "xmlcode";													//XML格式定义编号
	public final static String	DECODE							= "deid";														//DE格式定义编号
	public final static String	FILEPATH						= "filePath";													//文件路径
	public final static String	FILENAME						= "fileName";													//报文名称,导出报文时使用,在生成报文时先生称报文名
	public final static String	INNERCODE						= "innerCode";													//内码
	public final static String	OUTERCODE						= "outerCode";													//外码
	public final static String	OPERTYPE						= "opertype";													//操作类型
	public final static String	SHEETCODE						= "sheetCode";													//单据号
	public final static String	ODATE							= "odate";														//业务日期
	public final static String	STATUSFROM						= "statusFrom";												//起始状态
	public final static String	STATUSTO						= "statusTo";													//目标状态
	public final static String	STATUSDESC						= "statusDesc";												//回执结果描述
	public final static String	FILETYPE						= "filetype";													//文件类型[10:XML文件;11:DE;20:TXT文件]
	public final static String	SYSCODE_REQUEST					= "syscode_request";											//请求方编码
	public final static String	SYSCODE_RESPNESE				= "syscode_response";											//响应方编码
	public final static String	PROJTYPE						= "projtype";													//业务工程类型
	public final static String	DATATYPE						= "dataType";													//业务数据类型
	public final static String	INPUTSTREAM_PARAMNAME			= "inputStream";												//输入流参数名
	public final static String	INOUTTYPE						= "inouttype";													//请求响应
	public final static String	PROCESSRESULT					= "processResult";												//前一服务程序返回值
	public final static String	GINNERCODE						= "ginnercode";												//子表内码
	public final static String	EXCRESULT						= "excresult";
	public final static String	EXCRESULTDESC					= "excresultdesc";
	public final static String	QUERY_ADDFILTER					= "queryaddfilter";
	public final static String	QUERY_FROM_TIME					= "queryfromtime";
	public final static String	QUERY_TO_TIME					= "querytotime";
	public final static String	QUERY_TIME_FIELD				= "querytimefield";
	public final static String	METHOD_NAME						= "methodname";
	public final static String	EXCRESULTDESC_ORIGINAL			= "excresultdesc_original";									//原始异常描述信息
	//*********************************定时任务相关常量*************************************************************
	public final static String	MQBUSITYPEFILTER				= "MQBUSITYPEFILTER";											//定时任务根据业务类型区分过滤参数名称
	public final static String	MQTIMERTASKTIMEOUT				= "MQTIMERTASKTIMEOUT";										//定时任务根据业务类型区分过滤参数名称
	//MQ 默认异常类型
	public final static String	MQEXCEPTIONTYPEDEFAULT			= "3507";
	//服务引擎  默认异常类型
	public final static String	EXCEPTION_ENGINE_DEF			= "3508";
	//**********************************完成数据备份************************************
	public final static String	MQCOMPTABLESUFFIX				= "_comp";														//完成表表名后缀
	public final static String	MQCOMPDAYS						= "MQ.COMDATASDAYS";											// 备份X天前的完成数据,不定义则不备份
	public final static String	MQNEEDBACKUPTABLENAMES			= "mq_proj,mq_task,mq_task_log;mq_proj_wait,mq_proj_wait_log";	//需要备份完成数据的表,格式为:主表a,子表1,子表2...;主表b,子表1,子表2....
	//*********************************MQ 日志 总开关***********************************
	//MQ消息日志总开关   0    记录完整日志    1  异常时记录日志（默认）  2 不记录日志
	public final static String	MQ_LOG_PROJWAIT_ONOFF			= "MQ.LOG.PROJWAIT.ONOFF";
	//MQ工程日志总开关  0    记录完整日志    1  异常时记录日志（默认）  2 不记录日志
	public final static String	MQ_LOG_PROJTASK_ONOFF			= "MQ.LOG.PROJTASK.ONOFF";
	public final static int		MQ_Add_RunModel_Times			= 0;
	// 获取报文时是否需要检查路径是否正确
	public final static String	MQ_NEEDCHECKFTPFILEPATH			= "MQ.NEEDCHECKFTPFILEPATH";
	/*** 在busidao里边生成消息通知使用常量**********/
	public final static String	MQ_SAVEEVENT_CREATEMQPROJWAIT	= "MQ.SAVEEVENT.CREATEMQPROJWAIT";
	public final static String	MQ_SAVEEVENT_MQRUNPARAM			= "MQ.SAVEEVENT.MQRUNPARAM";
	/********公共服务类名参数**********/
	public final static String	MQPUBLICSERVICE_CLASSNAME		= "MQPUBLICSERVICE_CLASSNAME";
	//**************************消息缓冲对象状态*****************************************
	//10-未执行
	public final static String	MQ_BUFF_STATUS_INIT				= "10";
	//单部署控制的最大线程数
	public final static int		MQ_THREADPOOL_MAXSIZE			= 100;
	//**************************服务引擎常量*****************************************
	//服务引擎路由 config--KEY
	public final static String	MQSERV_MSECODE_ROUTER			= "MQServMseCodeRouter";
	//WS消息工程业务数据参数
	public final static String	MQ_BUSIDATA						= "BusiData";
	//WS工程服务ID **/
	public final static String	MQ_PROJ_SERV_ID					= "mq.MQService";
	/*FTP连接正常*/
	public static final String	FTP_STATUS_SUCC					= "10";
	/*FTP连接异常*/
	public static final String	FTP_STATUS_FAIL					= "90";
}
