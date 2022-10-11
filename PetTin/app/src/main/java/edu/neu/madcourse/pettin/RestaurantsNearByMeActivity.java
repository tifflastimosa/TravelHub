package edu.neu.madcourse.pettin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.content.res.Configuration;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.os.StrictMode;

import edu.neu.madcourse.pettin.Classes.RestaurantModel;

public class RestaurantsNearByMeActivity extends AppCompatActivity {
    //debug tool
    private static final String TAG = RestaurantsNearByMeActivity.class.getSimpleName();
    // Used in checking for runtime permissions.
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;


    //location Request
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    private final Location[] currentLocation = new Location[1];


    String latitude, longitude;
    TextView location_text;
    Thread differentThread;


    //progress bar
    AlertDialog.Builder builder;
    AlertDialog progressDialog;


    //recyclarView
    RecyclerView RestaurantRecyclerView;
    Adapter_for_Restaurants adapter;
    List<RestaurantModel> RestaurantItemList;
    private Handler textHandler = new Handler();

    BottomNavigationView bottomNav;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bottomNav.setSelectedItemId(R.id.nav_restaurant);
    }


    @Override
    protected void onRestart() {

        super.onRestart();
        if(RestaurantItemList.isEmpty()&& checkPermissions()){
            recreate();
        }
        if(!checkPermissions()){
            requestPermissions();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_near_by_me);
        setTitle("Nearby Pet-Friendly Restaurants on Google Map");


        //List of restaurant
        RestaurantItemList = new ArrayList<>();
        //Instantiate a adapter
        adapter = new Adapter_for_Restaurants(RestaurantItemList, this);
        RestaurantRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_restaurant);

        // Checks the orientation of the screen
        RestaurantRecyclerView.setHasFixedSize(false);
        //This defines the way in which the RecyclerView is oriented
        RestaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       //Associates the adapter with the RecyclerView
        RestaurantRecyclerView.setAdapter(adapter);
        RestaurantRecyclerView.addOnItemTouchListener(new RecyclerTouchListener_forRestaurant(getApplicationContext(), RestaurantRecyclerView, new RecyclerTouchListener_forRestaurant.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String latitude = RestaurantItemList.get(position).getLatitude();
                String location = RestaurantItemList.get(position).getCourse_name();
                String longitude =RestaurantItemList.get(position).getLongtitude();
                String uri = "geo:"+latitude+", "+longitude+"?q="+location;
                //                String uri = "https://www.google.com/maps/place/" + latitude + "," + longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        }));


/*        location_text = (TextView) findViewById(R.id.textView2);
        location_text.setText("");*/
        if (!checkPermissions()) {
            requestPermissions();


        }else{
            progressDialog = getDialogProgressBar().create();
            progressDialog.show();

            final boolean[] location_update = {false};
            //begin get permissions
            //client stuff
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            //Instantiating the Location request and setting the priority and the interval I need to update the location.
            locationRequest = locationRequest.create();
            locationRequest.setInterval(100);
            locationRequest.setFastestInterval(50);
            locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);

            //instantiating the LocationCallBack
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        if (locationResult == null) {
                            return;
                        }

                        //Showing the latitude, longitude and accuracy on the home screen.
                        for (Location location : locationResult.getLocations()) {
                            String location_text_result = String.format("Current Latitude: %.10f Longitude: %.10f Accuracy: %.10f, Request:%d\n", location.getLatitude(),
                                    location.getLongitude(), location.getAccuracy(),locationRequest.getPriority());
                        latitude = String.valueOf(location.getLatitude());
                        longitude = String.valueOf(location.getLongitude());

                            if(location_update[0] ==false){

                                //location_text.setText("Touch nearby restaurants to Google Map\nPowered by Yelp Fusion && Built by PetTin");

                                if(!latitude.isEmpty()&&!longitude.isEmpty()) {
                                    differentThread = new differentThread();
                                    differentThread.start();
                                }

                                location_update[0] = true;

                            }
                        }
                    }
                }
            };
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(RestaurantsNearByMeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());





        }


