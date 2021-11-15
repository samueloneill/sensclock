package com.example.autismapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class clockPage extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static int selectionFlag;
    private View decorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_page);
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility==0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        Button setClockBtn = (Button) findViewById(R.id.setClockBtn);
        setClockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionFlag = 0;
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button setAlarmBtn = (Button) findViewById(R.id.setAlarmBtn);
        setAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionFlag = 1;
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        CheckBox lightingCheckBox = (CheckBox) findViewById(R.id.lightingCheckBox);
        lightingCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlLightActivity();
            }
        });
    }

    public void openAlLightActivity() {
        Intent intent = new Intent(this, alarmLightPage.class);
        startActivity(intent);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        switch (selectionFlag){
            case 0:
                TextView selectedTime = (TextView) findViewById(R.id.selectedTimeTextView);
                if(minute < 10){
                    selectedTime.setText(hourOfDay + ":" +  "0" + minute);
                }
                else{
                    selectedTime.setText(hourOfDay + ":" + minute);
                }
                break;
            case 1:
                TextView selectedAlarm = (TextView) findViewById(R.id.selectedAlarmTextView);
                if(minute < 10){
                    selectedAlarm.setText(hourOfDay + ":" +  "0" + minute);
                }
                else{
                    selectedAlarm.setText(hourOfDay + ":" + minute);
                }
                break;
        }
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
