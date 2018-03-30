package com.mirath.models;

import android.os.AsyncTask;

import com.mirath.connection.ConnectionDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anas Masri on 3/25/2018.
 */

public class Parser {

    public static ArrayList<Question> parse(JSONArray jsonArray) {

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

                    questionObject.setId(id);
                    questionObject.setQuestion(question);
                    questionObject.setQuestion_en(question_en);
                    questionObject.setDesc(desc);
                    questionObject.setDesc_en(desc_en);
                    questionObject.setType_id(type_id);
                    questionObject.setPp(pp);
                    questionObject.setGroup_id(group_id);
                    questionObject.setSymbol(symbol);
                    questionObject.setVisible(visible);

                    Type type = new Type();
                    type.setId(typeId);
                    type.setName(typeName);
                    questionObject.setType(type);

                    JSONArray choicesJsonArray = questionJsonObject.getJSONArray("choices");

                    ArrayList<Choice> choices = new ArrayList<>();

                    for (int i1 = 0; i1 < choicesJsonArray.length(); i1++) {
                        Choice choice = new Choice();
                        JSONObject choiceJSONObject = choicesJsonArray.getJSONObject(i);
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

    public static class ParseTask extends AsyncTask<Void, Void, ArrayList<Question>> {

        ConnectionDelegate connectionDelegate;
        JSONArray jsonArray;

        public ParseTask(JSONArray jsonArray, ConnectionDelegate connectionDelegate) {
            this.jsonArray = jsonArray;
            this.connectionDelegate = connectionDelegate;
        }


        @Override
        protected void onPostExecute(ArrayList<Question> questions) {
            super.onPostExecute(questions);
            if (!questions.isEmpty())
                connectionDelegate.onConnectionSuccess(questions);
            else
                connectionDelegate.onConnectionFailed();
        }

        @Override
        protected ArrayList<Question> doInBackground(Void... voids) {
            return parse(jsonArray);
        }
    }

}
