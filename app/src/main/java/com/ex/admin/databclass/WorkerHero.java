package com.ex.admin.databclass;

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

import java.util.ArrayList;

/**
 * Created by Admin on 09.01.2018.
 */

public class WorkerHero extends AppCompatActivity {
    ArrayList<String> listHero;
    String maxIdHero;
    ArrayAdapter adapterForHero;
    ListView listViewHero;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hero_listview);
        listHero = new ArrayList<>();
        listViewHero = findViewById(R.id.idListViewHero);

        adapterForHero = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listHero);
        listViewHero.setAdapter(adapterForHero);

        DatabaseReference mDatabaseHeroIdMax = FirebaseDatabase.getInstance().getReference("AllHeroes/idMaxHeroes");


        mDatabaseHeroIdMax.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                maxIdHero = dataSnapshot.getValue().toString();
                workerHero();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void workerHero() {
        for (int q = 1; q < Integer.valueOf(maxIdHero); q++) {
            DatabaseReference mDateBasehero = FirebaseDatabase.getInstance().getReference("AllHeroes/idHeroes/" + q);
            mDateBasehero.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listHero.add(dataSnapshot.getValue().toString());
                    adapterForHero.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
