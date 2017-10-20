package cn.snsoft.db;

import java.sql.Connection;
public class TestMultiPool
{
	public static void main(String[] args)
	{
		//       DBConnection mysql = new DBConnection("mysql", "com.mysql.jdbc.Driver", "jdbc:mysql://192.168.229.123:3306/cpbs", "root", "yanfei");
		//       DBConnection oracle = new DBConnection("oracle", "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@//192.168.229.235:1521/ORCL_LG", "DBDC01", "DBDC01");
		//DBConnection oracle = new DBConnection("oracle", "192.168.229.235", "1521", "ORCL_LG", "DBDC01", "DBDC01");
		//DBConnection hive2 = new DBConnection("hive2", "192.168.229.124", "10000", "default", "hadoop", "hadoop");
		//conns.add(oracle);
		//conns.add(hive2);
		Connection conn = Database.getMySqlConnection();
		System.err.println(conn);
	}
}
