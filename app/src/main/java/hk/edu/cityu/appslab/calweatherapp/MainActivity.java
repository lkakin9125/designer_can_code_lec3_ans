package hk.edu.cityu.appslab.calweatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // for example
    TextView xml;


    // Data Source
    List<Weather> data;

    // Adapter
    WeatherAdapter weatherAdapter;

    private ListView weatherList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //select the activity_main.xml as the layout of this activity
        setContentView(R.layout.activity_main);
        //find toolbar and set it as ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //set the elevation of toolbar so shadow effect will come up with Android 5.0 above device.
        ViewCompat.setElevation(toolbar, getResources().getDimension(R.dimen.elevation));
        //find all the view needed
        weatherList = (ListView) findViewById(R.id.weather_list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        emptyView = findViewById(R.id.empty_view);

        //set the emptyView
        weatherList.setEmptyView(emptyView);

        //set the colorScheme of swipeRefreshLayout so that the loading circle can be different color while the loading animation
        swipeRefreshLayout.setColorSchemeResources(R.color.accent_dark, R.color.accent, R.color.accent_light);
        //set the OnRefreshListener so that trigger the download weather event
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //show the loading animation
                swipeRefreshLayout.setRefreshing(true);
//                download the weather
                downloadWeather();
            }
        });
        //post() is for waiting swipeRefreshLayout ready to show the loading animation and trigger show animation by the Runnable
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        //download the weather
        downloadWeather();

    }


    private void downloadWeather(){
        //create and execute the AsyncTask
        new WeatherQueryTask().execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            swipeRefreshLayout.setRefreshing(true);
            downloadWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //AsyncTask is to prevent no UI response to the user while data is downloading
    //Remember using multi-threading is not a choice and it is a "must" of Android system
    //if you try to use UI thread to have a network access
    //your app will be force closed
    //p.s. AsyncTask is only one of the ways to use multi-threading to do network access.
    //there are some many library to do network access, like Volley
    //but the logic behind is similar with AsyncTask
    //using AsyncTask as a example is because it is build-in library
    private class WeatherQueryTask extends AsyncTask<Void, Void, Void> {

        // the action before the download start
        @Override
        protected void onPreExecute() {
            //set weatherList adapter to null so that the emptyView will be shown while downloading
            weatherList.setAdapter(null);
        }
        //after onPreExecute(), this method will start automatically
        @Override
        protected Void doInBackground(Void... arg0) {

            // 1. Populate the Data Source
            //download the weather xml from yahoo
            String xml = YWeatherAPI.getForecastXml();

            // 1.1 Convert the xml to List of Weather Object
            try {
                //extract the data from xml
                WeatherParser parser = new WeatherParser(xml);
                data = parser.getWeatherForecastList();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // 2. Fill the adapter with data
            //create the adapter
            weatherAdapter = new WeatherAdapter(data);

            // 3. Setup the ListView
            //set the adapter to weatherList to show the data as a list
            weatherList.setAdapter(weatherAdapter);
            //stop the refreshing animation
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
