package com.example.keith.fyp.utils;

import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.Prescription;
import com.example.keith.fyp.models.ProblemLog;
import com.example.keith.fyp.models.Routine;
import com.example.keith.fyp.models.SocialHistory;
import com.example.keith.fyp.models.Vital;
import com.example.keith.fyp.renderers.ActionRenderer;
import com.example.keith.fyp.renderers.BackgroundRenderer;
import com.example.keith.fyp.renderers.ContentGameRecommendationRenderer;
import com.example.keith.fyp.renderers.ContentNewInfoObjectRenderer;
import com.example.keith.fyp.renderers.ContentNewPatientRenderer;
import com.example.keith.fyp.renderers.ContentRejectionInfoRenderer;
import com.example.keith.fyp.renderers.ContentRenderer;
import com.example.keith.fyp.renderers.ContentUpdateInfoFieldRenderer;
import com.example.keith.fyp.renderers.ContentUpdateInfoObjectRenderer;
import com.example.keith.fyp.renderers.HeaderRenderer;
import com.example.keith.fyp.renderers.NotificationRenderer;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * NotificationToRendererConverter is a singleton class to
 * convert a {@link Notification} to its {@link NotificationRenderer}
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class NotificationToRendererConverter {

    /**
     * Method to convert a {@link Notification} to its {@link NotificationRenderer}
     * @param context context of application
     * @param notification notification to be converted
     * @return renderer to render the notification
     */
    public static NotificationRenderer convert(Context context, Notification notification) {
        LayoutInflater inflater = LayoutInflater.from(context);

        BackgroundRenderer backgroundRenderer = new BackgroundRenderer(inflater);

        HeaderRenderer headerRenderer = new HeaderRenderer(inflater, notification.getSenderPhoto(), notification.getSenderName(), notification.getCreationDate(), notification.getSummary());

        ActionRenderer actionRenderer = new ActionRenderer(inflater);

        ContentRenderer contentRenderer = null;
        String logData = notification.getLogData();
        String additionalInfo = notification.getAdditionalInfo();
        String reject = notification.getRejectionReason();
        int k = notification.getRa();
        switch(notification.getType()) {
            case Notification.TYPE_GAME_RECOMMENDATION:
                contentRenderer = new ContentGameRecommendationRenderer(inflater);
                break;
            case Notification.TYPE_NEW_INFO_OBJECT:
                if(additionalInfo.equals("allergy")){
                    String[] allg1 = logData.split(";");
                    Allergy allgery = new Allergy(allg1[0], allg1[1],allg1[2]);
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, allgery);
                }else if(additionalInfo.equals("vital")){
                    String[] vital1 = logData.split(";");
                    String test = vital1[0];
                    DateTime a = DateTime.parse(test);
                    Vital vital2 = new Vital(a, Boolean.valueOf(vital1[1]),Float.parseFloat(vital1[2]),
                            Float.parseFloat(vital1[3]),Float.parseFloat(vital1[4]),Float.parseFloat(vital1[5]),Float.parseFloat(vital1[6]),
                            vital1[7]);
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, vital2);
                }else if(additionalInfo.equals("social history")){
                    String[] socialList = logData.split(";");
                    SocialHistory socialHistory = new SocialHistory(socialList[0],socialList[1],socialList[2], Boolean.valueOf(socialList[3]), Boolean.valueOf(socialList[4]),
                            socialList[5],socialList[6],socialList[7],socialList[8],socialList[9],socialList[10],socialList[11],socialList[12],socialList[13],socialList[14],
                            socialList[15],socialList[16],socialList[17]);
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, socialHistory);
                }else if(additionalInfo.equals("prescription")){
                    String[] prescri = logData.split(";");
                    String dateStart = prescri[4];
                    String endDate = prescri[5];
                    int year = Integer.parseInt(dateStart.substring(0, 4));
                    int month = Integer.parseInt(dateStart.substring(5, 7));
                    int day = Integer.parseInt(dateStart.substring(8, 10));
                    Log.v("Checking1", dateStart.substring(0, 4) + " " + dateStart.substring(5, 7) + " " + dateStart.substring(8, 10));
                    int year1 = Integer.parseInt(endDate.substring(0, 4));
                    int month1 = Integer.parseInt(endDate.substring(5, 7));
                    int day1 = Integer.parseInt(endDate.substring(8, 10));

                    DateTime test = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day);
                    DateTime test2 = DateTime.now().withYear(year1).withMonthOfYear(month1).withDayOfMonth(day1);
                    Prescription pe = new Prescription(prescri[0],prescri[1],Integer.parseInt(prescri[2]),prescri[3],test,
                            test2,prescri[6],prescri[7]);
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, pe);
                }else if(additionalInfo.equals("routine")){
                    String[] ro = logData.split(";");
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
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, rot);
                }else if(additionalInfo.equals("problem log")){
                    String[] pl = logData.split(";");
                    String dateStart = pl[0];
                    int year = Integer.parseInt(dateStart.substring(0,4));
                    int month = Integer.parseInt(dateStart.substring(5, 7));
                    int day = Integer.parseInt(dateStart.substring(8, 10));
                    DateTime test = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day);
                    ProblemLog newProblemLog = new ProblemLog(UtilsUi.generateUniqueId(), test, pl[1], pl[2]);
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, newProblemLog);
                }
