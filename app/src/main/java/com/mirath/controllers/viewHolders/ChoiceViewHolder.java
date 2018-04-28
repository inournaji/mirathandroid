package com.mirath.controllers.viewHolders;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.controllers.adapters.AdapterDelegate;
import com.mirath.controllers.adapters.ChoicesAdapter;
import com.mirath.models.Answer;
import com.mirath.models.Choice;
import com.mirath.models.Question;

/**
 * Created by Nour on 3/30/2018.
 */

public class ChoiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final Context context;
    private TextView label;
    private ImageView infoIcon;
    private Question question;
    private RecyclerView radioGroup;
    private AdapterDelegate adapterDelegate;
    private boolean onBind;
    int position;

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

        onBind = true;

        this.position = position;

        this.question = question;

        if (question.getDesc() != null && !question.getDesc().isEmpty()) {
            infoIcon.setVisibility(View.VISIBLE);
        } else
            infoIcon.setVisibility(View.INVISIBLE);

        label.setText(question.getQuestion());

        initRecyclerView();


        onBind = false;
    }

    private void initRecyclerView() {


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        radioGroup.setLayoutManager(linearLayoutManager);

        ChoicesAdapter choicesAdapter = new ChoicesAdapter(question.getChoices(), context, question, this);
        radioGroup.setAdapter(choicesAdapter);


    }

    @Override
    public void onClick(View v) {
        if (v == infoIcon) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setMessage(question.getDesc()).create().show();
        }
    }

    public  void update(){
        adapterDelegate.onAnswer(question.getAnswer(), position);

    }
}
