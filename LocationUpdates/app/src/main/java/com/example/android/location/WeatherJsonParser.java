package com.example.android.location;

import android.util.JsonReader;

import com.example.android.location.MainActivity.myEntry;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daz on 2014/4/9.
 */
public class WeatherJsonParser {
    public List<myEntry> read(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readWeatherArray(reader);
        } finally {
            reader.close();
        }

    }

    public List<myEntry> readWeatherArray(JsonReader reader) throws IOException {
        List<myEntry> weathers = new ArrayList<myEntry>();

        reader.beginArray();
        while (reader.hasNext()) {
            weathers.add(readWeather(reader));
        }
        reader.endArray();
        return weathers;
    }

    public myEntry readWeather(JsonReader reader) throws IOException {
        String city = null;
        String text = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                city = reader.nextString();
            } else if (name.equals("text")) {
                text = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new myEntry(city, "the json from server should be organized");
    }
}
