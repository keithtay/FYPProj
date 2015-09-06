package com.example.keith.fyp.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.example.keith.fyp.R;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialize.util.UIUtils;

/**
 * Created by Sutrisno on 6/9/2015.
 */
public class UtilsUi {

    public static void setNavigationDrawer(Activity activity, Bundle savedInstanceState) {
        Resources resource = activity.getResources();
        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(resource.getDrawable(R.drawable.avatar1));

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.account_header)
                .withTranslucentStatusBar(false)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();

        PrimaryDrawerItem homeDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_patient_list)
                .withIcon(GoogleMaterial.Icon.gmd_home)
                .withIdentifier(1);
        PrimaryDrawerItem notificationDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_notification)
                .withIcon(GoogleMaterial.Icon.gmd_notifications)
                .withBadge("2")
                .withBadgeStyle(new BadgeStyle(resource.getColor(R.color.red_100),
                        resource.getColor(R.color.red_100)))
                .withIdentifier(2);
        PrimaryDrawerItem accountDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_account)
                .withIcon(GoogleMaterial.Icon.gmd_person)
                .withIdentifier(3);
        PrimaryDrawerItem settingsDrawerItem = new PrimaryDrawerItem()
                .withName(R.string.nav_settings)
                .withIcon(GoogleMaterial.Icon.gmd_settings)
                .withIdentifier(4);

        Drawer navigationDrawer = new DrawerBuilder()
                .withActivity(activity)
                .withTranslucentStatusBar(false)
                .withAccountHeader(accountHeader)
                .addDrawerItems(
                        homeDrawerItem,
                        notificationDrawerItem,
                        accountDrawerItem,
                        settingsDrawerItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return true;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .buildView();

        MiniDrawer miniDrawer = new MiniDrawer()
                .withDrawer(navigationDrawer)
                .withInnerShadow(true)
                .withAccountHeader(accountHeader);

        int first = (int) UIUtils.convertDpToPixel(300, activity);
        int second = (int) UIUtils.convertDpToPixel(72, activity);

        View contentWrapper = activity.findViewById(R.id.contentWrapper);

        Crossfader crossFader = new Crossfader()
                .withContent(contentWrapper)
                .withFirst(navigationDrawer.getSlider(), first)
                .withSecond(miniDrawer.build(activity), second)
                .withSavedInstance(savedInstanceState)
                .build();

        miniDrawer.withCrossFader(new CrossfadeWrapper(crossFader));
    }
}
