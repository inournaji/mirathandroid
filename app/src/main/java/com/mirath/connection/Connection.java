package com.mirath.connection;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.mirath.models.Answer;
import com.mirath.models.Parser;
import com.mirath.models.Question;
import com.mirath.utils.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Anas Masri on 3/25/2018.
 */

public class Connection {

    public enum ErrorCodes {

        NO_CONTENT("204"),
        BAD_REQUEST("400");

        String code;

        ErrorCodes(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static void getQuestions(Context context, final ConnectionDelegate connectionDelegate) {

        Ion.with(context)
                .load(APIEndPoints.QUESTIONS_API)
                .addHeader("language", SharedPrefUtils.getLanguage())
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
                                public void onConnectionFailed(String code) {
                                    connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.getCode());

                                }
                            }).execute();

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.getCode());

                        }
                    } else {
                        connectionDelegate.onConnectionFailed(result.getHeaders().code() + "");
                    }
                });
    }

    public static void submitAnswers(Context context, final ConnectionDelegate connectionDelegate, JsonObject jsonObject) {
        Log.d("submit", "request body: " + jsonObject);

        Ion.with(context)
                .load("POST", APIEndPoints.SUBMIT_API)
                .setHeader("Content-Type", "application/json")
                .addHeader("language", SharedPrefUtils.getLanguage())
                .setJsonObjectBody(jsonObject)
                .asString()
                .withResponse()
                .setCallback((e, result) -> {

                    if (result != null && result.getResult() != null)
                        Log.d("submit", "submit result: " + result.getResult());

                    if (e == null && result != null && result.getHeaders().code() == 200) {
                        try {

                            new Parser.ParseResultTask(new JSONArray(result.getResult()), new ConnectionDelegate() {

                                @Override
                                public void onConnectionSuccess(ArrayList<Question> questions, ArrayList<Answer> answers) {
                                    connectionDelegate.onConnectionSuccess(null, answers);
                                }

                                @Override
                                public void onConnectionFailed(String code) {
                                    connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.getCode());

                                }
                            }).execute();

                        } catch (JSONException e1) {
                            Log.d("submit", "JSONException: " + result.getResult());

                            e1.printStackTrace();
                            connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.getCode());

                        }
                    } else {

                        if (e != null) {
                            Log.d("submit", "exception thrown");
                            connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.code);

                        } else {
                            if (result != null && result.getHeaders() != null && result.getResult() != null) {
                                Log.d("submit", "headers: "  + result.getHeaders());
                            }

                            if (result != null) {
                                String responseCode = result.getHeaders().code() + "    ";
                                if (responseCode.equals(ErrorCodes.BAD_REQUEST.code)) {

                                    try {
                                        JSONObject resultJson = new JSONObject(result.getResult());
                                        JSONObject errorJson = resultJson.optJSONObject("data").optJSONObject("error");
                                        StringBuilder resultText = new StringBuilder();

                                        Iterator<?> keys = errorJson.keys();

                                        while( keys.hasNext() ) {
                                            String key = (String)keys.next();
                                            if (errorJson.get(key) instanceof JSONObject ) {
                                                JSONObject errJson = errorJson.getJSONObject(key);
                                                resultText.append(key).append(" ").append(errJson.optString(key));
                                            }
                                        }

                                        connectionDelegate.onConnectionFailed(resultText.toString());

                                    }catch (JSONException je){
                                        connectionDelegate.onConnectionFailed(responseCode);

                                    }
                                } else {
                                    connectionDelegate.onConnectionFailed(responseCode);

                                }
                            } else
                                connectionDelegate.onConnectionFailed(ErrorCodes.BAD_REQUEST.code);
                        }
                    }
                });
    }
}
