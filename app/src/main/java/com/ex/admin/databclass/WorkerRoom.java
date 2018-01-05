package com.ex.admin.databclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Admin on 03.01.2018.
 */

public class WorkerRoom extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_first);
    }

    public void onClickStartChat(View view) {
        startActivity(new Intent(this, WorkerPost.class));
    }
}
