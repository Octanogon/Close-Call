package com.octanogon.closecall;

import android.content.Context;

public class GetPopulationPackage {

    public String latitude;
    public String longitude;
    public Context context;
    public GetPopulationPackage(String lat, String lon, Context con)
    {
        latitude = lat;
        longitude = lon;
        context = con;
    }
}
