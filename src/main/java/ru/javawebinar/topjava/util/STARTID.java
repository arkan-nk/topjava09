package ru.javawebinar.topjava.util;

public enum STARTID {
    START_SEQ (100000),
    USER_ID (100000),
    ADMIN_ID (100001);
    private final int val;
    public int getVal(){
        return val;
    }
    STARTID(int value){
        val=value;
    }
}