//                ProblemLog problemLog = new ProblemLog(UtilsUi.generateUniqueId(), DateTime.now(), "Emotion", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eu odio diam. Morbi sit amet erat at libero ullamcorper mollis et at turpis. Nam consequat ex sem, non ultricies risus molestie vel. Aenean placerat bibendum ipsum, eu volutpat sem pharetra sit amet. Proin metus nisi, lacinia id pharetra a, sollicitudin sit amet sapien. Vestibulum vehicula magna sit amet justo convallis tempor");
//                contentRenderer = new ContentNewInfoObjectRenderer(inflater, problemLog);
                break;
            case Notification.TYPE_DELETE_INFO_OBJECT:
                if(additionalInfo.equals("allergy")){
                    String[] allg1 = logData.split(";");
                    Allergy allgery = new Allergy(allg1[0], allg1[1],allg1[2]);
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, allgery);
                }else if(additionalInfo.equals("vital")){
                    String[] vital1 = logData.split(";");
                    String test = vital1[0];
                    DateTime a = DateTime.parse(test);
                    Vital vital2 = new Vital(a, Boolean.valueOf(vital1[1]),Float.parseFloat(vital1[2]),
                            Float.parseFloat(vital1[3]),Float.parseFloat(vital1[4]),Float.parseFloat(vital1[5]),Float.parseFloat(vital1[6]),
                            vital1[7]);
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, vital2);
                }else if(additionalInfo.equals("social history")){
                    String[] socialList = logData.split(";");
                    SocialHistory socialHistory = new SocialHistory(socialList[0],socialList[1],socialList[2], Boolean.valueOf(socialList[3]), Boolean.valueOf(socialList[4]),
                            socialList[5],socialList[6],socialList[7],socialList[8],socialList[9],socialList[10],socialList[11],socialList[12],socialList[13],socialList[14],
                            socialList[15],socialList[16],socialList[17]);
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, socialHistory);
                }else if(additionalInfo.equals("prescription")){
                    String[] prescri = logData.split(";");
                    String dateStart = prescri[4];
                    String endDate = prescri[5];
                    int year = Integer.parseInt(dateStart.substring(0, 4));
                    int month = Integer.parseInt(dateStart.substring(5, 7));
                    int day = Integer.parseInt(dateStart.substring(8, 10));
                    Log.v("Checking1", dateStart.substring(0, 4) + " " + dateStart.substring(5, 7) + " " + dateStart.substring(8, 10));
                    int year1 = Integer.parseInt(endDate.substring(0, 4));
                    int month1 = Integer.parseInt(endDate.substring(5, 7));
                    int day1 = Integer.parseInt(endDate.substring(8, 10));

                    DateTime test = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day);
                    DateTime test2 = DateTime.now().withYear(year1).withMonthOfYear(month1).withDayOfMonth(day1);
                    Prescription pe = new Prescription(prescri[0],prescri[1],Integer.parseInt(prescri[2]),prescri[3],test,
                            test2,prescri[6],prescri[7]);
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, pe);
                }else if(additionalInfo.equals("routine")){
                    String[] ro = logData.split(";");
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
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, rot);
                }else if(additionalInfo.equals("problem log")){
                    String[] pl = logData.split(";");
                    String dateStart = pl[0];
                    int year = Integer.parseInt(dateStart.substring(0,4));
                    int month = Integer.parseInt(dateStart.substring(5, 7));
                    int day = Integer.parseInt(dateStart.substring(8, 10));
                    DateTime test = DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day);
                    ProblemLog newProblemLog = new ProblemLog(UtilsUi.generateUniqueId(), test, pl[1], pl[2]);
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, newProblemLog);
                }
