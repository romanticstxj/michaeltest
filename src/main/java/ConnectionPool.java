import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***********************************************************************************************************************
 * FileName - ConnectionPool.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: tengx009 $ 
 * $Revision: #1 $ 
 * $Change: 715510 $ 
 * $Date: May 26, 2019 $
 **********************************************************************************************************************/

public class ConnectionPool {  
    // 连接池配置属性  
    private boolean isActive = false; // 连接池活动状态  
    private int contActive = 0;// 记录创建的总的连接数  
      
    // 空闲连接  
    private List<Connection> freeConnection = new Vector<Connection>();  
    // 活动连接  
    private List<Connection> activeConnection = new Vector<Connection>();  
  
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>(); 

    public ConnectionPool() {  
        super();  
//        this.dbBean = dbBean;  
        init();  
    }  
  
    public static void main(String[] args) {
        ConnectionPool pool = new ConnectionPool();
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<3;i++) {
            
            es.submit(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
//                    Connection conn = pool.getCurrentConnecton();
//                    Connection conn1 = pool.getCurrentConnecton();
//                    System.out.println(conn == conn1);
                    
                    Connection conn;
                	for(int i=0;i<10;i++) {
                	    conn = pool.getCurrentConnecton();
                	    try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                	    System.out.println(i + " connection is " + conn);
                	}
                }
                
            });
        }
    }
    // 初始化  
    public void init() {  
        try {  
//            Class.forName(dbBean.getDriverName());  
            for (int i = 0; i < 10; i++) {  
                Connection conn;  
                conn = newConnection();  
                // 初始化最小连接数  
                if (conn != null) {  
                    freeConnection.add(conn);  
                    contActive++;  
                }  
            }  
            isActive = true;  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
      
    // 获得当前连接  
    public Connection getCurrentConnecton(){  
        // 默认线程里面取  
        System.out.println(Thread.currentThread().getName());
        Connection conn = threadLocal.get();  
        System.out.println(conn);
        if(!isValid(conn)){  
            conn = getConnection();  
        }  
        return conn;  
    }  
  
    // 获得连接  
    public synchronized Connection getConnection() {  
        Connection conn = null;  
        try {  
            // 判断是否超过最大连接数限制  
            if(contActive < 15){  
                if (freeConnection.size() > 0) {  
                    conn = freeConnection.get(0);  
                    if (conn != null) {  
                        threadLocal.set(conn);  
                    }  
                    freeConnection.remove(0);  
                } else {  
                    conn = newConnection();  
                }  
                  
            }else{  
                // 继续获得连接,直到从新获得连接  
                wait(10000);  
                conn = getConnection();  
            }  
            if (isValid(conn)) {  
                activeConnection.add(conn);  
                contActive ++;  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        return conn;  
    }  
  
    // 获得新连接  
    private synchronized Connection newConnection()  
            throws ClassNotFoundException, SQLException {  
        Connection conn = new Connection();  
        return conn;  
    }  
  
    // 释放连接  
    public synchronized void releaseConn(Connection conn) throws SQLException {  
        if (isValid(conn)&& !(freeConnection.size() > 15)) {  
            freeConnection.add(conn);  
            activeConnection.remove(conn);  
            contActive --;  
            threadLocal.remove();  
            // 唤醒所有正待等待的线程，去抢连接              notifyAll();  
        }  
    }  
  
    // 判断连接是否可用  
    private boolean isValid(Connection conn) {  
            if (conn == null || conn.isClosed()) {  
                return false;  
            }  
        return true;  
    }  
  
    // 销毁连接池  
    public synchronized void destroy() {  
        for (Connection conn : freeConnection) {  
                if (isValid(conn)) {  
                    conn.close();  
                }  
        }  
        for (Connection conn : activeConnection) {  
                if (isValid(conn)) {  
                    conn.close(); 
                }
        }  
        isActive = false;  
        contActive = 0;  
    }  
  
    // 连接池状态      @Override  
    public boolean isActive() {  
        return isActive;  
    }  
      
} 
