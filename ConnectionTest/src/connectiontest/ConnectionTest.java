
package connectiontest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utill.DbUtill;


public class ConnectionTest {
static PreparedStatement ps;
static ResultSet rs;
    public static void main(String[] args) {
    try {
        DbUtill db =new DbUtill();      
        try {
            
            
            String insertSql ="insert into student(name,email,address,cell)"
                    + "values(?,?,?,?)";
            
            ps=db.getCon().prepareStatement(insertSql);
            
            ps.setString(1, "Raju");
            ps.setString(2, "raju@gmail.com");
            ps.setString(3, "Ajimpur,Dhaka");
            ps.setString(4, "354554615454");
            
            ps.executeUpdate();
            ps.close();
            db.getCon().close();
            
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String selectSql ="select* from student";
        
        ps=db.getCon().prepareStatement(selectSql);
        rs=ps.executeQuery();
        while (rs.next()) {            
            int id=rs.getInt("id");
            String name =rs.getString("name");
            String email =rs.getString("email");
            String address =rs.getString("address");
            String cell =rs.getString("cell");
            
            System.out.println("Id is: "+id+" name :"+name+"Email: "+email+" Address: "+ address 
                    +"Cell"+cell);
        }ps.close();
        rs.close();
        db.getCon().close();
        
        
        
    } catch (SQLException ex) {
        Logger.getLogger(ConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
    
    }
    
}
