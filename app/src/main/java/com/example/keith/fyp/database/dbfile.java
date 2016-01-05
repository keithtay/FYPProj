package com.example.keith.fyp.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.DefaultEvent;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.Prescription;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.models.Routine;
import com.example.keith.fyp.models.Schedule;
import com.example.keith.fyp.models.SocialHistory;
import com.example.keith.fyp.models.Vital;
import com.example.keith.fyp.utils.Global;
import com.example.keith.fyp.utils.UtilsUi;



import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;


import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Keith and KokSang on 4/12/2015.
 */
public class dbfile{
    String driver = "net.sourceforge.jtds.jdbc.Driver";
    String connString = "jdbc:jtds:sqlserver://PALM.arvixe.com:1433/dementiafypdb;encrypt=false;user=fyp2015;password=va5a7eve;instance=SQLEXPRESS;";
//    String connString = "jdbc:jtds:sqlserver://0.0.0.0:1433/dementiafypdb;encrypt=false;user=;password=;instance=SQLEXPRESS;";
//    String username = "";
//    String password = "";
    String username = "fyp2015";
    String password = "va5a7eve";
    int id;
    Calendar cal = Calendar.getInstance();
    java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
    ArrayList<Patient> patientList = new ArrayList<>();
    ArrayList<Patient> patientList1 = new ArrayList<>();
    ArrayList<DefaultEvent> defaultEvent = new ArrayList<>();

