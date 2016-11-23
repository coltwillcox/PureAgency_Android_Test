package com.koltinjo.pureagency_android_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MarkerActivity extends AppCompatActivity {

    private TextView textViewName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        textViewName = (TextView) findViewById(R.id.textView_name);
        name = getIntent().getExtras().getString("name");

        textViewName.setText(name);
    }
}
