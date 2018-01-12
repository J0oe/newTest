package com.ex.admin.databclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Admin on 03.01.2018.
 */

public class WorkerRoom extends AppCompatActivity {
    String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_first);
    }

    public void onClickStartChat(View view) {
        startActivity(new Intent(this, WorkerPost.class));
    }

    public void onClickStartAllHeroes(View view) {
        startActivity(new Intent(this, WorkerHero.class));
    }

    public void onCLickAlarm(View view) {
        final DatabaseReference mDataAlarm = FirebaseDatabase.getInstance().getReference("Alarm");
        mDataAlarm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                date = dataSnapshot.getValue().toString();
                String writeToDB = String.valueOf(Integer.valueOf(date) + 1);
                mDataAlarm.setValue(writeToDB);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void onClickEvent(View view) {
        startActivity(new Intent(this, ActivityEvent.class));
    }
}
