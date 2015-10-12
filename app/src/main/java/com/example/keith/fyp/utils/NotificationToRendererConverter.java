package com.example.keith.fyp.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Allergy;
import com.example.keith.fyp.models.Notification;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.models.ProblemLog;
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
 * Created by Sutrisno on 24/9/2015.
 */
public class NotificationToRendererConverter {
    public static NotificationRenderer convert(Context context, Notification notification) {
        LayoutInflater inflater = LayoutInflater.from(context);

        BackgroundRenderer backgroundRenderer = new BackgroundRenderer(inflater);

        HeaderRenderer headerRenderer = new HeaderRenderer(inflater, notification.getSenderPhoto(), notification.getSenderName(), notification.getCreationDate(), notification.getSummary());

        ActionRenderer actionRenderer = new ActionRenderer(inflater);

        ContentRenderer contentRenderer = null;

        switch(notification.getType()) {
            case Notification.TYPE_GAME_RECOMMENDATION:
                contentRenderer = new ContentGameRecommendationRenderer(inflater);
                break;
            case Notification.TYPE_NEW_LOG:
                ProblemLog problemLog = new ProblemLog(UtilsUi.generateUniqueId(), DateTime.now(), "Emotion", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eu odio diam. Morbi sit amet erat at libero ullamcorper mollis et at turpis. Nam consequat ex sem, non ultricies risus molestie vel. Aenean placerat bibendum ipsum, eu volutpat sem pharetra sit amet. Proin metus nisi, lacinia id pharetra a, sollicitudin sit amet sapien. Vestibulum vehicula magna sit amet justo convallis tempor");
                contentRenderer = new ContentNewInfoObjectRenderer(inflater, problemLog);
                break;
            case Notification.TYPE_NEW_PATIENT:
                Patient newPatient = new Patient();
                newPatient.setFirstName("Laura");
                newPatient.setLastName("Freeman");
                newPatient.setGender('F');
                newPatient.setDob(DateTime.now().withYear(1955).withMonthOfYear(5).withDayOfMonth(12));
                newPatient.setPhoto(BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar_20));
                contentRenderer = new ContentNewPatientRenderer(inflater, newPatient);
                break;
            case Notification.TYPE_UPDATE_INFO_OBJECT:
                Allergy oldAllergy = new Allergy("Milk", "Itchy skin", "");
                Allergy newAllergy = new Allergy("Milk", "Itchy skin", "Wash the skin with cold cloth");
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
