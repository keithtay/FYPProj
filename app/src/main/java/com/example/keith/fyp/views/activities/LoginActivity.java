package com.example.keith.fyp.views.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keith.fyp.R;
import com.example.keith.fyp.managers.SessionManager;

/**
 * Activity to display the login user interface
 */
public class LoginActivity extends ActionBarActivity {
    Button button;
    EditText usernameEditText;
    EditText passwordEditText;

    String Username;
    View activityRootView;
    View headerContainer;
    View appLogo;
    float displayDensity;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        displayDensity = getResources().getDisplayMetrics().density;

        session = new SessionManager(getApplicationContext());
        usernameEditText = (EditText) findViewById(R.id.username_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    tryLogin();
                    return true;
                }
                return false;
            }
        });
        onKeyboardOpenned();
        loginButton();
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
        button = (Button) findViewById(R.id.loginButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                tryLogin();
            }
        });
    }

    private void tryLogin() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean isFormValid = true;

        if (username.trim().length() == 0) {
            isFormValid = false;
            usernameEditText.setError(LoginActivity.this.getString(R.string.error_msg_field_required));
        }

        if (password.trim().length() == 0) {
            isFormValid = false;
            passwordEditText.setError(LoginActivity.this.getString(R.string.error_msg_field_required));
        }

        if (isFormValid) {
            // TODO create proper user accounts
            if (username.equals("admin") && password.equals("admin123")) {

                // TODO change with proper user's name, email and photo
                Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_06);
                session.createLoginSession("Supervisor1", "supervisor1@gmail.com", SessionManager.USER_SUPERVISOR, photo);

                // Staring MainActivity
                Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(i);
                finish();
            } else if(username.equals("staff") && password.equals("staff123")) {
                // TODO change with proper user's name, email and photo
                Bitmap photo = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_13);
                session.createLoginSession("Caregiver1", "caregiver1@gmail.com", SessionManager.USER_CAREGIVER, photo);

                // Staring MainActivity
                Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Login failed. Please enter valid username and password", Toast.LENGTH_LONG).show();
            }
        }
    }

}
