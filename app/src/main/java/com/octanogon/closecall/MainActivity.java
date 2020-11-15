package com.octanogon.closecall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    GetImage imageGetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Space spaceChild = (Space) findViewById(R.id.space);

        spaceChild.passMainActivity(this);

        FloatingActionButton moveToggleButton = (FloatingActionButton) findViewById(R.id.addButton);

        moveToggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {

                Space spaceChild = (Space) findViewById(R.id.space);
                spaceChild.addRandomAsteroid();
            }
        });

        FloatingActionButton resetButton = (FloatingActionButton) findViewById(R.id.resetButton);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Space spaceChild = (Space) findViewById(R.id.space);
                spaceChild.reset();
            }
        });

        FloatingActionButton centerButton = (FloatingActionButton) findViewById(R.id.centerButton);

        centerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Space spaceChild = (Space) findViewById(R.id.space);
                spaceChild.centerView();
            }
        });
    }

    public void doCollision() {

        //Intent image = new Intent(this, PopupWindow.class);
        //startActivity(image);

        /*
        if (imageGetter != null)
        {
            imageGetter.cancel(true);
            Log.i("INFO", "cancelled image");
        }
        */
        float lat = (float) (10 + 60 * Math.random());
        float lon = (float) (10 + 60 * Math.random());

        // Get the population

        GetPopulation getPopulation = new GetPopulation();

        getPopulation.execute(new GetPopulationPackage(lat + "", lon + "", getApplicationContext()));



        // Get the Image
        ImageView asteroidImageView = findViewById(R.id.collisionImage);

        imageGetter = new GetImage();

        imageGetter.execute(new GetImagePackage(lat + "", lon + "", asteroidImageView));
    }
}