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

import static com.mirath.models.Question.isYesNoCheckedQuestion;
import static com.mirath.models.Question.shouldShowNumberQuestion;

/**
 * Created by Anas Masri on 3/30/2018.
 */

public class QuestionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterDelegate {

    private ArrayList<Question> questions;
    private Context context;
    private AdapterDelegate adapterDelegate;

    @Override
    public void onAnswer(Answer answer, int position) {

        if (position != -100) { //submit button click
            questions.get(position).setAnswer(answer);
            checkQuestionsChanges();
        }

        adapterDelegate.onAnswer(answer, position);

    }

    ArrayList<String> shouldShowNumberQuestions = new ArrayList<>();

    private void checkQuestionsChanges() {

        shouldShowNumberQuestions = new ArrayList<>();

        for (Question question : questions) {

            if (isYesNoCheckedQuestion(question) || question.isMale()) {
                shouldShowNumberQuestions.add(question.getSymbol().replace("Bool", ""));
            }

        }

        for (Question question : questions) {

            if (shouldShowNumberQuestion(question, shouldShowNumberQuestions)) {
                question.setShown(true);
            } else if (question.getType().getId().equals(QuestionType.NUMBER.getTypeId())) {
                question.setShown(false);
                question.setAnswer(null);
            }
        }


 /*       for (Question question : questions) {

            boolean isFemale = false;

            if (question.getSymbol().equals("Gender") &&
                    question.getAnswer() != null &&
                    question.getAnswer().getValue().equals("2")) { //is female
                isFemale = true;

            }else { //not gender question


            }


        }*/

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
