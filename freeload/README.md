# Freeload 

��ǩ��Freeload

---

Ŀ¼

[TOC]

---

## ����
> Freeload ��һ���������档ͨ��������Ժܷ��������������񣬲���ʵʱ��ȡ���ع��̡���������������ά��������ӵ�кܺõ���չ�ԡ�

## ʹ��˵��
### 1�����������������
```java
private RequestQueue requestQueue = null;
requestQueue = Freeload.newRequestQueue(context);
```

### 2��������������
```java
private DownloadRequest request = null;
private Listener<String> listener = null;

// ���������ص�
listener = new Response.Listener<String>() {
    // �����¼���������
    @Override
    public void onResponse(String response) {
        return;
    }
    
    // �������ȼ�������
    @Override
    public void onProgressChange(long fileSize, long downloadedSize) {
        mDownloadSize = (int) downloadedSize;
        return;
    }
};
    
request = new DownloadRequest(1, Url, mDownloadSize, listener);
```

### 3��������������ӵ����ض�����
```java
requestQueue.add(request);
```

### 4����AndroidManifest�����Ȩ������
```java
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_INTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
```