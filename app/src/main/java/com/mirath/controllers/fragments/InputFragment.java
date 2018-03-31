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

import com.mirath.R;
import com.mirath.connection.Connection;
import com.mirath.connection.ConnectionDelegate;
import com.mirath.controllers.MainActivity;
import com.mirath.controllers.adapters.QuestionsAdapter;
import com.mirath.models.Answer;
import com.mirath.models.Question;
import com.mirath.utils.GsonUtils;

import java.util.ArrayList;

import static com.mirath.utils.GsonUtils.QUESTIONS_INTENT_TAG;

/**
 * Created by Anas Masri on 3/30/2018.
 */

public class InputFragment extends Fragment {

    RecyclerView questionsRecyclerView;
    ArrayList<Question> questionArrayList = new ArrayList<>();
    private RelativeLayout calculateBtn;
    MainActivity mainActivity;

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
        calculateBtn = containerView.findViewById(R.id.calculate_btn);
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
        QuestionsAdapter questionsAdapter = new QuestionsAdapter(getContext(), questionArrayList);
        questionsRecyclerView.setAdapter(questionsAdapter);
        RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    calculateBtn.setVisibility(View.VISIBLE);
                } else
                    calculateBtn.setVisibility(View.GONE);
            }
        };
        questionsRecyclerView.addOnScrollListener(mScrollListener);
        calculateBtn.setOnClickListener((v) -> calculate());
    }

    private void calculate() {

        ConnectionDelegate connectionDelegate = new ConnectionDelegate() {

            @Override
            public void onConnectionSuccess(ArrayList<Question> questions, ArrayList<Answer> answers) {
                Log.d("conn", "onConnectionSuccess: from submit");
                moveToResultsFragment(answers);
            }

            private void moveToResultsFragment(ArrayList<Answer> answers) {
                mainActivity.moveToResultsFragment(GsonUtils.convertAnswersToString(answers));
            }

            @Override
            public void onConnectionFailed() {
                Toast.makeText(getContext(), getString(R.string.connection_error), Toast.LENGTH_LONG).show();

            }
        };


        Connection.submitAnswers(this.getContext(), connectionDelegate);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }
}
