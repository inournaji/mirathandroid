package com.mirath.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirath.R;
import com.mirath.controllers.viewHolders.ChoiceViewHolder;
import com.mirath.controllers.viewHolders.NumberViewHolder;
import com.mirath.controllers.viewHolders.YesNoViewHolder;
import com.mirath.models.Answer;
import com.mirath.models.Question;

import java.util.ArrayList;

/**
 * Created by Anas Masri on 3/30/2018.
 */

public class QuestionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterDelegate{

    private ArrayList<Question> questions;
    private Context context;
    private AdapterDelegate adapterDelegate;

    @Override
    public void onAnswer(Answer answer, int position) {
        questions.get(position).setAnswer(answer);
        adapterDelegate.onAnswer(answer, position);
    }

    public enum QuestionType {

        YES_NO(1),
        CHOICE(2),
        NUMBER(3);

        public int getTypeId() {
            return typeId;
        }

        int typeId;

        QuestionType(int i) {
            typeId = i;
        }
    }

    public QuestionsAdapter(Context context, ArrayList<Question> questionArrayList, AdapterDelegate adapterDelegate) {
        this.questions = questionArrayList;
        this.context = context;
        this.adapterDelegate = adapterDelegate;
    }

    @Override
    public int getItemViewType(int position) {
        return questions.get(position).getType().getId();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;
        if (viewType == QuestionType.CHOICE.getTypeId()) {
            view = layoutInflater.inflate(R.layout.choice_view_holder, parent, false);
            return new ChoiceViewHolder(context, view, this);
        } else if (viewType == QuestionType.NUMBER.getTypeId()) {
            view = layoutInflater.inflate(R.layout.number_view_holder, parent, false);
            return new NumberViewHolder(context, view, this);
        } else if (viewType == QuestionType.YES_NO.getTypeId()) {
            view = layoutInflater.inflate(R.layout.yes_no_view_holder, parent, false    );
            return new YesNoViewHolder(context, view, this);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ChoiceViewHolder)
            ((ChoiceViewHolder) holder).bind(questions.get(position), position);
        if (holder instanceof NumberViewHolder)
            ((NumberViewHolder) holder).bind(questions.get(position), position);
        if (holder instanceof YesNoViewHolder)
            ((YesNoViewHolder) holder).bind(questions.get(position), position);

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
