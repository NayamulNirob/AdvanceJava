
package utill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DbUtill {
    static Connection con =null;
    static String url="jdbc:mysql://localhost:3307/dbcroud";
    static String user ="root";
    static String password="";
    static String  driver="com.mysql.cj.jdbc.Driver";
    
    
    public Connection getCon(){
    
        try {
            Class.forName(driver);
            con=DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DbUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    
    
    }
}
