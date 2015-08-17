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
import java.util.Scanner;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Hema
 */
@ManagedBean
@SessionScoped
public class Student implements Serializable{

    /**
     * Creates a new instance of Student
     */
      //attributes
    private int std_id;
    private String std_name;
    private String std_psw;
    private ArrayList<courses> crs;

//constructor
    public Student(String name) {
        std_name = name;
    }

    public Student(int id, String name, String psw) {
        std_id = id;
        std_name = name;
        std_psw = psw;
        crs = new ArrayList<courses>();

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
            result = statement.executeQuery("select * from registration where std_id = " + std_id);

            //dispaly registered courses
            while (result.next()) {
                allCrs.add(result.getInt(2));
            }
            //displaying registered courses details
            for (int i : allCrs) {
                result = statement.executeQuery("select * from courses where crs_id = " + i);
                if (result.next()) {
                    crs.add(new courses(result.getInt(1), result.getString(2), result.getInt(3), result.getString(4), result.getInt(5), result.getInt(6), result.getString(7), result.getDouble(8)));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

   /* public void welcome() {
        Scanner input = new Scanner(System.in);
        String option = "";

        while (!option.equals("5")) {
            System.out.println("\n*** Welcome, " + std_name + ". Please select one of the following options ***\n");
            System.out.println("1: Register a course");
            System.out.println("2: Show class schedule");
            System.out.println("3: Drop a course");
            System.out.println("4: View the bill");
            System.out.println("5: Sign out");

            option = input.next();

            if (option.equals("1")) {
                register();
            } else if (option.equals("2")) {
                viewSchedule();
            } else if (option.equals("3")) {
                dropClass();
            } else if (option.equals("4")) {
                viewBill();
            }
        }

    }

    public void register() {
        
        String selection = "";
        int id;

        //creating variables
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //connec to database
            connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
            statement = connection.createStatement();

            while (!selection.equals("3")) {
                System.out.println("1: Display all open courses");
                System.out.println("2: Search for a course by course ID");
                System.out.println("3: Exit the registration");
                selection = input.next();
                //displaying open courses
                if (selection.equals("1")) {
                    resultSet = statement.executeQuery("select * from courses where crs_status = 'Open'");
                    while (resultSet.next()) {

                        //System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2));
                    }

                    registerCourse();

                } else if (selection.equals("2")) {
                    registerCourse();

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                // resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void registerCourse() {
        
        String answer = "";
        int id=0;

        //get course id from user
       System.out.println("Enter the course ID");
        id = input.nextInt();

        //creating variables
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //connecting to database
            connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from courses where crs_id = " + id);

            if (resultSet.next()) {

                courses c = new courses(id, resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6), resultSet.getString(7), resultSet.getInt(8));
                //query to get faculty name
                resultSet = statement.executeQuery("select fac_name from faculty where fac_id = " + c.getFac_id());
                //displaying course datails
                if (resultSet.next()) {
                    //System.out.println("\n" + c.getCrs_id() + "\t" + c.getCrs_title() + "\n" + "Instructor: " + resultSet.getString(1) + "\n" + "Time: " + c.getCrs_time() + "\n" + "Status: " + c.getCrs_status() + "\n" + "Number of Seats: " + c.getCrs_capacity() + "\n" + "Number of Students enrolled: " + c.getCrs_students() + "\n" + "Course Fee: " + c.getCrs_fees() + "\n");
                }
                //asking to register
                if (c.getCrs_status().equalsIgnoreCase("open")) {
                    //System.out.println("Would you like to register the course? y or n");
                    //answer = input.next();

                    if (answer.equals("y")) {
                        //check if the student has registered the course already
                        resultSet = statement.executeQuery("select * from registration where std_id = " + std_id + " and crs_id = " + c.getCrs_id());
                        if (resultSet.next()) {
                            System.out.println("You have registered this class already!");
                        } else {
                            //update course table
                            statement.executeUpdate("update courses set crs_students=crs_students+1 where crs_id = " + c.getCrs_id());
                            
                            //insert records into registration table
                            statement.executeUpdate("insert into registration values (" + std_id + ", " + c.getCrs_id() + ")");
                            //updating arrayList
                            crs.add(c);
                            //updating the status of class
                            if (c.getCrs_capacity() == c.getCrs_students() + 1) {
                                statement.executeUpdate("update courses set crs_status = 'Closed' where crs_id = " + c.getCrs_id());
                            }
                            System.out.println("*** You have registered the class! ***");
                        }
                    }
                } else {
                    System.out.println("*** The class is closed! ***");
                }
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
    }*/

    public void viewSchedule() {

        Scanner input = new Scanner(System.in);
        String selection = "";
        for (int i = 0; i < crs.size(); i++) {
            System.out.printf("%d" + ". " + crs.get(i).getCrs_title() + "\t" + crs.get(i).getCrs_time() + "\n", i + 1);
        }
        System.out.println("x: Go back to main menu");
        System.out.println("\nSelect the course to view its details");
       // selection = input.next();

        //access database
        final String db = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

        //create variables
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;

        try {
            //connect to the database
            connection = DriverManager.getConnection(db, "lawatih3593", "1213902");
            statement = connection.createStatement();

            if (!selection.equals("x")) {
                int intSelection = Integer.parseInt(selection) - 1;
                //getting faculty name
                result = statement.executeQuery("select fac_name from faculty where fac_id = " + crs.get(intSelection).getFac_id());
                //displaying details of course
                if (result.next()) {
                    System.out.println("\n" + crs.get(intSelection).getCrs_id() + "\t" + crs.get(intSelection).getCrs_title() + "\n" + "Instructor: " + result.getString(1) + "\n" + "Time: " + crs.get(intSelection).getCrs_time() + "\n" + "Status: " + crs.get(intSelection).getCrs_status() + "\n" + "Number of Seats: " + crs.get(intSelection).getCrs_capacity() + "\n" + "Number of Students enrolled: " + crs.get(intSelection).getCrs_students() + "\n" + "Course Fee: " + crs.get(intSelection).getCrs_fees() + "\n");

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //method to drop class
    public void dropClass() {
        Scanner input = new Scanner(System.in);
        String selection = "";
        for (int i = 0; i < crs.size(); i++) {
            System.out.printf("%d" + ". " + crs.get(i).getCrs_title() + "\t" + crs.get(i).getCrs_time() + "\n", i + 1);
        }
        System.out.println("x: Go back to main menu");
        System.out.println("\nSelect the course to drop");
        selection = input.next();

        //access database
        final String db = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

        //create variables
        Connection connection = null;
        Statement statement = null;

        try {
            //connect to the database
            connection = DriverManager.getConnection(db, "lawatih3593", "1213902");
            statement = connection.createStatement();

            if (!selection.equals("x")) {
                int intSelection = Integer.parseInt(selection) - 1;

                //delete record from registration table
                statement.executeUpdate("delete from registration where crs_id = " + crs.get(intSelection).getCrs_id());
                //update the status and number of students in class after course deletion
                statement.executeUpdate("update courses set crs_status = 'Open' where crs_id = " + crs.get(intSelection).getCrs_id());
                statement.executeUpdate("update courses set crs_students = crs_students - 1 where crs_id = " + crs.get(intSelection).getCrs_id());
                //updating array
                crs.remove(intSelection);

            }
            System.out.println("*** The course has been dropped! ***");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //method to view the bill
    public void viewBill() {
        //variable
        double total = 0;

        //calculating tuition
        for (int i = 0; i < crs.size(); i++) {
            total = total + crs.get(i).getCrs_fees();
        }

        //displaying total tuition fees
        System.out.printf("Current total tuition and fees due: $%.2f", total);
        System.out.println();
    }

//get and set methods
    public String getStd_psw() {
        return std_psw;
    }

    public void setStd_psw(String std_psw) {
        this.std_psw = std_psw;
    }

    public int getStd_id() {
        return std_id;
    }

    public void setStd_id(int std_id) {
        this.std_id = std_id;
    }

    public String getStd_name() {
        return std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }

}
    
