package com.example.autismapp;

import android.app.Application;

public class ApplicationClass extends Application {
    private boolean none = false;

    public boolean getNone(){
        return none;
    }

    public void setNone(boolean none){
        this.none = none;
    }
}
