package com.mirath.controllers.adapters;

import com.mirath.models.Answer;

/**
 * Created by Anas Masri on 3/31/2018.
 */

public interface AdapterDelegate {
    void onAnswer(Answer answer, int position);
}
