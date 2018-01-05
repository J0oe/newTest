package com.ex.admin.databclass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Person");
    EditText nameEdit;
    EditText passEdit;


    FileInputStream fileNumberRead;
    String namePersonReg;
    FileInputStream fileNumberRead2;
    String passPersonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            fileNumberRead = openFileInput("nameReg.txt");
        } catch (Throwable e) {
        }
        byte[] buffer1 = new byte[0];
        try {
            buffer1 = new byte[fileNumberRead.available()];
        } catch (Throwable throwable) {
        }
        try {
            fileNumberRead.read(buffer1);
        } catch (Throwable e) {
        }
        namePersonReg = (new String(buffer1));
        if (namePersonReg.length() != 0) {
            nameEdit = (EditText) findViewById(R.id.texntNameId);

            nameEdit.setText(namePersonReg);
        }


        try {
            fileNumberRead2 = openFileInput("passReg.txt");
        } catch (Throwable e) {
        }
        byte[] buffer = new byte[0];
        try {
            buffer = new byte[fileNumberRead2.available()];
        } catch (Throwable throwable) {
        }
        try {
            fileNumberRead2.read(buffer);
        } catch (Throwable e) {
        }
        passPersonReg = (new String(buffer));
        if (passPersonReg.length() != 0) {
            passEdit = findViewById(R.id.textPasswordId);
            passEdit.setText(passPersonReg);
        }


    }


    public void onCLickStart(View view) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Person/" + nameEdit.getText().toString() + "/password");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    String date = dataSnapshot.getValue().toString();
                    if (date.equals(passEdit.getText().toString()))
                        startActivity(new Intent(getApplicationContext(), WorkerRoom.class));
                } catch (Throwable throwable) {
                    Toast.makeText(getApplicationContext(), "Wrong login or pass", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void onClickRegistr(View view) {
        startActivity(new Intent(this, RegistrTable.class));
    }
}