
package newconnectiontest;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utill.DbUtill;


public class NewConnectionTest {

    static DbUtill db=new DbUtill();
    static String sql;
    static PreparedStatement ps;
    static ResultSet rs;
    
    
    public static void main(String[] args) {
        saveData("Shawon", "shawon@gmail.com", "khulna", "5346874446");
        deleteData(2);
        editData(3, "Nirob", "nirobkgm@gmail.com", "Kurigram", "35854153");
        showData();
    }
    
    public static void saveData(String name,String email,String address,String cell){
    
        sql="insert into list(name,email,address,cell)values(?,?,?,?)";
        try {
            ps=db.getCon().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, address);
            ps.setString(4, cell);
            
            ps.executeUpdate();
            ps.close();
            db.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(NewConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    public static void showData(){
    
    sql="select* from list";
        try {
            ps=db.getCon().prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {         
               int id= rs.getInt("sl");
               String name =  rs.getString("name");
                String email= rs.getString("email");
                String address= rs.getString("address");
                String cell= rs.getString("cell");
                
                System.out.println("Sl is: "+id+"\tName is: "+name+"\tEmail is: "+email+"\tAddress is: "+address+"\t Contact number: "+cell);
            }
            ps.close();
            db.getCon().close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(NewConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    public static void deleteData(int sl){
    
    sql="delete from list where sl=?";
        try {
            ps=db.getCon().prepareStatement(sql);
            ps.setInt(1, sl);
            
            ps.executeUpdate();
            ps.close();
            db.getCon().close();
            
        } catch (SQLException ex) {
            Logger.getLogger(NewConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
     public static void editData(int sl,String name,String email, String address,String cell){
     sql="update list set name=?,email=?, address=?,cell=? where sl=?";
        try {
            ps=db.getCon().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, address);
            ps.setString(4,cell);
            ps.setInt(5, sl);
            
            ps.executeUpdate();
            ps.close();
            db.getCon().close();
            
        } catch (SQLException ex) {
            Logger.getLogger(NewConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     
     }
}
