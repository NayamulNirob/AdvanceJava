
package utill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DbUtill {
  private static  Connection connection=null;
  private static String url="jdbc:mysql://localhost:3307/craud";
  private static String user="root";
  private static String password="";
  private static String driver="com.mysql.cj.jdbc.Driver";
  
  public Connection getCon(){
  
      try {
          Class.forName(driver);
          connection=DriverManager.getConnection(url, user, password);
      } catch (ClassNotFoundException | SQLException ex) {
          Logger.getLogger(DbUtill.class.getName()).log(Level.SEVERE, null, ex);
      }
      return connection;
      
  }
}
