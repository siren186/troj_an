package com.rapid.jason.rapidnetwork.FreeLoad.core;

public interface Network {

    public void performRequest(Request<?> request);

    public void performRequest(Request<?> request, ResponseDelivery delivery);
}
