package com.mirath.controllers.viewHolders;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.models.Question;

/**
 * Created by Anas Masri on 3/30/2018.
 */

public class YesNoViewHolder  extends RecyclerView.ViewHolder{

    private TextView label;

    public YesNoViewHolder(Context context, View itemView) {
        super(itemView);
        label = itemView.findViewById(R.id.label_tv);
  /*      Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Amiri-Regular.ttf");
        label.setTypeface(face);*/
    }

    public void bind(Question question) {
        label.setText(question.getQuestion());

    }
}