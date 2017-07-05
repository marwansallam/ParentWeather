package com.marwansallam.parentweather.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.marwansallam.parentweather.Adapters.WeatherListAdapter;
import com.marwansallam.parentweather.Helpers.Constants;
import com.marwansallam.parentweather.Helpers.SharedPrefManager;
import com.marwansallam.parentweather.Models.Weather;
import com.marwansallam.parentweather.R;

import java.util.ArrayList;

public class WeatherDetailsFragment extends Fragment {

    TextView cityNameText;
    WeatherListAdapter weatherListAdapter;
    ListView weatherDaysListView;
    ImageView addCityBtn, removeCityBtn;
    boolean isLocationSaved;
    String cityName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);
        ArrayList<Weather> weatherArrayList = Constants.LAST_WEATHER_List;
        weatherDaysListView = (ListView) view.findViewById(R.id.details_weather_list_view);
        cityName = Constants.LAST_LOCATION.getCity() + "," + Constants.LAST_LOCATION.getCountry();
        cityNameText = (TextView) view.findViewById(R.id.details_city_name);
        addCityBtn = (ImageView) view.findViewById(R.id.add_to_home);
        addCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCity();
            }
        });
        removeCityBtn = (ImageView) view.findViewById(R.id.remove_from_home);
        removeCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCity();
            }
        });
        isLocationSaved = SharedPrefManager.isLocationSaved(cityName, getContext());
        if (isLocationSaved)
            removeCityBtn.setVisibility(View.VISIBLE);
        else
            addCityBtn.setVisibility(View.VISIBLE);
        cityNameText.setText(cityName);
        weatherListAdapter = new WeatherListAdapter(getContext(), weatherArrayList);
        weatherDaysListView.setAdapter(weatherListAdapter);
        return view;
    }

    private void removeCity() {
        if (SharedPrefManager.deleteUserLocation(cityName, getContext())) {
            removeCityBtn.setVisibility(View.INVISIBLE);
            addCityBtn.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getContext(), "Failed to remove city, try again !", Toast.LENGTH_SHORT).show();
        }
    }

    private void addCity() {
        if (SharedPrefManager.addUserLocation(cityName, getContext())) {
            addCityBtn.setVisibility(View.INVISIBLE);
            removeCityBtn.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getContext(), "Failed to add city, try again !", Toast.LENGTH_SHORT).show();
        }
    }
}
