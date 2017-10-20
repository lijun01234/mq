package cn.snsoft.mq.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import cn.snsoft.mq.constant.MQConstant;
import cn.snsoft.util.StrUtils;
/**
 * <p>标题：MQ 可执行  Model 包括消息、工程、任务  </p>
 * <p>功能： 数据 不要包括业务逻辑</p>
 * <p>所属模块： MQ</p>
 * <p>版权： Copyright © 2013 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2013年11月14日 下午6:03:31</p>
 * <p>类全名：snsoft.api.mq.model.MQRunModel</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
public abstract class MQRunModel extends BaseModel //implements CacheKey
{
	protected Date		startdate;				//*开始时间 (对于工程:整个工程开始执行的时间,即下属第一条任务的第一次开始执行时间;)
	protected Date		enddate;				//*结束时间 (对于工程:整个工程的结束时间)
	protected Date		executedate;			//*本次开始时间 (工程冗余当前任务的本次执行开始时间)
	protected long		exctime;				//*本次运行时长	(对于工程:整个工程的运行时长)
	protected Date		allowtime;				//*允许启动时间 
	protected String	status;				//状态 
	protected int		runnum_max;			//允许执行次数	(工程冗余当前任务的允许执行次数)
	protected int		runnum;				//已执行次数	(工程冗余当前任务的已执行次数)
	protected long		intvalseconds;			//间隔时间(秒)  间隔时间:当某次执行失败后,并还有剩余次数,会将当前时间+间隔时间更新到可执行时间 
	protected String	servip;				//创建此对象的服务器IP
	protected Date		predate;				//制单时间
	private boolean		isNoSave	= false;	//当前对象是否需要保持

	public MQRunModel()
	{
	}

	/**
	 * 设置工程消息不保存数据
	 */
	public void setIsNoSave(boolean isNoSave)
	{
		this.isNoSave = isNoSave;
	}

	public boolean isNoSave()
	{
		return this.isNoSave;
	}

	public boolean isCompleteStatus()
	{
		return MQConstant.STATUS_SUCCEED.compareTo(status) <= 0;
	}

	public boolean isDelayStatus()
	{
		return MQConstant.STATUS_EXECUTE.equals(status);//执行中状态
	}

	public boolean isSucceedStatus()
	{
		return MQConstant.STATUS_SUCCEED.equals(status);
	}

	public boolean isFailureStatus()
	{
		return MQConstant.STATUS_FAILURE.equals(status);
	}

	public boolean isMQFailureStatus()
	{
		return MQConstant.STATUS_MQFAILURE.equals(status);
	}

	public boolean isNumoverStatus()
	{
		return MQConstant.STATUS_NUMOVER.equals(status);
	}

	public boolean isInvalidStatus()
	{
		return MQConstant.STATUS_INVALID.equals(status);
	}

	/**
	 * 可执行状态
	 * isExecuteStatus
	 * @author shuquan
	 * @return
	 */
	public boolean isExecuteStatus()
	{
		if (status == null)
		{
			return true;
		}
		return MQConstant.STATUS_SUCCEED.compareTo(status) > 0;
	}

	public boolean isInOrder()
	{
		return false;
	}

	/************************   以下为    GET SET 方法**********************************/
	public String getStatus()
	{
		return status;
	}

	@Column
	public void setStatus(String status)
	{
		this.status = status;
		setUpdateValue("status", this.status);
	}

	public Date getStartdate()
	{
		return startdate;
	}

	@Column
	public void setStartdate(Date startdate)
	{
		this.startdate = startdate;
		setUpdateValue("startdate", this.startdate);
	}

	public Date getEnddate()
	{
		return enddate;
	}

	@Column
	public void setEnddate(Date enddate)
	{
		this.enddate = enddate;
		setUpdateValue("enddate", this.enddate);
	}

	public Date getExecutedate()
	{
		return executedate;
	}

	@Column
	public void setExecutedate(Date executedate)
	{
		this.executedate = executedate;
		setUpdateValue("executedate", this.executedate);
	}

	public int getRunnum_max()
	{
		return runnum_max;
	}

	@Column
	public void setRunnum_max(int runnum_max)
	{
		this.runnum_max = runnum_max;
		setUpdateValue("runnum_max", this.runnum_max);
	}

	public int getRunnum()
	{
		return runnum;
	}

	@Column
	public void setRunnum(int runnum)
	{
		this.runnum = StrUtils.obj2int(runnum, 0);
		setUpdateValue("runnum", this.runnum);
	}

	public long getExctime()
	{
		return exctime;
	}

	@Column
	public void setExctime(long exctime)
	{
		this.exctime = exctime;
		setUpdateValue("exctime", this.exctime);
	}

	public long getIntvalseconds()
	{
		return intvalseconds;
	}

	@Column
	public void setIntvalseconds(long intvalseconds)
	{
		this.intvalseconds = intvalseconds;
		setUpdateValue("intvalseconds", this.intvalseconds);
	}

	public String getServip()
	{
		return servip;
	}

	@Column
	public void setServip(String servip)
	{
		if (this.servip == null)
		{
			this.servip = servip;
		} else if (servip == null || servip.length() == 0)
		{
			return;
		} else
		{
			String[] temp = StrUtils.splitString(this.servip, ',');
			this.servip = temp[0] + ',' + servip;
		}
		setUpdateValue("servip", this.servip);
	}

	public Date getAllowtime()
	{
		return allowtime;
	}

	@Column
	public void setAllowtime(Date allowtime)
	{
		this.allowtime = allowtime;
		setUpdateValue("allowtime", this.allowtime);
	}

	public Date getPredate()
	{
		return predate;
	}

	@Column
	public void setPredate(Date predate)
	{
		this.predate = predate;
		setUpdateValue("predate", this.predate);
	}

	/************************   以下为    抽象 方法**********************************/
	/**
	 * 获取主键值
	 * @return
	 */
	public abstract String getPrimaryValues();

	/**
	 * 返回描述信息
	 * @return
	 */
	public abstract String getMessageStr();

	/**
	 * 获取同类型并发控制锁的KEY
	 * @return
	 */
	public abstract String getQueueLockKey();

	private String	tableName;

	public String getTableName()
	{
		if (StrUtils.isNotNull(this.tableName))
		{
			return this.tableName;
		}
		BaseModel.Table table = getClass().getAnnotation(BaseModel.Table.class);
		this.tableName = table.tableName();
		return this.tableName;
	}

	/**
	 * 获取恢复处理的方式
	 * @return
	 */
	private String	handlNumOverMode;

	public String getHandlNumOverMode()
	{
		return this.handlNumOverMode;
	}

	public void setHandlNumOverMode(String handlNumOverMode)
	{
		this.handlNumOverMode = handlNumOverMode;
	}

	private final Map<String,Object>	updateMap	= new HashMap<String,Object>();

	public Map<String,Object> getUpdateMap()
	{
		return updateMap;
	}

	protected final void setUpdateValue(String field, Object value)
	{
		this.updateMap.put(field, value);
	}

	private String	primaryKeys;

	/**
	 * 通过注解获取模型对应表结构的主键
	 * @return
	 */
	public String getPrimaryFields()
	{
		if (primaryKeys != null && primaryKeys.length() > 0)
		{
			return primaryKeys;
		}
		BaseModel.Table table = getClass().getAnnotation(BaseModel.Table.class);
		String primaryKeys = table.primaryKeys();
		return primaryKeys;
	}

	protected String trimDescInfo(String str)
	{
		return StrUtils.getSubValue(str, "UTF-8", 4000);
	}
}
