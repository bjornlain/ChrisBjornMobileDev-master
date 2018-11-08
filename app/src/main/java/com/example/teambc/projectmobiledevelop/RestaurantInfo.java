package com.example.teambc.projectmobiledevelop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantInfo {
    public List<Restaurant> restaurants = new ArrayList<Restaurant>();
    public static Map<String, Restaurant> RESTAURANT_MAP = new HashMap<String, Restaurant>();

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void addRestaurants(Restaurant restaurant) {
        restaurants.add(restaurant);
        setRestaurant_map(restaurant);
    }

    private static void setRestaurant_map(Restaurant restaurant_map) {
        RESTAURANT_MAP.put(restaurant_map.name, restaurant_map);
    }

    public void clearRestaurants(){
        restaurants.clear();
        clearRestMap();
    }

    private static void clearRestMap(){
        RESTAURANT_MAP.clear();
    }

    public void switchRest(int i, int j){
        Restaurant rest = restaurants.get(i);
        restaurants.set(i, restaurants.get(j));
        restaurants.set(j,rest);
    }

    private static void clearRestaurant_map(){
        RESTAURANT_MAP.clear();
    }

    public class Restaurant {
        public final String name;
        public final String number;
        public final String rating;
        public final String address;
        public final String website;
        public final double latitude;
        public final double longitude;

        public Restaurant(String name, String number, String rating, String address, String website, double latitude, double longitude) {
            this.name = name;
            this.number = number;
            this.rating = rating;
            this.address = address;
            this.website = website;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
