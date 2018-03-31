package com.mirath.controllers.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.models.Answer;

/**
 * Created by Anas Masri on 3/31/2018.
 */

public class AnswerViewHolder extends RecyclerView.ViewHolder {

    private TextView memberTv;
    private TextView valueTv;

    public AnswerViewHolder(View itemView) {
        super(itemView);
        memberTv = itemView.findViewById(R.id.member_tv);
        valueTv = itemView.findViewById(R.id.value_tv);
    }

    public void bind(Answer answer){
        memberTv.setText(answer.getName());
        valueTv.setText(answer.getValue());
    }
}
