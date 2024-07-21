package fetch.exercise.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader{
    public static String  readProperty(String propertyName){
        Properties prop = new Properties();
        String fileName = "configuration.properties";
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(propertyName);
    }
}
