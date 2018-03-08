package services.event;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.Reader;

public class EventLocationGenerator {
    private EventLocationData data;

    public EventLocationGenerator() {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader("/home/justin/AndroidStudioProjects/fms/server/src/main/java/services/event/locations.json");
            data = gson.fromJson(reader, EventLocationData.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public EventLocation randomLocation() {
        int min = 0;
        int range = (data.data.length - min);
        int i = (int)(Math.random() * range) + min;
        return data.data[i];
    }
}
