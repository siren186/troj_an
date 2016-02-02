package com.freeload.jason.toolbox;

import com.freeload.jason.core.Network;
import com.freeload.jason.core.Request;
import com.freeload.jason.core.Response;
import com.freeload.jason.core.ResponseDelivery;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class BasicDownload implements Network {

    /** connect to get downloadfile head timeout count. */
    private final static int CONNECT_TIMEOUT = 5 * 1000;

    @Override
    public void performRequest(Request<?> request) {
        performRequest(request, null);
    }

    @Override
    public void performRequest(Request<?> request, ResponseDelivery delivery) {
        try {
            URL downloadUrl = new URL(request.getUrl());
            long downLoadFileSize = request.getDownloadFileSize();
            long downloadLength = 0;

            postResponse(delivery, request, "start");

            //使用Get方式下载
            HttpURLConnection http = (HttpURLConnection) downloadUrl.openConnection();
            http.setConnectTimeout(CONNECT_TIMEOUT);
            http.setRequestMethod("GET");
            http.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            http.setRequestProperty("Accept-Language", "zh-CN");
            http.setRequestProperty("Referer", downloadUrl.toString());
            http.setRequestProperty("Charset", "UTF-8");

            int startPos = request.getDownloadStart();//开始位置
            long endPos = downLoadFileSize;//结束位置
            http.setRequestProperty("Range", "bytes=" + startPos + "-"+ endPos);//设置获取实体数据的范围
            http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            http.setRequestProperty("Connection", "Keep-Alive");

            InputStream inStream = http.getInputStream();
            byte[] buffer = new byte[1024];
            int offset = 0;
            RandomAccessFile threadfile = new RandomAccessFile(request.getSaveFile(), "rwd");
            threadfile.seek(startPos);

            while (true) {
                offset = inStream.read(buffer, 0, 1024);
                if (offset == -1) {
                    postResponse(delivery, request, "finish");
                    break;
                }

                threadfile.write(buffer, 0, offset);
                downloadLength += offset;

                if (request.isCanceled()) {
                    postResponse(delivery, request, "cancel");
                    break;
                }

                postProgress(request, delivery, downLoadFileSize, downloadLength + startPos);
            }

            threadfile.close();
            inStream.close();
        } catch (Exception e) {
        }
    }

    private void postProgress(Request<?> request, ResponseDelivery delivery, long downLoadFileSize, long downloadLength) {
        if (delivery == null) {
            return;
        }
        delivery.postDownloadProgress(request, downLoadFileSize, downloadLength);
    }

    private void postResponse(ResponseDelivery delivery, Request<?> request, String state) {
        if (delivery == null) {
            return;
        }
        delivery.postResponse(request, Response.success(state));
    }
}
