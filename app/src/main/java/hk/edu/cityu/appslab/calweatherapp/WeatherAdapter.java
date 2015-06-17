package hk.edu.cityu.appslab.calweatherapp;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WeatherAdapter extends BaseAdapter {

    private List<Weather> weatherList;

    public WeatherAdapter(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public int getCount() {
        // the return number is the total ListView item number
        return weatherList.size();
    }

    @Override
    public Weather getItem(int position) {
        return weatherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1. create a new ViewHolder
        WeatherHolder holder = new WeatherHolder();
        //2. init ViewHolder
        //this if is used to check whether the item View is built or not
        if (convertView == null) {
            //inflate the view
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.item_weather, parent, false);
            //find all the view and store the reference in the holder
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.day = (TextView) convertView.findViewById(R.id.day);
            holder.high = (TextView) convertView.findViewById(R.id.high);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            //set the holder to the view so that no need to find view again when the item reuse
            convertView.setTag(holder);

        } else {
            //get back the tag instead of finding view
            holder = (WeatherHolder) convertView.getTag();
        }
        //3. update content
        //get the weather data from the ArrayList according to the position which means the item view position, which start by 0
        Weather weather = weatherList.get(position);
        //set the data according to the weather data
        holder.date.setText(weather.getDate());
        holder.day.setText(weather.getDay());
        holder.high.setText(String.valueOf(weather.getHigh()) + (char) 0x00B0);
        holder.text.setText(weather.getText());
        //if the icon is sunny, make it to use AnimationDrawable to do the GIF-like animation
        if (weather.getIcon() == R.drawable.sunny) {
            //get the AnimationDrawable
            AnimationDrawable drawable = (AnimationDrawable) parent.getResources().getDrawable(R.drawable.sunny_anim_drawable);
            //set it to the ImageView
            holder.icon.setImageDrawable(drawable);
            //start the animation
            drawable.start();
        } else {
            //set other drawable
            holder.icon.setImageResource(weather.getIcon());
        }
        //4. return the view
        return convertView;
    }

    //a class for make down all view
    static class WeatherHolder {

        TextView day;
        TextView date;
        TextView high;
        TextView text;
        ImageView icon;

    }

}
