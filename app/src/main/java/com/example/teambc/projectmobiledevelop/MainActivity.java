package com.example.teambc.projectmobiledevelop;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity  {


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String TAG = "MainActivity";
    private static final int ProximityRadius = 10000;

    private Boolean mLocationPermissionsGranted = false;
    public String sortby = "distance";
    ScrollView mScrollView;
    LinearLayout mInsideLinear;
    Button terugButton;
    Button sorteerButton;
    private Context thisContext = MainActivity.this;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private List<HashMap<String, String>> detailsArray = new ArrayList<>();
    public double latitude,longitude;
    Button mapsButton;
    private String plaats;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    public int I;
    public int calledFrom;
    public String[] dummyNames = new String[20];
    public String[] websites = new String[20];
    public Button[] websiteButtons = new Button[20];
    public float[] latitudeArray = new float[20];
    public float[] longitudeArray = new float[20];
    public double[] distanceArray = new double[20];
    public double[] ratingArray  = new double[20];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        getLocationPermission();
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(MainActivity.this, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(MainActivity.this, COURSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }


        }


        configureerButtons();
        if(isServicesOK()){
        init();
        }
    }
    public void onActivityResult(int requestcode,int resultcode,Intent data){
        super.onActivityResult(requestcode,resultcode,data);
        if(requestcode==1){
            String message = data.getStringExtra("sortby");
            sortby = message;
        }
        initLayout();
    }
    private void configureerButtons(){
        terugButton = (Button) findViewById(R.id.terug_button);
        mInsideLinear = (LinearLayout) findViewById(R.id.inside_linear);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        sorteerButton = (Button) findViewById(R.id.sorteer_button);

        sorteerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sort = new Intent(MainActivity.this,SorteerActivity.class);
                startActivityForResult(sort,1);
            }
        });
    }

    private void init(){
        mapsButton = findViewById(R.id.maps_button);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,googleActivity.class);
                calledFrom = 21;
                startActivity(intent);
            }
        });
    }
    private String getUrl(double latitide, double longitude, String nearbyPlace)
    {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitide + "," + longitude);
        googleURL.append("&radius=" + ProximityRadius);
        googleURL.append("&type=" + nearbyPlace);
        googleURL.append("&key=" + "AIzaSyAsmCTRc_x9Pw38C6qQalKNWAdrSYWz5C4");

        Log.d("GoogleMapsActivity", "url = " + googleURL.toString());

        return googleURL.toString();
    }

    public boolean isServicesOK(){
        Log.d("MainActivity", "isServicesOK : checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS){
            Log.d("MainActivity","isServicesOK()) : Google play services si working");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //een error is gebeurt maar we kunnen het fixen
            Log.d("MainActivity", "an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this,"we cant me map requests",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void sortArrayByRating(){
        double temp;
        String tempNames;
        String tempWebsite;
        float tempLatitude;
        float tempLongitude;
        for (int i = 1; i < ratingArray.length; i++) {
            for (int j = i; j > 0; j--) {
                if (ratingArray[j] < ratingArray [j - 1]) {
                    temp = ratingArray[j];
                    ratingArray[j] = ratingArray[j - 1];
                    ratingArray[j - 1] = temp;
                    tempNames = dummyNames[j];
                    dummyNames[j] = dummyNames[j-1];
                    dummyNames[j-1] = tempNames;
                    tempWebsite = websites[j];
                    websites[j] = websites[j-1];
                    websites[j-1] = tempWebsite;
                    tempLatitude = latitudeArray[j];
                    latitudeArray[j] = latitudeArray[j-1];
                    latitudeArray[j-1]=tempLatitude;
                    tempLongitude = longitudeArray[j];
                    longitudeArray[j] = longitudeArray[j-1];
                    longitudeArray[j-1]=tempLongitude;
                }
            }
        }
    }
    public void sortArrayByDistance(){
        double temp;
        String tempNames;
        String tempWebsite;
        float tempLatitude;
        float tempLongitude;
        for (int i = 1; i < distanceArray.length; i++) {
            for (int j = i; j > 0; j--) {
                if (distanceArray[j] < distanceArray [j - 1]) {
                    temp = distanceArray[j];
                    distanceArray[j] = distanceArray[j - 1];
                    distanceArray[j - 1] = temp;
                    tempNames = dummyNames[j];
                    dummyNames[j] = dummyNames[j-1];
                    dummyNames[j-1] = tempNames;
                    tempWebsite = websites[j];
                    websites[j] = websites[j-1];
                    websites[j-1] = tempWebsite;
                    tempLatitude = latitudeArray[j];
                    latitudeArray[j] = latitudeArray[j-1];
                    latitudeArray[j-1]=tempLatitude;
                    tempLongitude = longitudeArray[j];
                    longitudeArray[j] = longitudeArray[j-1];
                    longitudeArray[j-1]=tempLongitude;
                }
            }
        }
    }
    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2) ;

        return Math.sqrt(distance);
    }
    public void initLayout(){



        // String[] dummyNames = {"Restaurant 1", "Restaurant 2" , "Restaurant 3" , "Restaurant 4", "Restaurant 5","Restaurant 6","Restaurant 7", "Restaurant 8", "Restaurant 9","Restaurant 10"};
        for(int i = 0; i< 20;i++) {
            HashMap<String, String> restaurant = detailsArray.get(i);
            dummyNames[i] = restaurant.get("place_name");
            websites[i] = restaurant.get("website");
            latitudeArray[i] = Float.parseFloat(restaurant.get("latitude"));
            longitudeArray[i] = Float.parseFloat(restaurant.get("longitude"));
            distanceArray[i] = distance(latitude, latitudeArray[i], longitude, longitudeArray[i]);
            ratingArray[i] = Double.parseDouble(restaurant.get("rating"));
        }
        //sort
        switch (sortby){
            case "rating":{
                sortArrayByRating();
                break;
            }
            case "distance":{
                sortArrayByDistance();
                break;
            }
        }

        for(int i = 0; i < dummyNames.length;i++){
            LinearLayout linear = new LinearLayout(this);
            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            linear.setWeightSum(100);
            linear.setOrientation(LinearLayout.HORIZONTAL);
            linear.setPadding(10,0,0,10);
            linear.setLayoutParams(linearLayoutParams);
            TextView restName = new TextView(this);



            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(0,150);
            textViewParams.weight = 43;
            restName.setLayoutParams(textViewParams);
            if(dummyNames[i].length() > 20){
                String[] split = dummyNames[i].split(" ");
                String text = "";
                Boolean check = true;
                for(int j = 0;j < split.length;j++){
                    if(j == split.length -1){
                        text+= split[j];
                    }else{
                        if((split[j] + " " + split[j+1]).length() >= 20 ){
                            text += split[j] + "\n" + split[j+1];
                            j++;
                        }else{
                            text += split[j] + " " ;
                        }
                    }
                }
                restName.setText(text);
            }else{
                restName.setText(dummyNames[i]);
            }
            restName.setTextSize(18);
            Button infoButton = new Button(this);
            LinearLayout.LayoutParams infoLayoutParams = new LinearLayout.LayoutParams(0,150);
            infoLayoutParams.setMargins(30,0,0,0);
            infoLayoutParams.weight=16;
            infoButton.setLayoutParams(infoLayoutParams);
            infoButton.setId(i);
            infoButton.setText("info");
            infoButton.setTextSize(13);
            infoButton.setBackgroundResource(R.drawable.roundedbutton);
            final Button locatieButton = new Button(this);
            LinearLayout.LayoutParams locatieLayoutParams = new LinearLayout.LayoutParams(0,150);
            locatieLayoutParams.setMargins(30,0,0,0);
            locatieLayoutParams.weight=18;
            locatieButton.setLayoutParams(locatieLayoutParams);
            locatieButton.setId(i);
            locatieButton.setText("locatie");
            locatieButton.setTextSize(13);
            locatieButton.setBackgroundResource(R.drawable.roundedbutton);

            final Button websiteButton = new Button(this);
            LinearLayout.LayoutParams websiteLayoutParams = new LinearLayout.LayoutParams(0,150);
            websiteLayoutParams.setMargins(30,0,0,0);
            websiteLayoutParams.weight=17;
            websiteButton.setLayoutParams(websiteLayoutParams);
            websiteButton.setId(i);
            websiteButton.setText("website");
            websiteButton.setTextSize(13);
            websiteButton.setBackgroundResource(R.drawable.roundedbutton);
            websiteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = websiteButton.getId();
                   browser(v , websites[i]);
                }
            });
            locatieButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int j = locatieButton.getId();
                    Intent intent = new Intent(MainActivity.this,googleActivity.class);
                    intent.putExtra("latitude",latitudeArray[j]);
                    intent.putExtra("longitude", longitudeArray[j]);
                    startActivity(intent);
                }
            });
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "InfoButton" + (v.getId()+1) + " is clicked",Toast.LENGTH_SHORT).show();
                }
            });

            linear.addView(restName);
            linear.addView(infoButton);
            linear.addView(locatieButton);
            linear.addView(websiteButton);
            mInsideLinear.addView(linear);

        }
    }
    private void getRestaurantsAfterLocation() throws ExecutionException, InterruptedException {
        // restaurants ophalen

        GetNearbyPlacesMenu getNearbyPlacesMenu = new GetNearbyPlacesMenu();


        Object transferData[] = new Object[2];

        String url = getUrl(latitude, longitude, "restaurant");
        transferData[0] = detailsArray;
        transferData[1] = url;

      String get = getNearbyPlacesMenu.execute(transferData).get();

        detailsArray = getNearbyPlacesMenu.getDetailsArray();




        initLayout();
    }
    public List<Float> checkWhichLocationIsCalled(){
        List<Float> tempList = new ArrayList<>();
        if(!(calledFrom == 21)){
            tempList.add(latitudeArray[calledFrom]);
            tempList.add(longitudeArray[calledFrom]);
        }else {
            tempList.add(1.1f);
        }
        return tempList;
    }
    public void browser(View view,String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
    private void getDeviceLocation() {
        Log.d(TAG, "getting devices current locaction");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionsGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "location found!");
                            Location currentLocation = (Location) task.getResult();
                            latitude = currentLocation.getLatitude();
                            longitude = currentLocation.getLongitude();
                            try {
                                getRestaurantsAfterLocation();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Log.d(TAG, "location not found!");
                            Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, " security exception in getDeviceLocation : " + e.getMessage());

        }
    }
    private void getLocationPermission(){
        Log.d(TAG,"getLocationPermission: get location  permissions");
        String[] permissions = {FINE_LOCATION,COURSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;

            }else{
                ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG,"onrequestpermissionresult : called");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length;i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            Log.d(TAG,"onrequestpermissionresult : failed");
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    Log.d(TAG,"onrequestpermissionresult : permission granted");

                    mLocationPermissionsGranted = true;

                }
            }
        }
    }
}
