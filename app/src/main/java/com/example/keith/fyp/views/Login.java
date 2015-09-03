package com.example.keith.fyp.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.utils.UnitConverter;


public class Login extends ActionBarActivity {
    Button button;
    EditText et1;
    String Username;
    View activityRootView;
    View headerContainer;
    View appLogo;
    float displayDensity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        displayDensity = getResources().getDisplayMetrics().density;

        onKeyboardOpenned();
        loginButton();
    }

    public void onKeyboardOpenned() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    public void loginButton() {

        final Context context = this;

        button = (Button) findViewById(R.id.loginButton);
        et1 = (EditText) findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Username = et1.getText().toString();
                Toast.makeText(getApplicationContext(), "Welcome back " + Username + "!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, Patient.class);
                startActivity(intent);

            }
        });
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
