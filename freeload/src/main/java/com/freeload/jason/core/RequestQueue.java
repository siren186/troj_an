package com.freeload.jason.core;

import android.os.Handler;
import android.os.Looper;

import com.freeload.jason.toolbox.BasicDownload;
import com.freeload.jason.toolbox.PrepareDownload;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestQueue {

    /** The network dispatchers. */
    private DownloadDispatcher[] mDispatchers;

    /** Number of download request dispatcher threads to start. */
    private static final int DEFAULT_DOWNLOAD_THREAD_POOL_SIZE = 4;

    /** The queue of requests that are actually going out to the network. */
    private final PriorityBlockingQueue<Request<?>> mDownloadQueue =
            new PriorityBlockingQueue<Request<?>>();

    private AtomicInteger mSequenceGenerator = new AtomicInteger();

    /** Download interface for performing requests. */
    private final BasicDownload mDownload;

    /** The prepare interface for prepare download. */
    private final PrepareDownload mPrepare;

    /** Response delivery mechanism. */
    private final ResponseDelivery mDelivery;

    public RequestQueue(BasicDownload basicDownload, PrepareDownload prepareDownload) {
        this(basicDownload, prepareDownload, DEFAULT_DOWNLOAD_THREAD_POOL_SIZE);
    }

    public RequestQueue(BasicDownload basicDownload, PrepareDownload prepareDownload, int threadPoolSize) {
        this(basicDownload, prepareDownload, threadPoolSize, new ExecutorDelivery(new Handler(Looper.getMainLooper())));
    }

    public RequestQueue(BasicDownload basicDownload, PrepareDownload prepareDownload, int threadPoolSize, ResponseDelivery delivery) {
        this.mDownload = basicDownload;
        this.mPrepare = prepareDownload;
        this.mDispatchers = new DownloadDispatcher[threadPoolSize];
        this.mDelivery = delivery;
    }

    /**
     * The set of all requests currently being processed by this RequestQueue. A Request
     * will be in this set if it is waiting in any queue or currently being processed by
     * any dispatcher.
     */
    private final Set<Request<?>> mCurrentRequests = new HashSet<Request<?>>();

    /**
     * Starts the dispatchers in this queue.
     */
    public void start() {
        stop();

        // Create network dispatchers (and corresponding threads) up to the pool size.
        for (int i = 0; i < mDispatchers.length; i++) {
            DownloadDispatcher downloadDispatcher =
                    new DownloadDispatcher(mDownloadQueue, mDownload,
                            mPrepare, mDelivery);
            mDispatchers[i] = downloadDispatcher;
            downloadDispatcher.start();
        }
    }

    /**
     * Stops download dispatchers.
     */
    public void stop() {
        for (int i = 0; i < mDispatchers.length; i++) {
            if (mDispatchers[i] != null) {
                mDispatchers[i].quit();
            }
        }
    }

    /**
     * Adds a Request to the dispatch queue.
     * @param request The request to service
     * @return The passed-in request
     */
    public <T> Request<T> add(Request<T> request) {
        request.setRequestQueue(this);
        synchronized (mCurrentRequests) {
            mCurrentRequests.add(request);
        }

        request.setSequence(getSequenceNumber());

        mDownloadQueue.add(request);

        return request;
    }

    /**
     * Gets a sequence number.
     */
    public int getSequenceNumber() {
        return mSequenceGenerator.incrementAndGet();
    }

    /**
     * Called from {@link Request#finish()}, indicating that processing of the given request
     * has finished.
     */
    <T> void finish(Request<T> request) {
        // Remove from the set of requests currently being processed.
        synchronized (mCurrentRequests) {
            mCurrentRequests.remove(request);
        }
    }
}
