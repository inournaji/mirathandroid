package com.mirath.connection;

import com.mirath.models.Answer;
import com.mirath.models.Question;

import java.util.ArrayList;

/**
 * Created by Nour on 3/25/2018.
 */

public interface ConnectionDelegate {
    void onConnectionSuccess(ArrayList<Question> questions, ArrayList<Answer> answers);
     void onConnectionFailed(String code);
}
