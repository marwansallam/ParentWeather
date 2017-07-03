package com.marwansallam.parentweather.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.marwansallam.parentweather.R;

/**
 * Created by marwansallam on 7/2/17.
 */

public class AddCityFragment extends Fragment {
    EditText cityNameInput;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_city, container, false);
        cityNameInput = (EditText) view.findViewById(R.id.city_name_input);
        return view;
    }
}
