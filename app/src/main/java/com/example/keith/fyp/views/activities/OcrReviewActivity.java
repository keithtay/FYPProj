package com.example.keith.fyp.views.activities;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.keith.fyp.R;
import com.example.keith.fyp.utils.Global;

public class OcrReviewActivity extends AppCompatActivity {

    private EditText recognizedTextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recognizedTextEditText = (EditText) findViewById(R.id.recognized_text_edit_text);

        String recognizedText = getIntent().getStringExtra(Global.EXTRA_RECOGNIZED_TEXT);
        recognizedTextEditText.setText(recognizedText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
