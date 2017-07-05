package com.marwansallam.parentweather.Helpers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by marwansallam on 7/1/17.
 */

public class WeatherFetcher {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    private static String BASE_URL_END = "&units=metric&cnt=5&appid=9fee7ab9f272cbc6d36cffaf4cc3258c";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String GEO_URL = "https://samples.openweathermap.org/data/2.5/forecast/daily?";
    private static String GEO_URL_END = "&cnt=10&appid=9fee7ab9f272cbc6d36cffaf4cc3258c";

    public String getWeatherData(String location) {
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            String URL = BASE_URL + location + BASE_URL_END;
            con = (HttpURLConnection) (new URL(URL)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null)
                buffer.append(line + "rn");

            is.close();
            con.disconnect();
            return buffer.toString();
        } catch (FileNotFoundException f) {
            f.printStackTrace();
            return null;
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
        return null;
    }

    public String getCityByGeo(String longitude, String latitude) {
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            String URL = GEO_URL + "lat=" + latitude + "&lon=" + longitude + BASE_URL_END;
            con = (HttpURLConnection) (new URL(URL)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null)
                buffer.append(line + "rn");

            is.close();
            con.disconnect();
            return buffer.toString();
        } catch (FileNotFoundException f) {
            f.printStackTrace();
            return null;
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
        return null;
    }

    public byte[] getImage(String code) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            con = (HttpURLConnection) (new URL(IMG_URL + code)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while (is.read(buffer) != -1)
                baos.write(buffer);

            return baos.toByteArray();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }

        return null;

    }
}