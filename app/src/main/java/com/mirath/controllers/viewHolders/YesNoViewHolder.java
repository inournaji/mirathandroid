package com.mirath.controllers.viewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.controllers.adapters.AdapterDelegate;
import com.mirath.models.Answer;
import com.mirath.models.Question;

/**
 * Created by Anas Masri on 3/30/2018.
 */

public class YesNoViewHolder extends RecyclerView.ViewHolder {

    private TextView label;
    private Switch aSwitch;
    private AdapterDelegate adapterDelegate;

    public YesNoViewHolder(Context context, View itemView, AdapterDelegate adapterDelegate) {
        super(itemView);
        label = itemView.findViewById(R.id.label_tv);
        aSwitch = itemView.findViewById(R.id.switch1);
        this.adapterDelegate = adapterDelegate;
        setIsRecyclable(false);
    }

    public void bind(Question question, int position) {
        label.setText(question.getQuestion());
        if (question.getAnswer() != null)
            aSwitch.setChecked(question.getAnswer().getValue().equals("1"));
        aSwitch.setOnClickListener((v) -> {
            if (question.getAnswer() == null)
                question.setAnswer(new Answer());

            question.getAnswer().setValue(aSwitch.isChecked() ? "1" : "0");

            adapterDelegate.onAnswer(question.getAnswer(), position);
        });
    }
}
