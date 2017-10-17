package cn.snsoft.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import cn.snsoft.util.XMLUtil;
/**
 * <p>标题： </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2017</p>
 * <p>公司: 北京南北天地科技股份有限公司</p>
 * <p>创建日期：2017年10月16日 上午11:14:54</p>
 * <p>类全名：cn.snsoft.db.WorkSpace</p>
 * 
 * 作者：
 * @version 1.0
 */
public class WorkSpace
{
	private String					id;
	private String					title;
	private Map<String,DataSource>	dataSources	= new HashMap();

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Map<String,DataSource> getDataSources()
	{
		return dataSources;
	}

	public void addDataSources(DataSource dataSources)
	{
		this.dataSources.put(dataSources.getId(), dataSources);
	}

	private static Map<String,WorkSpace>	workSpaces	= new HashMap();

	public static synchronized WorkSpace getWorkSpace(String id)
	{
		return getWorkSpace(id, false);
	}

	public static synchronized WorkSpace getWorkSpace(String id, boolean forceLoad)
	{
		java.io.InputStream is = null;
		try
		{
			is = WorkSpace.class.getResourceAsStream("../../../config/WorkSpace.xml");
			if (is == null)
			{
				String s = "未定义账套文件!";
				throw new RuntimeException(s);
			}
			final Document doc = XMLUtil.parse(is);
			final Element rootE = doc.getDocumentElement();
			for (final Element wsE : XMLUtil.iteratorChileElements(rootE))
			{
				WorkSpace workSpace = parseWorkspace(wsE);
				workSpaces.put(workSpace.getId(), workSpace);
			}
		} catch (Throwable ex)
		{
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally
		{
			if (is != null)
				try
				{
					is.close();
				} catch (Throwable ex)
				{
					throw new RuntimeException(ex);
				}
		}
		return workSpaces.get(id);
	}

	private static WorkSpace parseWorkspace(final Element wsE)
	{
		WorkSpace workSpace = new WorkSpace();
		String id = wsE.getAttribute("id"); // 帐套 ID
		workSpace.setId(id);
		String title = wsE.getAttribute("title");
		workSpace.setTitle(title);
		final List<Element> dsElememts = XMLUtil.getChildElements(wsE);
		for (final Element e : dsElememts)
		{
			String d_id = e.getAttribute("id");
			String d_host = e.getAttribute("host");
			String d_port = e.getAttribute("port");
			String d_database = e.getAttribute("database");
			String d_user = e.getAttribute("user");
			String d_password = e.getAttribute("password");
			String d_type = e.getAttribute("type");
			DataSource dataSource = new DataSource(d_id, d_host, d_port, d_database, d_user, d_password, d_type);
			workSpace.addDataSources(dataSource);
		}
		final org.w3c.dom.NamedNodeMap attrs = wsE.getAttributes();
		//添加其它属性
		return workSpace;
	}
}
