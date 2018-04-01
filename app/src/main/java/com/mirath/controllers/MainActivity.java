package com.mirath.controllers;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.controllers.fragments.InputFragment;
import com.mirath.controllers.fragments.ResultsFragment;
import com.mirath.models.Question;
import com.mirath.utils.GsonUtils;

import java.util.ArrayList;

import static com.mirath.utils.GsonUtils.ANSWERS_INTENT_TAG;
import static com.mirath.utils.GsonUtils.QUESTIONS_INTENT_TAG;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setBackgroundDrawableResource(R.drawable.questions_bg);

         fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.content_frame,
                InputFragment.newInstance(getIntent().getStringExtra(QUESTIONS_INTENT_TAG)))
                .commit();

     }

    public void moveToResultsFragment(String answers) {

        fragmentManager.beginTransaction().add(R.id.content_frame,
                ResultsFragment.newInstance(getIntent().getStringExtra(ANSWERS_INTENT_TAG)))
                .commit();

    }
}
