package com.example.bluetoothexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.Intent;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnTurnOn, btnGetVisible, btnListOfDevices, btnTurnOff;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTurnOn = (Button) findViewById(R.id.buttonTurnOn);
        btnTurnOn.setOnClickListener(this);

        btnGetVisible = (Button) findViewById(R.id.buttonGetVisible);
        btnGetVisible.setOnClickListener(this);

        btnListOfDevices = (Button) findViewById(R.id.buttonListOfDevices);
        btnListOfDevices.setOnClickListener(this);

        btnTurnOff = (Button) findViewById(R.id.buttonTurnOff);
        btnTurnOff.setOnClickListener(this);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        listView = (ListView) findViewById(R.id.listView);
    }

    public void turnOn() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void turnOff() {
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(), "Turned off", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Already off", Toast.LENGTH_LONG).show();
        }
    }


    public void getVisible() {
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivity(getVisible);
    }

    public void listDevices() {
        if (bluetoothAdapter.isEnabled()) {
            pairedDevices = bluetoothAdapter.getBondedDevices();

            ArrayList list = new ArrayList();

            for (BluetoothDevice bluetoothDevice : pairedDevices) {
                list.add(bluetoothDevice.getName());
            }

            Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();

            final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
            this.listView.setAdapter(adapter);
        }else {
            Toast.makeText(getApplicationContext(), "Please First Turned on", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonTurnOn:
                turnOn();
                break;

            case R.id.buttonGetVisible:
                getVisible();
                break;

            case R.id.buttonListOfDevices:
                listDevices();
                break;

            case R.id.buttonTurnOff:
                turnOff();
                break;

            default:
                break;
        }
    }
}