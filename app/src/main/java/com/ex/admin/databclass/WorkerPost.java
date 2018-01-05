package com.ex.admin.databclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    ListView listViewPost;
    ArrayList<String> arrayListPost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        DatabaseReference mDataPost = FirebaseDatabase.getInstance().getReference("Post2/idmax");

        mDataPost.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                maxId = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_table);
        listViewPost = findViewById(R.id.idListViewMessege);
        arrayListPost = new ArrayList<>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Post2/idpost/1");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String date = dataSnapshot.getValue().toString();
                arrayListPost.add(date);
                ArrayAdapter<String> adapterForMes = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayListPost);
                listViewPost.setAdapter(adapterForMes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void onClickAddPost(View view) {
        EditText editTextPost = findViewById(R.id.idTextMessege);
        addNewPost(Integer.parseInt(maxId), editTextPost.getText().toString(), "bla");

    }

    private void addNewPost(Integer idPost, String mesPost, String nickname) {
        idPostPlus();
        mDatabase.child(String.valueOf(idPost)).push();
        Post post = new Post(nickname, mesPost, idPost);
        Map<String, Object> postValues = post.toMap();
        Map<String, Object> childUpdates = new HashMap<>();


        childUpdates.put(String.valueOf(idPost), postValues);
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

}


