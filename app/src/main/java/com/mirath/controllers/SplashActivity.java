package com.mirath.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.connection.ConnectionDelegate;
import com.mirath.models.Question;
import com.mirath.utils.GsonUtils;

import java.util.ArrayList;

import static com.mirath.connection.Connection.*;

public class SplashActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final TextView tapToRetry = findViewById(R.id.tap_to_retry);

        progressBar.setVisibility(View.VISIBLE);

        ConnectionDelegate connectionDelegate = new ConnectionDelegate() {

            @Override
            public void onConnectionSuccess(ArrayList<Question> questions) {
                Log.d("conn", "onConnectionSuccess: from splash");
                new Handler().postDelayed(() -> {
                    progressBar.setVisibility(View.INVISIBLE);

                    Intent goToHome = new Intent(SplashActivity.this, MainActivity.class);

                    goToHome.putExtra("questions", GsonUtils.convertQuestionsToString(questions));
                    startActivity(goToHome);
                    finish();

                }, 2500);


            }

            @Override
            public void onConnectionFailed() {
                Log.d("conn", "onConnectionFailed: from splash");
                progressBar.setVisibility(View.INVISIBLE);
                tapToRetry.setVisibility(View.VISIBLE);
            }
        };

        getQuestions(this, connectionDelegate);

        tapToRetry.setOnClickListener((View v) -> {
            progressBar.setVisibility(View.VISIBLE);
            tapToRetry.setVisibility(View.GONE);
            getQuestions(SplashActivity.this, connectionDelegate);
        });

    }
}
