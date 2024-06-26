
package dbcroud2;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utill.Dbutill;

public class DbCroud2 {
    
static Dbutill db =new Dbutill();
static PreparedStatement ps;
static ResultSet rs;
static String sql;



    public static void main(String[] args) {
        
        
        addData("Nirjash", "nirjush@gamil.com", "Framget", "5651564665");
        deleteData(7);
        System.out.println("After delete");
        updateData(2, "Uopol", "upol@gmail.com", "Dhaka", "5866464684");
        System.out.println("After update");
        showData();
    }
    
    public static void addData(String name,String email,String address,String cell){
    
    sql="insert into student(name,email,address,cell)"
            + "values(?,?,?,?)";
    
    try {
        ps=db.getcon().prepareStatement(sql);
        
        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, address);
        ps.setString(4, cell);
        
        ps.executeUpdate();
        ps.close();
        db.getcon().close();
        
    } catch (SQLException ex) {
        Logger.getLogger(DbCroud2.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    
    }
    
    public static void showData() {

        sql = "select* from student";

        try {
            ps = db.getcon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String cell = rs.getString("cell");
                String id = rs.getString("id");

                System.out.println("Student Id: " + id + " Name : " + name + " Email Address: " + email + " Address :" + address + " Contact: " + cell);
            }
            ps.close();
            rs.close();
            db.getcon().close();
        } catch (SQLException ex) {
            Logger.getLogger(DbCroud2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public static void deleteData(int id) {

        sql = "delete from student where id=?";

        try {
            
            ps = db.getcon().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            db.getcon().close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DbCroud2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void updateData(int id,String name, String email, String address,String cell){
        sql="update student set name=?,email=?,address=?,cell=? where id=?";
        try {
            ps=db.getcon().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, address);
            ps.setString(4, cell);
            ps.setInt(5, id);
            ps.executeUpdate();
            
            ps.close();
            db.getcon().close();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DbCroud2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
