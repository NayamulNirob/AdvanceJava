
package testconnectionxam;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utill.DbUtill;


public class TestConnectionXam {

   static DbUtill db= new DbUtill();
   static String sql;
   static PreparedStatement ps;
   static ResultSet rs;
   
    public static void main(String[] args) {
        saveData("Hasan", "hasan@gmail.com","chadpur" , "5446545156");
        deleteData(2);
        editData(3, "Raju", "raju@gmail.com", "Azimpur", "6586846412");
        showData();
    }
    public static void saveData(String name, String email, String address,String cell){
    
    sql="insert into student (name,email,address,cell)values(?,?,?,?)";
    
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
           Logger.getLogger(TestConnectionXam.class.getName()).log(Level.SEVERE, null, ex);
       }
    
    }
    
    public static void showData(){
    sql="select* from student";
       try {
           ps=db.getCon().prepareStatement(sql);
           rs=ps.executeQuery();
           while (rs.next()) {               
               int id=rs.getInt("id");
               String name=rs.getString("name");
               String email=rs.getString("email");
               String address=rs.getString("address");
               String cell=rs.getString("cell");
               System.out.println("Id : "+id+"\t Name: "+name+" \tEmail: "+email+"\t Address: "+address+"\t Cell: "+cell);
           }
       } catch (SQLException ex) {
           Logger.getLogger(TestConnectionXam.class.getName()).log(Level.SEVERE, null, ex);
       }
    
    
    
    }
    
     public static void deleteData(int id){
     
     sql="delete from student where id=?";
       try {
           ps=db.getCon().prepareStatement(sql);
           ps.setInt(1, id);
           
           ps.executeUpdate();
           ps.close();
           db.getCon().close();
       } catch (SQLException ex) {
           Logger.getLogger(TestConnectionXam.class.getName()).log(Level.SEVERE, null, ex);
       }
     
     
     }
     
     public static void editData(int id, String name,String email, String address,String cell){
     sql="update student set name=?,email=?,address=?,cell=? where id=?";
     
       try {
           ps=db.getCon().prepareStatement(sql);
           ps.setString(1, name);
           ps.setString(2, email);
           ps.setString(3, address);
           ps.setString(4, cell);
           ps.setInt(5, id);
           ps.executeUpdate();
           ps.close();
           db.getCon().close();
       } catch (SQLException ex) {
           Logger.getLogger(TestConnectionXam.class.getName()).log(Level.SEVERE, null, ex);
       }
     
     }
}
