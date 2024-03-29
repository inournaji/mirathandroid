package com.mirath.models;

import com.mirath.controllers.adapters.QuestionsAdapter;

import java.util.ArrayList;

import static com.mirath.controllers.adapters.QuestionsAdapter.*;

/**
 * Created Nour on 3/25/2018.
 */

public class Question {

    private Integer id;
    private Integer type_id;
    private Boolean visible;
    private String question;
    private String question_en;
    private String desc;
    private String desc_en;
    private String parent;
    private String symbol;
    private Type type;
    private Answer answer;
    private int defaultAnswerValue;

    private ArrayList<Choice> choices;

    private boolean isShown = true;

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean visible) {
        isShown = visible;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }


    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion_en() {
        return question_en;
    }

    public void setQuestion_en(String question_en) {
        this.question_en = question_en;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc_en() {
        return desc_en;
    }

    public void setDesc_en(String desc_en) {
        this.desc_en = desc_en;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<Choice> choices) {
        this.choices = choices;
    }

    public void setDefaultAnswerValue(int defaultAnswerValue) {
        this.defaultAnswerValue = defaultAnswerValue;

    }

    public int getDefaultAnswerValue() {
        return defaultAnswerValue;
    }

    public boolean isYesNoCheckedQuestion() {
        return getType().getId().equals(QuestionType.YES_NO.getTypeId()) &&
                getAnswer() != null &&
                getAnswer().getValue().equals("1");
    }

    public boolean shouldShowNumberQuestion(ArrayList<String> shouldShowNumberQuestions) {
        return getType().getId().equals(QuestionType.NUMBER.getTypeId()) &&
                shouldShowNumberQuestions.contains(getSymbol());
    }

    public boolean isMale() {
        return getSymbol() != null &&
                getSymbol().equals("Gender") &&
                getAnswer() != null &&
                getAnswer().getValue().equals("1");
    }
}

