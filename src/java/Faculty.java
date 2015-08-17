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
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Hema
 */
@ManagedBean
@RequestScoped
public class Faculty implements Serializable {

    /**
     * Creates a new instance of Faculty
     */
    //attributes
    private int fac_id;
    private String fac_name;
    private String psw;
    private ArrayList<courses> course;
    private ArrayList<Student> stds;

    //constructor
    public Faculty(int id, String name, String password) {
        fac_id = id;
        fac_name = name;
        psw = password;
        course = new ArrayList<courses>();
        stds = new ArrayList<Student>();

    }

    //get and set methods
    public int getFac_id() {
        return fac_id;
    }

    public void setFac_id(int fac_id) {
        this.fac_id = fac_id;
    }

    public String getFac_name() {
        return fac_name;
    }

    public void setFac_name(String fac_name) {
        this.fac_name = fac_name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String welcome() {

        
        String strSelection = "";

        final String db = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

        //create variables
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        ArrayList<Integer> allCrs = new ArrayList<Integer>();

        try {
            //connect to the database
            connection = DriverManager.getConnection(db, "lawatih3593", "1213902");
            statement = connection.createStatement();
            result = statement.executeQuery("select * from courses where fac_id = " + fac_id);

            //dispaly registered courses
            while (result.next()) {
                allCrs.add(result.getInt(1));
                course.add(new courses(result.getInt(1), result.getString(2), result.getInt(3), result.getString(4), result.getInt(5), result.getInt(6), result.getString(7), result.getDouble(8)));

            }
            //boolean option = true;
            // while (option == true) {
            while (!strSelection.equalsIgnoreCase("x")) {
                int j = 0;
                System.out.println();
                //displaying registered courses details
                for (int i : allCrs) {
                    result = statement.executeQuery("select * from courses where crs_id = " + i);
                    if (result.next()) {
                        j++;
                        //System.out.printf("%d. " + i + "\t" + result.getString(2) + "\t" + result.getString(4) + "\n", j);

                    }
                }

                //view course details
               /* System.out.println("Select a course to view its details: ");
                 System.out.println("x: Sign out");
                 strSelection = input.nextLine();*/
                if (isInteger(strSelection)) {

                   // System.out.println("\n" + course.get(Integer.parseInt(strSelection) - 1).getCrs_id() + "\t" + course.get(Integer.parseInt(strSelection) - 1).getCrs_title() + "\n" + "Instructor: " + fac_name + "\n" + "Time: " + course.get(Integer.parseInt(strSelection) - 1).getCrs_time() + "\n" + "Status: " + course.get(Integer.parseInt(strSelection) - 1).getCrs_status() + "\n" + "Number of Seats: " + course.get(Integer.parseInt(strSelection) - 1).getCrs_capacity() + "\n" + "Number of Students enrolled: " + course.get(Integer.parseInt(strSelection) - 1).getCrs_students() + "\n" + "Course Fee: " + course.get(Integer.parseInt(strSelection) - 1).getCrs_fees() + "\n");
                    //displaying students name
                    int i = 0;
                    if (course.get(Integer.parseInt(strSelection) - 1).getCrs_students() > 0) {
                        //System.out.println("Names of Students enrolled in class: ");

                        result = statement.executeQuery("select s.std_name from registration r, students s where r.std_id = s.std_id and r.crs_id = " + course.get(Integer.parseInt(strSelection) - 1).getCrs_id() + " group by std_name ");

                        while (result.next()) {

                            //adding student names to array list
                            stds.add(new Student(result.getString(1)));
                            //printing students names
                            //System.out.printf("%d. " + stds.get(i).getStd_name() + "\n", i + 1);
                            i++;

                        }
                    } else {
                        //System.out.println("There are no students enrolled at this time!\n");
                    }

                }
            }
            //System.out.println("Logging out");
            return "LogOut";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        } finally {
            try {
                connection.close();
                statement.close();
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean isInteger(String a) {
        try {
            //if a is not an integer, an exception will be thrown out
            int i = Integer.parseInt(a);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
