package cn.snsoft.mq.model;

import cn.snsoft.base.data.Comp1Interface;

/**
 * <p>标题：报文扫描配置定义  </p>
 * <p>功能： </p>
 * <p>所属模块： MQ</p>
 * <p>版权： Copyright © 2015 SNSOFT</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2015年9月7日 下午2:26:25</p>
 * <p>类全名：cn.snsoft.component.mq.model.MqScanConfMatch</p>
 * 作者：宋建中
 * 初审：
 * 复审：
 * 监听使用界面:
 * @version 8.0
 */
@BaseModel.Table(tableName = "mq_scan_conf_match", cache = "SQL-MqScanConfMatch.[mq_scan_conf_match]|MqScanConfMatch")
public class MqScanConfMatch extends BaseModel implements Comp1Interface
{
	private String	scmid;
	private String	scid;
	private String	filematch;
	private String	projtype;
	private String	syscode;
	private String	syscode_mutual;

	public String getScid()
	{
		return scid;
	}

	@Column
	public void setScid(String scid)
	{
		this.scid = scid;
	}

	public String getFilematch()
	{
		return filematch;
	}

	@Column
	public void setFilematch(String filematch)
	{
		this.filematch = filematch;
	}

	public String getProjtype()
	{
		return projtype;
	}

	@Column
	public void setProjtype(String projtype)
	{
		this.projtype = projtype;
	}

	public String getSyscode()
	{
		return syscode;
	}

	@Column
	public void setSyscode(String syscode)
	{
		this.syscode = syscode;
	}

	public String getSyscode_mutual()
	{
		return syscode_mutual;
	}

	@Column
	public void setSyscode_mutual(String syscode_mutual)
	{
		this.syscode_mutual = syscode_mutual;
	}

	@Override
	public Object getComp1Key()
	{
		return getScid();
	}

	public String getScmid()
	{
		return scmid;
	}

	@Column
	public void setScmid(String scmid)
	{
		this.scmid = scmid;
	}
}
