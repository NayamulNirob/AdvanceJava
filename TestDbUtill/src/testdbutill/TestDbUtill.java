package testdbutill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utill.DbUtill;

public class TestDbUtill {

    static PreparedStatement ps;
    static ResultSet rs;
    static DbUtill db = new DbUtill();
    static String sql;

    public static void main(String[] args) {
        saveData("Nirjash", "nirjash@gmail.com", "Framget", "021358963241");
        deleteData(2);
        editData("Raju", "raju@gmail.com", "Azimpur", "0246514711", 3);
        showData();
    }

    public static void saveData(String name,String email,String address,String cell) {

        sql="insert into employee(name,email,address,cell)"
                + "values(?,?,?,?)";
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
            Logger.getLogger(TestDbUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void showData(){
    sql="select* from employee";
        try {
            ps=db.getCon().prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {                
             
            int empid = rs.getInt("empid");
            String name=rs.getString("name");
            String email= rs.getString("email");
            String address= rs.getString("address");
            String cell= rs.getString("cell");
                System.out.println("Id is: " + empid + " Name : "+name+ " Email: "+email+ "Address : "+address+" Cell: "+cell);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestDbUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void deleteData(int empid){
    sql="delete from employee where empid=?";
        try {
            ps=db.getCon().prepareStatement(sql);
            
             ps.setInt(1, empid);
             ps.executeUpdate();
             ps.close();
             db.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(TestDbUtill.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void editData(String name,String email,String address, String cell,int empid){
    sql="update employee set name=?,email=?,address=?,cell=? where empid=?";
    
        try {
            ps=db.getCon().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, address);
            ps.setString(4, cell);
            ps.setInt(5, empid);
            
            ps.executeUpdate();
            ps.close();
            db.getCon().close();
            
        } catch (SQLException ex) {
            Logger.getLogger(TestDbUtill.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
