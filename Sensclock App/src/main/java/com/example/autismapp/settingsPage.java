package com.example.autismapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class settingsPage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private CheckBox epilepsyCheck;
    private CheckBox noneCheck;
    private CheckBox adhdCheck;
    private CheckBox sound1Check;
    private CheckBox sound2Check;
    private CheckBox sleepCheck;
    private Button nextBtn;
    private EditText fave1;
    private EditText fave2;
    private EditText fave3;

    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        fave1 = findViewById(R.id.fave1EditText);
        fave2 = findViewById(R.id.fave2EditText);
        fave3 = findViewById(R.id.fave3EditText);

        epilepsyCheck = findViewById(R.id.epilepsyCheckBox);
        epilepsyCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noneCheck.isChecked()){
                    noneCheck.toggle();
                }
            }
        });

        adhdCheck = findViewById(R.id.adhdCheckBox);
        adhdCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noneCheck.isChecked()){
                    noneCheck.toggle();
                }
            }
        });

        sound1Check = findViewById(R.id.sound1CheckBox);
        sound1Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noneCheck.isChecked()){
                    noneCheck.toggle();
                }
                if(sound2Check.isChecked()){
                    sound2Check.toggle();
                }
            }
        });

        sound2Check = findViewById(R.id.sound2CheckBox);
        sound2Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noneCheck.isChecked()){
                    noneCheck.toggle();
                }
                if(sound1Check.isChecked()){
                    sound1Check.toggle();
                }
            }
        });

        sleepCheck = findViewById(R.id.sleepCheckBox);
        sleepCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(noneCheck.isChecked()){
                    noneCheck.toggle();
                }
            }
        });

        noneCheck = findViewById(R.id.noneCheckBox);
        noneCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(epilepsyCheck.isChecked()){
                    epilepsyCheck.toggle();
                }
                if(adhdCheck.isChecked()){
                    adhdCheck.toggle();
                }
                if(sound1Check.isChecked()){
                    sound1Check.toggle();
                }
                if(sound2Check.isChecked()){
                    sound2Check.toggle();
                }
                if(sleepCheck.isChecked()){
                    sleepCheck.toggle();
                }
            }
        });

        nextBtn = findViewById(R.id.confSetBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity();
            }
        });

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });
    }

    private void activity(){
        if(epilepsyCheck.isChecked()){
            editor.putBoolean("epilepsyBool", true);
            editor.apply();
        }
        if(adhdCheck.isChecked()){
            editor.putBoolean("adhdBool", true);
            editor.apply();
        }
        if(sound1Check.isChecked()){
            editor.putBoolean("sound1Bool", true);
            editor.apply();
        }
        if(sound2Check.isChecked()){
            editor.putBoolean("sound2Bool", true);
            editor.apply();
        }
        if(sleepCheck.isChecked()){
            editor.putBoolean("sleepBool", true);
            editor.apply();
        }
        if(noneCheck.isChecked()){
            editor.putBoolean("noneBool", true);
            editor.apply();
        }

        String fave1text = fave1.getText().toString();
        String fave2text = fave2.getText().toString();
        String fave3text = fave3.getText().toString();

        editor.putString(fave1text, "");
        editor.putString(fave2text, "");
        editor.putString(fave3text, "");
        editor.apply();

        finish();
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