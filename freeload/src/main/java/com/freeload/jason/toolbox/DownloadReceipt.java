package com.freeload.jason.toolbox;

import java.io.Serializable;

public class DownloadReceipt implements Serializable {

    private long mDownloadSize = 0;
    private long mDownloadTotalSize = 0;

    public void setDownloadSize(long downloadSize) {
        mDownloadSize = downloadSize;
    }

    public long getDownloadSize() {
        return mDownloadSize;
    }

    public void setDownloadTotalSize(long downloadTotalSize) {
        mDownloadTotalSize = downloadTotalSize;
    }

    public long getDownloadTotalSize() {
        return mDownloadTotalSize;
    }

    @Override
    public String toString() {
        return "downloadSize:"+mDownloadSize+";downloadTotalSize:"+mDownloadTotalSize;
    }
}
