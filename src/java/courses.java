/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Hema
 */
@ManagedBean
@SessionScoped
public class courses implements Serializable {

    /**
     * Creates a new instance of courses
     */
    //attributes
    private int crs_id;
    private String crs_title;
    private int fac_id;
    private String crs_time;
    private String fac_name;

    public String getFac_name() {
        return fac_name;
    }

    public void setFac_name(String fac_name) {
        this.fac_name = fac_name;
    }

    public int getCrs_id() {
        return crs_id;
    }

    public void setCrs_id(int crs_id) {
        this.crs_id = crs_id;
    }

    public String getCrs_title() {
        return crs_title;
    }

    public void setCrs_title(String crs_title) {
        this.crs_title = crs_title;
    }

    public int getFac_id() {
        return fac_id;
    }

    public void setFac_id(int fac_id) {
        this.fac_id = fac_id;
    }

    public String getCrs_time() {
        return crs_time;
    }

    public void setCrs_time(String crs_time) {
        this.crs_time = crs_time;
    }

    public String getCrs_status() {
        return crs_status;
    }

    public void setCrs_status(String crs_status) {
        this.crs_status = crs_status;
    }

    public int getCrs_capacity() {
        return crs_capacity;
    }

    public void setCrs_capacity(int crs_capacity) {
        this.crs_capacity = crs_capacity;
    }

    public int getCrs_students() {
        return crs_students;
    }

    public void setCrs_students(int crs_students) {
        this.crs_students = crs_students;
    }

    public double getCrs_fees() {
        return crs_fees;
    }

    public void setCrs_fees(double crs_fees) {
        this.crs_fees = crs_fees;
    }
    private String crs_status;
    private int crs_capacity;
    private int crs_students;
    private double crs_fees;

    //constructor
    public courses(int id, String title, int fac_id, String crs_time, int crs_capacity, int enrolledStd, String status, double crs_fees) {
        crs_id = id;
        crs_title = title;
        crs_status = status;
        this.crs_capacity = crs_capacity;
        crs_students = enrolledStd;
        this.crs_time = crs_time;
        this.fac_id = fac_id;
        this.crs_fees = crs_fees;

        facultyName();

    }

    public void facultyName() {
        //creating variables
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //connecting to database
            connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
            statement = connection.createStatement();

            //query to get faculty name
            resultSet = statement.executeQuery("select fac_name from faculty where fac_id = " + fac_id);
            //displaying course datails
            if (resultSet.next()) {
                //System.out.println("\n" + c.getCrs_id() + "\t" + c.getCrs_title() + "\n" + "Instructor: " + resultSet.getString(1) + "\n" + "Time: " + c.getCrs_time() + "\n" + "Status: " + c.getCrs_status() + "\n" + "Number of Seats: " + c.getCrs_capacity() + "\n" + "Number of Students enrolled: " + c.getCrs_students() + "\n" + "Course Fee: " + c.getCrs_fees() + "\n");
                fac_name = resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
