package com.octanogon.closecall;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;


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


public class GetImage extends AsyncTask<GetImagePackage, Void, Drawable>{

    private static String APIurl = "https://api.nasa.gov/planetary/earth/imagery?";

    private String APIKey = "0yuiCFcYnM1VGYAUnCAWpEceMPgFceWQxh9TK4Rc";

    private String date;

    private String Latitude;
    private String Longitude;


    private ImageView imageViewCallback;


    protected Drawable doInBackground(GetImagePackage... imPackage){

        imageViewCallback = imPackage[0].imageViewCallback;
        Latitude = imPackage[0].latitude;
        Longitude = imPackage[0].longitude;
        Calendar cal = Calendar.getInstance();
        date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        Drawable d = null;
        String imageURL = APIurl + "lat=" + Latitude + "&lon=" + Longitude + "&dim=0.15" + "&api_key=" + APIKey;


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

    @Override
    protected void onPostExecute(Drawable drawable) {
        super.onPostExecute(drawable);
        imageViewCallback.setImageDrawable(drawable);
    }
}


