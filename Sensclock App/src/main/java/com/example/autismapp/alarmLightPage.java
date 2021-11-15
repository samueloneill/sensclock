package com.example.autismapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class alarmLightPage extends Activity {

    private View decorView;
    private SeekBar brightnessBar;
    public String brightnessString;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outputStream = null;
    public String MACaddress = null;
    public String deviceUUID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_light_page);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility==0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        brightnessBar = (SeekBar) findViewById(R.id.brightnessSeekBar);
        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightnessString = ("A" + progress);
                sendData(brightnessString);
                Toast.makeText(getBaseContext(), "String to send: " + brightnessString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ImageButton multiColorBtn = (ImageButton) findViewById(R.id.multiColourButton);
        multiColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedAlLightTextView);
                selectedTextView.setText("Selected: Multicolour");
                sendData("*multi");
                Toast.makeText(getBaseContext(), "Sending data for multicolour", Toast.LENGTH_SHORT).show();
            }
        });

        Button greenBtn = (Button) findViewById(R.id.greenButton);
        greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedAlLightTextView);
                selectedTextView.setText("Selected: Green");
                sendData("1");
                Toast.makeText(getBaseContext(), "Sending data for green", Toast.LENGTH_SHORT).show();
            }
        });

        Button blueBtn = (Button) findViewById(R.id.blueButton);
        blueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedAlLightTextView);
                selectedTextView.setText("Selected: Blue");
                sendData("7");
                Toast.makeText(getBaseContext(), "Sending data for blue", Toast.LENGTH_SHORT).show();
            }
        });

        Button cyanBtn = (Button) findViewById(R.id.cyanButton);
        cyanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedAlLightTextView);
                selectedTextView.setText("Selected: Light Blue");
                sendData("8");
                Toast.makeText(getBaseContext(), "Sending data for light blue", Toast.LENGTH_SHORT).show();
            }
        });

        Button orangeBtn = (Button) findViewById(R.id.orangeButton);
        orangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedAlLightTextView);
                selectedTextView.setText("Selected: Orange");
                sendData("9");
                Toast.makeText(getBaseContext(), "Sending data for orange", Toast.LENGTH_SHORT).show();
            }
        });

        Button redBtn = (Button) findViewById(R.id.redButton);
        redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedAlLightTextView);
                selectedTextView.setText("Selected: Red");
                sendData("10");
                Toast.makeText(getBaseContext(), "Sending data for red", Toast.LENGTH_SHORT).show();
            }
        });

        Button pinkBtn = (Button) findViewById(R.id.pinkButton);
        pinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedAlLightTextView);
                selectedTextView.setText("Selected: Pink");
                sendData("11");
                Toast.makeText(getBaseContext(), "Sending data for pink", Toast.LENGTH_SHORT).show();
            }
        });

        Button purpleBtn = (Button) findViewById(R.id.purpleButton);
        purpleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedAlLightTextView);
                selectedTextView.setText("Selected: Purple");
                sendData("12");
                Toast.makeText(getBaseContext(), "Sending data for purple", Toast.LENGTH_SHORT).show();
            }
        });

        Button yellowBtn = (Button) findViewById(R.id.yellowButton);
        yellowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedAlLightTextView);
                selectedTextView.setText("Selected: Yellow");
                sendData("13");
                Toast.makeText(getBaseContext(), "Sending data for yellow", Toast.LENGTH_SHORT).show();
            }
        });

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        MACaddress = getApplicationContext().getResources().getString(R.string.BTMAC);
        deviceUUID = getApplicationContext().getResources().getString(R.string.UUID);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MACaddress = getApplicationContext().getResources().getString(R.string.BTMAC);
        deviceUUID = getApplicationContext().getResources().getString(R.string.UUID);

        BluetoothDevice device = btAdapter.getRemoteDevice(MACaddress);

        try {
            btSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(deviceUUID));
        } catch (IOException e1) {
            Toast.makeText(getBaseContext(), "ERROR - Couldn't open bluetooth socket", Toast.LENGTH_SHORT).show();
        }

        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                Toast.makeText(getBaseContext(), "ERROR - Couldn't close bluetooth socket", Toast.LENGTH_SHORT).show();
            }
        }

        try {
            outputStream = btSocket.getOutputStream();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "ERROR - Couldn't create bluetooth data stream", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            btSocket.close();
        } catch (IOException e2) {
            Toast.makeText(getBaseContext(), "ERROR - Couldn't close bluetooth socket", Toast.LENGTH_SHORT).show();
        }
    }

    private BluetoothSocket createBTSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(UUID.fromString(deviceUUID));
    }

    private void sendData(String message) {
        byte[] msgBuffer = message.getBytes();

        try {
            outputStream.write(msgBuffer);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "ERROR - Device not found, please check your connection", Toast.LENGTH_SHORT).show();
            finish();
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
