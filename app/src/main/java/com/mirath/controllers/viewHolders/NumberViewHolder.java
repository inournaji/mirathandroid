package com.mirath.controllers.viewHolders;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.controllers.adapters.AdapterDelegate;
import com.mirath.models.Answer;
import com.mirath.models.Question;

/**
 * Created by Anas Masri on 3/30/2018.
 */

public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView label;
    private EditText number;
    private AdapterDelegate adapterDelegate;
    private Question question;
    private ImageView infoIcon;
    private Context context;

    public NumberViewHolder(Context context, View itemView, AdapterDelegate adapterDelegate) {
        super(itemView);
        this.context = context;

        label = itemView.findViewById(R.id.label_tv);
        number = itemView.findViewById(R.id.number_et);
        infoIcon = itemView.findViewById(R.id.info_icon);
        infoIcon.setOnClickListener(this);

        this.adapterDelegate = adapterDelegate;
        setIsRecyclable(false);
       /* Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Amiri-Regular.ttf");
        label.setTypeface(face);*/
    }

    public void bind(Question question, int position) {

        this.question = question;

        if (question.getDesc() != null && !question.getDesc().isEmpty()) {
            infoIcon.setVisibility(View.VISIBLE);
        } else infoIcon.setVisibility(View.GONE);

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

    @Override
    public void onClick(View v) {
        if (v == infoIcon) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setMessage(question.getDesc()).create().show();
        }
    }
}
