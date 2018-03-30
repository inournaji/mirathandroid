package com.mirath.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mirath.models.Question;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anas Masri on 3/25/2018.
 */

public class GsonUtils {

    public static ArrayList<Question> getQuestions(String json) {

        Gson gson = new Gson();

        if (json.isEmpty()) {
            return new ArrayList<>();
        } else {
            Type type = new TypeToken<List<Question>>() {
            }.getType();
            return gson.fromJson(json, type);
        }
    }

    public static String convertQuestionsToString(ArrayList<Question> questions) {
        Gson gson = new Gson();
        return gson.toJson(questions);
    }

}
