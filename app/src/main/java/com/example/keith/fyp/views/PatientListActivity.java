package com.example.keith.fyp.views;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.models.Patient;
import com.example.keith.fyp.utils.CrossfadeWrapper;
import com.example.keith.fyp.views.adapters.PatientListAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class PatientListActivity extends ActionBarActivity {

    private Drawer navigationDrawer;
    private AccountHeader accountHeader;
    private MiniDrawer miniDrawer = null;
    private Crossfader crossFader;
    private RecyclerView patientListRecyclerView;
    private PatientListAdapter patientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);

        // ================
        // Prepare the patient list
        // ================
        patientListAdapter = new PatientListAdapter(this, getPatientList());

        patientListRecyclerView = (RecyclerView) findViewById(R.id.patientListGrid);
        patientListRecyclerView.setAdapter(patientListAdapter);
        int numOfColumn = 4;
        patientListRecyclerView.setLayoutManager(new GridLayoutManager(this, numOfColumn));
        patientListRecyclerView.addItemDecoration(
                new SpacesItemDecoration((int) getResources().getDimension(R.dimen.activity_content_root_padding)));

        // ================
        // Prepare the mini navigation drawer
        // ================
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

    // TODO: Receive patient data from database, instead of using hardcoded data
    private List<Patient> getPatientList() {
        List<Patient> patientList = new ArrayList<>();

        int[] patientPhotos = {R.drawable.avatar_01,
                R.drawable.avatar_02,
                R.drawable.avatar_03,
                R.drawable.avatar_04,
                R.drawable.avatar_05,
                R.drawable.avatar_06,
                R.drawable.avatar_07,
                R.drawable.avatar_08,
                R.drawable.avatar_09,
                R.drawable.avatar_10,
                R.drawable.avatar_11,
                R.drawable.avatar_12,
                R.drawable.avatar_13,
                R.drawable.avatar_14,
                R.drawable.avatar_15,
                R.drawable.avatar_16,
                R.drawable.avatar_17,
                R.drawable.avatar_18,
                R.drawable.avatar_19,
                R.drawable.avatar_20};

        String[] patientNames = {"Andy",
                "Bob",
                "Charlie",
                "David",
                "Eve",
                "Florence",
                "Gordon",
                "Hilda",
                "Ivan",
                "Justin",
                "Kelvin",
                "Loreen",
                "Mario",
                "Nellie",
                "Olive",
                "Prince",
                "Ross",
                "Susan",
                "Tanya",
                "Valerie"};

        String[] patientNric = {"123456",
                "654321",
                "984203",
                "562745",
                "234745",
                "234643",
                "234534",
                "234643",
                "345634",
                "743164",
                "346343",
                "256344",
                "234654",
                "234653",
                "753134",
                "356723",
                "257455",
                "634234",
                "345634",
                "745234"};

        for (int i = 0; i < patientPhotos.length; i++) {
            Patient newPatient = new Patient(patientNames[i], patientNric[i], patientPhotos[i]);
            patientList.add(newPatient);
        }

        return patientList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patientlist, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.action_patient_search));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String query) {
                patientListAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            return true;
            //testing
        }

        return super.onOptionsItemSelected(item);
    }

    class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space / 2;
            outRect.right = space / 2;
            outRect.bottom = space;
        }
    }
}