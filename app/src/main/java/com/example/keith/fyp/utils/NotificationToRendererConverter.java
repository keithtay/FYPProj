package com.example.keith.fyp.utils;

import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;

import com.example.keith.fyp.R;
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
import com.example.keith.fyp.renderers.ContentRenderer;
import com.example.keith.fyp.renderers.ContentUpdateInfoFieldRenderer;
import com.example.keith.fyp.renderers.ContentUpdateInfoObjectRenderer;
import com.example.keith.fyp.renderers.HeaderRenderer;
import com.example.keith.fyp.renderers.NotificationRenderer;

import org.joda.time.DateTime;

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
                    contentRenderer = new ContentNewInfoObjectRenderer(inflater, socialHistory);
//                    ProblemLog problemLog = new ProblemLog(UtilsUi.generateUniqueId(), DateTime.now(), "Emotion", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eu odio diam. Morbi sit amet erat at libero ullamcorper mollis et at turpis. Nam consequat ex sem, non ultricies risus molestie vel. Aenean placerat bibendum ipsum, eu volutpat sem pharetra sit amet. Proin metus nisi, lacinia id pharetra a, sollicitudin sit amet sapien. Vestibulum vehicula magna sit amet justo convallis tempor");
//                contentRenderer = new ContentNewInfoObjectRenderer(inflater, problemLog);
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
                newPatient.setPhoto(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_20));
                contentRenderer = new ContentNewPatientRenderer(inflater, newPatient);
                break;
            case Notification.TYPE_UPDATE_INFO_OBJECT:
//                Allergy oldAllergy = new Allergy("Milk", "Itchy skin", "");
//                Allergy newAllergy = new Allergy("Milk", "Itchy skin", "Wash the skin with cold cloth");
//                contentRenderer = new ContentUpdateInfoObjectRenderer(inflater, oldAllergy, newAllergy);
                DateTime d = DateTime.now();
                Routine oldAllergy = new Routine("Milk", "Itchy skin", d,d,d,d,5,"String");
                Routine newAllergy = new Routine("Rubbish", "Itchy skin", d,d,d,d,5,"String");
                contentRenderer = new ContentUpdateInfoObjectRenderer(inflater, oldAllergy, newAllergy);
                break;
            case Notification.TYPE_UPDATE_INFO_FIELD:
                contentRenderer = new ContentUpdateInfoFieldRenderer(inflater, "Personal Information", "Address", "32 Nanyang Avenue #12-7-23", "32 Nanyang Avenue #12-7-24");
                break;
        }

        NotificationRenderer renderer = new NotificationRenderer(inflater, backgroundRenderer, headerRenderer, contentRenderer, actionRenderer, notification);

        return renderer;
    }
}
