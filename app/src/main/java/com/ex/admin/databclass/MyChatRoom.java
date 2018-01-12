package com.ex.admin.databclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 10.01.2018.
 */

public class MyChatRoom extends AppCompatActivity {
    String maxMyRoomID;
    String maxID;
    FileOutputStream fileNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DatabaseReference maxMyRoom = FirebaseDatabase.getInstance().getReference("MyRoom/maxRoom");

        maxMyRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                maxMyRoomID = dataSnapshot.getValue().toString();

                maxMyRoomID = String.valueOf(Integer.valueOf(maxMyRoomID) + 1);
                DatabaseReference mBasaIDmax = FirebaseDatabase.getInstance().getReference("MyRoom/" + String.valueOf(maxMyRoomID) + "/idmax");
                mBasaIDmax.setValue("1");

                DatabaseReference mBasaIDpost = FirebaseDatabase.getInstance().getReference("MyRoom/" + String.valueOf(maxMyRoomID) + "/idpost/1");
                mBasaIDpost.setValue(maxMyRoomID);
                maxMyRoom.setValue(maxMyRoomID);
                DatabaseReference myRoomMess = FirebaseDatabase.getInstance().getReference("MyRoom/maxRoom");
                myRoomMess.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        maxID = dataSnapshot.getValue().toString();
                        try {
                            fileNumber = openFileOutput("myRoomId.txt", MODE_PRIVATE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            fileNumber.write(maxMyRoomID.getBytes());
                        } catch (IOException e) {

                        } finally {
                            try {
                                fileNumber.close();
                            } catch (IOException e) {

                            }
                        }

                        startActivity(new Intent(getApplicationContext(), WorkerMyRoom.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        finish();

    }


}

