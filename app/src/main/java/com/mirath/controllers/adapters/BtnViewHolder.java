package com.mirath.controllers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.mirath.R;

class BtnViewHolder extends RecyclerView.ViewHolder {
    public BtnViewHolder(Context context, View view, QuestionsAdapter questionsAdapter) {
        super(view);
        RelativeLayout calcButton =  view.findViewById(R.id.calculate_btn);
        calcButton.setOnClickListener((v -> questionsAdapter.onAnswer(null,-100)));
    }


}
