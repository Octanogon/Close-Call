package com.octanogon.closecall;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;

public class PopupWindow extends Activity {
    
    private ImageView imageView;
    GetImage imageGetter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);
        
        imageView = (ImageView) findViewById(R.id.imageView);

        imageGetter = new GetImage();

        float lat = (float) (10 + 60 * Math.random());
        float lon = (float) (10 + 60 * Math.random());
        //imageGetter.execute(new GetImagePackage(lat + "", lon + "", this));

    }

    public void setImage()
    {
        Drawable result = null;
        try {
            result = imageGetter.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        imageView.setImageDrawable(result);
    }
}