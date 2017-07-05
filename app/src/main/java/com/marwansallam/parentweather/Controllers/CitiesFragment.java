package com.marwansallam.parentweather.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.marwansallam.parentweather.Adapters.CitiesListAdapter;
import com.marwansallam.parentweather.Helpers.Constants;
import com.marwansallam.parentweather.Helpers.SharedPrefManager;
import com.marwansallam.parentweather.Helpers.WeatherFetcher;
import com.marwansallam.parentweather.JSONParsers.JsonWeatherParser;
import com.marwansallam.parentweather.Models.City;
import com.marwansallam.parentweather.Models.Weather;
import com.marwansallam.parentweather.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by marwansallam on 7/2/17.
 */

public class CitiesFragment extends Fragment {
    public ArrayList<City> cities;
    ListView citiesList;
    CitiesListAdapter citiesListAdapter;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAddNewCityFragment();
            }
        });
        cities = new ArrayList<>();
        try {
            JSONArray citiesSaved = SharedPrefManager.getUserLocations(getContext());
            for (int i = 0; i < citiesSaved.length(); i++) {
                City city = new City();
                city.setName(citiesSaved.get(i).toString() + "");
                cities.add(city);
            }
            if (cities.size() < 1) {
                LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                CitiesFragment.GetCityFromGeoTask task = new CitiesFragment.GetCityFromGeoTask();
                task.execute(new String[]{longitude + "", latitude + ""});
            }
        } catch (Exception e) {
            boolean x = SharedPrefManager.addUserLocation("London,GB", getContext());
            e.printStackTrace();
        }


        citiesList = (ListView) view.findViewById(R.id.user_cities_list);
        citiesListAdapter = new CitiesListAdapter(getContext(), cities);
        citiesList.setAdapter(citiesListAdapter);
        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                progressDialog = ProgressDialog.show(getActivity(), null, "Loading Weather ...");
                progressDialog.setCancelable(false);
                City city = (City) citiesList.getItemAtPosition(position);
                CitiesFragment.JSONWeatherCitiesTask task = new CitiesFragment.JSONWeatherCitiesTask();
                task.execute(new String[]{city.getName()});
            }
        });
        return view;
    }


    public void loadAddNewCityFragment() {
        if (cities.size() < 5) {
            SearchFragment searchFragment = new SearchFragment();
            loadFragmentAction(searchFragment);
        } else {
            Toast.makeText(getContext(), "You can add only 5 cities in your home ! You can remove any of the existing to add a new one!", Toast.LENGTH_LONG).show();
        }
    }

    public void loadFragmentAction(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fragment_placeholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private class JSONWeatherCitiesTask extends AsyncTask<String, Void, ArrayList<Weather>> {
        @Override
        protected ArrayList<Weather> doInBackground(String... params) {
            ArrayList<Weather> weathersArrayList = new ArrayList<Weather>();
            String data = ((new WeatherFetcher()).getWeatherData(params[0]));
            try {
                if (data.equals(null) || data.isEmpty()) {
                    Toast.makeText(getContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
                weathersArrayList = JsonWeatherParser.getFiveDaysWeather(data);
                Constants.LAST_WEATHER_List = weathersArrayList;
                Constants.LAST_LOCATION = weathersArrayList.get(0).location;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return weathersArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Weather> results) {
            super.onPostExecute(results);
            if (progressDialog != null)
                progressDialog.dismiss();
            if (results != null) {
                WeatherDetailsFragment weatherDetailsFragment = new WeatherDetailsFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.fragment_placeholder, weatherDetailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            } else
                Toast.makeText(getContext(), "Error! Please check your internet connection.", Toast.LENGTH_LONG).show();
        }
    }

    private class GetCityFromGeoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String data = ((new WeatherFetcher()).getCityByGeo(params[0], params[1]));
            String city = "";
            try {
                if (data.equals(null) || data.isEmpty()) {
                    Toast.makeText(getContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                } else {
                    city = JsonWeatherParser.getCity(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return city;
        }

        @Override
        protected void onPostExecute(String city) {
            super.onPostExecute(city);
            if (!city.isEmpty()) {
                boolean x = SharedPrefManager.addUserLocation(city, getContext());
            } else {
                boolean x = SharedPrefManager.addUserLocation("London,GB", getContext());
            }
        }
    }
}