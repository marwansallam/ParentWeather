package com.marwansallam.parentweather.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;

public class SharedPrefManager {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String LOCATIONS_KEY = "UserLocations";

    public static boolean isLocationSaved(String locationName, Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        try {
            JSONArray locationsArray = new JSONArray(sharedpreferences.getString(LOCATIONS_KEY, "[]"));
            for (int i = 0; i < locationsArray.length(); i++) {
                if (locationsArray.getString(i).toLowerCase().equals(locationName.toLowerCase()))
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static boolean addUserLocation(String locationName, Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        try {
            JSONArray locationsArray = new JSONArray(sharedpreferences.getString("UserLocations", "[]"));
            if (locationsArray.length() < 5) {
                locationsArray.put(locationName);
                editor.putString(LOCATIONS_KEY, locationsArray.toString());
                editor.commit();
            }
        } catch (Exception e) {
            Log.d("ADD_USER_EXCEPTION : ", e.toString());
            return false;
        }
        return true;
    }

    public static boolean deleteUserLocation(String locationName, Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        try {
            JSONArray locationsArray = new JSONArray(sharedpreferences.getString("UserLocations", null));
            if (locationsArray.length() > 0) {
                for (int i = 0; i < locationsArray.length(); i++) {
                    if (locationsArray.getString(i).toLowerCase().equals(locationName.toLowerCase())) {
                        locationsArray.remove(i);
                        editor.putString(LOCATIONS_KEY, locationsArray.toString());
                        editor.commit();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static JSONArray getUserLocations(Context context) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        try {
            JSONArray locationsArray = new JSONArray(sharedpreferences.getString("UserLocations", "[]"));
            return locationsArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
