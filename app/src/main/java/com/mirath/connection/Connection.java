package com.mirath.connection;

import android.content.Context;
import android.util.Log;

import com.koushikdutta.ion.Ion;
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

                            new Parser.ParseTask(new JSONArray(result.getResult()), new ConnectionDelegate() {

                                @Override
                                public void onConnectionSuccess(ArrayList<Question> questions) {
                                    connectionDelegate.onConnectionSuccess(questions);
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
}
