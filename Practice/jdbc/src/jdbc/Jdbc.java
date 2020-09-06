package jdbc;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @author Timo
 */
public class Jdbc {

    public static void main(String[] args) {
        ResourceBundle rb = ResourceBundle.getBundle("jdbc");
        String driver = rb.getString("driver");
        String url = rb.getString("url");
        String username = rb.getString("username");
        String password = rb.getString("password");

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1.注册驱动（反射：类加载时执行com.mysql.cj.jdbc.Driver这个类的静态代码块），告诉jvm要用哪种类型的数据库。
            Class.forName(driver);
            // 多态的方式：
            // Driver driver = new com.mysql.cj.jdbc.Driver();
            // DriverManager.registerDriver(driver);
            // 2.获取连接：建立jvm进程和mysql进程的连接，通信通道。
            connection = DriverManager.getConnection(url, username, password);
            // 3.获取数据库操作对象。
            statement = connection.createStatement();
            String sql = "select * from student;";
            // 4.执行sql语句。
            resultSet = statement.executeQuery(sql);
            // 5.处理结果集
            // executeUpdate()方法用于处理（DML语句），返回影响数据库中的记录条数。
            while(resultSet.next()) {
                System.out.println(resultSet.getString(1)
                        + "," + resultSet.getString(2)
                        + "," + resultSet.getString(3));
            }
        } catch(Exception e) {
            e.printStackTrace();
            // 6.finally后面释放资源
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
