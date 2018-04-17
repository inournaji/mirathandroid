package com.mirath.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.connection.ConnectionDelegate;
import com.mirath.models.Answer;
import com.mirath.models.Question;
import com.mirath.utils.GsonUtils;
import com.mirath.utils.SharedPrefUtils;

import org.json.JSONException;

import java.util.ArrayList;

import static com.mirath.connection.Connection.demoGetQuestions;
import static com.mirath.connection.Connection.getQuestions;
import static com.mirath.utils.GsonUtils.QUESTIONS_INTENT_TAG;

public class SplashActivity extends AppCompatActivity {
    TextView tapToRetry;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);
        tapToRetry = findViewById(R.id.tap_to_retry);

        final RelativeLayout arabicStartButton = findViewById(R.id.arabic_btn);
        final RelativeLayout englishStartButton = findViewById(R.id.english_btn);
        final LinearLayout buttonsLayout = findViewById(R.id.buttons_layout);

        arabicStartButton.setOnClickListener((v) -> {
            SharedPrefUtils.saveLanguage("ar");
            buttonsLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            startConnection();
        });

        englishStartButton.setOnClickListener((v) -> {
            SharedPrefUtils.saveLanguage("en");
            buttonsLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            startConnection();
        });


    }

    private void startConnection() {

        ConnectionDelegate connectionDelegate = new ConnectionDelegate() {

            @Override
            public void onConnectionSuccess(ArrayList<Question> questions, ArrayList<Answer> answers) {
                Log.d("conn", "onConnectionSuccess: from splash");
                new Handler().postDelayed(() -> {
                    progressBar.setVisibility(View.INVISIBLE);

                    Intent goToHome = new Intent(SplashActivity.this, MainActivity.class);

                    goToHome.putExtra(QUESTIONS_INTENT_TAG, GsonUtils.convertQuestionsToString(questions));
                    startActivity(goToHome);
                    finish();

                }, 2500);


            }

            @Override
            public void onConnectionFailed(String code) {
                Log.d("conn", "onConnectionFailed: from splash");
                progressBar.setVisibility(View.INVISIBLE);
                tapToRetry.setVisibility(View.VISIBLE);
            }
        };

        getQuestions(this, connectionDelegate);
     /*   try {
            demoGetQuestions(this, connectionDelegate);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        tapToRetry.setOnClickListener((View v) -> {
            progressBar.setVisibility(View.VISIBLE);
            tapToRetry.setVisibility(View.GONE);
            getQuestions(SplashActivity.this, connectionDelegate);
        });
    }
}
