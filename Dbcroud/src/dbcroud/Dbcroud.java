package dbcroud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utill.Dbutill;

public class Dbcroud {

    public static Dbutill db = new Dbutill();
    static PreparedStatement ps;
    static ResultSet rs;
    static String sql;

    public static void main(String[] args) {

        saveEmployee("Nirob", "nirobkgm2gmail.com", "Mohammodpur", "5654564");
        System.out.println("After insert");
        deleteData(2);
        System.out.println("After delete");
        upadateData(1, "Nirjash", "nirjash@gamil.com", "framget", "5865254546");
        showEmployee();
        
    }

    public static void saveEmployee(String name, String email, String address, String cell) {

        sql = "insert into employee(name,email,address,cell)"
                + "values(?,?,?,?)";

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, address);
            ps.setString(4, cell);

            ps.executeUpdate();
            ps.close();
            db.getCon().close();

        } catch (SQLException ex) {
            Logger.getLogger(Dbcroud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void showEmployee() {

        sql = "select* from employee";

        try {
            ps = db.getCon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String cell = rs.getString("cell");
                String empid = rs.getString("empid");

                System.out.println("Employee Id: " + empid + " Name : " + name + " Email Address: " + email + " Address :" + address + " Contact: " + cell);
            }
            ps.close();
            rs.close();
            db.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(Dbcroud.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void deleteData(int empid) {

        sql = "delete from employee where empid=?";

        try {
            
            ps = db.getCon().prepareStatement(sql);
            ps.setInt(1, empid);
            ps.executeUpdate();
            ps.close();
            db.getCon().close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Dbcroud.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void upadateData(int empid,String name, String email, String address,String cell){
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
            Logger.getLogger(Dbcroud.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
