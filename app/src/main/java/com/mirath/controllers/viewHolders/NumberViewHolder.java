package com.mirath.controllers.viewHolders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirath.R;
import com.mirath.controllers.adapters.AdapterDelegate;
import com.mirath.models.Answer;
import com.mirath.models.Question;

/**
 * Created by Nour on 3/30/2018.
 */

public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView label;
    private EditText number;
    private AdapterDelegate adapterDelegate;
    private Question question;
    private ImageView infoIcon;
    private LinearLayout parentLayout;
    private Context context;


    public NumberViewHolder(Context context, View itemView, AdapterDelegate adapterDelegate) {
        super(itemView);
        this.context = context;

        label = itemView.findViewById(R.id.label_tv);
        number = itemView.findViewById(R.id.number_et);
        infoIcon = itemView.findViewById(R.id.info_icon);
        parentLayout = itemView.findViewById(R.id.parent_layout);
        infoIcon.setOnClickListener(this);

        this.adapterDelegate = adapterDelegate;
       setIsRecyclable(false);


    }

    public void bind(Question question, int position) {

        this.question = question;

        if (question.isShown()) {
            parentLayout.setVisibility(View.VISIBLE);
            bindData(question, position);

        } else {
            parentLayout.setVisibility(View.GONE);
        }

    }

    private void bindData(Question question, int position) {

        if (question.getDesc() != null && !question.getDesc().isEmpty()) {
            infoIcon.setVisibility(View.VISIBLE);
        } else
            infoIcon.setVisibility(View.INVISIBLE);

        label.setText(question.getQuestion());

        if (question.getAnswer() != null) {
            number.setText(question.getAnswer().getValue());
        }else
            number.setText("");

        number.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId == EditorInfo.IME_ACTION_DONE ) && number.getEditableText() != null && !number.getEditableText().toString().isEmpty()) {
                if (question.getAnswer() == null)
                    question.setAnswer(new Answer());

                question.getAnswer().setValue(number.getEditableText().toString());
                hideKeyboard((Activity) context);
                adapterDelegate.onAnswer(question.getAnswer(), position);
                return true;
            }
            return false;
        });


    }

    @Override
    public void onClick(View v) {
        if (v == infoIcon) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setMessage(question.getDesc()).create().show();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
