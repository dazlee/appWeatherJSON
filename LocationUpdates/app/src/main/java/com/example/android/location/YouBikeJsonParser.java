package com.example.android.location;

import android.util.JsonReader;

import com.example.android.location.MainActivity.YouBikeEntry;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by daz on 2014/4/9.
 */
public class YouBikeJsonParser {
    public List<YouBikeEntry> read(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readYouBikeArray(reader);
        } finally {
            reader.close();
        }

    }

    public List<YouBikeEntry> readYouBikeArray(JsonReader reader) throws IOException {
        List<YouBikeEntry> youBikes = new ArrayList<YouBikeEntry>();

        reader.beginArray();
        while (reader.hasNext()) {
            youBikes.add(readYouBike(reader));
        }
        reader.endArray();
        return youBikes;
    }

    public YouBikeEntry readYouBike(JsonReader reader) throws IOException {
        Double lat = 0.0;
        Double lng = 0.0;
        String station = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("lat")) {
                lat = Double.parseDouble(reader.nextString());
            } else if (name.equals("lng")) {
                lng = Double.parseDouble(reader.nextString());
            } else if (name.equals("sna")) {
                station = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new YouBikeEntry(lat, lng, station);
    }
}
