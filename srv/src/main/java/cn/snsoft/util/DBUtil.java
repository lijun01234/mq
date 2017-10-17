package cn.snsoft.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import cn.snsoft.db.DataSource;
import cn.snsoft.db.WorkSpace;
public class DBUtil
{
	public static void main(String[] args)
	{
		//WorkSpace workSpace = WorkSpace.getWorkSpace("CLOUD_STANDARD");
		try
		{
			Connection conn = getDatabase("CLOUD_STANDARD", "MQ");
			System.out.println(conn.getMetaData());
			String[][] tables = getTables(conn);
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * jdbc:oracle:thin:@//10.77.4.1:1521/orcl?user=config_silin9_dev&password=config_silin9_dev
	 */
	public static Connection getDatabase(String workSpaceId, String dataSourceId)
	{
		WorkSpace workSpace = WorkSpace.getWorkSpace("CLOUD_STANDARD");
		Map<String,DataSource> dataSource = workSpace.getDataSources();
		try
		{
			String url = getUrl(dataSource.get(dataSourceId));//"jdbc:mysql://127.0.0.1/student";
			String name = "com.mysql.jdbc.Driver";
			//String name = "oracle.jdbc.OracleDriver";
			String user = "root";
			String password = "root";
			Class.forName(name);//指定连接类型  
			Connection conn = DriverManager.getConnection(url, user, password);//获取连接  
			return conn;
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static String getUrl(DataSource ds)
	{
		//String url = "jdbc:oracle:thin:@//" + ds.getHost() + ":" + ds.getPort() + "/" + ds.getDatabase();
		//?user=" + ds.getUser() + "&password=" + ds.getPassword() + "";
		String url = "jdbc:mysql://127.0.0.1/world";
		return url;
	}

	public PreparedStatement getPreparedStatement(Connection conn, String sql)
	{
		PreparedStatement pst;
		try
		{
			//准备执行语句  
			pst = conn.prepareStatement(sql);
			return pst;
		} catch (SQLException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	//	public void close()
	//	{
	//		try
	//		{
	//			this.conn.close();
	//			this.pst.close();
	//		} catch (SQLException e)
	//		{
	//			e.printStackTrace();
	//		}
	//	}
	public static boolean isTableExists(Connection conn, String tableName)
	{
		if (tableName == null)
			return false;
		Object tbls[][] = getTables(conn);
		if (tbls == null)
			return false;
		for (int i = 0; i < tbls.length; i++)
		{
			if (tableName.equalsIgnoreCase((String) tbls[i][2]))
				return true;
		}
		return false;
	}

	public static String[][] getTables(Connection conn)
	{
		DatabaseMetaData dbMetaData;
		ResultSet rs = null;
		try
		{
			dbMetaData = conn.getMetaData();
			final String types[] = new String[] { "TABLE" };
			final String schemaPattern = getSchema(conn); //"";//CYW;
			rs = dbMetaData.getTables(null, schemaPattern, null, types);
			final java.util.List<String[]> v = new ArrayList<String[]>();
			for (; rs.next();)
			{
				String e[] = new String[5];
				for (int j = 0; j < 5; j++)
					e[j] = rs.getString(j + 1);
				v.add(e);
			}
			String tables[][] = (String[][]) v.toArray(new String[v.size()][]);
			return tables;
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		} finally
		{
			try
			{
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public static final String getSchema(Connection con)
	{
		java.sql.DatabaseMetaData metaData;
		try
		{
			metaData = con.getMetaData();
			String name = metaData.getUserName();
			if (name != null)
			{
				try
				{
					if (metaData.storesUpperCaseIdentifiers())
						name = name.toUpperCase();
				} catch (Exception ex)
				{
				}
				return name;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
