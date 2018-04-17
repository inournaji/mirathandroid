package com.mirath.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mirath.controllers.adapters.QuestionsAdapter;
import com.mirath.models.Answer;
import com.mirath.models.Question;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Anas Masri on 3/25/2018.
 */

public class GsonUtils {

    public final static String QUESTIONS_INTENT_TAG = "questions";
    public final static String ANSWERS_INTENT_TAG = "answers";

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

    public static ArrayList<Answer> getAnswers(String json) {

        Gson gson = new Gson();

        if (json.isEmpty()) {
            return new ArrayList<>();
        } else {
            Type type = new TypeToken<List<Answer>>() {
            }.getType();
            return gson.fromJson(json, type);
        }
    }

    public static String convertAnswersToString(ArrayList<Answer> answers) {
        Gson gson = new Gson();
        return gson.toJson(answers);
    }

    public static JsonObject buildSubmitBody(ArrayList<Question> questionArrayList) {

        HashMap<Question, Answer> questionAnswerHashMap = new HashMap<>();

        Answer defaultAnswer = new Answer();
        defaultAnswer.setValue("0");

        for (Question question : questionArrayList) {

            if (!question.getType().getId().equals(QuestionsAdapter.QuestionType.BUTTON.getTypeId()) &&
                    !question.getSymbol().endsWith("Bool"))
                if (question.getAnswer() != null)
                    questionAnswerHashMap.put(question, question.getAnswer());
                else
                    questionAnswerHashMap.put(question, defaultAnswer.setValue(question.getDefaultAnswerValue() + ""));
        }

        JsonObject jsonObject = new JsonObject();

        for (Question question : questionAnswerHashMap.keySet()) {
            Answer answer = questionAnswerHashMap.get(question);
            jsonObject.addProperty(question.getSymbol(), answer.getValue());
        }

        return jsonObject;

    }
}
