package com.koltinjo.pureagency_android_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MarkerActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewAddress;
    private TextView textViewPhone;
    private TextView textViewUrl;
    private TextView textViewCheckins;
    private TextView textViewHereNow;
    private String name;
    private String address;
    private String phone;
    private String url;
    private String checkins;
    private String herenow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        name = getIntent().getExtras().getString("name");
        address = getIntent().getExtras().getString("address");
        phone = getIntent().getExtras().getString("phone");
        url = getIntent().getExtras().getString("url");
        checkins = getIntent().getExtras().getString("checkins");
        herenow = getIntent().getExtras().getString("herenow");


        textViewName = (TextView) findViewById(R.id.textView_name);
        textViewAddress = (TextView) findViewById(R.id.textView_address);
        textViewPhone = (TextView) findViewById(R.id.textView_phone);
        textViewUrl = (TextView) findViewById(R.id.textView_url);
        textViewCheckins = (TextView) findViewById(R.id.textView_checkins);
        textViewHereNow = (TextView) findViewById(R.id.textView_herenow);

        textViewName.setText(name);
        textViewAddress.setText(address);
        textViewPhone.setText(phone);
        textViewUrl.setText(url);
        textViewCheckins.setText(checkins);
        textViewHereNow.setText(herenow);
    }

}
