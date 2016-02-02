package com.rapid.jason.rapidnetwork.DownloadFile;

public class DownloadFileDb {
    private int mId;
    private String fileName;
    private int downloadStart;
    private int downloadState;

    public DownloadFileDb(int id, String fileName, int downloadStart, int downloadState) {
        this.mId = id;
        this.fileName = fileName;
        this.downloadStart = downloadStart;
        this.downloadState = downloadState;
    }

    public String getName() {
        return fileName;
    }

    public void setName(String fileName) {
        this.fileName = fileName;
    }

    public int getDownloadStart() {
        return downloadStart;
    }

    public void setDownloadStart(int downloadStart) {
        this.downloadStart = downloadStart;
    }

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    @Override
    public String toString() {
        return "downloadfile{" +
                "id=" + mId +
                ", fileName='" + fileName + '\'' +
                ", downloadstart=" + downloadStart +
                ", downloadstate=" + downloadState +
                '}';
    }
}
