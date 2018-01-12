package com.ex.admin.databclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 10.12.2017.
 */

public class RegistrTable extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Person");
    String maxIdHero;
    DatabaseReference mDatabaseHeroIdMax = FirebaseDatabase.getInstance().getReference("AllHeroes/idMaxHeroes");
    DatabaseReference mDatabaseHero = FirebaseDatabase.getInstance().getReference("AllHeroes/idHeroes");


    FileOutputStream fileNumber;
    FileOutputStream fileNumber2;
    FileOutputStream fileNumber3;

    EditText name;
    EditText nickname;
    EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registr_table);

        mDatabaseHeroIdMax.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                maxIdHero = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void onClickAddPerson(View view) {
        name = findViewById(R.id.nameTableReg);
        nickname = findViewById(R.id.nickTableReg);
        password = findViewById(R.id.passTableReg);


        try {
            fileNumber = openFileOutput("nameReg.txt", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileNumber.write(name.getText().toString().getBytes());
        } catch (IOException e) {

        } finally {
            try {
                fileNumber.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            fileNumber3 = openFileOutput("nickname.txt", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileNumber3.write(nickname.getText().toString().getBytes());
        } catch (IOException e) {

        } finally {
            try {
                fileNumber3.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            fileNumber2 = openFileOutput("passReg.txt", MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileNumber2.write(password.getText().toString().getBytes());
        } catch (IOException e) {

        } finally {
            try {
                fileNumber2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        addNewHero(password.getText().toString(), name.getText().toString(), nickname.getText().toString());

    }


    private void addNewHero(String password, String name, String nickname) {
        mDatabase.child(name).push();
        Person post = new Person(password, name, nickname);
        Map<String, Object> postValues = post.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(name, postValues);
        mDatabase.updateChildren(childUpdates);
        addNewHeroID(this.nickname.getText().toString());
        maxIdHero = String.valueOf(Integer.valueOf(maxIdHero) + 1);

        mDatabaseHeroIdMax.setValue(maxIdHero);
        startActivity(new Intent(this, MainActivity.class));

    }

    public void addNewHeroID(String nickname) {
        mDatabaseHero.child(maxIdHero).push();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(maxIdHero, nickname);
        mDatabaseHero.updateChildren(childUpdates);
    }
}
