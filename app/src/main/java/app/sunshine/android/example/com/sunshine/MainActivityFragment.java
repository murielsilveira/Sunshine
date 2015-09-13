package app.sunshine.android.example.com.sunshine;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        String[] forecastArray = {
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 70/46",
                "Mon - Cloudy - 72/63",
                "Tue - Rain - 72/63",
                "Weds - Heavy Rain - 72/63",
                "Thurs - HELP TRAPPED IN WEATHERSTATION - 72/63",
                "Fri - Sunny - 72/63",
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 70/46",
                "Mon - Cloudy - 72/63",
                "Tue - Rain - 72/63",
                "Weds - Heavy Rain - 72/63",
                "Thurs - HELP TRAPPED IN WEATHERSTATION - 72/63",
                "Fri - Sunny - 72/63",
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 70/46",
                "Mon - Cloudy - 72/63",
                "Tue - Rain - 72/63",
                "Weds - Heavy Rain - 72/63",
                "Thurs - HELP TRAPPED IN WEATHERSTATION - 72/63",
                "Fri - Sunny - 72/63",
        };

        ArrayList<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

        ArrayAdapter<String> forecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                weekForecast
        );

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(forecastAdapter);

        return rootView;
    }
}
