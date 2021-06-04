import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=" + Key.apiWeatherKey());

        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";

        while (scanner.hasNext()) {
            result += scanner.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));
        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int index = 0; index < getArray.length(); index++) {
            JSONObject weatherObj = getArray.getJSONObject(index);
            model.setIcon((String) weatherObj.get("icon"));
            model.setMain((String) weatherObj.get("main"));
        }

        return "Город: " + model.getName() + "\nТемпература: "
                + model.getTemp() + " C\nПогода: "
                + model.getMain() + "\nВлажность: "
                + model.getHumidity() + " %";
    }
}