//                ProblemLog problemLog = new ProblemLog(UtilsUi.generateUniqueId(), DateTime.now(), "Emotion", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eu odio diam. Morbi sit amet erat at libero ullamcorper mollis et at turpis. Nam consequat ex sem, non ultricies risus molestie vel. Aenean placerat bibendum ipsum, eu volutpat sem pharetra sit amet. Proin metus nisi, lacinia id pharetra a, sollicitudin sit amet sapien. Vestibulum vehicula magna sit amet justo convallis tempor");
//                contentRenderer = new ContentNewInfoObjectRenderer(inflater, problemLog);
                break;
            case Notification.TYPE_NEW_PATIENT:
                String[] patientDetail = logData.split(";");
                Patient newPatient = new Patient();
                newPatient.setFirstName(patientDetail[0]);
                newPatient.setLastName(patientDetail[1]);
                newPatient.setGender(patientDetail[2].charAt(0));
                String dat = patientDetail[3];
                int year = Integer.parseInt(dat.substring(0,4));
                int month = Integer.parseInt(dat.substring(5, 7));
                int day = Integer.parseInt(dat.substring(8,10));
                newPatient.setDob(DateTime.now().withYear(year).withMonthOfYear(month).withDayOfMonth(day));
                newPatient.setNric(patientDetail[4]);
                newPatient.setAddress(patientDetail[5]);
                newPatient.setHomeNumber(patientDetail[6]);
                newPatient.setPhoneNumber(patientDetail[7]);
                newPatient.setGuardianFullName(patientDetail[8]);
                newPatient.setGuardianContactNumber(patientDetail[9]);
                newPatient.setGuardianEmail(patientDetail[10]);
