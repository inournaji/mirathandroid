package com.mirath.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirath.R;
import com.mirath.controllers.viewHolders.ChoiceRadioViewHolder;
import com.mirath.controllers.viewHolders.ChoiceViewHolder;
import com.mirath.models.Answer;
import com.mirath.models.Choice;
import com.mirath.models.Question;

import java.util.ArrayList;

public class ChoicesAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Choice> choices;
    Context context;
    Question question;
    ChoiceViewHolder choiceViewHolder;

    public ChoicesAdapter(ArrayList<Choice> choices, Context context, Question question, ChoiceViewHolder choiceViewHolder) {
        this.choices = choices;
        this.context = context;
        this.question = question;
        this.choiceViewHolder = choiceViewHolder;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;;
        view = layoutInflater.inflate(R.layout.choice_radio_view_holder, parent, false);
        return new ChoiceRadioViewHolder(context, view, this, question);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ChoiceRadioViewHolder)holder).bind(choices.get(position));
    }

    @Override
    public int getItemCount() {
        return choices.size();
    }

    public void refresh(String answerValue, Choice choice) {


        if (question.getAnswer() == null)
            question.setAnswer(new Answer());

        question.getAnswer().setValue(answerValue + "");

        choiceViewHolder.update();

    }
}
