package com.mirath.controllers.viewHolders;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.controllers.adapters.AdapterDelegate;
import com.mirath.models.Answer;
import com.mirath.models.Question;

/**
 * Created by Nour on 3/30/2018.
 */

public class YesNoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final Context context;
    private TextView label;
    private Switch aSwitch;
    private AdapterDelegate adapterDelegate;
    private ImageView infoIcon;
    private Question question;
    private int position;
    private boolean onBind;

    public YesNoViewHolder(Context context, View itemView, AdapterDelegate adapterDelegate) {
        super(itemView);
        label = itemView.findViewById(R.id.label_tv);
        aSwitch = itemView.findViewById(R.id.switch1);
        this.adapterDelegate = adapterDelegate;
        infoIcon = itemView.findViewById(R.id.info_icon);
        infoIcon.setOnClickListener(this);
        aSwitch.setOnClickListener(this);
        this.context = context;
        setIsRecyclable(false);
    }

    public void bind(Question question, int position) {

        onBind = true;

        this.question = question;

        if (question.getDesc() != null && !question.getDesc().isEmpty()) {
            infoIcon.setVisibility(View.VISIBLE);
        } else infoIcon.setVisibility(View.INVISIBLE);


        label.setText(question.getQuestion());

        if (question.getAnswer() != null)
            aSwitch.setChecked(question.getAnswer().getValue().equals("1"));
        else
            aSwitch.setChecked(false);

        this.position = position;

        onBind = false;


    }

    @Override
    public void onClick(View v) {
        if (v == infoIcon) {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setMessage(question.getDesc()).create().show();

        } else if (v == aSwitch) {

            if (!onBind) {
                if (question.getAnswer() == null)
                    question.setAnswer(new Answer());

                question.getAnswer().setValue(aSwitch.isChecked() ? "1" : "0");

                adapterDelegate.onAnswer(question.getAnswer(), position);
            }
        }
    }
}
