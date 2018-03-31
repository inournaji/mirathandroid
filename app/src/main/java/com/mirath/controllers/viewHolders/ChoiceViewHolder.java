package com.mirath.controllers.viewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.models.Choice;
import com.mirath.models.Question;

/**
 * Created by Anas Masri on 3/30/2018.
 */

public class ChoiceViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private TextView label;
    private RadioGroup radioGroup;

    public ChoiceViewHolder(Context context, View itemView) {
        super(itemView);
        label = itemView.findViewById(R.id.label_tv);
        radioGroup = itemView.findViewById(R.id.radio_group);
   /*     Typeface face = ContextCompat.ge.getFont(R.font.Amiri_Regular);
        label.setTypeface(face);*/
        this.context = context;
    }

    public void bind(Question question) {
        label.setText(question.getQuestion());
        radioGroup.removeAllViews();
        for(Choice choice :question.getChoices()) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(choice.getText());
            radioButton.setTag(question.getId());

            radioGroup.addView(radioButton);
        }
    }
}
