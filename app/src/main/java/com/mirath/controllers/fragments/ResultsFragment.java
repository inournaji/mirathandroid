package com.mirath.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mirath.R;
import com.mirath.controllers.MainActivity;
import com.mirath.controllers.adapters.AnswersAdapter;
import com.mirath.models.Answer;
import com.mirath.utils.GsonUtils;

import java.util.ArrayList;

import static com.mirath.utils.GsonUtils.ANSWERS_INTENT_TAG;

/**
 * Created by Anas Masri on 3/30/2018.
 */

public class ResultsFragment extends Fragment {

    private ArrayList<Answer> answersArrayList;
    private RecyclerView answersRecyclerView;
    private ImageView backIv;

    public static ResultsFragment newInstance(String answersExtra) {

        Bundle args = new Bundle();
        args.putString(ANSWERS_INTENT_TAG, answersExtra);
        ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String answers = getArguments().getString(ANSWERS_INTENT_TAG);
            if (answers != null && !answers.isEmpty()) {
                answersArrayList = GsonUtils.getAnswers(answers);
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View containerView = inflater.inflate(R.layout.results_fragment, container, false);

        findViews(containerView);

        initAnswersList();

        return containerView;
    }

    private void findViews(View containerView) {
        answersRecyclerView = containerView.findViewById(R.id.answers_recycler_view);
        backIv = containerView.findViewById(R.id.back_iv);
        backIv.setOnClickListener(v -> ((MainActivity) getActivity()).backToInputFragment());
    }

    private void initAnswersList() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        answersRecyclerView.setLayoutManager(linearLayoutManager);
        AnswersAdapter answersAdapter = new AnswersAdapter(answersArrayList != null ? answersArrayList : new ArrayList<>(), getContext());
        answersRecyclerView.setAdapter(answersAdapter);

    }


}
