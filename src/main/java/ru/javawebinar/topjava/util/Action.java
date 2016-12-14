package ru.javawebinar.topjava.util;

/**
 * Created by arkan on 14.12.2016.
 */
public enum Action {
    DELETE("del"),
    EDIT("edit"),
    ADD("add");
    private String txt;
    Action(String t){
        txt = t;
    }
    public String getTxt(){
        return txt;
    }
}
