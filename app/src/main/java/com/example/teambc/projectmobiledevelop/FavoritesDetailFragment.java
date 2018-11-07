package com.example.teambc.projectmobiledevelop;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoritesDetailFragment extends Fragment {

    DatabaseHelper mDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favorites_detail_fragment, container, false);

        Bundle bundle = getArguments();
        TextView viewDetails = (TextView) view.findViewById(R.id.detailTextView);
        String item = "";

        if(bundle != null){
            item = getArguments().getString("item");
        }
        if(RestaurantInfo.RESTAURANT_MAP.containsKey(item)){
            RestaurantInfo.Restaurant rest = RestaurantInfo.RESTAURANT_MAP.get(item);
            viewDetails.setText("\t" + rest.number + "\n\t" + rest.website + "\n\t" + rest.address );
        }else{
            viewDetails.setText("item");
        }
        return view;
    }

}
