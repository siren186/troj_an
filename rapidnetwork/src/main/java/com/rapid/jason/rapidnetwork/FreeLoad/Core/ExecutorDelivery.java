package com.rapid.jason.rapidnetwork.FreeLoad.core;

import android.os.Handler;

import java.util.concurrent.Executor;

public class ExecutorDelivery implements ResponseDelivery {

    /** Used for posting responses, typically to the main thread. */
    private final Executor mResponsePoster;

    /**
     * Creates a new response delivery interface.
     * @param handler {@link Handler} to post responses on
     */
    public ExecutorDelivery(final Handler handler) {
        // Make an Executor that just wraps the handler.
        mResponsePoster = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    @Override
    public void postResponse(Request<?> request) {
        postResponse(request, null);
    }

    @Override
    public void postResponse(Request<?> request, Response<?> response) {
        postResponse(request, response, null);
    }

    @Override
    public void postResponse(Request<?> request, Response<?> response, Runnable runnable) {
        mResponsePoster.execute(new ResponseDeliveryRunnable(request, response, runnable));
    }

    @Override
    public void postDownloadProgress(Request<?> request, long fileSize, long downloadedSize) {
        mResponsePoster.execute(new ResponseProgressDeliveryRunnable(request, fileSize, downloadedSize));
    }

    private class ResponseProgressDeliveryRunnable implements Runnable {
        private final Request mRequest;
        private final long mFileSize;
        private final long mDownloadedSize;

        private ResponseProgressDeliveryRunnable(Request request, long mFileSize, long mDownloadedSize) {
            this.mRequest = request;
            this.mFileSize = mFileSize;
            this.mDownloadedSize = mDownloadedSize;
        }

        @Override
        public void run() {
            if (mRequest == null) {
                return;
            }

            mRequest.deliverDownloadProgress(mFileSize, mDownloadedSize);
        }
    }

    /**
     * A Runnable used for delivering network responses to a listener on the main thread.
     */
    @SuppressWarnings("rawtypes")
    private class ResponseDeliveryRunnable implements Runnable {
        private final Request mRequest;
        private final Response mResponse;
        private final Runnable mRunnable;

        public ResponseDeliveryRunnable(Request request, Response response, Runnable runnable) {
            mRequest = request;
            mResponse = response;
            mRunnable = runnable;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            // If this request has canceled, finish it and don't deliver.
//            if (mRequest.isCanceled()) {
//                mRequest.finish();
//                return;
//            }
//
            // Deliver a normal response or error, depending.
            if (mResponse != null) {
                mRequest.deliverResponse(mResponse.result);
            }
//
//            // If this is an intermediate response, add a marker, otherwise we're done
//            // and the request can be finished.
//            mRequest.finish();
//
//            // If we have been provided a post-delivery runnable, run it.
//            if (mRunnable != null) {
//                mRunnable.run();
//            }
        }
    }
}
