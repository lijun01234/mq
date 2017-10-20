package cn.snsoft.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
public class Database
{
	public static Connection getMySqlConnection()
	{
		DBConnection mysql = new DBConnection("mysql", "127.0.0.1", "3306", "world", "root", "root");
		List<DBConnection> conns = new ArrayList<DBConnection>();
		conns.add(mysql);
		ConnectionManager manager = ConnectionManager.getInstance();
		manager.init(conns);
		Connection conn = manager.getConnection("mysql");
		return conn;
	}
}
