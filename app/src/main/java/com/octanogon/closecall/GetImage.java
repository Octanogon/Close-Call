package com.octanogon.closecall;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class GetImage extends AsyncTask<String, Void, Drawable>{

    private static String APIurl = "https://api.nasa.gov/planetary/earth/imagery?";

    private String APIKey = "0yuiCFcYnM1VGYAUnCAWpEceMPgFceWQxh9TK4Rc";

    private String date;

    private String Latitude;
    private String Longitude;



    protected Drawable doInBackground(String... location){
        Latitude = location[0];
        Longitude = location[1];
        Calendar cal = Calendar.getInstance();
        date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        Drawable d = null;
        String imageURL = APIurl + "lat=" + Latitude + "&lon=" + Longitude + "&date=" + date + "&api_key=" + APIKey;


        Log.i("INFO", "Image URL = " + imageURL);

        try {

            Log.i("INFO", "Getting Image");
            InputStream is = (InputStream) new URL(imageURL).getContent();

            Log.i("INFO", "Got Image");
            d = Drawable.createFromStream(is, "src name");

        } catch (MalformedURLException e) {

            Log.e("ERROR", "MalFormedURLException");
        } catch (IOException e) {
            Log.e("ERROR", "IOException");
        }
        return d;
    }

}


