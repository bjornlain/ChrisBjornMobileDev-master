package com.example.teambc.projectmobiledevelop;

import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class GetNearbyPlacesMenu extends AsyncTask<Object,Void, String>
{
    private String googleplaceData, url;
    private Context context;
    GetNearbyPlacesDetails getNearbyPlacesDetails = new GetNearbyPlacesDetails();
    List<String> place_ids;
    DataParserDetails dataParser = new DataParserDetails();
     public List<HashMap<String, String>> detailsArray = new ArrayList<>() ;


    private static final String TAG = "MainActivity";

    @Override
    protected String doInBackground(Object... objects)
    {
        detailsArray = (List<HashMap<String, String>>) objects[0];
        url = (String) objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try
        {
            googleplaceData = downloadUrl.ReadTheURL(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        List<HashMap<String, String>> nearByPlacesList = null;
        DataParserMenu dataParser = new DataParserMenu();
        nearByPlacesList = dataParser.parse(googleplaceData);

        DisplayNearbyPlaces(nearByPlacesList);
        detailsArray = this.getDetailsArray();
        return googleplaceData;
    }


    @Override
    protected void onPostExecute(String s)
    {
        Log.d(TAG,"in postexecute geraakt");

    }


    private void DisplayNearbyPlaces(List<HashMap<String, String>> nearByPlacesList)
    {
        for (int i=0; i<nearByPlacesList.size(); i++)
        {

            HashMap<String, String> googleNearbyPlace = nearByPlacesList.get(i);
            String place_id = googleNearbyPlace.get("place_id");
           String searchUrl =  "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + place_id + "&fields=name,rating,website,vicinity,international_phone_number,opening_hours,geometry&key=AIzaSyAsmCTRc_x9Pw38C6qQalKNWAdrSYWz5C4";
            getDetailedInformation(searchUrl);

        }


    }
    public void getDetailedInformation(String s){
        DownloadUrl downloadUrl = new DownloadUrl();
        try
        {
            googleplaceData = downloadUrl.ReadTheURL(s);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        detailsArray.add(dataParser.parse(googleplaceData));
    }
    public List<HashMap<String, String>> getDetailsArray(){
        return detailsArray;
    }
}