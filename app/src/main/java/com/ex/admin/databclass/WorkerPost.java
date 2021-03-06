package com.ex.admin.databclass;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 03.01.2018.
 */

public class WorkerPost extends AppCompatActivity {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Post2/idpost");
    String maxId;

    FileInputStream nicknamePost;
    String nicknamePostMess;
    Integer date;
    ArrayAdapter<String> adapterForMes;
    ListView listViewPost;
    ArrayList<String> arrayListPost;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_table);

        date = 0;
        DatabaseReference mDataPost = FirebaseDatabase.getInstance().getReference("Post2/idmax");
        listViewPost = findViewById(R.id.idListViewMessege);
        arrayListPost = new ArrayList<>();
        adapterForMes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListPost);
        listViewPost.setAdapter(adapterForMes);

        mDataPost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                maxId = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDataPost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {

                    if (date != 0) {
                        maxId = dataSnapshot.getValue().toString();
                        chatRefresh();
                        listViewPost.smoothScrollByOffset(Integer.valueOf(maxId));
                    }
                    if (date == 0) {

                        for (int a = 1; a < Integer.valueOf(maxId); a++) {

                            DatabaseReference mData = FirebaseDatabase.getInstance().getReference("Post2/idpost/" + a);

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
                    startActivity(new Intent(getApplicationContext(), WorkerPost.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void onClickAddPost(View view) {
        EditText editTextPost = findViewById(R.id.idTextMessege);

        try {
            nicknamePost = openFileInput("nickname.txt");
        } catch (Throwable e) {
        }
        byte[] buffer1 = new byte[0];
        try {
            buffer1 = new byte[nicknamePost.available()];
        } catch (Throwable throwable) {
        }
        try {
            nicknamePost.read(buffer1);
        } catch (Throwable e) {
        }
        nicknamePostMess = (new String(buffer1));

        if (editTextPost.getText().toString().equals("")) {

        } else {
            addNewPost(Integer.parseInt(maxId), editTextPost.getText().toString(), nicknamePostMess);
            editTextPost.setText("");
        }


    }

    private void addNewPost(Integer idPost, String mesPost, String nickname) {
        idPostPlus();
        mDatabase.child(String.valueOf(idPost)).push();
        Post post = new Post(nickname, mesPost, idPost);
        Map<String, Object> childUpdates = new HashMap<>();
        listViewPost.smoothScrollByOffset(Integer.valueOf(maxId));


        childUpdates.put(String.valueOf(idPost), nickname + ": " + post.post);
        mDatabase.updateChildren(childUpdates);

    }

    public void idPostPlus() {
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Post2/idmax");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                maxId = dataSnapshot.getValue().toString();
                Integer dateId = Integer.parseInt(maxId) + 1;
                maxId = String.valueOf(dateId);
                mDatabase.setValue(maxId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void chatRefresh() {
        Integer maxPost = Integer.valueOf(maxId) - 1;

        DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference("Post2/idpost/" + maxPost);

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




