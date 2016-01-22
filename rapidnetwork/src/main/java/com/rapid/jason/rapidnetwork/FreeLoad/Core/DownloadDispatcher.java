package com.rapid.jason.rapidnetwork.FreeLoad.core;

import android.os.*;
import android.os.Process;

import com.rapid.jason.rapidnetwork.DownloadFile.DownloadEvent;
import com.rapid.jason.rapidnetwork.DownloadFile.FileDownloader;
import com.rapid.jason.rapidnetwork.FreeLoad.toolbox.BasicDownload;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

import de.greenrobot.event.EventBus;

public class DownloadDispatcher extends Thread {

    private File saveFile;
    private URL downUrl;
    private int block;

    /* 下载开始位置  */
    private int threadId = -1;
    private int downLength;
    private boolean finish = false;
    private FileDownloader downloader;

    /** The queue of requests to service. */
    private final BlockingQueue<Request<?>> mQueue;

    /** The download interface for processing requests. */
    private final BasicDownload mDownload;

    /** Used for telling us to die. */
    private volatile boolean mQuit = false;

    public DownloadDispatcher(BlockingQueue<Request<?>> queue, BasicDownload basicDownload) {
        this.mQueue = queue;
        this.mDownload = basicDownload;
    }

//    public DownloadDispatcher(FileDownloader downloader, URL downUrl, File saveFile, int block, int downLength, int threadId) {
//        this.downloader = downloader;
//        this.downUrl = downUrl;
//        this.saveFile = saveFile;
//        this.block = block;
//        this.downLength = downLength;
//        this.threadId = threadId;
//
//        this.mQueue = null;
//        this.mDownload = null;
//    }

    public boolean isFinish() {
        return finish;
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (true) {
            long startTimeMs = SystemClock.elapsedRealtime();
            Request<?> request;
            try {
                request = mQueue.take();
            } catch (InterruptedException e) {
                if (mQuit) {
                    return;
                }
                continue;
            }

            // If the request was cancelled already, do not perform the download request.
            if (request.isCanceled()) {
                request.finish("download-discard-cancelled");
                continue;
            }

            boolean prepare = request.preparePerform();
            if (!prepare) {
                continue;
            }

            EventBus.getDefault().post(new DownloadEvent("get job id:" + Thread.currentThread().getId()));
            mDownload.performRequest(request);
        }
    }

    public long getDownLength() {
        return downLength;
    }

    /**
     * Forces this dispatcher to quit immediately.  If any requests are still in
     * the queue, they are not guaranteed to be processed.
     */
    public void quit() {
        mQuit = true;
        interrupt();
    }
}