    public void insertNewPatientInfo(String oldData, String newData, String columnAffected, int patientId, int UserTypeID, int UserID){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            int checkIsSupervisor, checkIsSuper;
            if(UserTypeID == 3){
                checkIsSupervisor = 1;
            }else{
                checkIsSupervisor = 0;
            }
            if(checkIsSupervisor ==1){
                checkIsSuper = UserID;
            }else{
                checkIsSuper = 0;
            }
            String allData = oldData +";"+ newData;
            String logDesc = "New Update to Patient Particulars";
            String tableAffected = "patient";
            String sql1 = "INSERT INTO log " +
                    "VALUES ('" + allData + "','" + logDesc + "'," + 4 + "," + patientId + "," + UserID + ",'" + checkIsSuper + "','" + columnAffected + "'," + null + ",'" + tableAffected + "','" + columnAffected + "'," + null + "," + checkIsSupervisor + ",'" + timestamp + "')";
            Statement stmt1 = conn.createStatement();
            stmt1.executeUpdate(sql1);

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateRejectionNewPatientInfo(int logid,int UserID, String reason ){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE log SET isDeleted=1, userIDApproved=" + UserID + ", remarks='" + reason + "' WHERE logID=" + logid);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRejectionNotificationTables(int logid, int rowid, String tablename,int UserID, String reason ){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();
            if (tablename.equals("patient")){
                stmt.executeUpdate("UPDATE log SET isDeleted=1, userIDApproved=" + UserID + ", remarks='" + reason + "' WHERE logID=" + logid);
                stmt.executeUpdate("UPDATE patient SET isApproved=0, isDeleted=1 WHERE patientID=" + rowid);
                stmt.executeUpdate("UPDATE patientAllocation SET isApproved=0, isDeleted=1 WHERE patientID=" + rowid);
            }else if(tablename.equals("patientSpecInfo")){
                stmt.executeUpdate("UPDATE log SET isDeleted=1, userIDApproved=" + UserID + ", remarks='" + reason + "' WHERE logID=" + logid);
                stmt.executeUpdate("UPDATE patientSpecInfo SET isApproved=0, isDeleted=1 WHERE patientSpecInfoID=" + rowid);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateLogPatientInfo(int logid, int patientid, String newData, String columnname,int UserID ){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();
            Log.v("WHY NEVER READ", "W");
                stmt.executeUpdate("UPDATE log SET isDeleted=1, userIDApproved=" + UserID +" WHERE logID=" + logid);
                stmt.executeUpdate("UPDATE patient SET " + columnname + "='" + newData + "' WHERE patientID=" + patientid);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateNotificationTables(int logid, int rowid, String tablename,int UserID ){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();
            if (tablename.equals("patient")){
                stmt.executeUpdate("UPDATE log SET isDeleted=1, userIDApproved=" + UserID +" WHERE logID=" + logid);
                stmt.executeUpdate("UPDATE patient SET isApproved=1 WHERE patientID=" + rowid);
                stmt.executeUpdate("UPDATE patientAllocation SET isApproved=1 WHERE patientID=" + rowid);
            }else if(tablename.equals("patientSpecInfo")){
                stmt.executeUpdate("UPDATE log SET isDeleted=1, userIDApproved=" + UserID + " WHERE logID=" + logid);
                stmt.executeUpdate("UPDATE patientSpecInfo SET isApproved=1 WHERE patientSpecInfoID=" + rowid);
            }
             conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getLeastCareGiverID(){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();
            ResultSet reset = stmt.executeQuery("SELECT TOP 1 caregiverID, count(pa.caregiverID) as valueCount FROM [dementiafypdb].[dbo].[user] AS u\n" +
                    "INNER JOIN [dementiafypdb].[dbo].[patientAllocation] AS pa ON pa.caregiverID = u.userID\n" +
                    "WHERE u.userTypeID = 4 AND pa.isApproved=1 AND pa.isDeleted=0\n" +
                    "GROUP BY pa.caregiverID\n" +
                    "ORDER BY valueCount\n");

            while (reset.next()) {
                id = reset.getInt("caregiverID");
            }
            conn.close();

            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;

    }
    public ArrayList<Notification> prepareNotificationList(Context context){
        ArrayList<Notification> notificationList = new ArrayList<>();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();

            ResultSet reset = stmt.executeQuery("SELECT pa.firstName AS patientfirstname, pa.lastname AS patientlastname, pa.nric AS patientnric, lo.createDateTime as logdatetime," +
                    "lo.logDesc AS logDescription, lo.logCategoryID AS logcatid, us.firstName AS userfirstname, us.lastName AS userlastname," +
                    "lo.logID as logid, lo.logData AS logdata, lo.additionalInfo as additionalinfo, lo.tableAffected AS ta, lo.rowAffected AS ra, lo.patientID as patientsid " +
                    "  FROM [dementiafypdb].[dbo].[log] AS lo " +
                    "  INNER JOIN [dementiafypdb].[dbo].patient AS pa ON pa.patientID = lo.patientID" +
                    "  INNER JOIN [dementiafypdb].[dbo].[user] AS us ON us.userID = lo.userIDInit" +
                    "  WHERE lo.userIDApproved = 0 AND lo.isDeleted = 0 ");
            while(reset.next()){
                Bitmap patientAvatar1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_18);
                Patient patient1 = new Patient(reset.getString("patientfirstname"), reset.getString("patientlastname"), reset.getString("patientnric"), patientAvatar1);
                Bitmap caregiverAvatar1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_01);
                String date = reset.getString("logdatetime");
                int year = Integer.parseInt(date.substring(0, 4));
                int month = Integer.parseInt(date.substring(5, 7));
                int day = Integer.parseInt(date.substring(8, 10));
                int hour = Integer.parseInt(date.substring(11,13));
                int min = Integer.parseInt(date.substring(14,16));
                int sec = Integer.parseInt(date.substring(17, 19));
                DateTime date1 = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day).withHourOfDay(hour).withMinuteOfHour(min).withSecondOfMinute(sec);
                String caregiverName1 = reset.getString("userfirstname") + " " + reset.getString("userlastname");
                String summary1 = reset.getString("logDescription");
                int logid = reset.getInt("logid");
                String logData = reset.getString("logdata");
                String additionalinfo = reset.getString("additionalinfo");
                String ta = reset.getString("ta");
                int ra = reset.getInt("ra");
                int seewhichCat = reset.getInt("logcatid");
                int patientid = reset.getInt("patientsid");
                if(seewhichCat == 1){//type game recommendation
                    Notification notification1 = new Notification(date1, caregiverName1, caregiverAvatar1, summary1, patient1, Notification.STATUS_NONE, Notification.TYPE_GAME_RECOMMENDATION, logid,logData,additionalinfo, ta,ra, patientid);
                    notificationList.add(notification1);
                }else if(seewhichCat == 2){//typenewinfoobject
                    Notification notification1 = new Notification(date1, caregiverName1, caregiverAvatar1, summary1, patient1, Notification.STATUS_NONE, Notification.TYPE_NEW_INFO_OBJECT, logid,logData,additionalinfo, ta ,ra, patientid);
                    notificationList.add(notification1);
                }else if(seewhichCat == 3) {//typenewpatient
                    Notification notification1 = new Notification(date1, caregiverName1, caregiverAvatar1, summary1, patient1, Notification.STATUS_NONE, Notification.TYPE_NEW_PATIENT, logid,logData,additionalinfo, ta ,ra, patientid);
                    notificationList.add(notification1);
                }else if(seewhichCat == 4) {//typeupdateinfofield
                    Notification notification1 = new Notification(date1, caregiverName1, caregiverAvatar1, summary1, patient1, Notification.STATUS_NONE, Notification.TYPE_UPDATE_INFO_FIELD, logid,logData,additionalinfo, ta ,ra, patientid);
                    notificationList.add(notification1);
                }else if(seewhichCat == 5) {//typeupdateinfoobject
                    Notification notification1 = new Notification(date1, caregiverName1, caregiverAvatar1, summary1, patient1, Notification.STATUS_NONE, Notification.TYPE_UPDATE_INFO_OBJECT, logid,logData,additionalinfo, ta ,ra, patientid);
                    notificationList.add(notification1);
                }

            }

            conn.close();
            return notificationList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notificationList;
    }
    public ArrayList<Integer> checkUserExist(String username, String password){
        ArrayList<Integer> al = new ArrayList<Integer>();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();

            ResultSet reset = stmt.executeQuery("select * from [user] " +
                    " where firstName='" + username +"' AND password='" +  password + "' AND isApproved=1");
            while(reset.next()){
                al.add(reset.getInt("userID"));
                al.add(reset.getInt("userTypeID"));
                // Commit the edits!
            }

            conn.close();
            return al;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }
    public ArrayList<Integer> checkUserValid(int id, String username, String password){
        ArrayList<Integer> al = new ArrayList<Integer>();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();

            ResultSet reset = stmt.executeQuery("select * from [user] " +
                    " where userID="+  id + " AND firstName='" + username +"' AND password='" +  password + "' AND isApproved=1");
            while(reset.next()){
                al.add(reset.getInt("userID"));
                al.add(reset.getInt("userTypeID"));
                // Commit the edits!
            }

            conn.close();
            return al;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return al;
    }
    public void updateDefaultEvent(String scheduleTitle, String timeStart, String timeEnd, int interval, String scheduleDesc){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();

           stmt.executeUpdate("UPDATE schedule " +
                    " SET isDeleted=1 WHERE scheduleTitle='" + scheduleTitle + "' AND timeStart like '" + timeStart + "' AND timeEnd like '" + timeEnd
                    + "' AND interval=" + interval + " AND scheduleDesc='" + scheduleDesc + "' AND scheduleTypeID=1002 AND isDeleted=0 AND isApproved=1");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<DefaultEvent> retrieveAllDefaultEvent(){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();

            ResultSet reset = stmt.executeQuery("select * from schedule " +
                    " where scheduleTypeID=5 AND isApproved=1 AND isDeleted=0");
            while(reset.next()){
                String title=reset.getString("scheduleTitle");
                String timeStart=reset.getString("timeStart");
                String timeEnd = reset.getString("timeEnd");
                int hour = Integer.parseInt(timeStart.substring(0, 2));
                int min = Integer.parseInt(timeStart.substring(3, 5));
                int sec = Integer.parseInt(timeStart.substring(6, 8));
                int hour1 = Integer.parseInt(timeEnd.substring(0, 2));
                int min1 = Integer.parseInt(timeEnd.substring(3, 5));
                int sec1 = Integer.parseInt(timeEnd.substring(6, 8));
                DateTime test3 = DateTime.now().withHourOfDay(hour).withMinuteOfHour(min).withSecondOfMinute(sec);
                DateTime test4 = DateTime.now().withHourOfDay(hour1).withMinuteOfHour(min1).withSecondOfMinute(sec1);

                Integer interval =reset.getInt("interval");
                String scheduleDesc = reset.getString("scheduleDesc");
                DefaultEvent de = new DefaultEvent(title, test3,test4,interval,scheduleDesc,"Monday");
                defaultEvent.add(de);
            }

            conn.close();
            return defaultEvent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultEvent;
    }
    public void addnewSchedule(String scheduleTitle, DateTime timeStart, DateTime timeEnd, int interval, String scheduleDesc,int scheduleTypeID, int patientAllocationID){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Calendar cal = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            String dateNow = timestamp.toString().substring(0, 10);
            String sql = "INSERT INTO schedule " +
                    "VALUES ('" + scheduleTitle + "','" + timeStart + "','" + timeEnd + "','" + dateNow + "','" + dateNow + "'," + interval + ",'" + scheduleDesc + "'," + scheduleTypeID + "," + patientAllocationID + "," + 0 + "," + 1 + ",'" + timestamp + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addNewDefaultEvent(String scheduleTitle, DateTime timeStart, DateTime timeEnd, int interval, String scheduleDesc){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);

            String sql = "INSERT INTO schedule " +
                    "VALUES ('" + scheduleTitle + "','" + timeStart + "','" + timeEnd + "','" + "2010-12-15" + "','" + "2010-12-15" + "'," + interval + ",'" + scheduleDesc + "'," + 5 + "," + null + "," + 0 + "," + 1 + ",'" + timestamp + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Patient> getAllPatients(Context context) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();
//            ResultSet reset = stmt.executeQuery("select * from patient where isApproved=1 AND isDeleted=0 ");
            ResultSet reset = stmt.executeQuery("SELECT p.firstName AS firstName, p.lastName as lastName, p.nric as nric, pa.patientallocationID as allocationID, a.albumPath as albumPath " +
                    "FROM [patient] AS p " +
                    "INNER JOIN [patientAllocation] AS pa ON pa.patientID = p.patientID " +
                    "INNER JOIN [album] AS a ON a.patientID = p.patientID " +
                    "where p.isApproved=1 AND p.isDeleted=0 AND a.albumCatID=1");
            //original executeQuery done by keith
            /*ResultSet reset = stmt.executeQuery("select p.firstName AS firstName, p.lastName as lastName, p.nric as nric, pa.patientallocationID as allocationID " +
                    "FROM [patient] AS p " +
                    "INNER JOIN [patientAllocation] AS pa ON pa.patientID = p.patientID " +
                    "where p.isApproved=1 AND p.isDeleted=0");*/

            while (reset.next()) {
                Patient patient1 = new Patient();
                    Log.v("url path", ":"+reset.getString("albumPath"));
                    patient1.setFirstName(reset.getString("firstName"));
                    patient1.setLastName(reset.getString("lastName"));
                    patient1.setNric(reset.getString("nric"));
                    patient1.setAllocatonID(reset.getInt("allocationID"));
                    patient1.setPhoto(getPatientProfilePic(reset.getString("albumPath")));
                    //Bitmap photo = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_01);
                    //patient1.setPhoto(photo);
                patientList1.add(patient1);

            }


            conn.close();
            return patientList1;
        }catch(Exception e){
            e.printStackTrace();
        }

        return patientList1;

    }
    public ArrayList<Patient> getAllPatientInformation(String nric, Context context) {
        int count = 0;

        Patient patient1 = new Patient();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();
            ResultSet reset = stmt.executeQuery("SELECT a.albumPath as albumPath, * from patient AS p INNER JOIN album AS a ON a.patientID = p.patientID " +
                    " where p.nric='" + nric + "' AND  p.isDeleted=0 AND a.albumCatID=1 ");
            //original executeQuery done by keith
            /*ResultSet reset = stmt.executeQuery("select * from patient " +
                    " where nric='" + nric + "' AND  isDeleted=0");*/
//            ResultSet reset = stmt.executeQuery("select * from patient " +
//                    " where nric='" + nric + "' AND isApproved=1 AND isDeleted=0");

            while (reset.next()) {
                if (count == 0) {
                    patient1.setFirstName(reset.getString("firstName"));
                    patient1.setLastName(reset.getString("lastName"));
                    patient1.setNric(reset.getString("nric"));
                    patient1.setAddress(reset.getString("address"));
                    patient1.setHomeNumber(reset.getString("officeNo"));
                    patient1.setPhoneNumber(reset.getString("handphoneNo"));
                    patient1.setGender(reset.getString("gender").charAt(0));
                    String bday = reset.getString("DOB");
                    int year = Integer.parseInt(bday.substring(0, 4));
                    int month = Integer.parseInt(bday.substring(5, 7));
                    int day = Integer.parseInt(bday.substring(8, 10));
                    patient1.setDob(DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day));
                    //Bitmap photo = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_01);
                    patient1.setPhoto(getPatientProfilePic(reset.getString("albumPath")));
                    patient1.setGuardianFullName(reset.getString("guardianName"));
                    patient1.setGuardianContactNumber(reset.getString("guardianContactNo"));
                    patient1.setGuardianEmail(reset.getString("guardianEmail"));
                    patient1.setHasAllergy(false);
                    patient1.setAllergyList(new ArrayList<Allergy>());
                    patient1.setVitalList(new ArrayList<Vital>());
                    patient1.setRoutineList(new ArrayList<Routine>());
                    patient1.setProblemLogList(new ArrayList<ProblemLog>());
                    count++;
                }
                Statement stmt1 = conn.createStatement();
                ResultSet reset1 = stmt1.executeQuery("select * from patient AS p INNER JOIN patientSpecInfo AS psi " +
                        " ON p.patientID = psi.patientID" +
                        " INNER JOIN specInfo as si ON si.specInfoID = psi.specInfoID " +
                        " INNER JOIN patientAllocation as pa ON pa.patientID=p.patientID " +
                        " where p.nric='" + nric + "' AND psi.isApproved=1");
                while(reset1.next()){
                    Log.v("SpecInfoName:", String.valueOf(reset1.getString("specInfoName")));
                String specInfo = reset1.getString("specInfoName");
                if (specInfo.equals("Allergy")) {
                    patient1.setHasAllergy(true);
                    String allg = reset1.getString("patientSpecInfoValue");
                    String[] allg1 = allg.split(";");
                    Allergy allgery = new Allergy(allg1[0], allg1[1],allg1[2]);
                    patient1.getAllergyList().add(allgery);
                } else if (specInfo.equals("Vital")) {
                    String vital = reset1.getString("patientSpecInfoValue");
                    String[] vital1 = vital.split(";");
                    String test = vital1[0];
                    DateTime a = DateTime.parse(test);
                    Vital vital2 = new Vital(a, Boolean.valueOf(vital1[1]),Float.parseFloat(vital1[2]),
                            Float.parseFloat(vital1[3]),Float.parseFloat(vital1[4]),Float.parseFloat(vital1[5]),Float.parseFloat(vital1[6]),
                            vital1[7]);
                    patient1.getVitalList().add(vital2);
                } else if (specInfo.equals("Social History")) {
                    String social = reset1.getString("patientSpecInfoValue");
                    String[] socialList = social.split(";");
                    SocialHistory socialHistory = new SocialHistory();
                    socialHistory.setLiveWith(socialList[0]);
                    socialHistory.setDiet(socialList[1]);
                    socialHistory.setReligion(socialList[2]);
                    socialHistory.setIsSexuallyActive(Boolean.valueOf(socialList[3]));
                    socialHistory.setIsSecondhandSmoker(Boolean.valueOf(socialList[4]));
                    socialHistory.setAlcoholUse(socialList[5]);
                    socialHistory.setCaffeineUse(socialList[6]);
                    socialHistory.setTobaccoUse(socialList[7]);
                    socialHistory.setDrugUse(socialList[8]);
                    socialHistory.setPet(socialList[9]);
                    socialHistory.setOccupation(socialList[10]);
                    socialHistory.setLike(socialList[11]);
                    socialHistory.setDislike(socialList[12]);
                    socialHistory.setHobby(socialList[13]);
                    socialHistory.setHabbit(socialList[14]);
                    socialHistory.setHolidayExperience(socialList[15]);
                    socialHistory.setEducation(socialList[16]);
                    socialHistory.setExercise(socialList[17]);
                    patient1.setSocialHistory(socialHistory);
                } else if (specInfo.equals("Prescription")) {
                    String presc = reset1.getString("patientSpecInfoValue");
                    String[] prescri = presc.split(";");
                    String dateStart = prescri[4];
                    String endDate = prescri[5];
                    int year = Integer.parseInt(dateStart.substring(0, 4));
                    int month = Integer.parseInt(dateStart.substring(5, 7));
                    int day = Integer.parseInt(dateStart.substring(8, 10));
                    int year1 = Integer.parseInt(endDate.substring(0, 4));
                    int month1 = Integer.parseInt(endDate.substring(5, 7));
                    int day1 = Integer.parseInt(endDate.substring(8, 10));
                    
                    DateTime test = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day);
                    DateTime test2 = DateTime.now().withYear(year1).withMonthOfYear(month1).withDayOfMonth(day1);
                    Prescription pe = new Prescription(prescri[0],prescri[1],Integer.parseInt(prescri[2]),prescri[3],test,
                            test2,prescri[6],prescri[7]);
                    patient1.getPrescriptionList().add(pe);
                } else if (specInfo.equals("Routine")) {
                    String routine = reset1.getString("patientSpecInfoValue");
                    String[] ro = routine.split(";");
                    String dateStart = ro[2];
                    String endDate = ro[3];
                    int year = Integer.parseInt(dateStart.substring(0, 4));
                    int month = Integer.parseInt(dateStart.substring(5, 7));
                    int day = Integer.parseInt(dateStart.substring(8, 10));
                    int year1 = Integer.parseInt(endDate.substring(0, 4));
                    int month1 = Integer.parseInt(endDate.substring(5, 7));
                    int day1 = Integer.parseInt(endDate.substring(8, 10));
                    DateTime test = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day);
                    DateTime test2 = DateTime.now().withYear(year1).withMonthOfYear(month1).withDayOfMonth(day1);

                    String timeStart = ro[4];
                    String timeEnd = ro[5];
                    int hour = Integer.parseInt(timeStart.substring(11, 13));
                    int min = Integer.parseInt(timeStart.substring(14, 16));
                    int sec = Integer.parseInt(timeStart.substring(17, 19));
                    int hour1 = Integer.parseInt(timeEnd.substring(11, 13));
                    int min1 = Integer.parseInt(timeEnd.substring(14, 16));
                    int sec1 = Integer.parseInt(timeEnd.substring(17, 19));
                    DateTime test3 = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day).withHourOfDay(hour).withMinuteOfHour(min).withSecondOfMinute(sec);
                    DateTime test4 = DateTime.now().withYear(year1).withMonthOfYear(month1).withDayOfMonth(day1).withHourOfDay(hour1).withMinuteOfHour(min1).withSecondOfMinute(sec1);
                    Routine rot = new Routine(ro[0],ro[1],test,test2,test3,test4,Integer.parseInt(ro[6]),ro[7]);
                    patient1.getRoutineList().add(rot);

                }else if (specInfo.equals("Problem Log")) {
                    String problemlogdata = reset1.getString("patientSpecInfoValue");
                    String[] pl = problemlogdata.split(";");
                    String a = pl[0];
                    int year = Integer.parseInt(a.substring(0, 4));
                    int month = Integer.parseInt(a.substring(5, 7));
                    int day = Integer.parseInt(a.substring(8, 10));
                    DateTime test = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day);
                    ProblemLog p2 = new ProblemLog(UtilsUi.generateUniqueId(),test,pl[1],pl[2]);
                    patient1.getProblemLogList().add(p2);
                }
                }

            }

            ArrayList<Event> eventList = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            String dateNow = timestamp.toString().substring(0, 10);
            Statement stmt2 = conn.createStatement();
            ResultSet reset2 = stmt2.executeQuery("select * from patientAllocation AS pa INNER JOIN schedule AS s " +
                    " ON pa.patientAllocationID = s.patientAllocationID" +
                    " INNER JOIN patient as p ON p.patientID = pa.patientid where p.nric='" + nric + "' AND s.dateStart ='" + dateNow + "' AND s.isApproved=1" +
                    " ORDER BY timeStart");
            DateTimeFormatter formatter = Global.DATE_TIME_FORMAT;
            String todayDateStr = DateTime.now().toString(Global.DATE_FORMAT);
            while (reset2.next()) {
                Event event1 = new Event(reset2.getString("ScheduleTitle"), reset2.getString("ScheduleDesc"),
                        formatter.parseDateTime(todayDateStr + " " + reset2.getString("timeStart").substring(0,5)), formatter.parseDateTime(todayDateStr + " " + reset2.getString("timeEnd").substring(0,5)));
                eventList.add(event1);
            }

            Schedule schedule = new Schedule();
            schedule.setEventList(eventList);
            patient1.setTodaySchedule(schedule);

