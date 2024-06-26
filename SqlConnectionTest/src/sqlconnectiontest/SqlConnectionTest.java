package sqlconnectiontest;

import entity.Employee;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utill.DbUtill;

public class SqlConnectionTest {

    static PreparedStatement ps;
    static ResultSet rs;
    static DbUtill db = new DbUtill();
    

    public static void main(String[] args) {

       
       List<Employee>employees=getById(1);
//            Employee employee=new Employee();
        Employee employee = new Employee(4, "Rizvi", "rizvihamed@gmail.com", "Ajimpur,Dhaka", "544655214166");
        
//        employee.setName(employees.get(0).getName());
//        employee.setEmail("neyamulislam@gmail.com");
//        employee.setAddress(employees.get(0).getAddress());
//        employee.setCell(employees.get(0).getCell());
//        employee.setId(employees.get(0).getId());

        editdata(employee);
        deleteData(0);
//        saveData();
         showdata();
    }

    public static void saveData() {

        try {

            String insertSql = "insert into employee (name,email,address,cell)"
                    + "values(?,?,?,?)";

            ps = db.getCon().prepareStatement(insertSql);
            ps.setString(1, "Nirjash");
            ps.setString(2, "nirjash@gmail.com");
            ps.setString(3, "Framget,Dhaka");
            ps.setString(4, "324646546455");

            ps.executeUpdate();
            ps.close();
            db.getCon().close();

        } catch (SQLException ex) {
            Logger.getLogger(SqlConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void showdata() {

        String selectSql = "select * from employee";
        try {
            ps = db.getCon().prepareStatement(selectSql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String cell = rs.getString("cell");

                System.out.println("Id is :" + id + " Name is:" + name + " Email is: " + email
                        + " Address is: " + address + " Cell is :" + cell);
            }
            ps.close();
            rs.close();
            db.getCon().close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void editdata(Employee e){
    
    String editSql="update employee set name=?,email=?,address=?,cell=? where id=?";
    
        try {
            ps=db.getCon().prepareStatement(editSql);
            
            ps.setString(1, "Rizvi");
            ps.setString(2, e.getEmail());
            ps.setString(3, e.getAddress());
            ps.setString(4, e.getCell());
            ps.setInt(5, e.getId());
            
            ps.executeUpdate();
            ps.close();
            db.getCon().close();
            System.out.println("Edited successfull");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }
    public static List<Employee>getById(int id){
    
        List<Employee>es=new ArrayList<>();
        
        
        String sql="select * from employee where id=?";
        try {
            ps=db.getCon().prepareStatement(sql);
            ps.setInt(1, id);
            rs=ps.executeQuery();
            
            while (rs.next()) {                
                Employee  e =new Employee(
                        rs.getInt("id"), 
                        rs.getString("name"), 
                        rs.getString("email"), 
                        rs.getString("address"), 
                        rs.getString("cell"));
                es.add(e);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return es;
        
    
    
    }
    
    public static void deleteData(int id) {
        String sql = "delete from employee where id=?";
        
        try {
            ps=db.getCon().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            db.getCon().close();
            
        } catch (SQLException ex) {
            Logger.getLogger(SqlConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

}
