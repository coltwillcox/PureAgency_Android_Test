package com.koltinjo.pureagency_android_test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private Button buttonSearch;
    private TextView textViewLatLon;
    private ProgressBar progressBar;

    private LocationManager locationManager;
    private LocationListener locationListener;
    // TODO Make them empty. Just for debug.
//    private String latitude = "40.703321957188955";
//    private String longitude = "-73.99287114236125";
    private String latitude = "";
    private String longitude = "";

    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = (EditText) findViewById(R.id.editText_search);
        buttonSearch = (Button) findViewById(R.id.button_search);
        textViewLatLon = (TextView) findViewById(R.id.textView_latlon);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());
                textViewLatLon.setText(latitude + " - " + longitude);
                if (clicked) {
                    startMapActivity();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                //Take user to location settings to enable gps.
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10); //10 is request code, used down in switch.
            }
        } else {
            configureButton();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                break;
        }
    }

    private void configureButton() {
        locationManager.requestLocationUpdates("gps", 500, 0, locationListener); //Provider, minTime (milis), minDistance (m), location listener. Ignore the error. :)
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked = true;
                String searchQuery = editTextSearch.getText().toString().trim();
                if (searchQuery.isEmpty()) {
                    searchQuery = "beer";
                }
                progressBar.setVisibility(View.VISIBLE);
                if (!latitude.isEmpty() && !longitude.isEmpty()) {
                    startMapActivity();
                } else {
                    Toast.makeText(MainActivity.this, "Looking for your location...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startMapActivity() {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }

}