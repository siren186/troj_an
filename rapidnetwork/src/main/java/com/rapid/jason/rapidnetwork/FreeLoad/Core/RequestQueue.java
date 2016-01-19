package com.rapid.jason.rapidnetwork.FreeLoad.core;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestQueue {

    /** The queue of requests that are actually going out to the network. */
    private final PriorityBlockingQueue<Request<?>> mDownloadQueue =
            new PriorityBlockingQueue<Request<?>>();

    private AtomicInteger mSequenceGenerator = new AtomicInteger();

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
}
