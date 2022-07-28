package com.example.crt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;


public class BluetoothActivity extends AppCompatActivity {
    ListView l;
    ListView k;
    String devices[]
            = { "Paired Devices", "Device1","Device1","Device1","Device1","Device1" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        getSupportActionBar().setTitle("Configuration");

         l = findViewById(R.id.listAvail);
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                devices);
        l.setAdapter(arr);

        k = findViewById(R.id.list);
        ArrayAdapter<String> arra;
        arra
                = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                devices);
        k.setAdapter(arr);
    }
}