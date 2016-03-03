# Freeload 

标签：Freeload

---

目录

[TOC]

---

## 概括
> Freeload 是一款下载引擎。通过它你可以很方便的添加下载任务，并且实时获取下载过程。该引擎轻巧易于维护，并且拥有很好的扩展性。

## 使用说明
### 1、创建下载请求队列
```java
private RequestQueue requestQueue = null;
requestQueue = Freeload.newRequestQueue(context);
```

### 2、创建下载请求
```java
private DownloadRequest request = null;
private Listener<String> listener = null;

// 创建监听回调
listener = new Response.Listener<String>() {
    // 创建事件监听过程
    @Override
    public void onResponse(String response) {
        return;
    }
    
    // 创建进度监听过程
    @Override
    public void onProgressChange(long fileSize, long downloadedSize) {
        mDownloadSize = (int) downloadedSize;
        return;
    }
};
    
request = new DownloadRequest(1, Url, mDownloadSize, listener);
```

### 3、将下载请求添加到下载队列里
```java
requestQueue.add(request);
```

### 4、在AndroidManifest里添加权限请求
```java
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_INTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
```