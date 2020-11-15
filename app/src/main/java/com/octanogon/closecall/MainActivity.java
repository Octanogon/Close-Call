package com.octanogon.closecall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private boolean moveMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton moveToggleButton = (FloatingActionButton) findViewById(R.id.moveButton);

        moveToggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                moveMode = !moveMode;

                if (moveMode) {

                    moveToggleButton.setImageResource(android.R.drawable.ic_menu_compass);
                }
                else {
                    moveToggleButton.setImageResource(android.R.drawable.ic_menu_edit);
                }

                Space spaceChild = (Space) findViewById(R.id.space);
                spaceChild.setMoveMode(moveMode);
            }
        });
    }
}