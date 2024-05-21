package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.todolist.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView RecyclerView_for_list_item;
    private FloatingActionButton floating_Action_Button;
    private DataBaseHelper myDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#0A3D62"));

        /**
         *
         * Initilization
         *
         */
        RecyclerView_for_list_item=findViewById(R.id.RecyclerView_for_list_item);
        floating_Action_Button=findViewById(R.id.floating_Action_Button);

        /**
         * Handle check
         */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
            }
        },1000);


    }
}