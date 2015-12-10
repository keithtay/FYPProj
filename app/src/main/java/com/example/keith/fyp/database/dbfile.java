package com.example.keith.fyp.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.Event;
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
    ArrayList<Patient> patientList = new ArrayList<>();


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
//            ResultSet reset = stmt.executeQuery("select * from patient AS p INNER JOIN patientSpecInfo AS psi " +
//                    " ON p.patientID = psi.patientID" +
//                    " INNER JOIN specInfo as si ON si.specInfoID = psi.specInfoID " +
//                    " INNER JOIN patientAllocation as pa ON pa.patientID=p.patientID " +
//                    " where p.nric='" + nric + "'");
            ResultSet reset = stmt.executeQuery("select * from patient " +
                    " where nric='" + nric + "'");

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
                    Bitmap photo = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_01);
                    patient1.setPhoto(photo);
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
//                String specInfo = reset.getString("specInfoName");
//                if (specInfo.equals("Allergy")) {
//                    patient1.setHasAllergy(true);
//                    String allg = reset.getString("patientSpecInfoValue");
//                    String[] allg1 = allg.split(";");
//                    Allergy allgery = new Allergy(allg1[0], allg1[1],allg1[2]);
//                    patient1.getAllergyList().add(allgery);
//                } else if (specInfo.equals("Vital")) {
//                    String vital = reset.getString("patientSpecInfoValue");
//                    String[] vital1 = vital.split(";");
//                    String test = vital1[0];
//                    DateTime a = DateTime.parse(test);
//                    Vital vital2 = new Vital(a, Boolean.valueOf(vital1[1]),Float.parseFloat(vital1[2]),
//                            Float.parseFloat(vital1[3]),Float.parseFloat(vital1[4]),Float.parseFloat(vital1[5]),Float.parseFloat(vital1[6]),
//                            vital1[7]);
//                    patient1.getVitalList().add(vital2);
//                } else if (specInfo.equals("Social History")) {
//                    String social = reset.getString("patientSpecInfoValue");
//                    String[] socialList = social.split(";");
//                    SocialHistory socialHistory = new SocialHistory();
//                    socialHistory.setLiveWith(socialList[0]);
//                    socialHistory.setDiet(socialList[1]);
//                    socialHistory.setReligion(socialList[2]);
//                    socialHistory.setIsSexuallyActive(Boolean.valueOf(socialList[3]));
//                    socialHistory.setIsSecondhandSmoker(Boolean.valueOf(socialList[4]));
//                    socialHistory.setAlcoholUse(socialList[5]);
//                    socialHistory.setCaffeineUse(socialList[6]);
//                    socialHistory.setTobaccoUse(socialList[7]);
//                    socialHistory.setDrugUse(socialList[8]);
//                    socialHistory.setPet(socialList[9]);
//                    socialHistory.setOccupation(socialList[10]);
//                    socialHistory.setLike(socialList[11]);
//                    socialHistory.setDislike(socialList[12]);
//                    socialHistory.setHobby(socialList[13]);
//                    socialHistory.setHabbit(socialList[14]);
//                    socialHistory.setHolidayExperience(socialList[15]);
//                    socialHistory.setEducation(socialList[16]);
//                    socialHistory.setExercise(socialList[17]);
//                    patient1.setSocialHistory(socialHistory);
//                } else if (specInfo.equals("Prescription")) {
//                    String presc = reset.getString("patientSpecInfoValue");
//                    String[] prescri = presc.split(";");
//                    String dateStart = prescri[4];
//                    String endDate = prescri[5];
//                    int year = Integer.parseInt(dateStart.substring(0, 4));
//                    int month = Integer.parseInt(dateStart.substring(5, 7));
//                    int day = Integer.parseInt(dateStart.substring(8, 10));
//                    int year1 = Integer.parseInt(endDate.substring(0, 4));
//                    int month1 = Integer.parseInt(endDate.substring(5, 7));
//                    int day1 = Integer.parseInt(endDate.substring(8, 10));
//                    DateTime test = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day);
//                    DateTime test2 = DateTime.now().withYear(year1).withMonthOfYear(month1).withDayOfMonth(day1);
//                    Prescription pe = new Prescription(prescri[0],prescri[1],Integer.parseInt(prescri[2]),prescri[3],test,
//                            test2,prescri[6],prescri[7]);
//                    patient1.getPrescriptionList().add(pe);
//                } else if (specInfo.equals("Routine")) {
//                    String routine = reset.getString("patientSpecInfoValue");
//                    String[] ro = routine.split(";");
//                    String dateStart = ro[2];
//                    String endDate = ro[3];
//                    int year = Integer.parseInt(dateStart.substring(0, 4));
//                    int month = Integer.parseInt(dateStart.substring(5, 7));
//                    int day = Integer.parseInt(dateStart.substring(8, 10));
//                    int year1 = Integer.parseInt(endDate.substring(0, 4));
//                    int month1 = Integer.parseInt(endDate.substring(5, 7));
//                    int day1 = Integer.parseInt(endDate.substring(8, 10));
//                    DateTime test = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day);
//                    DateTime test2 = DateTime.now().withYear(year1).withMonthOfYear(month1).withDayOfMonth(day1);
//
//                    String timeStart = ro[4];
//                    String timeEnd = ro[5];
//                    int hour = Integer.parseInt(timeStart.substring(11, 13));
//                    int min = Integer.parseInt(timeStart.substring(14, 16));
//                    int sec = Integer.parseInt(timeStart.substring(17, 19));
//                    int hour1 = Integer.parseInt(timeEnd.substring(11, 13));
//                    int min1 = Integer.parseInt(timeEnd.substring(14, 16));
//                    int sec1 = Integer.parseInt(timeEnd.substring(17, 19));
//                    DateTime test3 = DateTime.now().withHourOfDay(hour).withMinuteOfHour(min).withSecondOfMinute(sec);
//                    DateTime test4 = DateTime.now().withHourOfDay(hour1).withMinuteOfHour(min1).withSecondOfMinute(sec1);
//                    Routine rot = new Routine(ro[0],ro[1],test,test2,test3,test4
//                            ,Integer.parseInt(ro[6]),ro[7]);
//                    patient1.getRoutineList().add(rot);
//                }
            }
            DateTimeFormatter formatter = Global.DATE_TIME_FORMAT;
            String todayDateStr = DateTime.now().toString(Global.DATE_FORMAT);

            DateTime startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            DateTime endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            Event event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            DateTime startTime2 = formatter.parseDateTime(todayDateStr + " 12:30");
            DateTime endTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            Event event2 = new Event("Games", "Play memory games", startTime2, endTime2);

            DateTime startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            DateTime endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            Event event3 = new Event("Games", "Play cognitive games", startTime3, endTime3);

            ArrayList<Event> eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            Schedule schedule = new Schedule();
            schedule.setEventList(eventList);
            patient1.setTodaySchedule(schedule);

            ArrayList<ProblemLog> problemLogList = new ArrayList<>();

            problemLogList.add(new ProblemLog(UtilsUi.generateUniqueId(), DateTime.now().minusDays(5), "Communication", "Patient did not respond to question"));
            problemLogList.add(new ProblemLog(UtilsUi.generateUniqueId(), DateTime.now().minusDays(3), "Communication", "Patient did not respond to when called"));
            problemLogList.add(new ProblemLog(UtilsUi.generateUniqueId(), DateTime.now().minusDays(1), "Communication", "Patient seems to be not socializing with the other patient"));
            patient1.setProblemLogList(problemLogList);
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
                    " INNER JOIN patient as p ON p.patientID = pa.patientid where pa.caregiverID='" + careGiverID + "' AND s.dateStart ='" + date +"'");

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
