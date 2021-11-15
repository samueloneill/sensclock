package com.example.autismapp;

import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class schedulePage extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private View decorView;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outputStream = null;
    public String MACaddress = null;
    public String deviceUUID = null;

    public String scheduleTextString = null;
    public String schedTime = null;
    public int nextTaskText = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_page);

        addKeyListener();

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility==0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        Button setTimeBtn = findViewById(R.id.timeBtn);
        setTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePicker();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button t1DelBtn = findViewById(R.id.t1DelBtn);
        t1DelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t1Text = findViewById(R.id.t1TextView);
                t1Text.setText("");
                sendData("S1");
            }
        });

        Button t2DelBtn = findViewById(R.id.t2DelBtn);
        t2DelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t2Text = findViewById(R.id.t2TextView);
                t2Text.setText("");
                sendData("S2");
            }
        });

        Button t3DelBtn = findViewById(R.id.t3DelBtn);
        t3DelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t3Text = findViewById(R.id.t3TextView);
                t3Text.setText("");
                sendData("S3");
            }
        });

        Button t4DelBtn = findViewById(R.id.t4DelBtn);
        t4DelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t4Text = findViewById(R.id.t4TextView);
                t4Text.setText("");
                sendData("S4");
            }
        });

        Button t5DelBtn = findViewById(R.id.t5DelBtn);
        t5DelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t5Text = findViewById(R.id.t5TextView);
                t5Text.setText("");
                sendData("S5");
            }
        });

        Button confirmBtn = findViewById(R.id.confirmSchedBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(scheduleTextString.isEmpty()==true){
                    Toast.makeText(getBaseContext(), "Error: No Text Entered", Toast.LENGTH_SHORT).show();
                }
                else{
                    String text = "S0" + "~" + schedTime + "~" + scheduleTextString;
                    sendData(text);
                    displayTask(text);
                    Toast.makeText(getBaseContext(), "String: " + text, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        MACaddress = getApplicationContext().getResources().getString(R.string.BTMAC);
        deviceUUID = getApplicationContext().getResources().getString(R.string.UUID);
    }

    public void addKeyListener() {
        final EditText scheduleText = findViewById(R.id.detailsEditText);

        //Keep track of user text input and send over bluetooth when finished
        scheduleText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((keyCode == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
                    scheduleTextString = scheduleText.getText().toString();
                    return true;
                }
                return false;
            }
        });
    }

    private void displayTask(String message) {
        TextView task1 = findViewById(R.id.t1TextView);
        TextView task2 = findViewById(R.id.t2TextView);
        TextView task3 = findViewById(R.id.t3TextView);
        TextView task4 = findViewById(R.id.t4TextView);
        TextView task5 = findViewById(R.id.t5TextView);

        String text1 = task1.getText().toString();
        String text2 = task2.getText().toString();
        String text3 = task3.getText().toString();
        String text4 = task4.getText().toString();
        String text5 = task5.getText().toString();

        if(text1.isEmpty()){
            task1.setText(message);
            return;
        }
        else if(text2.isEmpty()){
            task2.setText(message);
            return;
        }
        else if(text3.isEmpty()){
            task3.setText(message);
            return;
        }
        else if(text4.isEmpty()){
            task4.setText(message);
            return;
        }
        else if(text5.isEmpty()){
            task5.setText(message);
            return;
        }
        else{
            Toast.makeText(getBaseContext(), "Error: Task limit reached, please delete a task", Toast.LENGTH_SHORT).show();
        }
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
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        Button setTimeBtn = findViewById(R.id.timeBtn);
        String hour = String.valueOf(hourOfDay);
        String min = String.valueOf(minute);

        if(minute < 10){
            setTimeBtn.setText(hourOfDay + ":0" + minute);
            schedTime = hour + "0" + min;
            Toast.makeText(getBaseContext(), "String:" + schedTime, Toast.LENGTH_SHORT).show();
        }
        else{
            setTimeBtn.setText(hourOfDay + ":" + minute);
            schedTime = hour + minute;
            Toast.makeText(getBaseContext(), "String: " + schedTime, Toast.LENGTH_SHORT).show();
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
