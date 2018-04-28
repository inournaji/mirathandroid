package com.mirath.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirath.R;
import com.mirath.controllers.viewHolders.AnswerViewHolder;
import com.mirath.models.Answer;

import java.util.ArrayList;

/**
 * Created by Nour on 3/31/2018.
 */

public class AnswersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Answer> answers;
    private Context context;

    public AnswersAdapter(ArrayList<Answer> answers, Context context) {
        this.answers = answers;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;
        view = layoutInflater.inflate(R.layout.answer_view_holder, parent, false);

        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AnswerViewHolder)
            ((AnswerViewHolder)holder).bind(answers.get(position));
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }
}
