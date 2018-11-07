package com.example.teambc.projectmobiledevelop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class FavoritesDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_detail);

        Intent intent = getIntent();
        String item = intent.getStringExtra("item");

        TextView viewDetails = (TextView) findViewById(R.id.detailTextView);

        if(RestaurantInfo.RESTAURANT_MAP.containsKey(item)){
            RestaurantInfo.Restaurant rest = RestaurantInfo.RESTAURANT_MAP.get(item);
            viewDetails.setText(rest.name+ "\n\n tel:" + rest.number + "\n\n website:" + rest.website + "\n\n adres" + rest.address );
        }else{
            viewDetails.setText("item");
        }
    }

}
