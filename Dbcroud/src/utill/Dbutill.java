
package utill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dbutill {
    
    private Connection connection =null;
    
    private String url="jdbc:mysql://localhost:3307/dbcroud";
    
    private String user= "root";
    
    private String password="";
    
    private String driver="com.mysql.cj.jdbc.Driver";
    
    public Connection getCon(){
    
        try {
            Class.forName(driver);
            connection =DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Dbutill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    
    
    }
}
