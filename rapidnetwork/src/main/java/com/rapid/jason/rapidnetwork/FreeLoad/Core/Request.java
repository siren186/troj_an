package com.rapid.jason.rapidnetwork.FreeLoad.core;

import android.net.Uri;
import android.os.Environment;

import java.io.File;

public abstract class Request<T> implements Comparable<Request<T>> {

    /** Sequence number of this request, used to enforce FIFO ordering. */
    private Integer mSequence = 0;

    /** The request queue this request is associated with. */
    private RequestQueue mRequestQueue;

    /** Whether or not this request has been canceled. */
    private boolean mCanceled = false;

    /** URL of this request. */
    private final String mUrl;

    /** save file in this request. */
    private File mSaveFile = null;

    /** save file size. */
    private long mDownloadFileSize = 0;

    /** download file name. */
    private final String mFileName;

    /** download file parent folder. */
    private final String mFileFolder;

    private final static String fileSaveDir = Environment.getExternalStorageDirectory() + "/freeload/downloadfile";

    protected Request(String Url, String fileName, String fileFolder, int downloadFileSize) {
        this.mUrl = Url;
        this.mDownloadFileSize = downloadFileSize;

        this.mFileFolder = setFileFolder(fileFolder);
        this.mFileName = setFileName(Url, fileName);
    }

    private String setFileFolder(String fileFolder) {
        String downloadFileFolder;
        if (fileFolder == null) {
            downloadFileFolder = fileSaveDir;
        } else {
            downloadFileFolder = fileFolder;
        }
        return downloadFileFolder;
    }

    private String setFileName(String Url, String fileName) {
        String downloadFileName;
        if (fileName == null) {
            Uri uri = Uri.parse(Url);
            String strPath = uri.getPath();
            downloadFileName = strPath.substring(strPath.lastIndexOf('/') + 1, strPath.length());
        } else {
            downloadFileName = fileName;
        }
        return downloadFileName;
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public Request<?> setRequestQueue(RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
        return this;
    }

    /**
     * Mark this request as canceled.  No callback will be delivered.
     */
    public void cancel() {
        mCanceled = true;
    }

    /**
     * Returns true if this request has been canceled.
     */
    public boolean isCanceled() {
        return mCanceled;
    }

    /**
     * Returns the sequence number of this request.
     */
    public final int getSequence() {
        if (mSequence == null) {
            throw new IllegalStateException("getSequence called before setSequence");
        }
        return mSequence;
    }

    /**
     * Sets the sequence number of this request.
     *
     * @return This Request object to allow for chaining.
     */
    public final Request<?> setSequence(int sequence) {
        mSequence = sequence;
        return this;
    }

    /**
     * Subclasses must implement this to perform delivery of the parsed
     * response to their listeners.  The given response is guaranteed to
     * be non-null; responses that fail to parse are not delivered.
     * @param response
     */
    protected abstract void deliverResponse(T response);

    /**
     * Returns the URL of this request.
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * @return the save file objet of this request.
     */
    public File getSaveFile() {
        return mSaveFile;
    }

    /**
     * @return the download file has been download length.
     */
    public long getDownloadFileSize() {
        return mDownloadFileSize;
    }

    /**
     * @param downloadFileSize
     */
    public void setDownloadFileSize(long downloadFileSize) {
        mDownloadFileSize = downloadFileSize;
    }

    /**
     * @return download file name.
     */
    public String getFileName() {
        return mFileName;
    }

    /**
     * @return download folder name.
     */
    public String getFolderName() {
        return mFileFolder;
    }

    /**
     * @param saveFile the download file object;
     */
    public void setDownloadFile(File saveFile) {
        mSaveFile = saveFile;
    }

    @Override
    public int compareTo(Request<T> other) {
        Priority left = this.getPriority();
        Priority right = other.getPriority();

        // High-priority requests are "lesser" so they are sorted to the front.
        // Equal priorities are sorted by sequence number to provide FIFO ordering.
        return left == right ?
                this.mSequence - other.mSequence :
                right.ordinal() - left.ordinal();
    }

    /**
     * Notifies the request queue that this request has finished (successfully or with error).
     */
    public void finish() {
        if (mRequestQueue != null) {
            mRequestQueue.finish(this);
        }
    }

    /** Delivers when download request progress change to the Listener. */
    public abstract void deliverDownloadProgress(long fileSize, long downloadedSize);
}
