package com.rapid.jason.rapidnetwork.FreeLoad.core;

import android.os.*;
import android.os.Process;

import com.rapid.jason.rapidnetwork.DownloadFile.DownloadEvent;
import com.rapid.jason.rapidnetwork.FreeLoad.toolbox.BasicDownload;
import com.rapid.jason.rapidnetwork.FreeLoad.toolbox.PrepareDownload;

import java.util.concurrent.BlockingQueue;

import de.greenrobot.event.EventBus;

public class DownloadDispatcher extends Thread {

    private boolean finish = false;

    /** The queue of requests to service. */
    private final BlockingQueue<Request<?>> mQueue;

    /** The download interface for processing requests. */
    private final BasicDownload mDownload;

    /** The prepare interface for prepare download. */
    private final PrepareDownload mPrepare;

    /** Used for telling us to die. */
    private volatile boolean mQuit = false;

    public DownloadDispatcher(BlockingQueue<Request<?>> queue, BasicDownload basicDownload, PrepareDownload prepareDownload) {
        this.mQueue = queue;
        this.mDownload = basicDownload;
        this.mPrepare = prepareDownload;
    }

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

            boolean prepare = mPrepare.preparePerform(request);
            if (!prepare) {
                continue;
            }

            EventBus.getDefault().post(new DownloadEvent("get job id:" + Thread.currentThread().getId()));
            mDownload.performRequest(request);
        }
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