//                dbfile db=new dbfile();
//                newPatient.setPhoto(db.getPatientProfilePic(db.getImageFilePath(k),context));
                newPatient.setPhoto(BitmapFactory.decodeResource(context.getResources(), R.drawable.anonymous));
                newPatient.setHasAllergy(false);
                newPatient.setAllergyList(new ArrayList<Allergy>());
                newPatient.setVitalList(new ArrayList<Vital>());
                newPatient.setRoutineList(new ArrayList<Routine>());
                newPatient.setProblemLogList(new ArrayList<ProblemLog>());
                contentRenderer = new ContentNewPatientRenderer(inflater, newPatient);
                break;
            case Notification.TYPE_UPDATE_INFO_OBJECT:
                //have to use variable k to go to patientspecinfo and retrieve information
                if(additionalInfo.equals("allergy")){
                    String[] allg1 = logData.split(">");
                    String[] allg2 = allg1[0].split(";");;
                    String[] allg3 = allg1[1].split(";");
                    Allergy allgery = new Allergy(allg2[0], allg2[1],allg2[2]);
                    Allergy allgery1 = new Allergy(allg3[0], allg3[1],allg3[2]);
                    //content renderer old then new. DB is stored new to old
                    contentRenderer = new ContentUpdateInfoObjectRenderer(inflater, allgery1, allgery);
                }else if(additionalInfo.equals("vital")){
                    String[] vital1 = logData.split(">");
                    String[] vital2 = vital1[0].split(";");;
                    String[] vital3 = vital1[1].split(";");
                    String test = vital2[0];
                    DateTime a = DateTime.parse(test);
                    Vital vital4 = new Vital(a, Boolean.valueOf(vital2[1]),Float.parseFloat(vital2[2]),
                            Float.parseFloat(vital2[3]),Float.parseFloat(vital2[4]),Float.parseFloat(vital2[5]),Float.parseFloat(vital2[6]),
                            vital2[7]);
                    String test2 = vital3[0];
                    DateTime a2 = DateTime.parse(test2);
                    Vital vital5 = new Vital(a2, Boolean.valueOf(vital3[1]),Float.parseFloat(vital3[2]),
                            Float.parseFloat(vital3[3]),Float.parseFloat(vital3[4]),Float.parseFloat(vital3[5]),Float.parseFloat(vital3[6]),
                            vital3[7]);
                    contentRenderer = new ContentUpdateInfoObjectRenderer(inflater, vital5, vital4);
                }else if(additionalInfo.equals("social history")){
                    String[] socialList = logData.split(">");
                    String[] socialList1 = socialList[0].split(";");
                    String[] socialList2 = socialList[1].split(";");
                    SocialHistory socialHistory = new SocialHistory(socialList1[0],socialList1[1],socialList1[2], Boolean.valueOf(socialList1[3]), Boolean.valueOf(socialList1[4]),
                            socialList1[5],socialList1[6],socialList1[7],socialList1[8],socialList1[9],socialList1[10],socialList1[11],socialList1[12],socialList1[13],socialList1[14],
                            socialList1[15],socialList1[16],socialList1[17]);
                    SocialHistory socialHistory2 = new SocialHistory(socialList2[0],socialList2[1],socialList2[2], Boolean.valueOf(socialList2[3]), Boolean.valueOf(socialList2[4]),
                            socialList2[5],socialList2[6],socialList2[7],socialList2[8],socialList2[9],socialList2[10],socialList2[11],socialList2[12],socialList2[13],socialList2[14],
                            socialList2[15],socialList2[16],socialList2[17]);
                    contentRenderer = new ContentUpdateInfoObjectRenderer(inflater, socialHistory2, socialHistory);
                }else if(additionalInfo.equals("prescription")){
                    String[] prescri = logData.split(">");
                    String[] prescri1 = prescri[0].split(";");
                    String[] prescri2 = prescri[1].split(";");
                    String dateStart = prescri1[4];
                    String endDate = prescri1[5];
                    int year2 = Integer.parseInt(dateStart.substring(0, 4));
                    int month2 = Integer.parseInt(dateStart.substring(5, 7));
                    int day2 = Integer.parseInt(dateStart.substring(8, 10));
                    int year1 = Integer.parseInt(endDate.substring(0, 4));
                    int month1 = Integer.parseInt(endDate.substring(5, 7));
                    int day1 = Integer.parseInt(endDate.substring(8, 10));
                    DateTime test = DateTime.now().withYear(year2).withMonthOfYear(month2).withDayOfMonth(day2);
                    DateTime test2 = DateTime.now().withYear(year1).withMonthOfYear(month1).withDayOfMonth(day1);
                    Prescription pe = new Prescription(prescri1[0],prescri1[1],Integer.parseInt(prescri1[2]),prescri1[3],test,
                            test2,prescri1[6],prescri1[7]);

                    String dateStart1 = prescri2[4];
                    String endDate1 = prescri2[5];
                    int year4 = Integer.parseInt(dateStart1.substring(0, 4));
                    int month4 = Integer.parseInt(dateStart1.substring(5, 7));
                    int day4 = Integer.parseInt(dateStart1.substring(8, 10));
                    int year5 = Integer.parseInt(endDate1.substring(0, 4));
                    int month5 = Integer.parseInt(endDate1.substring(5, 7));
                    int day5 = Integer.parseInt(endDate1.substring(8, 10));
                    DateTime test4 = DateTime.now().withYear(year4).withMonthOfYear(month4).withDayOfMonth(day4);
                    DateTime test5 = DateTime.now().withYear(year5).withMonthOfYear(month5).withDayOfMonth(day5);
                    Prescription pe1 = new Prescription(prescri2[0],prescri2[1],Integer.parseInt(prescri2[2]),prescri2[3],test4,
                            test5,prescri2[6],prescri2[7]);
                    contentRenderer = new ContentUpdateInfoObjectRenderer(inflater, pe1, pe);
                }else if(additionalInfo.equals("routine")){
                    String[] ro = logData.split(">");
                    String[] ro1 = ro[0].split(";");
                    String[] ro2 = ro[1].split(";");
                    String dateStart = ro1[2];
                    String endDate = ro1[3];
                    int year2 = Integer.parseInt(dateStart.substring(0, 4));
                    int month2 = Integer.parseInt(dateStart.substring(5, 7));
                    int day2 = Integer.parseInt(dateStart.substring(8, 10));
                    int year1 = Integer.parseInt(endDate.substring(0, 4));
                    int month1 = Integer.parseInt(endDate.substring(5, 7));
                    int day1 = Integer.parseInt(endDate.substring(8, 10));
                    DateTime test = DateTime.now().withYear(year2).withMonthOfYear(month2).withDayOfMonth(day2);
                    DateTime test2 = DateTime.now().withYear(year1).withMonthOfYear(month1).withDayOfMonth(day1);

                    String timeStart = ro1[4];
                    String timeEnd = ro1[5];
                    int hour = Integer.parseInt(timeStart.substring(11, 13));
                    int min = Integer.parseInt(timeStart.substring(14, 16));
                    int sec = Integer.parseInt(timeStart.substring(17, 19));
                    int hour1 = Integer.parseInt(timeEnd.substring(11, 13));
                    int min1 = Integer.parseInt(timeEnd.substring(14, 16));
                    int sec1 = Integer.parseInt(timeEnd.substring(17, 19));
                    DateTime test3 = DateTime.now().withYear(year2).withMonthOfYear(month2).withDayOfMonth(day2).withHourOfDay(hour).withMinuteOfHour(min).withSecondOfMinute(sec);
                    DateTime test4 = DateTime.now().withYear(year1).withMonthOfYear(month1).withDayOfMonth(day1).withHourOfDay(hour1).withMinuteOfHour(min1).withSecondOfMinute(sec1);
                    Routine rot = new Routine(ro1[0],ro1[1],test,test2,test3,test4,Integer.parseInt(ro1[6]),ro1[7]);


                    String dateStart2 = ro2[2];
                    String endDate2 = ro2[3];
                    int year4 = Integer.parseInt(dateStart2.substring(0, 4));
                    int month4 = Integer.parseInt(dateStart2.substring(5, 7));
                    int day4 = Integer.parseInt(dateStart2.substring(8, 10));
                    int year5 = Integer.parseInt(endDate2.substring(0, 4));
                    int month5 = Integer.parseInt(endDate2.substring(5, 7));
                    int day5 = Integer.parseInt(endDate2.substring(8, 10));
                    DateTime test5 = DateTime.now().withYear(year4).withMonthOfYear(month4).withDayOfMonth(day4);
                    DateTime test6 = DateTime.now().withYear(year5).withMonthOfYear(month5).withDayOfMonth(day5);

                    String timeStart4 = ro2[4];
                    String timeEnd4 = ro2[5];
                    int hour4 = Integer.parseInt(timeStart4.substring(11, 13));
                    int min4 = Integer.parseInt(timeStart4.substring(14, 16));
                    int sec4 = Integer.parseInt(timeStart4.substring(17, 19));
                    int hour5 = Integer.parseInt(timeEnd4.substring(11, 13));
                    int min5 = Integer.parseInt(timeEnd4.substring(14, 16));
                    int sec5 = Integer.parseInt(timeEnd4.substring(17, 19));
                    DateTime test7 = DateTime.now().withYear(year4).withMonthOfYear(month4).withDayOfMonth(day4).withHourOfDay(hour4).withMinuteOfHour(min4).withSecondOfMinute(sec4);
                    DateTime test8 = DateTime.now().withYear(year5).withMonthOfYear(month5).withDayOfMonth(day5).withHourOfDay(hour5).withMinuteOfHour(min5).withSecondOfMinute(sec5);
                    Routine rot1 = new Routine(ro2[0],ro2[1],test5,test6,test7,test8,Integer.parseInt(ro2[6]),ro2[7]);
                    contentRenderer = new ContentUpdateInfoObjectRenderer(inflater, rot1, rot);
                }else if(additionalInfo.equals("problem log")){
                    String[] pl = logData.split(">");
                    String[] pl2 = pl[0].split(";");
                    String[] pl3 = pl[1].split(";");
                    String dateStart = pl2[0];
                    int year9 = Integer.parseInt(dateStart.substring(0,4));
                    int month9 = Integer.parseInt(dateStart.substring(5, 7));
                    int day9 = Integer.parseInt(dateStart.substring(8, 10));
                    DateTime test = DateTime.now().withYear(year9).withMonthOfYear(month9).withDayOfMonth(day9);
                    ProblemLog newProblemLog = new ProblemLog(UtilsUi.generateUniqueId(), test, pl2[1], pl2[2]);

                    String dateStart2 = pl3[0];
                    int year19 = Integer.parseInt(dateStart2.substring(0,4));
                    int month19 = Integer.parseInt(dateStart2.substring(5, 7));
                    int day19 = Integer.parseInt(dateStart2.substring(8, 10));
                    DateTime test2 = DateTime.now().withYear(year19).withMonthOfYear(month19).withDayOfMonth(day19);
                    ProblemLog newProblemLog1 = new ProblemLog(UtilsUi.generateUniqueId(), test2, pl3[1], pl3[2]);
                    contentRenderer = new ContentUpdateInfoObjectRenderer(inflater, newProblemLog1, newProblemLog);
                }
                break;
            case Notification.TYPE_UPDATE_INFO_FIELD:
                String[] info = logData.split(";");
                contentRenderer = new ContentUpdateInfoFieldRenderer(inflater, "Personal Information", additionalInfo, info[0], info[1]);
                break;
            case Notification.TYPE_REJECTION_INFO_OBJECT:
                contentRenderer = new ContentRejectionInfoRenderer(inflater, "Rejection of information", reject);
                break;
        }

        NotificationRenderer renderer = new NotificationRenderer(inflater, backgroundRenderer, headerRenderer, contentRenderer, actionRenderer, notification);

        return renderer;
    }
}
