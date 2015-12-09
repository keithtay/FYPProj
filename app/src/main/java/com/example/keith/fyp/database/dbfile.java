package com.example.keith.fyp.database;

import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Keith on 4/12/2015.
 */
public class dbfile {
    String driver = "net.sourceforge.jtds.jdbc.Driver";
    String connString = "jdbc:jtds:sqlserver://PALM.arvixe.com:1433/dementiafypdb;encrypt=false;user=fyp2015;password=va5a7eve;instance=SQLEXPRESS;";
    String username = "fyp2015";
    String password = "va5a7eve";
    int id;
    Calendar cal = Calendar.getInstance();
    java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());


    public int getPatientId(String nric){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();
            ResultSet reset = stmt.executeQuery("select * from patient where nric='" + nric + "'");

            while (reset.next()) {
                id = reset.getInt("patientID");
            }
            conn.close();

            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;

    }
    public void insertNewPatient(String firstname,String lastname, String nric, String address, String officeno, String handphoneno, char gender, String date, String gname, String gcontactno, String gemail){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);

            String sql = "INSERT INTO patient " +
                    "VALUES ('" + firstname + "','" + lastname + "','" + nric + "','" + address + "','" + officeno + "','" + handphoneno + "','" + gender + "','" + date + "','" + gname + "','" + gcontactno + "','" + gemail + "'," + 0 + "," + 0 + ",'" + timestamp + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);


            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertPatientSpec(String info, int patientId, int specValue){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);

            if (specValue == 1){//allergy
                 String sql = "INSERT INTO patientSpecInfo " +
                        "VALUES ('" + info + "'," + patientId + "," + 1 + "," + 0 + "," + 0 + ",'"+ timestamp + "')";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                conn.close();
            }else if(specValue == 2){//vital
                String sql = "INSERT INTO patientSpecInfo " +
                        "VALUES ('" + info + "'," + patientId + "," + 2 + "," + 0 + "," + 0 + ",'"+ timestamp + "')";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                conn.close();
            }else if(specValue == 3){
                String sql = "INSERT INTO patientSpecInfo " +
                        "VALUES ('" + info + "'," + patientId + "," + 3 + "," + 0 + "," + 0 + ",'"+ timestamp + "')";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                conn.close();
            }else if(specValue == 4){
                String sql = "INSERT INTO patientSpecInfo " +
                        "VALUES ('" + info + "'," + patientId + "," + 4 + "," + 0 + "," + 0 + ",'"+ timestamp + "')";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                conn.close();
            }else if(specValue == 5){
                String sql = "INSERT INTO patientSpecInfo " +
                        "VALUES ('" + info + "'," + patientId + "," + 5 + "," + 0 + "," + 0 + ",'"+ timestamp + "')";
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                conn.close();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testInsert(){
                if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {

            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            String sql = "INSERT INTO userType " +
                    "VALUES ('Supervisior', 1, '2015-12-04 00:00:00.0')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);


            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> testingConnection(){
        ArrayList<String> listofCategory= new ArrayList<String>();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();
            ResultSet reset = stmt.executeQuery("select * from userType");

            while (reset.next()) {
                listofCategory.add(reset.getString("userTypeID"));
                listofCategory.add(reset.getString("userTypeName"));
                listofCategory.add(reset.getString("isDeleted"));
                listofCategory.add(reset.getString("createDateTime"));
            }
            conn.close();
            return listofCategory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listofCategory;
}

}