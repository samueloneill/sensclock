package com.example.autismapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class LightPage extends Activity implements AdapterView.OnItemSelectedListener {

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
        setContentView(R.layout.activity_light_page);

        Spinner spinner = findViewById(R.id.timeDropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        brightnessBar = (SeekBar) findViewById(R.id.brightnessSeekBar);
        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightnessString = ("B" + progress);
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
                TextView selectedTextView = (TextView) findViewById(R.id.selectedLightTextView);
                selectedTextView.setText("Selected: Multicolour");
                sendData("C1");
                Toast.makeText(getBaseContext(), "Sending data for multicolour", Toast.LENGTH_SHORT).show();
            }
        });

        Button greenBtn = (Button) findViewById(R.id.greenButton);
        greenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedLightTextView);
                selectedTextView.setText("Selected: Green");
                sendData("C2");
                Toast.makeText(getBaseContext(), "Sending data for green", Toast.LENGTH_SHORT).show();
            }
        });

        Button blueBtn = (Button) findViewById(R.id.blueButton);
        blueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedLightTextView);
                selectedTextView.setText("Selected: Blue");
                sendData("C3");
                Toast.makeText(getBaseContext(), "Sending data for blue", Toast.LENGTH_SHORT).show();
            }
        });

        Button cyanBtn = (Button) findViewById(R.id.cyanButton);
        cyanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedLightTextView);
                selectedTextView.setText("Selected: Light Blue");
                sendData("C4");
                Toast.makeText(getBaseContext(), "Sending data for light blue", Toast.LENGTH_SHORT).show();
            }
        });

        Button orangeBtn = (Button) findViewById(R.id.orangeButton);
        orangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedLightTextView);
                selectedTextView.setText("Selected: Orange");
                sendData("C5");
                Toast.makeText(getBaseContext(), "Sending data for orange", Toast.LENGTH_SHORT).show();
            }
        });

        Button redBtn = (Button) findViewById(R.id.redButton);
        redBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedLightTextView);
                selectedTextView.setText("Selected: Red");
                sendData("C6");
                Toast.makeText(getBaseContext(), "Sending data for red", Toast.LENGTH_SHORT).show();
            }
        });

        Button pinkBtn = (Button) findViewById(R.id.pinkButton);
        pinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedLightTextView);
                selectedTextView.setText("Selected: Pink");
                sendData("C7");
                Toast.makeText(getBaseContext(), "Sending data for pink", Toast.LENGTH_SHORT).show();
            }
        });

        Button purpleBtn = (Button) findViewById(R.id.purpleButton);
        purpleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedLightTextView);
                selectedTextView.setText("Selected: Purple");
                sendData("C8");
                Toast.makeText(getBaseContext(), "Sending data for purple", Toast.LENGTH_SHORT).show();
            }
        });

        Button yellowBtn = (Button) findViewById(R.id.yellowButton);
        yellowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView selectedTextView = (TextView) findViewById(R.id.selectedLightTextView);
                selectedTextView.setText("Selected: Yellow");
                sendData("C9");
                Toast.makeText(getBaseContext(), "Sending data for yellow", Toast.LENGTH_SHORT).show();
            }
        });

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        MACaddress = getApplicationContext().getResources().getString(R.string.BTMAC);
        deviceUUID = getApplicationContext().getResources().getString(R.string.UUID);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility==0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });
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

    public String timeString;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String times[] = getApplicationContext().getResources().getStringArray(R.array.times);
        String selected = parent.getItemAtPosition(position).toString();

        if (selected.equals(times[0])){
            timeString = ("T0");
            sendData(timeString);
        }
        if (selected.equals(times[1])){
            timeString = ("T1");
            sendData(timeString);
        }
        if (selected.equals(times[2])){
            timeString = ("T2");
            sendData(timeString);
        }
        if (selected.equals(times[3])){
            timeString = ("T3");
            sendData(timeString);
        }
        if (selected.equals(times[4])){
            timeString = ("T4");
            sendData(timeString);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
