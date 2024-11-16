package com.saurav.WeatherApp2.controller;

import com.saurav.WeatherApp2.model.AqiResponse;
import org.springframework.beans.factory.annotation.Value;
import com.saurav.WeatherApp2.model.WeatherResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import java.text.DecimalFormat;
@Controller
public class WeatherController {
	@Value("${api.key}")
	private String apiKey;

    @GetMapping("/")
    public String getIndex()
    {
        return "index";
    }
    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city,Model model)
    {
    	String url="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appId="+apiKey+"&units=metic";
    	RestTemplate restTemplate=new RestTemplate();
        WeatherResponse weatherResponse= restTemplate.getForObject(url,WeatherResponse.class);
        String aqiUrl="http://api.openweathermap.org/data/2.5/air_pollution?lat="+weatherResponse.getCoord().getLat()+"&lon="+weatherResponse.getCoord().getLon()+"&appid="+apiKey;
        RestTemplate restTemplate1=new RestTemplate();
        AqiResponse aqiResponse= restTemplate1.getForObject(aqiUrl, AqiResponse.class);
        if(weatherResponse!=null)
        {
            model.addAttribute("city",weatherResponse.getName());
            model.addAttribute("country",weatherResponse.getSys().getCountry());
            model.addAttribute("weatherDescription",weatherResponse.getWeather().get(0).getDescription());
            int temp= (int) (weatherResponse.getMain().getTemp()-273.15);
            DecimalFormat df = new DecimalFormat("#.00");
            String formattedNum = df.format(temp);
            model.addAttribute("temperature",temp);
            model.addAttribute("humidity",weatherResponse.getMain().getHumidity());
            model.addAttribute("windSpeed",weatherResponse.getWind().getSpeed());
            String weatherIcon="wi wi-owm-"+weatherResponse.getWeather().get(0).getId();
            model.addAttribute("weatherIcon",weatherIcon);
            model.addAttribute("aqi",aqiResponse.getList().get(0).getMain().getAqi());
            model.addAttribute("co",aqiResponse.getList().get(0).getComponents().getCo());
            model.addAttribute("no",aqiResponse.getList().get(0).getComponents().getNo());

            model.addAttribute("no2",aqiResponse.getList().get(0).getComponents().getNo2());

            model.addAttribute("o3",aqiResponse.getList().get(0).getComponents().getO3());

            model.addAttribute("so2",aqiResponse.getList().get(0).getComponents().getSo2());

            model.addAttribute("pm2_5",aqiResponse.getList().get(0).getComponents().getPm2_5());

            model.addAttribute("pm10",aqiResponse.getList().get(0).getComponents().getPm10());

            model.addAttribute("nh3",aqiResponse.getList().get(0).getComponents().getNh3());

        }
        else {
            model.addAttribute("error","City not found...");
             }

        return "weather";
    }
}
