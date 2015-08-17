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
public class index implements Serializable {

    /**
     * Creates a new instance of index
     */
    //attributes
    courses c;
    String stuMsg;
    String facMsg;
    int crs_id = 0;
    Integer logID = null;
    private int loginID = 0;
    private String psw = "";
    Integer courseID = null;
    private Student std_login;
    private courses displayCourse;
    private Faculty fac_login;
    ArrayList<Integer> allCrs = new ArrayList<>();
    ArrayList<Student> stu_name = new ArrayList<>();
    ArrayList<courses> open_crs = new ArrayList<>();
    ArrayList<courses> fac_course = new ArrayList<>();
    ArrayList<courses> registered_crs = new ArrayList<>();
    
    public courses getDisplayCourse() {
        return displayCourse;
    }

    //get and set methids
    public void setDisplayCourse(courses displayCourse) {    
        this.displayCourse = displayCourse;
    }

    public ArrayList<Student> getStu_name() {
        return stu_name;
    }

    public void setStd_name(ArrayList<Student> stu_name) {
        this.stu_name = stu_name;
    }

    public ArrayList<courses> getFac_course() {
        return fac_course;
    }

    public void setFac_course(ArrayList<courses> fac_course) {
        this.fac_course = fac_course;
    }

    public String getFacMsg() {
        return facMsg;
    }

    public void setFacMsg(String facMsg) {
        this.facMsg = facMsg;
    }

    public String getStuMsg() {
        return stuMsg;
    }

