package cn.snsoft.db;

import java.util.ArrayList;
import java.util.List;
public class TestMultiPool
{
	public static void main(String[] args)
	{
		//       DBConnection mysql = new DBConnection("mysql", "com.mysql.jdbc.Driver", "jdbc:mysql://192.168.229.123:3306/cpbs", "root", "yanfei");
		//       DBConnection oracle = new DBConnection("oracle", "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@//192.168.229.235:1521/ORCL_LG", "DBDC01", "DBDC01");
		DBConnection mysql = new DBConnection("mysql", "127.0.0.1", "3306", "world", "root", "root");
		//DBConnection oracle = new DBConnection("oracle", "192.168.229.235", "1521", "ORCL_LG", "DBDC01", "DBDC01");
		//DBConnection hive2 = new DBConnection("hive2", "192.168.229.124", "10000", "default", "hadoop", "hadoop");
		List<DBConnection> conns = new ArrayList<DBConnection>();
		conns.add(mysql);
		//conns.add(oracle);
		//conns.add(hive2);
		ConnectionManager manager = ConnectionManager.getInstance();
		manager.init(conns);
		System.out.println("init complete");
		//MultiPool pool = MultiPool.getInstance();
	}
}
