package com.rapid.jason.rapidnetwork.FreeLoad.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

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

    /** save file has been download length. */
    private long mDownloadLength = 0;

    /** download file name. */
    private final String mFileName;

    /** download file parent folder. */
    private final String mFileFolder;

    protected Request(String Url, String fileName, String fileFolder, int downloadLength) {
        this.mUrl = Url;
        this.mFileName = fileName;
        this.mFileFolder = fileFolder;
        this.mDownloadLength = downloadLength;
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
    abstract protected void deliverResponse(T response);

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
    public long getDownloadLength() {
        return mDownloadLength;
    }

    /**
     * @param downloadLength
     */
    public void setDownloadLength(long downloadLength) {
        mDownloadLength = downloadLength;
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
    public void finish(final String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.finish(this);
        }
    }
}