    public void setStuMsg(String stuMsg) {
        this.stuMsg = stuMsg;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public ArrayList<Integer> getAllCrs() {
        return allCrs;
    }

    public void setAllCrs(ArrayList<Integer> allCrs) {
        this.allCrs = allCrs;
    }

    public Integer getLogID() {
        return logID;
    }

    public void setLogID(Integer logID) {
        this.logID = logID;
    }

    public courses getC() {
        return c;
    }

    public void setC(courses c) {
        this.c = c;
    }

    public int getCrs_id() {
        return crs_id;
    }

    public void setCrs_id(int crs_id) {
        this.crs_id = crs_id;
    }

    public int getLoginID() {
        return loginID;
    }

    public void setLoginID(int loginID) {
        this.loginID = loginID;
    }

    public Student getStd_login() {
        return std_login;
    }

    public void setStd_login(Student std_login) {
        this.std_login = std_login;
    }

    public Faculty getFac_login() {
        return fac_login;
    }

    public void setFac_login(Faculty fac_login) {
        this.fac_login = fac_login;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getErrorMsg() {
        return stuMsg;
    }

    public void setErrorMsg(String stuMsg) {
        this.stuMsg = stuMsg;
    }

    public ArrayList<courses> getOpen_crs() {
        return open_crs;
    }

    public void setOpen_crs(ArrayList<courses> open_crs) {
        this.open_crs = open_crs;
    }

    public ArrayList<courses> getRegistered_crs() {
        return registered_crs;
    }

    public void setRegistered_crs(ArrayList<courses> registered_crs) {
        this.registered_crs = registered_crs;
    }

    //student login
    public String stdLogin() {

        loginID = logID;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //variable
        boolean login = false;

        while (login == false) {

            //access database
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

            //variables
            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;

            try {

                //connecting to database
                connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
                statement = connection.createStatement();
                rs = statement.executeQuery("Select * from students where std_id = " + loginID);

                //find id
                if (rs.next()) {
                    //check password
                    if (psw.equals(rs.getString(3))) {
                        //password is good
                        login = true;
                        //adding to arraylist
                        std_login = new Student(loginID, rs.getString(2), psw);
                        //display welcome page
                        return "std_welcome";

                    } else {

                        stuMsg = "Your password is incorrect! Enter password again.";
                        return "std_login_error";
                    }
                } else {

                    stuMsg = "User ID is incorrect! Enter User ID again.";
                    return "std_login_error";
                }

            } catch (SQLException e) {
                e.printStackTrace();

                stuMsg = "Internal Error 100";
                return "std_login_error";

            } finally {
                try {
                    connection.close();
                    statement.close();
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();

                    stuMsg = "Internal Error 200";
                    return "std_login_error";
                }
            }
        }
        stuMsg = "Internal Error 200";
        return "std_login_error";
    }

    //initialize open courses courses
    public String register() {

        open_crs.clear();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

        //variable
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            //connect to database
            connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from courses where crs_status = 'Open'");

            //adding open courses to array list
            while (rs.next()) {
                open_crs.add(new courses(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "open_course.xhtml";

    }

    //course registration
    public String registerCourse() {
        crs_id = courseID;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from courses where crs_id = " + crs_id);

            if (rs.next()) {

                c = new courses(crs_id, rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8));

                //asking to register
                if (c.getCrs_status().equalsIgnoreCase("open")) {

                    //check if the student has registered the course already
                    rs = statement.executeQuery("select * from registration where std_id = " + loginID + " and crs_id = " + c.getCrs_id());
                    if (rs.next()) {

                        stuMsg = "You have registered this class already!";
                        return "stu_message";

                    } else {

                        //update course table
                        statement.executeUpdate("update courses set crs_students = crs_students+1 where crs_id = " + c.getCrs_id());

                        //insert records into registration table
                        statement.executeUpdate("insert into registration values (" + loginID + ", " + c.getCrs_id() + ")");

                        //updating arrayList
                        open_crs.add(c);

                        //updating the status of class
                        if (c.getCrs_capacity() == c.getCrs_students() + 1) {
                            statement.executeUpdate("update courses set crs_status = 'Closed' where crs_id = " + c.getCrs_id());
                        }

                        stuMsg = "You have registered the class";
                        return "successful";
                    }

                } else {

                    stuMsg = "The class is closed!";
                    return "stu_message";
                }
            }

            stuMsg = "Internal Error 100";
            return "stu_message";

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        stuMsg = "Internal Error 200";
        return "stu_message";

    }

    //searching course by ID
    public String searchCourse() {

        crs_id = courseID;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";
        //variables
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            //connect to database
            connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from courses where crs_id = " + crs_id);

            //adding items to course type to display course details
            if (rs.next()) {
                c = new courses(crs_id, rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8));
            }
            return "search_course";

        } catch (Exception e) {
            e.printStackTrace();

            stuMsg = "Internal Error 100";
            return "stu_message";

        } finally {
            try {
                connection.close();
                statement.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //view Schedule
    public String viewSchedule() {

        allCrs.clear();
        registered_crs.clear();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

        //variables
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;

        try {
            //connect to database
            connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
            statement = connection.createStatement();
            result = statement.executeQuery("select * from registration where std_id = " + loginID);

            //dispaly registered courses
            while (result.next()) {
                allCrs.add(result.getInt(2));
            }

            //displaying registered courses details
            for (int i : allCrs) {
                result = statement.executeQuery("select * from courses where crs_id = " + i);
                if (result.next()) {
                    registered_crs.add(new courses(result.getInt(1), result.getString(2), result.getInt(3), result.getString(4), result.getInt(5), result.getInt(6), result.getString(7), result.getDouble(8)));
                }
            }
            return "std_schedule";

        } catch (Exception e) {
            e.printStackTrace();

            stuMsg = "Internal Error 100";
            return "stu_message";

        } finally {
            try {
                connection.close();
                statement.close();
                result.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //schedule detail
    public String detailSchedule() {

        crs_id = courseID;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

        //variables
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            //connect to database
            connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from courses where crs_id = " + crs_id);

            //adding items to course type
            if (rs.next()) {
                c = new courses(crs_id, rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getInt(8));
            }
            return "std_detail_schedule";

        } catch (Exception e) {
            e.printStackTrace();

            stuMsg = "Internal Error 100";
            return "stu_message";

        } finally {
            try {
                connection.close();
                statement.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //method to drop class
    public String dropClass() {

        loginID = logID;
        crs_id = courseID;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String db = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

        //create variables
        Connection connection = null;
        Statement statement = null;

        try {
            //connect to the database
            connection = DriverManager.getConnection(db, "lawatih3593", "1213902");
            statement = connection.createStatement();

            //delete record from registration table
            statement.executeUpdate("delete from registration where crs_id = " + crs_id + " and std_id = " + loginID);
            //update the status and number of students in class after course deletion
            statement.executeUpdate("update courses set crs_status = 'Open' where crs_id = " + crs_id);
            statement.executeUpdate("update courses set crs_students = crs_students - 1 where crs_id = " + crs_id);

            //updating array
            int index = 0;
            for (int i = 0; i < registered_crs.size(); i++) {
                if (crs_id == registered_crs.get(i).getCrs_id()) {
                    index = i;
                }
            }
            registered_crs.remove(index);

            stuMsg = "The course has been dropped!";
            return "stu_message";

        } catch (SQLException e) {
            e.printStackTrace();

            stuMsg = "Error in deletion. Try Again!";
            return "stu_message";

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
    public String viewBill() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //variable
        double total = 0;

        //calculating tuition fees
        for (int i = 0; i < registered_crs.size(); i++) {
            total = total + registered_crs.get(i).getCrs_fees();
        }

        stuMsg = "Current total tuition and fees due: $" + total;
        return "stu_message";

    }

    //faculty login
    public String facLogin() {

        fac_course.clear();
        loginID = logID;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //variables
        boolean login = false;

        while (login == false) {
            //access database
            final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

            //variables
            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;

            try {

                //connecting to database
                connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
                statement = connection.createStatement();
                rs = statement.executeQuery("Select * from faculty where fac_id = " + loginID);

                //find id
                if (rs.next()) {
                    //check password
                    if (psw.equals(rs.getString(3))) {
                        //password is good
                        login = true;
                        fac_login = new Faculty(loginID, rs.getString(2), psw);

                        //calling method to display faculty schedule
                        facSchedule();

                        //display welcome page
                        return "fac_welcome";

                    } else {

                        //password incorrect
                        facMsg = "Your password is incorrect! Enter password again.";
                        return "fac_login_error";
                    }
                } else {

                    //ID incorrect
                    facMsg = "User ID is incorrect! Enter User ID again.";
                    return "fac_login_error";
                }

            } catch (SQLException e) {
                e.printStackTrace();

                facMsg = "Internal Error 100";
                return "fac_login_error";
            } finally {
                try {
                    connection.close();
                    statement.close();
                    rs.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        facMsg = "Internal Error 200";
        return "fac_login_error";

    }

    //displaying faculty schedule
    public void facSchedule() {

        loginID = logID;
        //crs_id = courseID;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //access database
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";

        //variables
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;

        try {

            //connecting to database
            connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
            statement = connection.createStatement();
            result = statement.executeQuery("Select * from courses where fac_id = " + loginID);

            //dispaly registered courses
            while (result.next()) {

                fac_course.add(new courses(result.getInt(1), result.getString(2), result.getInt(3), result.getString(4), result.getInt(5), result.getInt(6), result.getString(7), result.getDouble(8)));
            }

        } catch (SQLException e) {
            e.printStackTrace();

            //facMsg = "Internal Error 100";
            //return "fac_login_error";
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

    public String facCourseDetail() {
        stu_name.clear();
        crs_id = courseID;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/lawatih3593";
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        //
        for(courses c:fac_course){
            if(c.getCrs_id()==crs_id){
                displayCourse=c;
            }
        }
        
        try {
            connection = DriverManager.getConnection(DATABASE_URL, "lawatih3593", "1213902");
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from students s, registration r where s.std_id = r.std_id and r.crs_id = " + crs_id);

            while (rs.next()) {
                stu_name.add(new Student(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            return "fac_detail_schedule";
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        facMsg="Error";
        return "fac_login_error";
    }
}
