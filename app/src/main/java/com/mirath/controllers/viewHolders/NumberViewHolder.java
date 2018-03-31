package com.mirath.controllers.viewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.controllers.adapters.AdapterDelegate;
import com.mirath.models.Answer;
import com.mirath.models.Question;

/**
 * Created by Anas Masri on 3/30/2018.
 */

public class NumberViewHolder extends RecyclerView.ViewHolder {

    private TextView label;
    private EditText number;
    private AdapterDelegate adapterDelegate;

    public NumberViewHolder(Context context, View itemView, AdapterDelegate adapterDelegate) {
        super(itemView);
        label = itemView.findViewById(R.id.label_tv);
        number = itemView.findViewById(R.id.number_et);
        this.adapterDelegate = adapterDelegate;
        setIsRecyclable(false);
       /* Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Amiri-Regular.ttf");
        label.setTypeface(face);*/
    }

    public void bind(Question question, int position) {

        label.setText(question.getQuestion());

        if (question.getAnswer() != null) {
            number.setText(question.getAnswer().getValue());
        }

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (question.getAnswer() == null)
                    question.setAnswer(new Answer());

                question.getAnswer().setValue(s.toString());

                adapterDelegate.onAnswer(question.getAnswer(), position);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
