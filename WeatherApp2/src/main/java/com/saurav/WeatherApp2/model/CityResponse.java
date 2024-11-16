package com.saurav.WeatherApp2.model;

import lombok.Data;

@Data
public class CityResponse {
    private Lat latitude;
    private Lon longitude;
    @Data
    public static class Lat{
        private double lat;
    }

    @Data
    public static class Lon
    {
        private double lon;
    }

}
