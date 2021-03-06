package com.octanogon.closecall;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class AsteroidList {
    /* Stores the asteroids */

    Asteroid[] asteroids;

    public AsteroidList() {

        findAsteroids();
    }

    public void add(Asteroid asteroid) {

        Asteroid[] newList = new Asteroid[asteroids.length + 1];

        for (int i = 0; i < asteroids.length; i++)
        {
            newList[i] = asteroids[i];
        }

        newList[asteroids.length] = asteroid;

        asteroids = newList;

        Log.d("ASTEROID LIST", "new length: " + asteroids.length);
    }

    public int getLength()
    {
        return asteroids.length;
    }

    public void updateAll(long time) {
        for (Asteroid a : asteroids) {
            a.updateTimeAndDistance(time);
        }
    }

    public void addRandom() {

        double closestDist = 2e7 + 4e8 * Math.random();
        add(new Asteroid(closestDist * 2, (float) (Math.random() * (2 * Math.PI)), 100 * Math.random() + 30, 150 * Math.random() + 40, closestDist, 1 + 10 * Math.random()));
    }

    private void findAsteroids() {

        GetAsteroidData getData = new GetAsteroidData();
        getData.execute();
        JsonObject results = null;
        try {
            results = getData.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("Results", results.toString());

        int numberOfAsteroids = (int) Integer.valueOf(results.get("element_count").toString());

        Log.i("JSON", "number: " + numberOfAsteroids);
        asteroids = new Asteroid[numberOfAsteroids];

        JsonObject asteroidDays = results.getAsJsonObject("near_earth_objects");

        int asteroidNumber = 0;

        for (String day : asteroidDays.keySet())
        {
            JsonArray asteroidsJson = asteroidDays.getAsJsonArray(day);

            for (JsonElement asteroidJsonElement : asteroidsJson)
            {
                Log.i("JSON", asteroidJsonElement.toString());

                JsonObject asteroidJson = asteroidJsonElement.getAsJsonObject();

                float minor = asteroidJson.getAsJsonObject("estimated_diameter").getAsJsonObject("kilometers").get("estimated_diameter_min").getAsFloat() * 30;
                float major = asteroidJson.getAsJsonObject("estimated_diameter").getAsJsonObject("kilometers").get("estimated_diameter_max").getAsFloat() * 30;

                double velocity = asteroidJson.getAsJsonArray("close_approach_data").get(0).getAsJsonObject().getAsJsonObject("relative_velocity").get("kilometers_per_second").getAsFloat();
                double closest_distance = asteroidJson.getAsJsonArray("close_approach_data").get(0).getAsJsonObject().getAsJsonObject("miss_distance").get("kilometers").getAsFloat();// * 1000;

                float angle = (float) (Math.PI * Math.random() * 2);
                double dist = (double) closest_distance * 10;

                Log.i("JSON Added", "dist: " + dist + " closest: " + closest_distance + " vel: " + velocity);

                Asteroid newAsteroid = new Asteroid(dist, angle, minor, major, closest_distance, velocity);

                asteroids[asteroidNumber] = newAsteroid;

                asteroidNumber += 1;
            }
        }

        Log.i("AST", Arrays.toString(asteroids));

        /*
        asteroids[0] = new Asteroid(1000000, (float) ((30.0/360.0) * (2 * Math.PI)), 100, 90, 40000, 10000);
        asteroids[1] = new Asteroid(900000, (float) ((79.0/360.0) * (2 * Math.PI)), 50, 100, 20000, 20000);
        asteroids[2] = new Asteroid(2000000, (float) ((120.0/360.0) * (2 * Math.PI)), 20, 35, 1000000, 10000);
        asteroids[3] = new Asteroid(1500000, (float) ((240.0/360.0) * (2 * Math.PI)), 40, 100, 100000, 50000);
        asteroids[4] = new Asteroid(1200000, (float) ((340.0/360.0) * (2 * Math.PI)), 10, 50, 160000, 40000);
        */
    }

    public Asteroid[] getAsteroids() {
        return asteroids;
    }
}
