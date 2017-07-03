package com.marwansallam.parentweather.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.marwansallam.parentweather.Models.City;
import com.marwansallam.parentweather.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by marwansallam on 7/2/17.
 */

public class CitiesListAdapter extends ArrayAdapter<City> implements View.OnClickListener {

    private ArrayList<City> dataSet;
    Context mContext;
    BaseSwipeAdapter baseSwipeAdapter;

    // View lookup cache
    private static class ViewHolder {
        TextView cityName;
        ImageView citylogo;
    }

    public CitiesListAdapter(Context context, ArrayList<City> cities) {
        super(context, R.layout.list_item_city, cities);
        this.dataSet = cities;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        lastPosition = position;
        viewHolder.cityName.setText(city.getName());
        viewHolder.cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Hiiiiiiiß", Toast.LENGTH_SHORT).show();
            }
        });
        Picasso.with(this.getContext()).load("https://ssl.gstatic.com/onebox/weather/48/sunny.png").into(viewHolder.citylogo);

        return convertView;


    }
}
