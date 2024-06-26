package jdbcnewproject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainProject {

    static PreparedStatement ps;
    static ResultSet rs;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        JdbcNewProject db = new JdbcNewProject();

        ps = db.getCon().prepareStatement("select* from city");
        rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("ID");
            String name = rs.getString("Name");
            String country = rs.getString("CountryCode");
            String district = rs.getString("District");
            int population = rs.getInt("Population");

            System.out.println("Id is : " + id + " Name is : " + name + " Country : " + country + " District : " + district + " Population : " + population);

        }

        ps.close();
        rs.close();
        db.getCon().close();
    }
}
