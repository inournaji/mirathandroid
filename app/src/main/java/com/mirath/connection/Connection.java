package com.mirath.connection;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.mirath.models.Answer;
import com.mirath.models.Parser;
import com.mirath.models.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Anas Masri on 3/25/2018.
 */

public class Connection {

    public static void getQuestions(Context context, final ConnectionDelegate connectionDelegate) {

        Ion.with(context)
                .load(APIEndPoints.QUESTIONS_API)
                .asString()
                .withResponse()
                .setCallback((e, result) -> {
                    if (e == null && result.getHeaders().code() == 200) {
                        try {
                            Log.d("conn", "result gotten!");

                            new Parser.ParseQuestionsTask(new JSONArray(result.getResult()), new ConnectionDelegate() {

                                @Override
                                public void onConnectionSuccess(ArrayList<Question> questions, ArrayList<Answer> answers) {
                                    connectionDelegate.onConnectionSuccess(questions, null);
                                }

                                @Override
                                public void onConnectionFailed() {
                                    connectionDelegate.onConnectionFailed();

                                }
                            }).execute();

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            connectionDelegate.onConnectionFailed();

                        }
                    } else {
                        connectionDelegate.onConnectionFailed();

                    }
                });
    }

    public static void submitAnswers(Context context, final ConnectionDelegate connectionDelegate, JsonObject jsonObject) {
        Log.d("conn", "request body: " +jsonObject);

        Ion.with(context)
                .load("POST", APIEndPoints.SUBMIT_API)
                .setHeader("Content-Type", "application/json")
                .setJsonObjectBody(jsonObject)
                .asString()
                .withResponse()
                .setCallback((e, result) -> {
                    if (e == null && result.getHeaders().code() == 200) {
                        try {
                            Log.d("conn", "submit result: " + result.getResult());

                            new Parser.ParseResultTask(new JSONArray(result.getResult()), new ConnectionDelegate() {

                                @Override
                                public void onConnectionSuccess(ArrayList<Question> questions, ArrayList<Answer> answers) {
                                    connectionDelegate.onConnectionSuccess(null, answers);
                                }

                                @Override
                                public void onConnectionFailed() {
                                    connectionDelegate.onConnectionFailed();

                                }
                            }).execute();

                        } catch (JSONException e1) {
                            Log.d("conn", "JSONException: " + result.getResult());

                            e1.printStackTrace();
                            connectionDelegate.onConnectionFailed();

                        }
                    } else {
                        Log.d("conn", result.getResult());

                        connectionDelegate.onConnectionFailed();

                    }
                });
    }
}