/*        Button button_test;
        button_test = findViewById(R.id.button_test);
        button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude = "138+Congress+St,+Portland,+ME+04101";
                String longitude = "-70.24822142112734";
                String uri = "geo:45, -70.24827775767166?q= Maine";
                //                String uri = "https://www.google.com/maps/place/" + latitude + "," + longitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });*/







        //Navigation Bar Start
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_restaurant);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_chat:
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_playdate:
                    startActivity(new Intent(getApplicationContext(), PlayDateActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_post:
                    startActivity(new Intent(getApplicationContext(), PostActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_restaurant:
                    return true;

            }
            return false;
        });//Navigation Bar End
    }

    /**
     * Returns the boolean of the permissions needed.
     */
    private boolean checkPermissions() {
        boolean permission = ( PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION))||( PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION));
        Log.i(TAG, "Debug by Qia Lin: "+permission);
        return permission;
    }



    private void requestPermissions() {
        Log.i(TAG, "Debug by Qia Lin: Requesting permission");
        // Request permission. It's possible this can be auto answered if device policy
        // sets the permission in a given state or the user denied the permission
        // previously and checked "Never ask again".
        ActivityCompat.requestPermissions(RestaurantsNearByMeActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
        Log.i(TAG, "Debug by Qia Lin: "+checkPermissions());
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
                onRestart();
            } else {
                // Permission denied.
                //setButtonsState(false);
                Snackbar.make(
                                findViewById(R.id.RestaurantXML),
                                "Permission denied go setting",
                                Snackbar.LENGTH_INDEFINITE)
                        .setAction("Setting", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();

            }
        }
    }



    //Class which extends the Thread class.
    private class differentThread extends Thread {

        @Override
        public void run() {


            // Create a neat value object to hold the URL
            try {

               System.out.println("dsdsdasdsadsa"+latitude);
                System.out.println("dsdsdasdsadsa"+longitude);

                    RestaurantItemList.clear();
                URL url = new URL(
                        "https://api.yelp.com/v3/businesses/search?term="+"dog friendly restaurants"+"&latitude="+latitude+"&longitude="+longitude);
                // Open a connection(?) on the URL(??) and cast the response(???)
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // Now it's "open", we can set the request method, headers etc.
                connection.setRequestProperty("Authorization",
                        "Bearer _4i7urUyt84DLV_ysALE8ANOe6EQbF7x16fBWyOA4oJFbr7xr4KvbFkWnkp5VLzCezQcwldDYZK77fN0PoyWBbFAp0fHid6XCDPq23Dg6EAGcxJ33I5R53e7FYnxYnYx");
                connection.setRequestMethod("GET");

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    JSONObject jobject = new JSONObject(response.toString());
                    JSONArray jsa = new JSONArray(jobject.get("businesses").toString());
                    for(int i = 0; i < jsa.length(); i++)
                    {
                        JSONObject objects = jsa.getJSONObject(i);
                        //Iterate through the elements of the array i.
                        String title = objects.get("name").toString();
                        if(!title.isEmpty()){
                            Log.i(TAG, "Debug by Qia Lin: title "+title);
                            String category = "";
                            String address = "";
                            String distance = "";
                            String imagelink = "";

                            double course_rating = 0;
                            // 回答里面找distance 是一个JSONObject 但直接转成Str
                            distance = objects.get("distance").toString();
                            // 回答里面找imagelink 是一个JSONObject 但直接转成Str
                            imagelink = objects.get("image_url").toString();
                            // 回答里面找location 是一个JSONObject
                            JSONObject location= objects.getJSONObject("location");
                            JSONArray address_arr = location.getJSONArray("display_address");
                            if(!address_arr.isNull(0)){
                                address =  address_arr.join("\n").replace("\"","");
                            }
                            // 回答里面找category 是一个jasonarray
                            JSONArray categories_jsa = objects.getJSONArray("categories");
                            if(!categories_jsa.isNull(0)){
                                JSONObject categories_obj = categories_jsa.getJSONObject(0);
                                category = categories_obj.get("title").toString();
                                Log.i(TAG, "Debug by Qia Lin: categories "+category);
                            }
                            JSONObject coordinates= objects.getJSONObject("coordinates");
                            // 回答里面找integer
                            course_rating = Double.parseDouble(objects.get("rating").toString());
                            RestaurantItemList.add(new RestaurantModel(title,course_rating,category,distance,address,imagelink,coordinates.get("longitude").toString(),coordinates.get("latitude").toString()));

                        }


                    }


                }

                catch (IOException | JSONException e) {
                    e.printStackTrace();
                }


                textHandler.post(new Runnable() {
                    @Override
                    public void run() {

                       adapter.notifyDataSetChanged();

                       progressDialog.dismiss();
                    }
                });








            } catch (MalformedURLException e) {
                Log.i(TAG, "Debug by Qia Lin: "+e);
            } catch (IOException e) {
                Log.i(TAG, "Debug by Qia Lin: "+e);
            }


        }

    }
    public AlertDialog.Builder getDialogProgressBar() {

        if (builder == null) {
            builder = new AlertDialog.Builder(this);

            builder.setTitle("Loading Nearby Pet Friendly Restaurants...");
            builder.setCancelable(false);
            final ProgressBar progressBar = new ProgressBar(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            progressBar.setLayoutParams(lp);
            progressBar.setPadding(0, 0, 0, 30);

            builder.setView(progressBar);
        }
        return builder;
    }


}