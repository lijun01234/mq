package cn.snsoft.mq.model;

/**
 * <p>标题：工程下任务对象 不缓存</p>
 * <p>功能： </p>
 * <p>所属模块： MQ</p>
 * <p>版权： Copyright © 2013 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2013年11月14日 下午5:55:29</p>
 * <p>类全名：snsoft.api.mq.model.MQTask</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
@BaseModel.Table(tableName = "mq_task", primaryKeys = "taskid")
public class MQTask extends MQRunModel
{
	private String	taskid;		//TID
	private String	projid;		//PID
	private String	projcode;		//工程编号
	private String	taskcode;		//任务编号
	private String	syscode_input;	//输入方 
	private String	inputservid;	//输入服务配置ID   
	private String	syscode_output; //输出方
	private String	outputservid;	//输出服务配置ID
	private String	syscode_return; //回调方
	private String	returnservid;	//回调服务配置ID	
	private int		stepnum;		//步骤 用于排序,当一个工程下有多个任务的时候,可能需要按先后顺序依次执行
	private String	param01;		//短参数32/01
	private String	param02;		//短参数32/02
	private String	param03;		//短参数32/03
	private String	param21;		//中参数64/01
	private String	param22;		//中参数64/02
	private String	param41;		//长参数128/01
	private String	param51;		//超长参数512/01
	private String	excresult;		//执行结果	
	private String	excresultdesc;	//执行结果描述		
	private long	exctime_input;	//输入运行时长
	private long	exctime_output; //输出运行时长
	private int		discardflag;	//废弃标识

	public String getTaskid()
	{
		return taskid;
	}

	@Column
	public void setTaskid(String taskid)
	{
		this.taskid = taskid;
		setUpdateValue("taskid", this.taskid);
	}

	public String getProjid()
	{
		return projid;
	}

	@Column
	public void setProjid(String projid)
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

	public String getTaskcode()
	{
		return taskcode;
	}

	@Column
	public void setTaskcode(String taskcode)
	{
		this.taskcode = taskcode;
		setUpdateValue("taskcode", this.taskcode);
	}

	public String getSyscode_input()
	{
		return syscode_input;
	}

	@Column
	public void setSyscode_input(String syscode_input)
	{
		this.syscode_input = syscode_input;
		setUpdateValue("syscode_input", this.syscode_input);
	}

	public String getSyscode_output()
	{
		return syscode_output;
	}

	@Column
	public void setSyscode_output(String syscode_output)
	{
		this.syscode_output = syscode_output;
		setUpdateValue("syscode_output", this.syscode_output);
	}

	public String getInputservid()
	{
		return inputservid;
	}

	@Column
	public void setInputservid(String inputservid)
	{
		this.inputservid = inputservid;
		setUpdateValue("inputservid", this.inputservid);
	}

	public String getOutputservid()
	{
		return outputservid;
	}

	@Column
	public void setOutputservid(String outputservid)
	{
		this.outputservid = outputservid;
		setUpdateValue("outputservid", this.outputservid);
	}

	public String getSyscode_return()
	{
		return syscode_return;
	}

	@Column
	public void setSyscode_return(String syscode_return)
	{
		this.syscode_return = syscode_return;
		setUpdateValue("syscode_return", this.syscode_return);
	}

	public String getReturnservid()
	{
		return returnservid;
	}

	@Column
	public void setReturnservid(String returnservid)
	{
		this.returnservid = returnservid;
		setUpdateValue("returnservid", this.returnservid);
	}

	public long getExctime_input()
	{
		return exctime_input;
	}

	@Column
	public void setExctime_input(long exctime_input)
	{
		this.exctime_input = exctime_input;
		setUpdateValue("exctime_input", this.exctime_input);
	}

	public long getExctime_output()
	{
		return exctime_output;
	}

	@Column
	public void setExctime_output(long exctime_output)
	{
		this.exctime_output = exctime_output;
		setUpdateValue("exctime_output", this.exctime_output);
	}

	public int getStepnum()
	{
		return stepnum;
	}

	@Column
	public void setStepnum(int stepnum)
	{
		this.stepnum = stepnum;
		setUpdateValue("stepnum", this.stepnum);
	}

	public String getParam01()
	{
		return param01;
	}

	@Column
	public void setParam01(String param01)
	{
		this.param01 = param01;
		setUpdateValue("param01", param01);
	}

	public String getParam02()
	{
		return param02;
	}

	@Column
	public void setParam02(String param02)
	{
		this.param02 = param02;
		setUpdateValue("param02", param02);
	}

	public String getParam03()
	{
		return param03;
	}

	@Column
	public void setParam03(String param03)
	{
		this.param03 = param03;
		setUpdateValue("param03", param03);
	}

	public String getParam21()
	{
		return param21;
	}

	@Column
	public void setParam21(String param21)
	{
		this.param21 = param21;
		setUpdateValue("param21", param21);
	}

	public String getParam22()
	{
		return param22;
	}

	@Column
	public void setParam22(String param22)
	{
		this.param22 = param22;
		setUpdateValue("param22", param22);
	}

	public String getParam41()
	{
		return param41;
	}

	@Column
	public void setParam41(String param41)
	{
		this.param41 = param41;
		setUpdateValue("param41", param41);
	}

	public String getParam51()
	{
		return param51;
	}

	@Column
	public void setParam51(String param51)
	{
		this.param51 = param51;
		setUpdateValue("param51", param51);
	}

	public String getExcresult()
	{
		return excresult;
	}

	@Column
	public void setExcresult(String excresult)
	{
		this.excresult = excresult;
		setUpdateValue("excresult", this.excresult);
	}

	public String getExcresultdesc()
	{
		return excresultdesc;
	}

	@Column
	public void setExcresultdesc(String excresultdesc)
	{
		this.excresultdesc = trimDescInfo(excresultdesc);
		setUpdateValue("excresultdesc", this.excresultdesc);
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
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((taskid == null) ? 0 : taskid.hashCode());
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
		MQTask other = (MQTask) obj;
		if (taskid == null)
		{
			if (other.taskid != null)
			{
				return false;
			}
		} else if (!taskid.equals(other.taskid))
		{
			return false;
		}
		return true;
	}

	@Override
	public String getPrimaryValues()
	{
		return this.getTaskid();
	}

	@Override
	public String getMessageStr()
	{
		return null;
	}

	@Override
	public String getQueueLockKey()
	{
		return null;
	}
}
