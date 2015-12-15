package com.example.keith.fyp.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.database.dbfile;

import java.util.ArrayList;

/**
 * Activity to display the login user interface
 */
public class LoginActivity extends ActionBarActivity {
    Button button;
    EditText et1,et2;
    String Username, Password;
    View activityRootView;
    View headerContainer;
    View appLogo;
    float displayDensity;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        displayDensity = getResources().getDisplayMetrics().density;

        onKeyboardOpenned();
        loginButton();
        sharedpreferences = getSharedPreferences(
                "Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    private void onKeyboardOpenned() {
        activityRootView = findViewById(R.id.activityRoot);
        headerContainer = findViewById(R.id.headerContainer);
        appLogo = findViewById(R.id.appLogo);

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 100) {
                    // If keyboard is opened, set the app logo to small and make the header container not flexible

                    float logoSmallWidth = getResources().getDimension(R.dimen.appLogoSmallWidth);
                    appLogo.getLayoutParams().width = (int) logoSmallWidth;

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            0f);
                    headerContainer.setLayoutParams(params);
                } else {
                    // If keyboard is closed, set the app logo to big and make the header container flexible

                    float logoBigWidth = getResources().getDimension(R.dimen.appLogoBigWidth);
                    appLogo.getLayoutParams().width = (int) logoBigWidth;

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            0,
                            1f);
                    headerContainer.setLayoutParams(params);
                }
            }
        });
    }

    private void loginButton() {

        final Context context = this;

        button = (Button) findViewById(R.id.loginButton);
        et1 = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editText2);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Username = et1.getText().toString();
                Password = et2.getText().toString();
                dbfile db = new dbfile();
                ArrayList<Integer> a1 = new ArrayList<Integer>();
                a1 = db.checkUserExist(Username,Password);

                if(a1.size() >= 1){
                    sharedpreferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("userid", String.valueOf(a1.get(0)));
                    editor.putString("userTypeId", String.valueOf(a1.get(0)));
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Welcome back " + Username + "!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, DashboardActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Credentials! Please Try Again", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
