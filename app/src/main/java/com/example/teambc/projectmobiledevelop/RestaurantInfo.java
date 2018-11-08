package com.example.teambc.projectmobiledevelop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantInfo {
    public List<Restaurant> Restaurants = new ArrayList<Restaurant>();
    public static Map<String, Restaurant> RESTAURANT_MAP = new HashMap<String, Restaurant>();

    public List<Restaurant> getRestaurants() {
        return Restaurants;
    }

    public Map<String, Restaurant> getRestaurant_map() {
        return RESTAURANT_MAP;
    }

    public void addRestaurants(Restaurant restaurant) {
        Restaurants.add(restaurant);
        setRestaurant_map(restaurant);
    }

    private static void setRestaurant_map(Restaurant restaurant_map) {
        RESTAURANT_MAP.put(restaurant_map.name, restaurant_map);
    }

    public class Restaurant {
        public final String name;
        public final String number;
        public final String rating;
        public final String address;
        public final String website;
        public final float latitude;
        public final float longitude;

        public Restaurant(String name, String number, String rating, String address, String website, float latitude, float longitude) {
            this.name = name;
            this.number = number;
            this.rating = rating;
            this.address = address;
            this.website = website;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
