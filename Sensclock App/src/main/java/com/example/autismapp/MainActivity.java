package com.example.autismapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(MainActivity.this, settingsPage.class);
        startActivity(intent);

        setContentView(R.layout.activity_main);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        Button clockBtn = (Button) findViewById(R.id.clockBtn);
        clockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClockActivity();
            }
        });

        Button lightsBtn = (Button) findViewById(R.id.lightsBtn);
        lightsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLightsActivity();
            }
        });

        Button scheduleBtn = (Button) findViewById(R.id.scheduleBtn);
        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScheduleActivity();
            }
        });

        Button moodBtn = (Button) findViewById(R.id.moodBtn);
        moodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMoodActivity();
            }
        });

        Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });
    }

    public void openSettingsActivity() {
        Intent intent = new Intent(this, DeviceListActivity .class);
        startActivity(intent);
    }

    public void openClockActivity() {
        Intent intent = new Intent(this, clockPage.class);
        startActivity(intent);
    }

    public void openLightsActivity() {
        Intent intent = new Intent(this, LightPage.class);
        startActivity(intent);
    }

    public void openScheduleActivity() {
        Intent intent = new Intent(this, schedulePage.class);
        startActivity(intent);
    }

    public void openMoodActivity() {
        Intent intent = new Intent(this, moodPage.class);
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }
}