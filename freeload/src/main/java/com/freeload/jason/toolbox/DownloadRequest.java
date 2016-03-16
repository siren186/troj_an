package com.freeload.jason.toolbox;

import com.freeload.jason.core.DownloadThreadType;
import com.freeload.jason.core.Request;
import com.freeload.jason.core.RequestQueue;
import com.freeload.jason.core.Response;

import java.util.ArrayList;

public class DownloadRequest extends Request<String> {

    private Response.Listener<String> mListener;
    private ArrayList<DownloadRequest> mDownloadRequestList;

    public static DownloadRequest create(int id, String Url) {
        return create(id, Url, 0);
    }

    public static DownloadRequest create(int id, String Url, int downloadStart) {
        return create(id, Url, downloadStart, null);
    }

    public static DownloadRequest create(int id, String Url, int downloadStart, String fileName) {
        return create(id, Url, downloadStart, fileName, null);
    }

    public static DownloadRequest create(int id, String Url, int downloadStart, String fileName, String fileFolder) {
        return create(id, Url, downloadStart, fileName, fileFolder, 0);
    }

    public static DownloadRequest create(int id, String Url, int downloadStart, String fileName, String fileFolder, int downloadLength) {
        return new DownloadRequest(id, Url, downloadStart, fileName, fileFolder, downloadLength);
    }

    protected DownloadRequest(int id, String Url, int downloadStart, String fileName, String fileFolder, int downloadLength) {
        super(id, Url, downloadStart, fileName, fileFolder, downloadLength);
    }

    public DownloadRequest setListener(Response.Listener<String> listener) {
        this.mListener = listener;
        return this;
    }

    public void cancel() {
        switch (getThreadType()) {
            case DownloadThreadType.NORMAL:
                cancelSingleRequest();
                break;
            case DownloadThreadType.DOUBLETHREAD:
                cancelDoubleRequest();
                break;
            default:
                cancelSingleRequest();
                break;
        }
    }

    private void cancelSingleRequest() {
        super.cancel();
    }

    private void cancelDoubleRequest() {
        super.cancel();
    }

    private DownloadRequest setThreadPositon(int position) {
        super.setThreadPosition(position);
        return this;
    }

    public DownloadRequest addRequestQueue(RequestQueue requestQueue) {
        switch (getThreadType()) {
            case DownloadThreadType.NORMAL:
                addSingleRequestQueue();
                break;
            case DownloadThreadType.DOUBLETHREAD:
                addDoubleRequestQueue();
                break;
            default:
                addSingleRequestQueue();
                break;
        }

        for (DownloadRequest downloadRequest : mDownloadRequestList) {
            requestQueue.add(downloadRequest);
        }

        return this;
    }

    private void addSingleRequestQueue() {
        mDownloadRequestList.add(this);
    }

    private void addDoubleRequestQueue() {
        for (int position = 0; position < 2; ++position) {
            mDownloadRequestList.add(createMulitDownloadRequest(position, DownloadThreadType.DOUBLETHREAD));
        }
    }

    private DownloadRequest createMulitDownloadRequest(int position, int threadType) {
        DownloadRequest downloadRequest;
        downloadRequest = DownloadRequest.create(getId(), getUrl())
                .setThreadPositon(position)
                .setDownloadThreadType(threadType)
                .setListener(new Response.Listener<String>() {
                    @Override
                    public void onProgressChange(long fileSize, long downloadedSize, String response) {
                        this.onProgressChange(fileSize, downloadedSize, response);
                    }
                });
        return downloadRequest;
    }

    public DownloadRequest setDownloadThreadType(int type) {
        setThreadType(type);
        return this;
    }

    @Override
    public void deliverDownloadProgress(long fileSize, long downloadedSize, String response) {
        if (this.mListener != null) {
            this.mListener.onProgressChange(fileSize, downloadedSize, response);
        }
    }
}
