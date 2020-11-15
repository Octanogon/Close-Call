package com.octanogon.closecall;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import java.text.SimpleDateFormat;
import okhttp3.*;


public class GetPopulation extends AsyncTask<String, Void, Integer> {
    private static String apiKey = "030ce8e697msh1b2e53c97d53e7dp1e1198jsnb91ccf928dd6";

    private String latitude;
    private String longitude;
    private String range;
    private Integer populationInRange = 0;
    JsonParser jsonParser;

    protected Integer doInBackground(String... locationAndRadius) {
        // get current date
        latitude = locationAndRadius[0];
        longitude = locationAndRadius[1];
        // Approximate radius due to time constriants
        range = "8000";

        try {
            // issue the Get request

            String urlText = "https://geocodeapi.p.rapidapi.com/GetNearestCities?latitude=" + latitude + "&longitude=" + longitude + "&range=" + range;

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(urlText)
                    .get()
                    .addHeader("x-rapidapi-key", "030ce8e697msh1b2e53c97d53e7dp1e1198jsnb91ccf928dd6")
                    .addHeader("x-rapidapi-host", "geocodeapi.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();
            if (response.code() == 204){
                populationInRange = 0;
                Log.i("Response", "Zero Population");
            } else {
                String respStr = response.body().string();
                Log.i("Response", respStr);
                JSONArray jsonA = new JSONArray(respStr);
                for (int i = 0; i < jsonA.length(); i++) {
                    JSONObject tempJson = new JSONObject(jsonA.get(i).toString());
                    Integer tempPop = (Integer) tempJson.get("Population");
                    populationInRange += tempPop;
                    Log.i("TempPop", Integer.toString(tempPop));
                }
            }

            return populationInRange;
        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }

    }
}