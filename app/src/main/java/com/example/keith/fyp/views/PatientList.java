package com.example.keith.fyp.views;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.keith.fyp.R;
import com.example.keith.fyp.utils.CrossfadeWrapper;
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

public class PatientList extends ActionBarActivity {

    private Drawer navigationDrawer;
    private AccountHeader accountHeader;
    private MiniDrawer miniDrawer = null;
    private Crossfader crossFader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);


        final IProfile profile = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.avatar1));

        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.account_header)
                .withTranslucentStatusBar(false)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();

        PrimaryDrawerItem homeDrawerItem = new PrimaryDrawerItem()
                .withName("Home")
                .withIcon(GoogleMaterial.Icon.gmd_home)
                .withIdentifier(1);
        PrimaryDrawerItem notificationDrawerItem = new PrimaryDrawerItem()
                .withName("Notification")
                .withIcon(GoogleMaterial.Icon.gmd_notifications)
                .withBadge("2")
                .withBadgeStyle(new BadgeStyle(getResources().getColor(R.color.red_100),
                        getResources().getColor(R.color.red_100)))
                .withIdentifier(2);
        PrimaryDrawerItem accountDrawerItem = new PrimaryDrawerItem()
                .withName("Account")
                .withIcon(GoogleMaterial.Icon.gmd_person)
                .withIdentifier(3);
        PrimaryDrawerItem settingsDrawerItem = new PrimaryDrawerItem()
                .withName("Settings")
                .withIcon(GoogleMaterial.Icon.gmd_settings)
                .withIdentifier(4);

        navigationDrawer = new DrawerBuilder()
                .withActivity(this)
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

        miniDrawer = new MiniDrawer()
                .withDrawer(navigationDrawer)
                .withInnerShadow(true)
                .withAccountHeader(accountHeader);


        int first = (int) UIUtils.convertDpToPixel(300, this);
        int second = (int) UIUtils.convertDpToPixel(72, this);

        crossFader = new Crossfader()
                .withContent(findViewById(R.id.contentWrapper))
                .withFirst(navigationDrawer.getSlider(), first)
                .withSecond(miniDrawer.build(this), second)
                .withSavedInstance(savedInstanceState)
                .build();

        miniDrawer.withCrossFader(new CrossfadeWrapper(crossFader));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patientlist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
            //testing
        }

        return super.onOptionsItemSelected(item);
    }
}
