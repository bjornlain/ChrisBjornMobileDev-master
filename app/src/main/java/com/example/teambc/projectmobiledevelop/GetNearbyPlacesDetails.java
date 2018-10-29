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



public class GetNearbyPlacesDetails //extends AsyncTask<Object,Void, String>
{
    private String googleplaceData, url;
    private Context context;
    public List<HashMap<String, String>> detailsArray = new ArrayList<>();

    private static final String TAG = "DetailsActivity";

   /* @Override
    protected String doInBackground(Object... objects)
    {
        context = (Context) objects[0];
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
        DataParserDetails dataParser = new DataParserDetails();
        detailsArray.add(dataParser.parse(googleplaceData));

        return googleplaceData;
    }


    @Override
    protected void onPostExecute(String s)
    {

    }
    */

    public List<HashMap<String, String>> getDetailsArray(){
        return detailsArray;
    }

}