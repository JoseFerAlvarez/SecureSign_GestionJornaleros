package com.example.securesign;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class ActivitySplashScreen extends Activity {
    private final int timesplashscreen=2000;

    private ImageView lggarlic;
    private ImageView lgname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lggarlic = (ImageView) findViewById(R.id.lggarlic);
        lgname = (ImageView) findViewById(R.id.lgname);

        lggarlic.setImageResource(R.drawable.lg_garlic);
        lgname.setImageResource(R.drawable.lg_name);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(ActivitySplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },timesplashscreen);
    }
}