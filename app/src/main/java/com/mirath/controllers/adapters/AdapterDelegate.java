package com.mirath.controllers.adapters;

import com.mirath.models.Answer;

/**
 * Created by Nour on 3/31/2018.
 */

public interface AdapterDelegate {
    void onAnswer(Answer answer, int position);
}
