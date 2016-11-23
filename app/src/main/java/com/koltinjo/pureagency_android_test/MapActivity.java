package com.koltinjo.pureagency_android_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private String latitude;
    private String longitude;
    private String searchQuery;

    private String clientId = "PWSM1K5Y4FPYRQNBN5VTV00TA5PBIRGNQ4GOX1BJOVIEH5RX";
    private String clientSecret = "14CLKGBEMGHFAAC2W4UFJM3INJILSDTPEANMU3NYMJQAFRUH";
    private String categoryId = "4bf58dd8d48988d116941735";
    private String version = "20140806";

    private ListView listView;
    private String urlFull;
    private String jsonString = "";
    private ArrayList<Bar> bars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        listView = (ListView) findViewById(R.id.listView);
        urlFull = getIntent().getExtras().getString("urlFull");
        latitude = getIntent().getExtras().getString("latitude");
        longitude = getIntent().getExtras().getString("longitude");
        searchQuery = getIntent().getExtras().getString("query");
        bars = new ArrayList<>();
    }

    private void downloadData() {
        Single
                .create(new Single.OnSubscribe<SingleSubscriber>() {
                    @Override
                    public void call(SingleSubscriber singleSubscriber) {
                        URL url;
                        HttpURLConnection connection = null;

                        String urlStart = "https://api.foursquare.com/v2/venues/search?ll=" + latitude + "," + longitude +
                                "&client_id=" + clientId +
                                "&client_secret=" + clientSecret +
                                "&categoryId=" + categoryId +
                                "&v=" + version +
                                "&query=" + searchQuery;
//                        Date cDate = new Date();
//                        String urlDate = new SimpleDateFormat("yyyyMMdd").format(cDate);
//                        String urlFull = urlStart + urlDate;
                        try {
                            // Step 1: Get JSON document.
                            url = new URL(urlStart);

                            connection = (HttpURLConnection) url.openConnection();
                            connection.setConnectTimeout(5000);
                            connection.setReadTimeout(5000);
                            connection.setRequestMethod("GET");

                            // Read the input stream into a String.
                            InputStream inputStream = connection.getInputStream();
                            StringBuffer buffer = new StringBuffer();
                            if (inputStream == null) {
                                return;
                            }

                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                            String line;
                            while ((line = reader.readLine()) != null) {
                                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                                // But it does make debugging a *lot* easier if you print out the completed buffer for debugging.
                                buffer.append(line + "\n");
                            }

                            jsonString = buffer.toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (connection != null) {
                                connection.disconnect();
                            }
                            singleSubscriber.onSuccess(new Object());
                        }
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Object>() {
                            @Override
                            public void call(Object o) {
                                parseJson();
                                addMarkers();
                                updateList();
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        }
                );
    }

    private void parseJson() {
        try {
            JSONObject root = new JSONObject(jsonString);
            JSONArray venues = root.getJSONObject("response").getJSONArray("venues");
            for (int i = 0; i < venues.length(); i++) {
                JSONObject venue = venues.getJSONObject(i);
                String name = venue.getString("name");
                String address = venue.getJSONObject("location").getString("address");
                JSONObject icon = venue.getJSONArray("categories").getJSONObject(0).getJSONObject("icon");
                String prefix = icon.getString("prefix");
                String suffix = icon.getString("suffix");
                String image = prefix + "64" + suffix;
                String phone = venue.getJSONObject("contact").getString("formattedPhone");
                String url = venue.getString("url");
                String checkins = venue.getJSONObject("stats").getString("checkinsCount");
                String hereNow = venue.getJSONObject("hereNow").getString("count");

                double latitude = venue.getJSONObject("location").getDouble("lat");
                double longitude = venue.getJSONObject("location").getDouble("lng");
                bars.add(new Bar(name, address, image, phone, url, checkins, hereNow, latitude, longitude));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        downloadData();
    }

    private void addMarkers() {
        for (final Bar bar : bars) {
            LatLng marker = new LatLng(bar.getLatitude(), bar.getLongitude());
            map.addMarker(new MarkerOptions().position(marker).title(bar.getName())).setTag(bar);
            map.moveCamera(CameraUpdateFactory.newLatLng(marker));
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent intent = new Intent(MapActivity.this, MarkerActivity.class);
                    intent.putExtra("name", ((Bar) marker.getTag()).getName());
                    intent.putExtra("address", ((Bar) marker.getTag()).getAddress());
                    intent.putExtra("phone", ((Bar) marker.getTag()).getPhone());
                    intent.putExtra("url", ((Bar) marker.getTag()).getUrl());
                    intent.putExtra("checkins", ((Bar) marker.getTag()).getCheckins());
                    intent.putExtra("herenow", ((Bar) marker.getTag()).getHereNow());
                    startActivity(intent);
                    return true;
                }
            });
        }
    }

    private void updateList() {
        listView.setAdapter(new BarAdapter(bars));
    }
}