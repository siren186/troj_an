package com.rapid.jason.rapidnetwork.WidgetsWindow;

import java.util.Observable;

public class WidgetObservable extends Observable {

    private int data = 0;

    public int getData(){
        return data;
    }

    public void setData(int i){
        if (this.data != i) {
            this.data = i;
            setChanged();
        }
        notifyObservers();
    }
}
