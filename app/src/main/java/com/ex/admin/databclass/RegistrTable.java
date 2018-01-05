package com.ex.admin.databclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    FileOutputStream fileNumber;
    FileOutputStream fileNumber2;

    EditText name;
    EditText nickname;
    EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registr_table);
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
        startActivity(new Intent(this, MainActivity.class));
    }
}
