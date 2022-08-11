package com.example.crt;

public class singleToneClass {
    String s;
    Integer f;
    private static final singleToneClass ourInstance = new singleToneClass();
    public static singleToneClass getInstance() {
        return ourInstance;
    }
    private singleToneClass() {
    }
    public void setData(String s) {
        this.s = s;
    }
    public String getData() {
        return s;
    }

    public void setFrag(Integer f){this.f=f ;}
    public Integer getFrag() {
        return f;
    }
}
