package com.marwansallam.parentweather.Controllers;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.marwansallam.parentweather.Adapters.CitiesListAdapter;
import com.marwansallam.parentweather.Models.City;
import com.marwansallam.parentweather.R;
import java.util.ArrayList;

/**
 * Created by marwansallam on 7/2/17.
 */

public class CitiesFragment extends Fragment {
    public ArrayList<City> cities;
    ListView citiesList;
    CitiesListAdapter citiesListAdapter;
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
        City cityOne = new City();
        City cityTwo = new City();
        City cityThree = new City();
        City cityFour = new City();
        City cityFive = new City();
        cityOne.setName("City 1");
        cityTwo.setName("City 2");
        cityThree.setName("City 3");
        cityFour.setName("City 4");
        cityFive.setName("City 5");
        cities.add(cityOne);
        cities.add(cityTwo);
        cities.add(cityThree);
        cities.add(cityFour);
        cities.add(cityFive);
        citiesList = (ListView) view.findViewById(R.id.user_cities_list);
        citiesListAdapter = new CitiesListAdapter(getContext(),cities);
        citiesList.setAdapter(citiesListAdapter);
        return view;
    }

    public void loadAddNewCityFragment() {
        AddCityFragment addCityFragment = new AddCityFragment();
        loadFragmentAction(addCityFragment);
    }

    public void loadFragmentAction(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left, R.anim.exit_to_right);
        transaction.replace(R.id.fragment_placeholder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}