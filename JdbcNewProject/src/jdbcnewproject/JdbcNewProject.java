
package jdbcnewproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcNewProject {

   private Connection connection =null;
   private String url="jdbc:mysql://localhost:3306/world";
   private String user ="root";
   private String password ="1234";
   private String driver ="com.mysql.cj.jdbc.Driver";
   
    public Connection getCon() throws ClassNotFoundException, SQLException{
  
  Class.forName(driver);
       Connection con = DriverManager.getConnection(url, user, password);
  
  return con;
  
  }
  
  
  
}
   
