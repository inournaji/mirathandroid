package com.mirath.controllers.viewHolders;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.controllers.adapters.AdapterDelegate;
import com.mirath.models.Answer;
import com.mirath.models.Choice;
import com.mirath.models.Question;

/**
 * Created by Anas Masri on 3/30/2018.
 */

public class ChoiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final Context context;
    private TextView label;
    private ImageView infoIcon;
    private Question question;
    private RadioGroup radioGroup;
    private AdapterDelegate adapterDelegate;

    public ChoiceViewHolder(Context context, View itemView, AdapterDelegate adapterDelegate) {
        super(itemView);
        label = itemView.findViewById(R.id.label_tv);
        radioGroup = itemView.findViewById(R.id.radio_group);
        infoIcon = itemView.findViewById(R.id.info_icon);
        infoIcon.setOnClickListener(this);
        this.adapterDelegate = adapterDelegate;
        this.context = context;
        setIsRecyclable(false);
    }

    public void bind(Question question, int position) {

        this.question = question;

        if(question.getDesc() != null && !question.getDesc().isEmpty()){
            infoIcon.setVisibility(View.VISIBLE);
        }else infoIcon.setVisibility(View.GONE);

        label.setText(question.getQuestion());
        //radioGroup.removeAllViews();
        if (radioGroup.getChildCount() == 0)
            for (Choice choice : question.getChoices()) {

                RadioButton radioButton = new RadioButton(context);
                radioButton.setText(choice.getText());
                radioButton.setTag(choice.getValue());

                if (question.getAnswer() != null) {
                    if (question.getAnswer().getValue().equals(radioButton.getTag() + "")) {
                        radioButton.setChecked(true);
                    }
                }

                radioGroup.addView(radioButton);

                radioButton.setOnClickListener((v) -> {
                    clearOtherChoices(radioGroup, radioButton);
                    if (question.getAnswer() == null)
                        question.setAnswer(new Answer());

                    question.getAnswer().setValue(radioButton.getTag() + "");

                    adapterDelegate.onAnswer(question.getAnswer(), position);

                });
            }
    }

    private void clearOtherChoices(RadioGroup radioGroup, RadioButton radioButton) {

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View childView = radioGroup.getChildAt(i);

            if (childView instanceof RadioButton) {
                if (childView.getTag() != radioButton.getTag()) {
                    ((RadioButton) childView).setChecked(false);
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (v == infoIcon) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setMessage(question.getDesc()).create().show();
        }
    }
}
