package com.example.keith.fyp.scheduler;

import android.os.StrictMode;
import android.util.Log;

import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.DefaultEvent;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.ScheduleList;

import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Keith on 21/12/2015.
 */

public class scheduleScheduler {
    String driver = "net.sourceforge.jtds.jdbc.Driver";
    String connString = "jdbc:jtds:sqlserver://PALM.arvixe.com:1433/dementiafypdb;encrypt=false;user=fyp2015;password=va5a7eve;instance=SQLEXPRESS;";
    Connection conn = null;
    String username = "fyp2015";
    String password = "va5a7eve";
    Random rand = new Random();
    DateTime deCurrent, deRoutineCurrent;
    Calendar cal = Calendar.getInstance();
    java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
    String dateNow = timestamp.toString().substring(0, 10);
    DateTime testDateNow;
    int x = 0, y=0;

    public void insertNewSchedules(ArrayList<Patient> patient, ArrayList<DefaultEvent> de, DateTime testDateNow){
        ArrayList<ScheduleList> schedule= getEvent();
        DateTime startDay;
        DateTime endDay;

//        dbfile db = new dbfile();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(connString, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //to insert for everypatient schedule
        for (int i = 0; i< patient.size(); i++){
            dbfile db = new dbfile();

            ArrayList<String> prescriptionCheck = new ArrayList<String>();
            ArrayList<DefaultEvent> routineCheck = new ArrayList<DefaultEvent>();
            String cDate = testDateNow.toString().substring(0,10);
            String gameList = db.getGametoPlay(patient.get(i).getAllocatonID(),cDate);
            prescriptionCheck = db.getPatientSpecInfoforPrescription(patient.get(i).getAllocatonID(), testDateNow.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0));
            routineCheck = db.getPatientSpecInfoforRoutine(patient.get(i).getAllocatonID(), testDateNow.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0));
            Collections.sort(routineCheck, DefaultEvent.COMPARE_BY_TIME);
            startDay = DateTime.now().withHourOfDay(9).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            endDay = DateTime.now().withHourOfDay(17).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfDay(0);
            ArrayList<DefaultEvent> de1 = new ArrayList<DefaultEvent>();
            de1 = de;
            x=0;
            y=0;
            //use as a condition to terminate if the time reached the end time of the day
            while(startDay.getHourOfDay() < 17){
                if(de1.size() != 0 && x < de1.size()) {
                    deCurrent = de1.get(x).getStartTime();
                }
                if(routineCheck.size() != 0 && y < routineCheck.size()) {
                    deRoutineCurrent = routineCheck.get(y).getStartTime();
                }

                //always check if the default event is in turn to do it
                if(de1.size() != 0 && deCurrent.getHourOfDay() == startDay.getHourOfDay() && deCurrent.getMinuteOfDay() == startDay.getMinuteOfDay()){
                    if(de1.get(x).getName().equals("Lunch") && prescriptionCheck.size()!=0){
                            if(prescriptionCheck.size() == 1){
                                addnewSchedule(de1.get(x).getName(), de1.get(x).getStartTime().withMillisOfSecond(0), de1.get(x).getEndTime().withMillisOfSecond(0), 0, prescriptionCheck.get(0), 1, patient.get(i).getAllocatonID(),testDateNow);
                            }else if(prescriptionCheck.size() > 1){
                                String listofPrescription ="";
                                for(int s=0;s<prescriptionCheck.size();s++){
                                    listofPrescription += prescriptionCheck.get(s);
                                }
                                addnewSchedule(de1.get(x).getName(), de1.get(x).getStartTime().withMillisOfSecond(0), de1.get(x).getEndTime().withMillisOfSecond(0), 0, listofPrescription, 1, patient.get(i).getAllocatonID(),testDateNow);
                            }
                    }else{
                        addnewSchedule(de1.get(x).getName(), de1.get(x).getStartTime().withMillisOfSecond(0), de1.get(x).getEndTime().withMillisOfSecond(0), 0, "Default Event", 1, patient.get(i).getAllocatonID(),testDateNow);
                    }
                    DateTime time1 = de1.get(x).getStartTime();
                    DateTime time2 = de1.get(x).getEndTime();
                    int a1 = time1.getHourOfDay();
                    int a2 = time1.getMinuteOfHour();
                    int a3 = time2.getHourOfDay();
                    int a4 = time2.getMinuteOfHour();
                    int a5 = a3-a1;
                    int a6 = a4-a2;
                    startDay=startDay.plusHours(a5).plusMinutes(a6).withMillisOfSecond(0);
//                    de1.remove(0);
                    x++;
                    continue;
                }

                if(routineCheck.size() != 0 && deRoutineCurrent.getHourOfDay() == startDay.getHourOfDay() && deRoutineCurrent.getMinuteOfDay() == startDay.getMinuteOfDay()){
                    addnewSchedule(routineCheck.get(y).getName(), routineCheck.get(y).getStartTime().withMillisOfSecond(0), routineCheck.get(y).getEndTime().withMillisOfSecond(0), 0, "Patient has routine to do", 1, patient.get(i).getAllocatonID(),testDateNow);
                    DateTime time1 = routineCheck.get(y).getStartTime();
                    DateTime time2 = routineCheck.get(y).getEndTime();
                    int a1 = time1.getHourOfDay();
                    int a2 = time1.getMinuteOfHour();
                    int a3 = time2.getHourOfDay();
                    int a4 = time2.getMinuteOfHour();
                    int a5 = a3-a1;
                    int a6 = a4-a2;
                    startDay=startDay.plusHours(a5).plusMinutes(a6).withMillisOfSecond(0);
                    y++;
                    continue;
                }
                if(gameList.length() > 7){
                    addnewSchedule("Android Game", startDay.withMillisOfSecond(0), startDay.plusHours(1).withMillisOfSecond(0), 0, gameList, 1, patient.get(i).getAllocatonID(),testDateNow);
                    startDay=startDay.plusHours(1).plusMinutes(0).withMillisOfSecond(0);
                    gameList ="";
                    continue;
                }
                Integer randomInt = rand.nextInt(schedule.size());
                addnewSchedule(schedule.get(randomInt).getEventName(), startDay, startDay.plusHours(1), 0, schedule.get(randomInt).getEventDesc(), 1, patient.get(i).getAllocatonID(),testDateNow);
                startDay= startDay.plusHours(1);
                continue;

            }

        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertNewSpecificPatient(ArrayList<Patient> patient, ArrayList<DefaultEvent> de, DateTime testDateNow, int k){
        ArrayList<ScheduleList> schedule= getEvent();
        DateTime startDay;
        DateTime endDay;

//        dbfile db = new dbfile();
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(connString, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //to insert for everypatient schedule

            dbfile db = new dbfile();

            ArrayList<String> prescriptionCheck = new ArrayList<String>();
            ArrayList<DefaultEvent> routineCheck = new ArrayList<DefaultEvent>();
            String cDate = testDateNow.toString().substring(0,10);
            String gameList = db.getGametoPlay(patient.get(k).getAllocatonID(),cDate);

            prescriptionCheck = db.getPatientSpecInfoforPrescription(patient.get(k).getAllocatonID(), testDateNow.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0));
            routineCheck = db.getPatientSpecInfoforRoutine(patient.get(k).getAllocatonID(), testDateNow.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0));
            Collections.sort(routineCheck, DefaultEvent.COMPARE_BY_TIME);
            startDay = DateTime.now().withHourOfDay(9).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            endDay = DateTime.now().withHourOfDay(17).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfDay(0);
            ArrayList<DefaultEvent> de1 = new ArrayList<DefaultEvent>();
            de1 = de;
            x=0;
            y=0;
            //use as a condition to terminate if the time reached the end time of the day
            while(startDay.getHourOfDay() < 17){
                if(de1.size() != 0 && x < de1.size()) {
                    deCurrent = de1.get(x).getStartTime();
                }
                if(routineCheck.size() != 0 && y < routineCheck.size()) {
                    deRoutineCurrent = routineCheck.get(y).getStartTime();
                }

                //always check if the default event is in turn to do it
                if(de1.size() != 0 && deCurrent.getHourOfDay() == startDay.getHourOfDay() && deCurrent.getMinuteOfDay() == startDay.getMinuteOfDay()){
                    if(de1.get(x).getName().equals("Lunch") && prescriptionCheck.size()!=0){
                        if(prescriptionCheck.size() == 1){
                            addnewSchedule(de1.get(x).getName(), de1.get(x).getStartTime().withMillisOfSecond(0), de1.get(x).getEndTime().withMillisOfSecond(0), 0, prescriptionCheck.get(0), 1, patient.get(k).getAllocatonID(),testDateNow);
                        }else if(prescriptionCheck.size() > 1){
                            String listofPrescription ="";
                            for(int s=0;s<prescriptionCheck.size();s++){
                                listofPrescription += prescriptionCheck.get(s);
                            }
                            addnewSchedule(de1.get(x).getName(), de1.get(x).getStartTime().withMillisOfSecond(0), de1.get(x).getEndTime().withMillisOfSecond(0), 0, listofPrescription, 1, patient.get(k).getAllocatonID(),testDateNow);
                        }
                    }else{
                        addnewSchedule(de1.get(x).getName(), de1.get(x).getStartTime().withMillisOfSecond(0), de1.get(x).getEndTime().withMillisOfSecond(0), 0, "Default Event", 1, patient.get(k).getAllocatonID(),testDateNow);
                    }
                    DateTime time1 = de1.get(x).getStartTime();
                    DateTime time2 = de1.get(x).getEndTime();
                    int a1 = time1.getHourOfDay();
                    int a2 = time1.getMinuteOfHour();
                    int a3 = time2.getHourOfDay();
                    int a4 = time2.getMinuteOfHour();
                    int a5 = a3-a1;
                    int a6 = a4-a2;
                    startDay=startDay.plusHours(a5).plusMinutes(a6).withMillisOfSecond(0);
//                    de1.remove(0);
                    x++;
                    continue;
                }

                if(routineCheck.size() != 0 && deRoutineCurrent.getHourOfDay() == startDay.getHourOfDay() && deRoutineCurrent.getMinuteOfDay() == startDay.getMinuteOfDay()){
                    addnewSchedule(routineCheck.get(y).getName(), routineCheck.get(y).getStartTime().withMillisOfSecond(0), routineCheck.get(y).getEndTime().withMillisOfSecond(0), 0, "Patient has routine to do", 1, patient.get(k).getAllocatonID(),testDateNow);
                    DateTime time1 = routineCheck.get(y).getStartTime();
                    DateTime time2 = routineCheck.get(y).getEndTime();
                    int a1 = time1.getHourOfDay();
                    int a2 = time1.getMinuteOfHour();
                    int a3 = time2.getHourOfDay();
                    int a4 = time2.getMinuteOfHour();
                    int a5 = a3-a1;
                    int a6 = a4-a2;
                    startDay=startDay.plusHours(a5).plusMinutes(a6).withMillisOfSecond(0);
                    y++;
                    continue;
                }

                if(gameList.length() > 7){
                    addnewSchedule("Android Game", startDay.withMillisOfSecond(0), startDay.plusHours(1).withMillisOfSecond(0), 0, gameList, 1, patient.get(k).getAllocatonID(),testDateNow);
                    startDay=startDay.plusHours(1).plusMinutes(0).withMillisOfSecond(0);
                    gameList ="";
                    continue;
                }
                Integer randomInt = rand.nextInt(schedule.size());
                addnewSchedule(schedule.get(randomInt).getEventName(), startDay, startDay.plusHours(1), 0, schedule.get(randomInt).getEventDesc(), 1, patient.get(k).getAllocatonID(),testDateNow);
                startDay= startDay.plusHours(1);
                continue;

            }


        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addnewSchedule(String scheduleTitle, DateTime timeStart, DateTime timeEnd, int interval, String scheduleDesc,int scheduleTypeID, int patientAllocationID, DateTime testDateNow){
        String date1 = testDateNow.toString().substring(0,10);
        try {
            String sql = "INSERT INTO schedule " +
                    "VALUES ('" + scheduleTitle + "','" + timeStart + "','" + timeEnd + "','" + date1 + "','" + date1 + "'," + interval + ",'" + scheduleDesc + "'," + scheduleTypeID + "," + patientAllocationID + "," + 0 + "," + 1 + ",'" + timestamp + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ScheduleList> getEvent(){
        ArrayList<ScheduleList> eventList = new ArrayList<ScheduleList>();
        ScheduleList sl = new ScheduleList();
        sl.ScheduleList("TV Programme", "Catch a current Local or overseas series");
        eventList.add(sl);
        ScheduleList s2 = new ScheduleList();
        s2.ScheduleList("Android Games", "Play some android games");
        eventList.add(s2);
        ScheduleList s3 = new ScheduleList();
        s3.ScheduleList("Board Games", "Play board games in the centre");
        eventList.add(s3);
        ScheduleList s4 = new ScheduleList();
        s4.ScheduleList("Card Games", "Play card games with caregiver and other patients");
        eventList.add(s4);
        ScheduleList s5 = new ScheduleList();
        s5.ScheduleList("Musical Instruments", "Play a musical instrument");
        eventList.add(s5);
        ScheduleList s6 = new ScheduleList();
        s6.ScheduleList("Listening to Music", "Listen to past favourite music or favourite genre");
        eventList.add(s6);
        ScheduleList s7 = new ScheduleList();
        s7.ScheduleList("Story Time", "Listen to a story by the caregiver or get a patient to read from a book");
        eventList.add(s7);
        ScheduleList s8 = new ScheduleList();
        s8.ScheduleList("Stretching", "Perform stretching or eye massaging activities");
        eventList.add(s8);
        ScheduleList s9 = new ScheduleList();
        s9.ScheduleList("Gym Exercises", "Do certain gym exercise e.g. lift dumbbells");
        eventList.add(s9);
        ScheduleList s10 = new ScheduleList();
        s10.ScheduleList("Exercises", "Do taiji or squats or frog jump etc.");
        eventList.add(s10);
        ScheduleList s11 = new ScheduleList();
        s11.ScheduleList("View Picture of Past", "View past photos with family or self from android");
        eventList.add(s11);
        ScheduleList s12 = new ScheduleList();
        s12.ScheduleList("Memory Games", "Play memory games from android");
        eventList.add(s12);
        ScheduleList s13 = new ScheduleList();
        s13.ScheduleList("Movie Screening", "Catch a movie screening");
        eventList.add(s13);
        ScheduleList s14 = new ScheduleList();
        s14.ScheduleList("Pet Therapy", "Brushing or playing with the pet");
        eventList.add(s14);
        ScheduleList s15 = new ScheduleList();
        s15.ScheduleList("Watering Plants", "Water the plants in the garden");
        eventList.add(s15);
        ScheduleList s16 = new ScheduleList();
        s16.ScheduleList("Trimming Plants", "Trim or organize the plants");
        eventList.add(s16);
        ScheduleList s17 = new ScheduleList();
        s17.ScheduleList("Sewing", "Sew half done work or start a new sewing item");
        eventList.add(s17);
        ScheduleList s18 = new ScheduleList();
        s18.ScheduleList("Toss a ball activity", "Play toss with other patients or caregivers");
        eventList.add(s18);
        ScheduleList s19 = new ScheduleList();
        s19.ScheduleList("Picture Coloring", "Color a picture");
        eventList.add(s19);
        ScheduleList s20 = new ScheduleList();
        s20.ScheduleList("Make Homemade Drinks", "Learn to make drinks like ribena,milo,coffee or lemonade");
        eventList.add(s20);
        ScheduleList s21 = new ScheduleList();
        s21.ScheduleList("Count Trading Cards", "Count the number of cards by category");
        eventList.add(s21);
        ScheduleList s22 = new ScheduleList();
        s22.ScheduleList("Clip Coupons", "Clip coupons to respective items");
        eventList.add(s22);
        ScheduleList s23 = new ScheduleList();
        s23.ScheduleList("Sort Poker Chips", "Sorting of colored chips");
        eventList.add(s23);
        ScheduleList s24 = new ScheduleList();
        s24.ScheduleList("String beads", "String simple accessories");
        eventList.add(s24);
        ScheduleList s25 = new ScheduleList();
        s25.ScheduleList("Bake Cookies", "Premade baking classes");
        eventList.add(s25);
        ScheduleList s26 = new ScheduleList();
        s26.ScheduleList("Cleaning", "Clean the windows, tables etc");
        eventList.add(s26);
        ScheduleList s27 = new ScheduleList();
        s27.ScheduleList("Fold Laundry", "Fold the laundry at centre");
        eventList.add(s27);
        ScheduleList s28 = new ScheduleList();
        s28.ScheduleList("Cutting Traced Pictures", "Cutting pictures out from magazines or newspaper");
        eventList.add(s28);
        ScheduleList s29 = new ScheduleList();
        s29.ScheduleList("Singing favourite Song", "Singing performance of favourite past song");
        eventList.add(s29);
        ScheduleList s30 = new ScheduleList();
        s30.ScheduleList("Reminisce about the past", "Start a topic asking about their first job, hobby etc.");
        eventList.add(s30);
        ScheduleList s31 = new ScheduleList();
        s31.ScheduleList("Make a family tree poster", "Getting patient to draft out family tree");
        eventList.add(s31);
        ScheduleList s32 = new ScheduleList();
        s32.ScheduleList("Dance", "Dance with music");
        eventList.add(s32);
        ScheduleList s33 = new ScheduleList();
        s33.ScheduleList("Make Holiday Cards", "Prepare holiday cards for family");
        eventList.add(s33);
        ScheduleList s34 = new ScheduleList();
        s34.ScheduleList("Origami", "Learning how to fold paper cranes etc.");
        eventList.add(s34);
        ScheduleList s35 = new ScheduleList();
        s35.ScheduleList("Play Hangman", "A game with multiple patients");
        eventList.add(s35);


        return eventList;

    }
}
