package com.mirath.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Anas Masri on 4/1/2018.
 */

public class AppClass extends Application {

    private static AppClass instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static AppClass getInstance(){
        return instance;
    }
}
