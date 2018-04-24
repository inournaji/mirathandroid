package com.mirath.controllers.adapters;

import com.mirath.models.Question;

import java.util.ArrayList;

public interface FragmentDelegate {

   void onDone(ArrayList<Question> questions);
}
