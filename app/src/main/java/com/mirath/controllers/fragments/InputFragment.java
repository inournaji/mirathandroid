package com.mirath.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mirath.R;
import com.mirath.connection.Connection;
import com.mirath.connection.ConnectionDelegate;
import com.mirath.controllers.MainActivity;
import com.mirath.controllers.adapters.AdapterDelegate;
import com.mirath.controllers.adapters.FragmentDelegate;
import com.mirath.controllers.adapters.QuestionsAdapter;
import com.mirath.models.Answer;
import com.mirath.models.Question;
import com.mirath.models.Type;
import com.mirath.utils.GsonUtils;

import java.util.ArrayList;

import static com.mirath.utils.GsonUtils.QUESTIONS_INTENT_TAG;

/**
 * Created by Anas Masri on 3/30/2018.
 */

public class InputFragment extends Fragment implements FragmentDelegate {

    RecyclerView questionsRecyclerView;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    MainActivity mainActivity;
    private RelativeLayout progressBarLayout;
    QuestionsAdapter questionsAdapter;

    public static InputFragment newInstance(String questionsExtra) {

        Bundle args = new Bundle();
        args.putString(QUESTIONS_INTENT_TAG, questionsExtra);
        InputFragment fragment = new InputFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View containerView = inflater.inflate(R.layout.input_fragment, container, false);

        findViews(containerView);

        initQuestionsList();

        return containerView;
    }

    private void findViews(View containerView) {
        questionsRecyclerView = containerView.findViewById(R.id.questions_recycler_view);
        progressBarLayout = containerView.findViewById(R.id.progressBarLayout);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String questions = getArguments().getString(QUESTIONS_INTENT_TAG);
            if (questions != null && !questions.isEmpty()) {
                questionArrayList = GsonUtils.getQuestions(questions);


            }
        }

    }

    private void initQuestionsList() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        questionsRecyclerView.setLayoutManager(linearLayoutManager);

        for (Question question : questionArrayList) {
            if (question.getType().getId().equals(QuestionsAdapter.QuestionType.NUMBER.getTypeId()) &&
                    question.getDefaultAnswerValue() != 1) {
                question.setShown(false);

            }

        }

        Question btnQuestion = new Question();
        Type btnType = new Type();
        btnType.setId(QuestionsAdapter.QuestionType.BUTTON.getTypeId());
        btnType.setName("Btn");
        btnQuestion.setType(btnType);
        questionArrayList.add(questionArrayList.size(), btnQuestion);

        questionsAdapter =
                new QuestionsAdapter(getActivity(),
                        questionArrayList,
                        this);

        questionsRecyclerView.setAdapter(questionsAdapter);

    }

    private void calculate() {

        progressBarLayout.setVisibility(View.VISIBLE);

        ConnectionDelegate connectionDelegate = new ConnectionDelegate() {

            @Override
            public void onConnectionSuccess(ArrayList<Question> questions, ArrayList<Answer> answers) {
                Log.d("conn", "onConnectionSuccess: from submit");
                progressBarLayout.setVisibility(View.GONE);
                moveToResultsFragment(answers);
            }

            private void moveToResultsFragment(ArrayList<Answer> answers) {
                mainActivity.moveToResultsFragment(GsonUtils.convertAnswersToString(answers));
            }

            @Override
            public void onConnectionFailed(String code) {
                progressBarLayout.setVisibility(View.GONE);
                if (code.trim().equals(Connection.ErrorCodes.NO_CONTENT.getCode())) {
                    Toast.makeText(getContext(), R.string.no_solution_found, Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getContext(), code, Toast.LENGTH_LONG).show();
            }
        };

        JsonObject jsonObject = GsonUtils.buildSubmitBody(questionArrayList);

        Connection.submitAnswers(getContext(), connectionDelegate, jsonObject);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }


    @Override
    public void onDone(ArrayList<Question> questions) {

        if(questions == null) //submit button
            calculate();
        else {
            this.questionArrayList.clear();
            this.questionArrayList.addAll(questions);
            questionsAdapter.updateAdapterValues(questions);

        }
    }
}
