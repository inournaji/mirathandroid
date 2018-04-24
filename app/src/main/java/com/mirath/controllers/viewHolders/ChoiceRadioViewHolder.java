package com.mirath.controllers.viewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.mirath.R;
import com.mirath.controllers.adapters.ChoicesAdapter;
import com.mirath.models.Answer;
import com.mirath.models.Choice;
import com.mirath.models.Question;

public class ChoiceRadioViewHolder extends RecyclerView.ViewHolder {

    RadioButton radioButton;
    Question question;
    ChoicesAdapter choicesAdapter;

    public ChoiceRadioViewHolder(Context context, View view, ChoicesAdapter choicesAdapter, Question question) {
        super(view);
        radioButton = view.findViewById(R.id.radioButton);
        this.question = question;
        this.choicesAdapter = choicesAdapter;

    }

    public void bind(Choice choice) {

        radioButton.setText(choice.getText());
        radioButton.setTag(choice.getValue());

        if (question.getAnswer() != null) {
            if (question.getAnswer().getValue().equals(radioButton.getTag() + "")) {
                radioButton.setChecked(true);
            }
        }else {
            radioButton.setChecked(false);
        }


        radioButton.setOnClickListener((v) -> {

            choicesAdapter.refresh(radioButton.getTag()+"", choice);


        });
    }

    }



