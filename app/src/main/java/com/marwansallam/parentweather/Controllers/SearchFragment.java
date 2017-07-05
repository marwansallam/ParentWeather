package com.marwansallam.parentweather.Controllers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.marwansallam.parentweather.Helpers.Constants;
import com.marwansallam.parentweather.Helpers.WeatherFetcher;
import com.marwansallam.parentweather.JSONParsers.JsonWeatherParser;
import com.marwansallam.parentweather.Models.Weather;
import com.marwansallam.parentweather.R;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by marwansallam on 7/2/17.
 */

public class SearchFragment extends Fragment {
    EditText cityNameInput;
    ArrayList results;
    ImageView searchBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        cityNameInput = (EditText) view.findViewById(R.id.city_name_input);
        searchBtn = (ImageView) view.findViewById(R.id.city_search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityNameInput.getText().toString().length() < 1) {
                    Toast.makeText(getContext(), "Please enter keyword !!", Toast.LENGTH_SHORT).show();
                } else {
                    SearchFragment.JSONWeatherCitiesTask task = new SearchFragment.JSONWeatherCitiesTask();
                    task.execute(new String[]{cityNameInput.getText().toString()});
                }
            }
        });
        results = new ArrayList();
        return view;
    }

    private class JSONWeatherCitiesTask extends AsyncTask<String, Void, ArrayList<Weather>> {
        @Override
        protected ArrayList<Weather> doInBackground(String... params) {
            ArrayList<Weather> weathersArrayList = new ArrayList<Weather>();
            String data = ((new WeatherFetcher()).getWeatherData(params[0]));
            try {
                if (data.isEmpty()) {
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
            if (results != null) {
                WeatherDetailsFragment weatherDetailsFragment = new WeatherDetailsFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.fragment_placeholder, weatherDetailsFragment);
                transaction.commit();
            } else
                Toast.makeText(getContext(), "No Cities Found , Kindly check for right city name and internet connection", Toast.LENGTH_LONG).show();
        }
    }
}
