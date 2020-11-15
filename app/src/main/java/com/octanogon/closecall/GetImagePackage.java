package com.octanogon.closecall;

import android.widget.ImageView;

public class GetImagePackage {

    public String latitude;
    public String longitude;
    public ImageView imageViewCallback;

    public GetImagePackage(String lat, String lon, ImageView imgCallBack)
    {
        latitude = lat;
        longitude = lon;
        imageViewCallback = imgCallBack;
    }
}
