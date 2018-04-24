package com.mirath.models;

import android.os.AsyncTask;

import com.mirath.connection.Connection;
import com.mirath.connection.ConnectionDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anas Masri on 3/25/2018.
 */

public class Parser {

    static ArrayList<Question> parseQuestions(JSONArray jsonArray) {

        ArrayList<Question> questions = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                if (jsonArray.get(i) instanceof JSONObject) {

                    Question questionObject = new Question();

                    JSONObject questionJsonObject = jsonArray.getJSONObject(i);
                    Integer id = questionJsonObject.optInt("id");
                    String question = questionJsonObject.optString("question");
                    String question_en = questionJsonObject.optString("question_en");
                    String desc = questionJsonObject.optString("desc");
                    String desc_en = questionJsonObject.optString("desc_en");
                    Integer type_id = questionJsonObject.optInt("type_id");
                    Integer pp = questionJsonObject.optInt("pp");
                    Integer group_id = questionJsonObject.optInt("group_id");
                    String symbol = questionJsonObject.optString("symbol");
                    Boolean visible = questionJsonObject.optBoolean("visible");
                    JSONObject typeJsonObject = questionJsonObject.optJSONObject("type");
                    Integer typeId = typeJsonObject.optInt("id");
                    String typeName = typeJsonObject.optString("name");
                    int default_answer = typeJsonObject.optInt("default_answer");

                    questionObject.setId(id);
                    questionObject.setQuestion(question);
                    questionObject.setQuestion_en(question_en);
                    questionObject.setDesc(desc);
                    questionObject.setDesc_en(desc_en);
                    questionObject.setType_id(type_id);

                    questionObject.setSymbol(symbol);
                    questionObject.setVisible(visible);
                    questionObject.setDefaultAnswerValue(default_answer);

                    Type type = new Type();
                    type.setId(typeId);
                    type.setName(typeName);
                    questionObject.setType(type);

                    JSONArray choicesJsonArray = questionJsonObject.getJSONArray("choices");

                    ArrayList<Choice> choices = new ArrayList<>();

                    for (int i1 = 0; i1 < choicesJsonArray.length(); i1++) {
                        Choice choice = new Choice();
                        JSONObject choiceJSONObject = choicesJsonArray.getJSONObject(i1);
                        int choiceId = choiceJSONObject.optInt("id");
                        String choiceText = choiceJSONObject.optString("text");
                        String choiceTextEn = choiceJSONObject.optString("text_en");
                        int choiceValue = choiceJSONObject.optInt("value");
                        int choiceQuestionId = choiceJSONObject.optInt("question_id");
                        choice.setId(choiceId);
                        choice.setText(choiceText);
                        choice.setText_en(choiceTextEn);
                        choice.setValue(choiceValue);
                        choice.setQuestion_id(choiceQuestionId);
                        choices.add(choice);
                    }

                    questionObject.setChoices(choices);

                    questions.add(questionObject);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return questions;
    }

    static ArrayList<Answer> parseAnswers(JSONArray jsonArray) {

        ArrayList<Answer> answers = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                if (jsonArray.get(i) instanceof JSONArray) {
                    Answer answer = new Answer();
                    answer.setName(jsonArray.getJSONArray(i).getString(0));
                    answer.setValue(jsonArray.getJSONArray(i).getString(1));
                    answers.add(answer);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return answers;
    }

    public static class ParseQuestionsTask extends AsyncTask<Void, Void, ArrayList<Question>> {

        ConnectionDelegate connectionDelegate;
        JSONArray jsonArray;

        public ParseQuestionsTask(JSONArray jsonArray, ConnectionDelegate connectionDelegate) {
            this.jsonArray = jsonArray;
            this.connectionDelegate = connectionDelegate;
        }


        @Override
        protected void onPostExecute(ArrayList<Question> questions) {
            super.onPostExecute(questions);
            if (!questions.isEmpty())
                connectionDelegate.onConnectionSuccess(questions , null);
            else
                connectionDelegate.onConnectionFailed(Connection.ErrorCodes.BAD_REQUEST.getCode());
        }

        @Override
        protected ArrayList<Question> doInBackground(Void... voids) {
            return parseQuestions(jsonArray);
        }
    }

    public static class ParseResultTask extends AsyncTask<Void, Void, ArrayList<Answer>> {

        ConnectionDelegate connectionDelegate;
        JSONArray jsonArray;

        public ParseResultTask(JSONArray jsonArray, ConnectionDelegate connectionDelegate) {
            this.jsonArray = jsonArray;
            this.connectionDelegate = connectionDelegate;
        }


        @Override
        protected void onPostExecute(ArrayList<Answer> answers) {
            super.onPostExecute(answers);
            if (!answers.isEmpty())
                connectionDelegate.onConnectionSuccess(null, answers);
            else
                connectionDelegate.onConnectionFailed(Connection.ErrorCodes.BAD_REQUEST.getCode());
        }

        @Override
        protected ArrayList<Answer> doInBackground(Void... voids) {
            return parseAnswers(jsonArray);
        }
    }

}
