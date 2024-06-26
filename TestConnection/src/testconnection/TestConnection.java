package testconnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utill.DbUtill;

public class TestConnection {

    static DbUtill db = new DbUtill();
    static String sql;
    static PreparedStatement ps;
    static ResultSet rs;

    public static void main(String[] args) {
        saveData("Nirjash", "nirjash@gmail.com", "Framget", "565824555");
        editdata(2, "Raju", "raju@gmail.com", "Panthophot", "558146584");
        deleteData(3);
        showdata();
    }

    public static void saveData(String name,String email, String address, String cell){
    
    sql="insert into data(name,email,address,cell)values(?,?,?,?)";
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
            Logger.getLogger(TestConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    public static void showdata(){
    
    sql="select* from data";
        try {
            ps=db.getCon().prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {                
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String email=rs.getString("email");
                String address=rs.getString("address");
                String cell=rs.getString("cell");
                
                System.out.println("id:"+id+"\tName: "+name+"\tEmail:"+email+"\tAddress:"+address+"\tCell:"+cell);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }
    public static void editdata(int id,String name,String email,String address,String cell){
    sql="update data set name=?,email=?,address=?,cell=? where id=?";
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
            Logger.getLogger(TestConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }
    public static void deleteData(int id){
    
    sql="delete from data where id=?";
        try {
            ps=db.getCon().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            db.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(TestConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
}
