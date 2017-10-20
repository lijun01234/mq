package cn.snsoft.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ConnectionManager
{

    private static ConnectionManager instance = null;//单例模式
    private static MultiPool pool = MultiPool.getInstance();
    
    /***
     * 获取实例
     * @return
     */
    public static synchronized ConnectionManager getInstance(){
        if(instance == null){
            instance = new ConnectionManager();
        }
        return instance;
    }
    
    /**
     * 获取连接
     * @param dbType 数据库类型
     * @return
     */
    public static Connection getConnection(String dbType){
        return pool.get(dbType);
    }
    
    public static void closeConnection(String dbType, Connection conn){
        pool.release(dbType, conn);
    }
    
    /***
     * 初始化连接池
     * @param conns
     */
    public void init(List<DBConnection> conns){
        if(pool.isAlive()){
            System.out.println("connection pool has been init");
            return;
        }
        
        for(DBConnection db:conns){
            for(int i=0;i<db.getInitialSize();i++){
                String dbType = db.getDbType();
                Connection conn = connection(db.getDriverClassName(), db.getUrl(), db.getUsername(), db.getPassword());
                pool.add(dbType, conn);
            }
        }
        System.out.println("complete");
    }
    
    /**
     * 摧毁连接池
     */
    public void destroy(){
        pool.close();
    }
    
    /**
     * 连接数据库
     * @param driverClass
     * @param url
     * @param username
     * @param password
     * @return
     */
    private Connection connection(String driverClass, String url, String username, String password){
        
        try {
            Class.forName(driverClass);
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("connect success");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e1){
            e1.printStackTrace();
            return null;
        }
    }
}
