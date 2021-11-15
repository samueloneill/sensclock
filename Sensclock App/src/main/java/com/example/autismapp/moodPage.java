package com.example.autismapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;

public class moodPage extends AppCompatActivity {

    private View decorView;
    Handler bluetoothIn;
    final int handlerState = 0;
    private StringBuilder recDataString = new StringBuilder();

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_page);

        final GraphView graph = findViewById(R.id.moodGraph);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility==0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        final TextView currentTextView = findViewById(R.id.currentTextView);

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {                                          //if message is what we want
                    String readMessage = (String) msg.obj;                               //msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                   //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                     //determine the end-of-line
                    if (endOfLineIndex > 0) {                                            //make sure there data before ~

                        if (recDataString.charAt(0) == '#')                              //if it starts with # we know it is what we are looking for
                        {
                            String currentMood = recDataString.substring(1, 3);
                            String mood1date = recDataString.substring(6, 10);
                            String mood1 = recDataString.substring(6, 10);
                            String mood2date = recDataString.substring(11, 15);
                            String mood2 = recDataString.substring(11, 15);
                            String mood3date = recDataString.substring(16, 20);
                            String mood3 = recDataString.substring(16, 20);
                            String mood4date = recDataString.substring(16, 20);
                            String mood4 = recDataString.substring(16, 20);

                            String happy = "1";
                            String ok = "2";
                            String sad = "3";

                            if(currentMood.equals(happy)){
                                currentTextView.setText("Current Mood: Happy");
                            }
                            else if(currentMood.equals(ok)){
                                currentTextView.setText("Current Mood: OK");
                            }
                            else if(currentMood.equals(sad)){
                                currentTextView.setText("Current Mood: Sad");
                            }


                        }
                        recDataString.delete(0, recDataString.length());                    //clear all string data
                    }
                }
            }
        };
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
