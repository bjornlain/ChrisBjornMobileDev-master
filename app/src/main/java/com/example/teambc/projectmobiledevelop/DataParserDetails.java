package com.example.teambc.projectmobiledevelop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataParserDetails
{
    public HashMap<String, String> getSingleNearbyPlace(JSONObject googlePlaceJSON)
    {
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String NameOfPlace = "-NA-";
        int rating = 0;
        String vicinity ="";
        String open ="";
        String website ="";
        String gsm_nummer = "";
        String latitude = "";
        String longitude = "";


        try
        {
            if (!googlePlaceJSON.getJSONObject("result").isNull("name"))
            {
                NameOfPlace = googlePlaceJSON.getJSONObject("result").getString("name");
            }
            if (!googlePlaceJSON.getJSONObject("result").isNull("vicinity"))
            {
                vicinity = googlePlaceJSON.getJSONObject("result").getString("vicinity");
            }
            if (!googlePlaceJSON.getJSONObject("result").isNull("rating"))
            {
                rating = googlePlaceJSON.getJSONObject("result").getInt("rating");
            }
            if (!googlePlaceJSON.getJSONObject("result").isNull("opening_hours"))
            {
                open = googlePlaceJSON.getJSONObject("result").getJSONObject("opening_hours").getString("open_now");
            }
            if (!googlePlaceJSON.getJSONObject("result").isNull("international_phone_number"))
            {
                gsm_nummer= googlePlaceJSON.getJSONObject("result").getString("international_phone_number");
            }
            if (!googlePlaceJSON.getJSONObject("result").isNull("website"))
            {
                website= googlePlaceJSON.getJSONObject("result").getString("website");
            }
            latitude = googlePlaceJSON.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSON.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").getString("lng");

            googlePlaceMap.put("website",website);
            googlePlaceMap.put("gsm_nummer",gsm_nummer);
            googlePlaceMap.put("open",open);
            googlePlaceMap.put("rating",String.valueOf(rating));
            googlePlaceMap.put("place_name", NameOfPlace);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("latitude", latitude);
            googlePlaceMap.put("longitude", longitude);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return googlePlaceMap;
    }



    private List<HashMap<String, String>> getAllNearbyPlaces(JSONArray jsonArray)
    {
        int counter = jsonArray.length();


        List<HashMap<String,String>> NearbyPlaceMap = null;

        for (int i=0; i<counter; i++)
        {
            try
            {
                NearbyPlaceMap.add(getSingleNearbyPlace( (JSONObject) jsonArray.get(i)));

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return NearbyPlaceMap;
    }



    public HashMap<String, String> parse(String jSONdata)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        try
        {
            jsonObject = new JSONObject(jSONdata);
           
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return getSingleNearbyPlace(  jsonObject);
    }
}