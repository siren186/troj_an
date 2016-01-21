package com.rapid.jason.rapidnetwork.FreeLoad.core;

import android.os.*;
import android.os.Process;

import com.rapid.jason.rapidnetwork.DownloadFile.DownloadEvent;
import com.rapid.jason.rapidnetwork.DownloadFile.FileDownloader;

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

    /** Used for telling us to die. */
    private volatile boolean mQuit = false;

    public DownloadDispatcher(BlockingQueue<Request<?>> queue) {
        mQueue = queue;
    }

    public DownloadDispatcher(FileDownloader downloader, URL downUrl, File saveFile, int block, int downLength, int threadId) {
        this.downloader = downloader;
        this.downUrl = downUrl;
        this.saveFile = saveFile;
        this.block = block;
        this.downLength = downLength;
        this.threadId = threadId;

        this.mQueue = null;
    }

    public boolean isFinish() {
        return finish;
    }

    @Override
    public void run() {
//        if(downLength < block){//未下载完成
//            try {
//                //使用Get方式下载
//                HttpURLConnection http = (HttpURLConnection) downUrl.openConnection();
//                http.setConnectTimeout(5 * 1000);
//                http.setRequestMethod("GET");
//                http.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
//                http.setRequestProperty("Accept-Language", "zh-CN");
//                http.setRequestProperty("Referer", downUrl.toString());
//                http.setRequestProperty("Charset", "UTF-8");
//
//                int startPos = block * (threadId - 1) + downLength;//开始位置
//                int endPos = block * threadId -1;//结束位置
//                http.setRequestProperty("Range", "bytes=" + startPos + "-"+ endPos);//设置获取实体数据的范围
//                http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
//                http.setRequestProperty("Connection", "Keep-Alive");
//
//                InputStream inStream = http.getInputStream();
//                byte[] buffer = new byte[1024];
//                int offset = 0;
//                RandomAccessFile threadfile = new RandomAccessFile(this.saveFile, "rwd");
//                threadfile.seek(startPos);
//
//                while ((offset = inStream.read(buffer, 0, 1024)) != -1) {
//                    threadfile.write(buffer, 0, offset);
//                    downLength += offset;
//                    downloader.update(this.threadId, downLength);
//                    downloader.append(offset);
//                }
//
//                threadfile.close();
//                inStream.close();
//                this.finish = true;
//            } catch (Exception e) {
//                this.downLength = -1;
//            }
//        }

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

            try {
                // If the request was cancelled already, do not perform the network request.
                if (request.isCanceled()) {
                    request.finish("download-discard-cancelled");
                    continue;
                }

                EventBus.getDefault().post(new DownloadEvent("get job id:" + Thread.currentThread().getId()));



            } catch (Exception e) {

            }
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
