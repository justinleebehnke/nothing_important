package services.fill;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.Reader;

public class NameGenerator {
        private LastNames lastNames;
        private FemaleNames femaleNames;
        private MaleNames maleNames;

    public String getFemaleName() {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader("/home/justin/AndroidStudioProjects/fms/server/src/main/java/services/fill/fnames.json");
            femaleNames = gson.fromJson(reader, FemaleNames.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        int min = 0;
        int range = (femaleNames.data.length - min);
        int i = (int)(Math.random() * range) + min;
        return femaleNames.data[i];

    }
    public String getMaleName() {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader("/home/justin/AndroidStudioProjects/fms/server/src/main/java/services/fill/mnames.json");
            maleNames = gson.fromJson(reader, MaleNames.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        int min = 0;
        int range = (maleNames.data.length - min);
        int i = (int)(Math.random() * range) + min;
        return maleNames.data[i];
    }
    public String getLastName() {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader("/home/justin/AndroidStudioProjects/fms/server/src/main/java/services/fill/snames.json");
            lastNames = gson.fromJson(reader, LastNames.class);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        int min = 0;
        int range = (lastNames.data.length - min);
        int i = (int)(Math.random() * range) + min;
        return lastNames.data[i];
    }

}
