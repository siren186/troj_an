package com.rapid.jason.rapidnetwork.FreeLoad.toolbox;

import android.os.Environment;

import com.rapid.jason.rapidnetwork.FreeLoad.core.Response;
import com.rapid.jason.rapidnetwork.FreeLoad.core.Request;

public class DownloadRequest extends Request<String>{

    private final Response.Listener<String> listener;

    private final static String fileSaveDir = Environment.getExternalStorageDirectory() + "/freeload/downloadfile";
    private final static String fileSaveName = "shoujiyingyongshichang_1.apk";

    public DownloadRequest(String Url) {
        this(Url, fileSaveName);
    }

    public DownloadRequest(String Url, String fileName) {
        this(Url, fileName, fileSaveDir);
    }

    public DownloadRequest(String Url, String fileName, String fileFolder) {
        this(Url, fileName, fileFolder, 0);
    }

    public DownloadRequest(String Url, String fileName, String fileFolder, int downloadLength) {
        this(Url, fileName, fileFolder, 0, null);
    }

    public DownloadRequest(String Url, String fileName, String fileFolder, int downloadLength, Response.Listener<String> listener) {
        super(Url, fileName, fileFolder, downloadLength);
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(String response) {
        this.listener.onResponse(response);
    }
}
