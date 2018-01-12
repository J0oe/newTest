package com.ex.admin.databclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.FileInputStream;

/**
 * Created by Admin on 10.01.2018.
 */

public class ActivityEvent extends AppCompatActivity {
    FileInputStream fileNumberRead;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_one_table);

        try {
            fileNumberRead = openFileInput("myRoomId.txt");
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

        String namePersonReg = (new String(buffer1));
        if (namePersonReg.equals("empty")) {
        } else {
            startActivity(new Intent(getApplicationContext(), WorkerMyRoom.class));
            finish();

        }
    }


    public void onCLickAddNewGroup(View view) {
        startActivity(new Intent(this, MyChatRoom.class));
        finish();

    }

}