//            ArrayList<ProblemLog> problemLogList = new ArrayList<>();
//
//            problemLogList.add(new ProblemLog(UtilsUi.generateUniqueId(), DateTime.now().minusDays(5), "Communication", "Patient did not respond to question"));
//            problemLogList.add(new ProblemLog(UtilsUi.generateUniqueId(), DateTime.now().minusDays(3), "Communication", "Patient did not respond to when called"));
//            problemLogList.add(new ProblemLog(UtilsUi.generateUniqueId(), DateTime.now().minusDays(1), "Communication", "Patient seems to be not socializing with the other patient"));
//            patient1.setProblemLogList(problemLogList);
            patientList.add(patient1);

                conn.close();
            return patientList;
            }catch(Exception e){
                e.printStackTrace();
            }

            return patientList;

        }

    public ArrayList<Schedule> getPatientSchedule(int careGiverID, String date){
        ArrayList<Schedule> patientScheduleList = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();
            ResultSet reset = stmt.executeQuery("select * from patientAllocation AS pa INNER JOIN schedule AS s " +
                    " ON pa.patientAllocationID = s.patientAllocationID" +
                    " INNER JOIN patient as p ON p.patientID = pa.patientid where pa.caregiverID='" + careGiverID + "' AND s.dateStart ='" + date + "' AND s.isApproved=1 AND s.isDeleted=0");

            while (reset.next()) {
                Schedule schedule1 = new Schedule(R.drawable.avatar_01, reset.getString("firstName"), reset.getString("nric"), reset.getString("scheduleTitle"), reset.getString("timeStart"), reset.getString("timeEnd"));
                patientScheduleList.add(schedule1);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return patientScheduleList;
    }
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
    public void insertNewPatient(String firstname,String lastname, String nric, String address, String officeno, String handphoneno, char gender, String date, String gname, String gcontactno, String gemail, int UserTypeID, int UserID){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            int checkIsSupervisor;
            if(UserTypeID == 3){
                checkIsSupervisor = 1;
            }else{
                checkIsSupervisor = 0;
            }
            String sql = "INSERT INTO patient " +
                    "VALUES ('" + firstname + "','" + lastname + "','" + nric + "','" + address + "','" + officeno + "','" + handphoneno + "','" + gender + "','" + date + "','" + gname + "','" + gcontactno + "','" + gemail + "'," + 0 + "," + checkIsSupervisor + ",'" + timestamp + "')";
//            Statement stmt = conn.createStatement();
//            stmt.executeUpdate(sql);
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                int key = rs.getInt(1);
                int checkIsSuper;
                if(UserTypeID == 3){
                    checkIsSuper = UserID;
                }else{
                    checkIsSuper = 0;
                }
                int k;
                if (checkIsSuper==0){
                    k=0;
                }else{
                    k=1;
                }
                String allData = firstname +";" + lastname  +";" + gender + ";" + date + ";" + nric + ";" + address + ";" + officeno + ";" + handphoneno + ";" + gname + ";" + gcontactno + ";" + gemail   ;
                String logDesc = "Create new patient " + firstname + " " + lastname;
                String tableAffected = "patient";
                String columnAffected = "all";
                String sql1 = "INSERT INTO log " +
                        "VALUES ('" + allData + "','" + logDesc + "'," + 3 + "," + key + "," + UserID + ",'" + checkIsSuper + "'," + null + "," + null + ",'" + tableAffected + "','" + columnAffected + "'," + key + "," + k + ",'" + timestamp + "')";
            Statement stmt1 = conn.createStatement();
            stmt1.executeUpdate(sql1);
                int caregiverid = getLeastCareGiverID();
                String sql2 = "INSERT INTO patientAllocation " +
                        "VALUES (" + key + "," + 2 + "," + caregiverid + "," + 0 + "," + checkIsSupervisor + ",'" + timestamp + "')";
                Statement stmt2 = conn.createStatement();
                stmt2.executeUpdate(sql2);
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertPatientSpec(String info, int patientId, int specValue, int UserTypeID, int UserID){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            int checkIsSupervisor;
            if(UserTypeID == 3){
                checkIsSupervisor = 1;
            }else{
                checkIsSupervisor = 0;
            }
            int checkSuper;
            if (checkIsSupervisor ==1){
                checkSuper = UserID;
            }else{
                checkSuper = 0;
            }
            int k;
            if (checkSuper==0){
                k=0;
            }else{
                k=1;
            }
            String tableAffected = "patientSpecInfo";
            String columnAffected ="all";
            String logDesc = "New Patient Spec Info for patient";
            if (specValue == 1){//allergy
                 String sql = "INSERT INTO patientSpecInfo " +
                        "VALUES ('" + info + "'," + patientId + "," + 1 + "," + 0 + "," + checkIsSupervisor + ",'"+ timestamp + "')";
//                Statement stmt = conn.createStatement();
//                stmt.executeUpdate(sql);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    int key = rs.getInt(1);
                    String sql1 = "INSERT INTO log " +
                            "VALUES ('" + info + "','" + logDesc + "'," + 2 + "," + patientId + "," + UserID + "," + checkSuper + ",'allergy'," + null + ",'" + tableAffected + "','" + columnAffected + "'," + key + "," + k + ",'" + timestamp + "')";
                    Statement stmt1 = conn.createStatement();
                    stmt1.executeUpdate(sql1);
                }
                conn.close();
            }else if(specValue == 2){//vital
                String sql = "INSERT INTO patientSpecInfo " +
                        "VALUES ('" + info + "'," + patientId + "," + 2 + "," + 0 + "," + checkIsSupervisor + ",'"+ timestamp + "')";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    int key = rs.getInt(1);
                    String sql1 = "INSERT INTO log " +
                            "VALUES ('" + info + "','" + logDesc + "'," + 2 + "," + patientId + "," + UserID + "," + checkSuper + ",'vital'," + null + ",'" + tableAffected + "','" + columnAffected + "'," + key + "," + k + ",'" + timestamp + "')";
                    Statement stmt1 = conn.createStatement();
                    stmt1.executeUpdate(sql1);
                }
                conn.close();
            }else if(specValue == 3){
                String sql = "INSERT INTO patientSpecInfo " +
                        "VALUES ('" + info + "'," + patientId + "," + 3 + "," + 0 + "," + checkIsSupervisor + ",'"+ timestamp + "')";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    int key = rs.getInt(1);
                    String sql1 = "INSERT INTO log " +
                            "VALUES ('" + info + "','" + logDesc + "'," + 2 + "," + patientId + "," + UserID + "," + checkSuper + ",'social history'," + null + ",'" + tableAffected + "','" + columnAffected + "'," + key + "," + k + ",'" + timestamp + "')";
                    Statement stmt1 = conn.createStatement();
                    stmt1.executeUpdate(sql1);
                }
                conn.close();
            }else if(specValue == 4){
                String sql = "INSERT INTO patientSpecInfo " +
                        "VALUES ('" + info + "'," + patientId + "," + 4 + "," + 0 + "," + checkIsSupervisor + ",'"+ timestamp + "')";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    int key = rs.getInt(1);
                    String sql1 = "INSERT INTO log " +
                            "VALUES ('" + info + "','" + logDesc + "'," + 2 + "," + patientId + "," + UserID + "," + checkSuper + ",'prescription'," + null + ",'" + tableAffected + "','" + columnAffected + "'," + key + "," + k + ",'" + timestamp + "')";
                    Statement stmt1 = conn.createStatement();
                    stmt1.executeUpdate(sql1);
                }
                conn.close();
            }else if(specValue == 5){
                String sql = "INSERT INTO patientSpecInfo " +
                        "VALUES ('" + info + "'," + patientId + "," + 5 + "," + 0 + "," + checkIsSupervisor + ",'"+ timestamp + "')";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    int key = rs.getInt(1);
                    String sql1 = "INSERT INTO log " +
                            "VALUES ('" + info + "','" + logDesc + "'," + 2 + "," + patientId + "," + UserID + "," + checkSuper + ",'routine'," + null + ",'" + tableAffected + "','" + columnAffected + "'," + key + "," + k + ",'" + timestamp + "')";
                    Statement stmt1 = conn.createStatement();
                    stmt1.executeUpdate(sql1);
                }
                conn.close();
            }else if(specValue == 12){
                String sql = "INSERT INTO patientSpecInfo " +
                        "VALUES ('" + info + "'," + patientId + "," + 12 + "," + 0 + "," + checkIsSupervisor + ",'"+ timestamp + "')";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if(rs.next()) {
                    int key = rs.getInt(1);
                    String sql1 = "INSERT INTO log " +
                            "VALUES ('" + info + "','" + logDesc + "'," + 2 + "," + patientId + "," + UserID + "," + checkSuper + ",'problem log'," + null + ",'" + tableAffected + "','" + columnAffected + "'," + key + "," + k + ",'" + timestamp + "')";
                    Statement stmt1 = conn.createStatement();
                    stmt1.executeUpdate(sql1);
                }
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
    public ArrayList <String> getPicturesURLS (int patientId , int albumCatId){
        String path = "http://dementiafypdb.com/";
        ArrayList<String> albumFilePath = new ArrayList<String>();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Connection conn = null;
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(connString, username, password);
            Statement stmt = conn.createStatement();
            ResultSet reset = stmt.executeQuery("Select albumPath\n" +
                                                "From album\n" +
                                                "where albumCatID= '" +albumCatId+ "' AND patientID= '"+patientId+ "';");

            while (reset.next()) {
                albumFilePath.add(path + reset.getString("albumPath").replace("\\","/"));
                Log.v("Testing", path + reset.getString("albumPath").replace("\\", "/")); //test file path.
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
      return albumFilePath;
    }

    public Bitmap getPatientProfilePic(String albumPath){
        String path = "http://dementiafypdb.com/";
        String modifiedpath;
        Bitmap photo = null;
        modifiedpath = albumPath.replace("\\","/");
        path +=modifiedpath;
        try {
            URL ulrn = new URL(path);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            photo = BitmapFactory.decodeStream(is);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return photo;
    }


}
