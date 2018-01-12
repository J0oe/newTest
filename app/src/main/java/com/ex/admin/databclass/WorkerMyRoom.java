package com.ex.admin.databclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 12.01.2018.
 */

public class WorkerMyRoom extends AppCompatActivity {
    FileOutputStream fileNumber;
    ArrayAdapter<String> adapterForMes;
    ListView listViewPost;
    ArrayList<String> arrayListPost;
    String maxId;
    FileInputStream fileNumberRead;
    String idMyRoom;
    Integer date;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_chatroom);
        date = 0;

        listViewPost = findViewById(R.id.idListViewMessegeMyChat);
        arrayListPost = new ArrayList<>();
        adapterForMes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListPost);
        listViewPost.setAdapter(adapterForMes);

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

        idMyRoom = (new String(buffer1));


        DatabaseReference mDataPostMaxId = FirebaseDatabase.getInstance().getReference("MyRoom/" + idMyRoom + "/idmax");

        mDataPostMaxId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                maxId = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDataPostMaxId.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {

                    if (date != 0) {
                        maxId = dataSnapshot.getValue().toString();
                        chatRefresh();
                        listViewPost.smoothScrollByOffset(Integer.valueOf(maxId));
                    }
                    if (date == 0) {

                        for (int a = 1; a <=Integer.valueOf(maxId); a++) {

                            DatabaseReference mData = FirebaseDatabase.getInstance().getReference("MyRoom/" + idMyRoom + "/idpost/" + a);

                            mData.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    arrayListPost.add(dataSnapshot.getValue().toString());
                                    adapterForMes.notifyDataSetChanged();
                                    listViewPost.setSelection(Integer.valueOf(maxId));

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                    date++;
                } catch (Throwable throwable) {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void onCLickLeaveMyRoom(View view) {
        try {
            fileNumber = openFileOutput("myRoomId.txt", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileNumber.write("empty".getBytes());
        } catch (IOException e) {

        } finally {
            try {
                fileNumber.close();
            } catch (IOException e) {

            }
        }

        startActivity(new Intent(getApplicationContext(), ActivityEvent.class));
        finish();
    }


    public void chatRefresh() {
        Integer maxPost = Integer.valueOf(maxId)  ;

        DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference("MyRoom/" + idMyRoom +"/" + maxPost);

        mDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayListPost.add(dataSnapshot.getValue().toString());
                adapterForMes.notifyDataSetChanged();
                listViewPost.smoothScrollByOffset(Integer.valueOf(maxId));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}