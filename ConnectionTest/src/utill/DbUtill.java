
package utill;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbUtill {
    
    private Connection con=null;
    
    private String url ="jdbc:mysql://localhost:3306/dbcrud";
    
    private String user ="root";
    
    private String password ="2020";
    
    private String driver ="com.mysql.cj.jdbc.Driver";
    
    public Connection getCon(){
    
        try {
            Class.forName(driver);
            con=DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
    
       
    
    
     return con;
    }
}
