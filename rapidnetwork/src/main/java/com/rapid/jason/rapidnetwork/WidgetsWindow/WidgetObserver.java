package com.rapid.jason.rapidnetwork.WidgetsWindow;

import java.util.Observable;
import java.util.Observer;

public class WidgetObserver implements Observer {

    public WidgetObserver(WidgetObservable wo) {
        wo.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        System.out.println("Data has changed to" + ((WidgetObservable) observable).getData());
    }
}
