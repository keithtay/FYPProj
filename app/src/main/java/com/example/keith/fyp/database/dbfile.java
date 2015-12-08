package com.example.keith.fyp.database;

import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Keith on 4/12/2015.
 */
public class dbfile {
    String driver = "net.sourceforge.jtds.jdbc.Driver";
    String connString = "jdbc:jtds:sqlserver://PALM.arvixe.com:1433/dementiafypdb;encrypt=false;user=fyp2015;password=va5a7eve;instance=SQLEXPRESS;";
    String username = "fyp2015";
    String password = "va5a7eve";

    public void connectionSettings(){

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
