package com.octanogon.closecall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}