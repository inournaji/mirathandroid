package com.mirath.controllers.adapters;

import android.app.Activity;
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

public class QuestionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterDelegate {

    private final FragmentDelegate fragmentDelegate;
    private ArrayList<Question> questions;
    private Activity context;
    private ArrayList<String> shouldShowNumberQuestions = new ArrayList<>();

    @Override
    public void onAnswer(Answer answer, int position) {

        if (position != -100) {
            questions.get(position).setAnswer(answer);
            fragmentDelegate.onDone(checkQuestionsChanges());

        } else //submit button click
            fragmentDelegate.onDone(null);

    }


    private ArrayList<Question> checkQuestionsChanges() {

        shouldShowNumberQuestions = new ArrayList<>();

        ArrayList<Question> newQuestions = new ArrayList<>();
        newQuestions.addAll(questions);

        for (Question question : newQuestions) {

            if (question.isYesNoCheckedQuestion())
                shouldShowNumberQuestions.add(question.getSymbol().replace("Bool", ""));
            else if (question.isMale())
                shouldShowNumberQuestions.add("Wives");

        }

        for (Question question : newQuestions) {

            if (question.shouldShowNumberQuestion(shouldShowNumberQuestions)) {
                question.setShown(true);
            } else if (question.getType().getId().equals(QuestionType.NUMBER.getTypeId())) {
                question.setShown(false);
                question.setAnswer(null);
            }
        }

        return newQuestions;
       // updateAdapterValues(newQuestions);


    }

    public void updateAdapterValues(ArrayList<Question> newQuestions) {
        questions.clear();
        questions.addAll(newQuestions);
        notifyDataSetChanged();
    }


    public enum QuestionType {

        YES_NO(1),
        CHOICE(2),
        NUMBER(3),
        BUTTON(4);

        public int getTypeId() {
            return typeId;
        }

        int typeId;

        QuestionType(int i) {
            typeId = i;
        }
    }

    public QuestionsAdapter(Activity context, ArrayList<Question> questionArrayList, FragmentDelegate fragmentDelegate) {
        this.questions = questionArrayList;
        this.context = context;
        this.fragmentDelegate = fragmentDelegate;
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
            view = layoutInflater.inflate(R.layout.yes_no_view_holder, parent, false);
            return new YesNoViewHolder(context, view, this);
        } else if (viewType == QuestionType.BUTTON.getTypeId()) {
            view = layoutInflater.inflate(R.layout.btn_view_holder, parent, false);
            return new BtnViewHolder(context, view, this);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ChoiceViewHolder)
            ((ChoiceViewHolder) holder).bind(questions.get(position), position);
        else if (holder instanceof NumberViewHolder)
            ((NumberViewHolder) holder).bind(questions.get(position), position);
        else if (holder instanceof YesNoViewHolder)
            ((YesNoViewHolder) holder).bind(questions.get(position), position);

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }


}
