package com.marwansallam.parentweather.JSONParsers;

import com.marwansallam.parentweather.Models.UserLocation;
import com.marwansallam.parentweather.Models.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marwansallam on 7/3/17.
 */

public class JsonWeatherParser {

    public static ArrayList<Weather> getFiveDaysWeather(String data) throws JSONException {

        ArrayList<Weather> weatherArrayList = new ArrayList<Weather>();
        // create JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        // start extracting the info
        UserLocation loc = new UserLocation();

        JSONObject cityObj = getObject("city", jObj);
        String countryObj = getString("country", cityObj);
        JSONObject coordObj = getObject("coord", cityObj);
        int count = getInt("cnt", jObj);
        JSONArray jArr = jObj.getJSONArray("list");

        for (int i = 0; i < count; i++) {
            Weather weather = new Weather();
            JSONObject currentObj = jArr.getJSONObject(i);
            JSONObject tempObject = getObject("temp", currentObj);
            JSONObject currentWeatherObj = currentObj.getJSONArray("weather").getJSONObject(0);
            weather.location = new UserLocation();
            weather.location.setLatitude(getFloat("lat", coordObj));
            weather.location.setLongitude(getFloat("lon", coordObj));
            weather.location.setCity(getString("name", cityObj));
            weather.location.setCountry(countryObj.toString());
            weather.currentCondition.setHumidity(getInt("humidity", currentObj));
            weather.currentCondition.setPressure(getInt("pressure", currentObj));
            weather.currentCondition.setTime(Long.parseLong(currentObj.getString("dt")));
            weather.currentCondition.setWeatherId(getInt("id", currentWeatherObj));
            weather.currentCondition.setDescr(getString("description", currentWeatherObj));
            weather.currentCondition.setCondition(getString("main", currentWeatherObj));
            weather.currentCondition.setIcon(getString("icon", currentWeatherObj));
            weather.temperature.setTemp(getFloat("day", tempObject));
            weather.temperature.setMaxTemp(getFloat("max", tempObject));
            weather.temperature.setMinTemp(getFloat("min", tempObject));
            weather.clouds.setPerc(getInt("clouds", currentObj));
            weather.wind.setSpeed(getFloat("speed", currentObj));
            weather.wind.setDeg(getFloat("deg", currentObj));
            weatherArrayList.add(weather);
        }
        return weatherArrayList;
    }

    public static String getCity(String data) {
        String cityName;
        try {
            JSONObject jObj = new JSONObject(data);
            JSONObject cityObj = getObject("city", jObj);
            cityName = getString("name", cityObj);
        } catch (Exception e) {
            return "";
        }
        return cityName;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}
