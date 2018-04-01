package com.mirath.models;

/**
 * Created by Anas Masri on 3/31/2018.
 */

public class Answer {

    String name;
    String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public Answer setValue(String value) {
        this.value = value;
        return this;
    }
}
