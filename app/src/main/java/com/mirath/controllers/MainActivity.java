package com.mirath.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.models.Question;
import com.mirath.utils.GsonUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Question> questions =
                GsonUtils.getQuestions(getIntent().getStringExtra("questions"));
    }
}
