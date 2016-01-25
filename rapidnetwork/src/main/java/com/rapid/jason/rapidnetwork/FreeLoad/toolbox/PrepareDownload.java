package com.rapid.jason.rapidnetwork.FreeLoad.toolbox;

import com.rapid.jason.rapidnetwork.FreeLoad.core.Prepare;
import com.rapid.jason.rapidnetwork.FreeLoad.core.Request;
import com.rapid.jason.rapidnetwork.FreeLoad.core.Response;
import com.rapid.jason.rapidnetwork.FreeLoad.core.ResponseDelivery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class PrepareDownload implements Prepare {

    /** connect to get downloadfile head timeout count. */
    private final static int CONNECT_TIMEOUT = 5 * 1000;

    @Override
    public boolean preparePerform(Request<?> request, ResponseDelivery delivery) {
        postResponse(delivery, request, "get file size");
        long downloadFileSize = getFileSize(request);
        if (downloadFileSize <= 0) {
            return false;
        }
        request.setDownloadFileSize(downloadFileSize);

        postResponse(delivery, request, "create file");
        File saveFile = createFile(request);
        if (saveFile == null) {
            return false;
        }
        request.setDownloadFile(saveFile);

        return true;
    }

    private void postResponse(ResponseDelivery delivery, Request<?> request, String state) {
        if (delivery == null) {
            return;
        }
        delivery.postResponse(request, Response.success(state));
    }

    private File createFile(Request<?> request) {
        if (!createFolder(request.getFolderName())) {
            return null;
        }

        File saveFile = new File(request.getFolderName(), request.getFileName());

        try {
            RandomAccessFile randOut = new RandomAccessFile(saveFile, "rw");
            if(request.getDownloadFileSize() > 0) {
                randOut.setLength(request.getDownloadFileSize());
            }
            randOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return saveFile;
    }

    private boolean createFolder(String fileFolder) {
        File folder = new File(fileFolder);
        if (!createAndCheckFolder(folder)) {
            return false;
        }

        if (!folder.isDirectory()) {
            if (!folder.delete()) {
                return false;
            }
        }

        if (!createAndCheckFolder(folder)) {
            return false;
        }

        return true;
    }

    private boolean createAndCheckFolder(File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            return true;
        }

        if (!folder.exists()) {
            return false;
        }
        return true;
    }

    private long getFileSize(Request<?> request) {
        if (request.getDownloadFileSize() > 0) {
            return 0;
        }

        long downloadFileSize = 0;
        try {
            downloadFileSize = connectAndGetFileSize(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return downloadFileSize;
    }

    private long connectAndGetFileSize(Request<?> request) throws Exception {
        URL mUrl = new URL(request.getUrl());
        HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
        conn.setConnectTimeout(CONNECT_TIMEOUT);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Encoding", "identity");
        conn.setRequestProperty("Referer", request.getUrl());
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.connect();

        long lenght = 0;
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            lenght = conn.getContentLength();
        }
        conn.disconnect();

        return lenght;
    }
}
