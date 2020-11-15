package com.octanogon.closecall;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Calendar;

import java.text.SimpleDateFormat;


public class GetAsteroidData extends AsyncTask<Void, Void, JsonObject>{
    private static String apiKey = "0yuiCFcYnM1VGYAUnCAWpEceMPgFceWQxh9TK4Rc";
    JsonParser jsonParser;

    protected JsonObject doInBackground(Void... urls) {
        // get current date
        Calendar cal = Calendar.getInstance();
        String currentDateString = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        cal.add(Calendar.DATE, -6);
        String earlierDateString = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        try {
            // issue the Get request

            String urlText = "https://api.nasa.gov/neo/rest/v1/feed?start_date=" + earlierDateString + "&end_data=" + currentDateString + "&api_key=" + apiKey;
            URL url = new URL(urlText);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                JsonObject output = jsonParser.parseString(stringBuilder.toString()).getAsJsonObject();
                return output;
            }
            finally{
                urlConnection.disconnect();
            }

        }
        catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }

    }
}

