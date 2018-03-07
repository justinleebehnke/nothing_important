package services.event;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.Reader;

public class EventLocationGenerator {
    private EventLocationData data;

    public EventLocationGenerator() {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader("locations.json");
            data = gson.fromJson(reader, EventLocationData.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public EventLocation randomLocation() {
        //inclusive
        int min = 0;
        int range = (data.data.length - min) + 1;
        int i = (int)(Math.random() * range) + min;
        assert i < data.data.length;
        assert i >= 0;

        return data.data[i];
    }
}
