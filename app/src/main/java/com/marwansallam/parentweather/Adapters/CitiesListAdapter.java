package com.marwansallam.parentweather.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.marwansallam.parentweather.Models.City;
import com.marwansallam.parentweather.R;

import java.util.ArrayList;

/**
 * Created by marwansallam on 7/2/17.
 */

public class CitiesListAdapter extends ArrayAdapter<City> implements View.OnClickListener {

    Context mContext;
    private ArrayList<City> dataSet;

    public CitiesListAdapter(Context context, ArrayList<City> cities) {
        super(context, R.layout.list_item_city, cities);
        this.dataSet = cities;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        City city = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_city, parent, false);
            viewHolder.cityName = (TextView) convertView.findViewById(R.id.city_name);
            viewHolder.citylogo = (ImageView) convertView.findViewById(R.id.city_logo);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        viewHolder.cityName.setText(city.getName());
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView cityName;
        ImageView citylogo;
    }
}
