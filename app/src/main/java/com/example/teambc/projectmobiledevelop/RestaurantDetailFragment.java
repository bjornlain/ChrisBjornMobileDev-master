package com.example.teambc.projectmobiledevelop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class RestaurantDetailFragment extends Fragment {
    public static final String ARG_REST_ID = "rest_id";

    private RestaurantInfo.Restaurant mItem;

    private FloatingActionButton favoriteButton;

    DatabaseHelper mDatabaseHelper;

    public RestaurantDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_REST_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = RestaurantInfo.RESTAURANT_MAP.get(getArguments().getString(ARG_REST_ID));

            final Context context = this.getContext();
            final Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
            mDatabaseHelper = new DatabaseHelper(context);
            favoriteButton = (FloatingActionButton) activity.findViewById(R.id.fab);
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabaseHelper.addName(mItem.name);
                    Toast.makeText(context, "Added" + mItem.name + " to favorites", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_info, container, false);

		// Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText("Rating: " + mItem.rating + "\n\nNummer: " + mItem.number + "\n\nAdres: " + mItem.address + "\n\nWebsite: "
            + mItem.website);
        }

        return rootView;
    }
}
