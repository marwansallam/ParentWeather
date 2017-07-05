package com.marwansallam.parentweather.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.marwansallam.parentweather.Models.Weather;
import com.marwansallam.parentweather.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by marwansallam on 7/2/17.
 */

public class WeatherListAdapter extends ArrayAdapter<Weather> implements View.OnClickListener {

    Context mContext;
    private ArrayList<Weather> dataSet;
    private int lastPosition = -1;

    public WeatherListAdapter(Context context, ArrayList<Weather> weatherArray) {
        super(context, R.layout.list_item_weather, weatherArray);
        this.dataSet = weatherArray;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Weather weather = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_weather, parent, false);
            viewHolder.dayName = (TextView) convertView.findViewById(R.id.details_weather_day_name);
            viewHolder.temp = (TextView) convertView.findViewById(R.id.details_weather_temp);
            viewHolder.humidity = (TextView) convertView.findViewById(R.id.details_weather_humidity);
            viewHolder.windDegree = (TextView) convertView.findViewById(R.id.details_weather_windd);
            viewHolder.windSpeed = (TextView) convertView.findViewById(R.id.details_weather_winds);
            viewHolder.rain = (TextView) convertView.findViewById(R.id.city_name);
            viewHolder.clouds = (TextView) convertView.findViewById(R.id.details_weather_cloud);
            viewHolder.weatherPic = (ImageView) convertView.findViewById(R.id.details_weather_pic);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        lastPosition = position;

        viewHolder.temp.setText(Math.round((weather.temperature.getMaxTemp())) + "°C - " + Math.round(weather.temperature.getMinTemp()) + "°C");
        viewHolder.humidity.setText(weather.currentCondition.getHumidity() + " %");
        viewHolder.windDegree.setText(weather.wind.getDeg() + "°");
        viewHolder.windSpeed.setText(weather.wind.getSpeed() + "");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, position);
        Date date = calendar.getTime();
        String dayname = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        viewHolder.dayName.setText(dayname);
        viewHolder.clouds.setText(weather.clouds.getPerc() + " %");
        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView dayName, temp, humidity, windSpeed, windDegree, rain, clouds;
        ImageView weatherPic;
    }
}
