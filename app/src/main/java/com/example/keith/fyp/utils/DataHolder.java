package com.example.keith.fyp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.DefaultEvent;
import com.example.keith.fyp.models.Event;
import com.example.keith.fyp.models.NotificationGroup;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.Prescription;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.models.Routine;
import com.example.keith.fyp.models.Schedule;
import com.example.keith.fyp.models.SocialHistory;
import com.example.keith.fyp.models.Vital;
import com.example.keith.fyp.views.fragments.CreatePatientInfoFormFragment;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.UUID;

/**
 * DataHolder is a singleton class that
 * hold data that will be shared accross different part of the application
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class DataHolder {
    // Patient currently being created
    private static Patient createdPatient;

    // For comparing whether or not the patient draft is editted
    private static Patient createdPatientEditInitial;

    private static ArrayList<Patient> patientDraftList = new ArrayList<>();

    // Patient currently being viewed
    private static Patient viewedPatient;

    private static ArrayList<NotificationGroup> notificationGroupList = new ArrayList<>();
    private static ArrayList<DefaultEvent> defaultEventList = new ArrayList<>();

    // Care center patient list
    private static ArrayList<Patient> patientList = new ArrayList<>();

    /**
     * @return current created {@link Patient} in the application
     */
    public static Patient getCreatedPatient() {
        if (createdPatient == null) {
            createdPatient = new Patient();
        }
        return createdPatient;
    }

    /**
     * Reset the created {@link Patient} being hold by the {@link DataHolder}
     */
    public static void resetCreatedPatient() {
        createdPatient = new Patient();
    }

    /**
     * @return current viewed {@link Patient} in the application
     */
    public static Patient getViewedPatient() {
        if (viewedPatient == null) {
            viewedPatient = new Patient();
        }
        return viewedPatient;
    }

    /*
     * @param viewedPatient current viewed {@link Patient} in the application
     */
    public static void setViewedPatient(Patient viewedPatient) {
        DataHolder.viewedPatient = viewedPatient;
    }

    /**
     * Reset the viewed {@link Patient} being hold by the {@link DataHolder}
     */
    public static void resetViewedPatient() {
        viewedPatient = new Patient();
    }

    /**
     * @return notification list of the current logged in user
     */
    public static ArrayList<NotificationGroup> getNotificationGroupList() {
        return notificationGroupList;
    }

    /**
     * @param notificationGroupList notification list of the current logged in user
     */
    public static void setNotificationGroupList(ArrayList<NotificationGroup> notificationGroupList) {
        DataHolder.notificationGroupList = notificationGroupList;
    }

    /**
     * @return list of {@link DefaultEvent} of the current care centre
     */
    public static ArrayList<DefaultEvent> getDefaultEventList() {
        return defaultEventList;
    }

    /**
     * @param createdPatient current created {@link Patient} in the application
     */
    public static void setCreatedPatient(Patient createdPatient) {
        DataHolder.createdPatient = createdPatient;
    }

    /**
     * @return {@link Patient} draft before it is continued to be edited
     */
    public static Patient getCreatedPatientEditInitial() {
        return createdPatientEditInitial;
    }

    /**
     * @param createdPatientEditInitial {@link Patient} draft before it is continued to be edited
     */
    public static void setCreatedPatientEditInitial(Patient createdPatientEditInitial) {
        DataHolder.createdPatientEditInitial = createdPatientEditInitial;
    }

    /**
     * @param context context of the application
     * @return list of {@link Patient} in the care centre
     */
    public static ArrayList<Patient> getPatientList(Context context) {
        if (patientList.size() == 0) {
            // Initialize mock patient list

            // ==========================
            // Patient 1
            // ==========================
            Patient patient1 = new Patient();
            patient1.setFirstName("Andy");
            patient1.setLastName("Perkins");
            patient1.setNric("1000001");
            patient1.setAddress("296 Route 64 The Villages");
            patient1.setHomeNumber("1234 5678");
            patient1.setPhoneNumber("1234 5678");
            patient1.setGender('M');
            patient1.setDob(DateTime.now().withYear(1955).withMonthOfYear(12).withDayOfMonth(3));
            Bitmap photo = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_01);
            patient1.setPhoto(photo);
            patient1.setGuardianFullName("Kenneth Simmons");
            patient1.setGuardianContactNumber("1234 5678");
            patient1.setGuardianEmail("kenneth@gmail.com");
            patient1.setHasAllergy(false);
            patient1.setAllergyList(new ArrayList<Allergy>());
            patient1.setVitalList(new ArrayList<Vital>());
            patient1.setRoutineList(new ArrayList<Routine>());
            patient1.setProblemLogList(new ArrayList<ProblemLog>());
            SocialHistory socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Wife and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient1.setSocialHistory(socialHistory);

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

            // ==========================
            // Patient 2
            // ==========================
            Patient patient2 = new Patient();
            patient2.setFirstName("Brooke");
            patient2.setLastName("Roberson");
            patient2.setNric("1000002");
            patient2.setAddress("869 Myrtle Avenue Winter Haven");
            patient2.setHomeNumber("2222 1111");
            patient2.setPhoneNumber("1111 2222");
            patient2.setGender('F');
            patient2.setDob(DateTime.now().withYear(1945).withMonthOfYear(4).withDayOfMonth(15));
            Bitmap photo2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_02);
            patient2.setPhoto(photo2);
            patient2.setGuardianFullName("Leon Wagner");
            patient2.setGuardianContactNumber("1233 5677");
            patient2.setGuardianEmail("leon@gmail.com");
            patient2.setHasAllergy(false);
            patient2.setAllergyList(new ArrayList<Allergy>());
            patient2.setVitalList(new ArrayList<Vital>());
            patient2.setRoutineList(new ArrayList<Routine>());
            patient2.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient2.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient2.setTodaySchedule(schedule);

            // ==========================
            // Patient 3
            // ==========================
            Patient patient3 = new Patient();
            patient3.setFirstName("Clara");
            patient3.setLastName("Harris");
            patient3.setNric("1000003");
            patient3.setAddress("284 Edgewood Road Valley Stream");
            patient3.setHomeNumber("2255 2211");
            patient3.setPhoneNumber("1155 5522");
            patient3.setGender('F');
            patient3.setDob(DateTime.now().withYear(1948).withMonthOfYear(9).withDayOfMonth(23));
            Bitmap photo3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_03);
            patient3.setPhoto(photo3);
            patient3.setGuardianFullName("Cedric Wheeler");
            patient3.setGuardianContactNumber("2389 1283");
            patient3.setGuardianEmail("cedric@gmail.com");
            patient3.setHasAllergy(false);
            patient3.setAllergyList(new ArrayList<Allergy>());
            patient3.setVitalList(new ArrayList<Vital>());
            patient3.setRoutineList(new ArrayList<Routine>());
            patient3.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient3.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient3.setTodaySchedule(schedule);

            // ==========================
            // Patient 4
            // ==========================
            Patient patient4 = new Patient();
            patient4.setFirstName("Dianne");
            patient4.setLastName("Hopkins");
            patient4.setNric("1000004");
            patient4.setAddress("954 Route 29 Malden");
            patient4.setHomeNumber("2255 2211");
            patient4.setPhoneNumber("1155 5522");
            patient4.setGender('F');
            patient4.setDob(DateTime.now().withYear(1950).withMonthOfYear(5).withDayOfMonth(4));
            Bitmap photo4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_04);
            patient4.setPhoto(photo4);
            patient4.setGuardianFullName("Leslie Huff");
            patient4.setGuardianContactNumber("2389 1283");
            patient4.setGuardianEmail("leslie@gmail.com");
            patient4.setHasAllergy(false);
            patient4.setAllergyList(new ArrayList<Allergy>());
            patient4.setVitalList(new ArrayList<Vital>());
            patient4.setRoutineList(new ArrayList<Routine>());
            patient4.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient4.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient4.setTodaySchedule(schedule);

            // ==========================
            // Patient 5
            // ==========================
            Patient patient5 = new Patient();
            patient5.setFirstName("Emma");
            patient5.setLastName("Shaw");
            patient5.setNric("1000005");
            patient5.setAddress("755 Sycamore Street Ann Arbor");
            patient5.setHomeNumber("2255 2211");
            patient5.setPhoneNumber("1155 5522");
            patient5.setGender('F');
            patient5.setDob(DateTime.now().withYear(1938).withMonthOfYear(1).withDayOfMonth(28));
            Bitmap photo5 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_05);
            patient5.setPhoto(photo5);
            patient5.setGuardianFullName("Andy Perkins");
            patient5.setGuardianContactNumber("2389 1283");
            patient5.setGuardianEmail("andy@gmail.com");
            patient5.setHasAllergy(false);
            patient5.setAllergyList(new ArrayList<Allergy>());
            patient5.setVitalList(new ArrayList<Vital>());
            patient5.setRoutineList(new ArrayList<Routine>());
            patient5.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient5.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient5.setTodaySchedule(schedule);

            // ==========================
            // Patient 6
            // ==========================
            Patient patient6 = new Patient();
            patient6.setFirstName("Florence");
            patient6.setLastName("Hines");
            patient6.setNric("1000006");
            patient6.setAddress("915 Evergreen Lane Desoto");
            patient6.setHomeNumber("2255 2211");
            patient6.setPhoneNumber("1155 5522");
            patient6.setGender('F');
            patient6.setDob(DateTime.now().withYear(1944).withMonthOfYear(8).withDayOfMonth(16));
            Bitmap photo6 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_06);
            patient6.setPhoto(photo6);
            patient6.setGuardianFullName("Ramiro Gross");
            patient6.setGuardianContactNumber("2389 1283");
            patient6.setGuardianEmail("ramiro@gmail.com");
            patient6.setHasAllergy(false);
            patient6.setAllergyList(new ArrayList<Allergy>());
            patient6.setVitalList(new ArrayList<Vital>());
            patient6.setRoutineList(new ArrayList<Routine>());
            patient6.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient6.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient6.setTodaySchedule(schedule);

            // ==========================
            // Patient 7
            // ==========================
            Patient patient7 = new Patient();
            patient7.setFirstName("Marco");
            patient7.setLastName("Sullivan");
            patient7.setNric("1000013");
            patient7.setAddress("170 Redwood Drive");
            patient7.setHomeNumber("5299 3085");
            patient7.setPhoneNumber("13");
            patient7.setGender('M');
            patient7.setDob(DateTime.now().withYear(1948).withMonthOfYear(10).withDayOfMonth(17));
            Bitmap photo7 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_13);
            patient7.setPhoto(photo7);
            patient7.setGuardianFullName("Eugene Gilbert");
            patient7.setGuardianContactNumber("8520 6802");
            patient7.setGuardianEmail("eugene@gmail.com");
            patient7.setHasAllergy(false);
            patient7.setAllergyList(new ArrayList<Allergy>());
            patient7.setVitalList(new ArrayList<Vital>());
            patient7.setRoutineList(new ArrayList<Routine>());
            patient7.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient7.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient7.setTodaySchedule(schedule);

            // ==========================
            // Patient 8
            // ==========================
            Patient patient8 = new Patient();
            patient8.setFirstName("Irving");
            patient8.setLastName("Bradley");
            patient8.setNric("1000009");
            patient8.setAddress("822 4th Street South");
            patient8.setHomeNumber("6340 4877");
            patient8.setPhoneNumber("09");
            patient8.setGender('M');
            patient8.setDob(DateTime.now().withYear(1933).withMonthOfYear(6).withDayOfMonth(14));
            Bitmap photo8 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_09);
            patient8.setPhoto(photo8);
            patient8.setGuardianFullName("Danny Martin");
            patient8.setGuardianContactNumber("3279 9029");
            patient8.setGuardianEmail("danny@gmail.com");
            patient8.setHasAllergy(false);
            patient8.setAllergyList(new ArrayList<Allergy>());
            patient8.setVitalList(new ArrayList<Vital>());
            patient8.setRoutineList(new ArrayList<Routine>());
            patient8.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient8.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient8.setTodaySchedule(schedule);

            // ==========================
            // Patient 9
            // ==========================
            Patient patient9 = new Patient();
            patient9.setFirstName("Ruby");
            patient9.setLastName("Robinson");
            patient9.setNric("1000017");
            patient9.setAddress("49 Oak Avenue");
            patient9.setHomeNumber("2701 7736");
            patient9.setPhoneNumber("17");
            patient9.setGender('F');
            patient9.setDob(DateTime.now().withYear(1955).withMonthOfYear(10).withDayOfMonth(19));
            Bitmap photo9 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_17);
            patient9.setPhoto(photo9);
            patient9.setGuardianFullName("Abraham Pratt");
            patient9.setGuardianContactNumber("1322 2393");
            patient9.setGuardianEmail("abraham@gmail.com");
            patient9.setHasAllergy(false);
            patient9.setAllergyList(new ArrayList<Allergy>());
            patient9.setVitalList(new ArrayList<Vital>());
            patient9.setRoutineList(new ArrayList<Routine>());
            patient9.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient9.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient9.setTodaySchedule(schedule);

            // ==========================
            // Patient 10
            // ==========================
            Patient patient10 = new Patient();
            patient10.setFirstName("Jeremiah");
            patient10.setLastName("Morales");
            patient10.setNric("1000010");
            patient10.setAddress("456 Linda Lane");
            patient10.setHomeNumber("1362 5477");
            patient10.setPhoneNumber("10");
            patient10.setGender('M');
            patient10.setDob(DateTime.now().withYear(1932).withMonthOfYear(1).withDayOfMonth(6));
            Bitmap photo10 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_10);
            patient10.setPhoto(photo10);
            patient10.setGuardianFullName("Lowell Ramos");
            patient10.setGuardianContactNumber("6263 3831");
            patient10.setGuardianEmail("lowell@gmail.com");
            patient10.setHasAllergy(false);
            patient10.setAllergyList(new ArrayList<Allergy>());
            patient10.setVitalList(new ArrayList<Vital>());
            patient10.setRoutineList(new ArrayList<Routine>());
            patient10.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient10.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient10.setTodaySchedule(schedule);

            // ==========================
            // Patient 11
            // ==========================
            Patient patient11 = new Patient();
            patient11.setFirstName("Tracey");
            patient11.setLastName("Nash");
            patient11.setNric("1000019");
            patient11.setAddress("806 Manor Drive");
            patient11.setHomeNumber("9235 6111");
            patient11.setPhoneNumber("19");
            patient11.setGender('F');
            patient11.setDob(DateTime.now().withYear(1953).withMonthOfYear(6).withDayOfMonth(25));
            Bitmap photo11 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_19);
            patient11.setPhoto(photo11);
            patient11.setGuardianFullName("Simon Little");
            patient11.setGuardianContactNumber("2684 1898");
            patient11.setGuardianEmail("simon@gmail.com");
            patient11.setHasAllergy(false);
            patient11.setAllergyList(new ArrayList<Allergy>());
            patient11.setVitalList(new ArrayList<Vital>());
            patient11.setRoutineList(new ArrayList<Routine>());
            patient11.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient11.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient11.setTodaySchedule(schedule);

            // ==========================
            // Patient 12
            // ==========================

            Patient patient12 = new Patient();
            patient12.setFirstName("Paula");
            patient12.setLastName("Baldwin");
            patient12.setNric("1000016");
            patient12.setAddress("584 5th Street West");
            patient12.setHomeNumber("7192 5710");
            patient12.setPhoneNumber("16");
            patient12.setGender('F');
            patient12.setDob(DateTime.now().withYear(1957).withMonthOfYear(12).withDayOfMonth(6));
            Bitmap photo12 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_16);
            patient12.setPhoto(photo12);
            patient12.setGuardianFullName("Javier Francis");
            patient12.setGuardianContactNumber("2728 5535");
            patient12.setGuardianEmail("javier@gmail.com");
            patient12.setHasAllergy(false);
            patient12.setAllergyList(new ArrayList<Allergy>());
            patient12.setVitalList(new ArrayList<Vital>());
            patient12.setRoutineList(new ArrayList<Routine>());
            patient12.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient12.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient12.setTodaySchedule(schedule);

            // ==========================
            // Patient 13
            // ==========================

            Patient patient13 = new Patient();
            patient13.setFirstName("Samuel");
            patient13.setLastName("Parsons");
            patient13.setNric("1000018");
            patient13.setAddress("87 Aspen Drive");
            patient13.setHomeNumber("2571 4886");
            patient13.setPhoneNumber("18");
            patient13.setGender('M');
            patient13.setDob(DateTime.now().withYear(1939).withMonthOfYear(9).withDayOfMonth(2));
            Bitmap photo13 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_18);
            patient13.setPhoto(photo13);
            patient13.setGuardianFullName("Mandy Leonard");
            patient13.setGuardianContactNumber("7388 1831");
            patient13.setGuardianEmail("mandy@gmail.com");
            patient13.setHasAllergy(false);
            patient13.setAllergyList(new ArrayList<Allergy>());
            patient13.setVitalList(new ArrayList<Vital>());
            patient13.setRoutineList(new ArrayList<Routine>());
            patient13.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient13.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient13.setTodaySchedule(schedule);

            // ==========================
            // Patient 14
            // ==========================

            Patient patient14 = new Patient();
            patient14.setFirstName("Lynn");
            patient14.setLastName("Simpson");
            patient14.setNric("1000012");
            patient14.setAddress("547 Route 1");
            patient14.setHomeNumber("1036 4996");
            patient14.setPhoneNumber("12");
            patient14.setGender('F');
            patient14.setDob(DateTime.now().withYear(1944).withMonthOfYear(10).withDayOfMonth(14));
            Bitmap photo14 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_12);
            patient14.setPhoto(photo14);
            patient14.setGuardianFullName("Jonathon Hunter");
            patient14.setGuardianContactNumber("7732 6018");
            patient14.setGuardianEmail("jonathon@gmail.com");
            patient14.setHasAllergy(false);
            patient14.setAllergyList(new ArrayList<Allergy>());
            patient14.setVitalList(new ArrayList<Vital>());
            patient14.setRoutineList(new ArrayList<Routine>());
            patient14.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient14.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient14.setTodaySchedule(schedule);

            // ==========================
            // Patient 15
            // ==========================

            Patient patient15 = new Patient();
            patient15.setFirstName("Nicolle");
            patient15.setLastName("Murray");
            patient15.setNric("1000014");
            patient15.setAddress("857 Clinton Street");
            patient15.setHomeNumber("6165 1682");
            patient15.setPhoneNumber("14");
            patient15.setGender('F');
            patient15.setDob(DateTime.now().withYear(1949).withMonthOfYear(9).withDayOfMonth(11));
            Bitmap photo15 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_14);
            patient15.setPhoto(photo15);
            patient15.setGuardianFullName("Annie Moreno");
            patient15.setGuardianContactNumber("5794 8534");
            patient15.setGuardianEmail("annie@gmail.com");
            patient15.setHasAllergy(false);
            patient15.setAllergyList(new ArrayList<Allergy>());
            patient15.setVitalList(new ArrayList<Vital>());
            patient15.setRoutineList(new ArrayList<Routine>());
            patient15.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient15.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient15.setTodaySchedule(schedule);

            // ==========================
            // Patient 16
            // ==========================

            Patient patient16 = new Patient();
            patient16.setFirstName("Horace");
            patient16.setLastName("Jacobs");
            patient16.setNric("1000008");
            patient16.setAddress("815 Forest Street");
            patient16.setHomeNumber("5713 3900");
            patient16.setPhoneNumber("08");
            patient16.setGender('M');
            patient16.setDob(DateTime.now().withYear(1943).withMonthOfYear(3).withDayOfMonth(11));
            Bitmap photo16 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_08);
            patient16.setPhoto(photo16);
            patient16.setGuardianFullName("Israel Miller");
            patient16.setGuardianContactNumber("2558 4666");
            patient16.setGuardianEmail("israel@gmail.com");
            patient16.setHasAllergy(false);
            patient16.setAllergyList(new ArrayList<Allergy>());
            patient16.setVitalList(new ArrayList<Vital>());
            patient16.setRoutineList(new ArrayList<Routine>());
            patient16.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient16.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient16.setTodaySchedule(schedule);

            // ==========================
            // Patient 17
            // ==========================

            Patient patient17 = new Patient();
            patient17.setFirstName("Vanessa");
            patient17.setLastName("Watkins");
            patient17.setNric("1000020");
            patient17.setAddress("286 Route 202");
            patient17.setHomeNumber("1795 7366");
            patient17.setPhoneNumber("20");
            patient17.setGender('F');
            patient17.setDob(DateTime.now().withYear(1944).withMonthOfYear(2).withDayOfMonth(27));
            Bitmap photo17 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_20);
            patient17.setPhoto(photo17);
            patient17.setGuardianFullName("Marsha Craig");
            patient17.setGuardianContactNumber("4460 7696");
            patient17.setGuardianEmail("marsha@gmail.com");
            patient17.setHasAllergy(false);
            patient17.setAllergyList(new ArrayList<Allergy>());
            patient17.setVitalList(new ArrayList<Vital>());
            patient17.setRoutineList(new ArrayList<Routine>());
            patient17.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient17.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient17.setTodaySchedule(schedule);

            // ==========================
            // Patient 18
            // ==========================

            Patient patient18 = new Patient();
            patient18.setFirstName("Glenda");
            patient18.setLastName("Mendez");
            patient18.setNric("1000007");
            patient18.setAddress("2 Sherman Street");
            patient18.setHomeNumber("6911 1095");
            patient18.setPhoneNumber("07");
            patient18.setGender('F');
            patient18.setDob(DateTime.now().withYear(1958).withMonthOfYear(4).withDayOfMonth(27));
            Bitmap photo18 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_07);
            patient18.setPhoto(photo18);
            patient18.setGuardianFullName("Beth Tyler");
            patient18.setGuardianContactNumber("7746 9022");
            patient18.setGuardianEmail("beth@gmail.com");
            patient18.setHasAllergy(false);
            patient18.setAllergyList(new ArrayList<Allergy>());
            patient18.setVitalList(new ArrayList<Vital>());
            patient18.setRoutineList(new ArrayList<Routine>());
            patient18.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient18.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient18.setTodaySchedule(schedule);

            // ==========================
            // Patient 19
            // ==========================

            Patient patient19 = new Patient();
            patient19.setFirstName("Otis");
            patient19.setLastName("Thompson");
            patient19.setNric("1000015");
            patient19.setAddress("351 Fairview Avenue");
            patient19.setHomeNumber("3631 4312");
            patient19.setPhoneNumber("15");
            patient19.setGender('M');
            patient19.setDob(DateTime.now().withYear(1960).withMonthOfYear(1).withDayOfMonth(16));
            Bitmap photo19 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_15);
            patient19.setPhoto(photo19);
            patient19.setGuardianFullName("Allan Frank");
            patient19.setGuardianContactNumber("6763 7083");
            patient19.setGuardianEmail("allan@gmail.com");
            patient19.setHasAllergy(false);
            patient19.setAllergyList(new ArrayList<Allergy>());
            patient19.setVitalList(new ArrayList<Vital>());
            patient19.setRoutineList(new ArrayList<Routine>());
            patient19.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient19.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient19.setTodaySchedule(schedule);

            // ==========================
            // Patient 20
            // ==========================

            Patient patient20 = new Patient();
            patient20.setFirstName("Kathryn");
            patient20.setLastName("Sherman");
            patient20.setNric("1000011");
            patient20.setAddress("417 Riverside Drive");
            patient20.setHomeNumber("3437 9236");
            patient20.setPhoneNumber("11");
            patient20.setGender('F');
            patient20.setDob(DateTime.now().withYear(1937).withMonthOfYear(9).withDayOfMonth(8));
            Bitmap photo20 = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_11);
            patient20.setPhoto(photo20);
            patient20.setGuardianFullName("Leo Daniels");
            patient20.setGuardianContactNumber("9259 9319");
            patient20.setGuardianEmail("leo@gmail.com");
            patient20.setHasAllergy(false);
            patient20.setAllergyList(new ArrayList<Allergy>());
            patient20.setVitalList(new ArrayList<Vital>());
            patient20.setRoutineList(new ArrayList<Routine>());
            patient20.setProblemLogList(new ArrayList<ProblemLog>());
            socialHistory = new SocialHistory();
            socialHistory.setLiveWith("Husband and 2 children");
            socialHistory.setDiet("Vegetarian");
            socialHistory.setReligion("Catholic");
            socialHistory.setIsSexuallyActive(false);
            socialHistory.setIsSecondhandSmoker(false);
            socialHistory.setAlcoholUse("Seldom");
            socialHistory.setCaffeineUse("Regular");
            socialHistory.setTobaccoUse("Never");
            socialHistory.setDrugUse("Never");
            socialHistory.setPet("2 dogs");
            socialHistory.setOccupation("Entrepreneur");
            socialHistory.setLike("Sandwich, salad");
            socialHistory.setDislike("Soft drinks");
            socialHistory.setHobby("");
            socialHistory.setHabbit("");
            socialHistory.setHolidayExperience("Thailand (2002), Bali (2010)");
            socialHistory.setEducation("Bachelor of Science, Computer Science - Princeton University");
            socialHistory.setExercise("Tennis, swimming");
            patient20.setSocialHistory(socialHistory);

            startTime1 = formatter.parseDateTime(todayDateStr + " 12:00");
            endTime1 = formatter.parseDateTime(todayDateStr + " 12:30");
            event1 = new Event("Lunch", "Patient is lactose intolerant", startTime1, endTime1);

            startTime2 = formatter.parseDateTime(todayDateStr + " 13:30");
            endTime2 = formatter.parseDateTime(todayDateStr + " 14:30");
            event2 = new Event("Games", "Play speed games", startTime2, endTime2);

            startTime3 = formatter.parseDateTime(todayDateStr + " 14:30");
            endTime3 = formatter.parseDateTime(todayDateStr + " 15:30");
            event3 = new Event("Games", "Play memory games", startTime3, endTime3);

            eventList = new ArrayList<>();
            eventList.add(event1);
            eventList.add(event2);
            eventList.add(event3);

            schedule = new Schedule();
            schedule.setEventList(eventList);
            patient20.setTodaySchedule(schedule);

            // ==========================
            // Adding to patientList
            // ==========================

            patientList.add(patient1);
            patientList.add(patient2);
            patientList.add(patient3);
            patientList.add(patient4);
            patientList.add(patient5);
            patientList.add(patient6);
            patientList.add(patient7);
            patientList.add(patient8);
            patientList.add(patient9);
            patientList.add(patient10);
            patientList.add(patient11);
            patientList.add(patient12);
            patientList.add(patient13);
            patientList.add(patient14);
            patientList.add(patient15);
            patientList.add(patient15);
            patientList.add(patient17);
            patientList.add(patient18);
            patientList.add(patient19);
            patientList.add(patient20);
        }
        return patientList;
    }
}