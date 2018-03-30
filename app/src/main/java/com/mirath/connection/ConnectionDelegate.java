package com.mirath.connection;

import com.mirath.models.Question;

import java.util.ArrayList;

/**
 * Created by Anas Masri on 3/25/2018.
 */

public interface ConnectionDelegate {
    void onConnectionSuccess(ArrayList<Question> questions);
    void onConnectionFailed();
}
