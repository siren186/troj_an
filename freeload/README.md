# Freeload 

��ǩ��Freeload

---

## ����
> Freeload ��һ���������档ͨ��������Ժܷ��������������񣬲���ʵʱ��ȡ���ع��̡���������������ά��������ӵ�кܺõ���չ�ԡ����°������������˶��߳����ص���Դ��֧�֣��ܹ�֧��˫�̡߳����߳��������߳�ͬ�����ص���Դ��ʹ����֧����ʽ��̣��ô���༭�ĸ��Ӻ���

## ʹ��˵��
### 1�����������������
```java
private RequestQueue requestQueue = null;
requestQueue = Freeload.newRequestQueue(context);
```

### 2�������������󲢽�����������ӵ����ض�����
```java
private DownloadRequestManager request = null;

// ���������ص�
request = DownloadRequestManager.create(id, Url)
    .setDownloadThreadType(DownloadThreadType.DOUBLETHREAD)
    .setListener(new Response.Listener<EscapeReceipt>() {
        @Override
        public void onProgressChange(EscapeReceipt s) {
            System.out.println(s.toString());
        }
    })
    .addRequestQueue(requestQueue);
```

1. `DownloadRequestManager.create(id, Url)`����һ��`DownloadRequestManager`���󣬲���Ϊ���ص�id�����ص�url��
2. `setDownloadThreadType(DownloadThreadType.DOUBLETHREAD)`�������ص��߳�����Freeload���ؿ�֧�ֶ��߳�ͬ�����ص���Դ�����Դ����������NORMAL`DownloadThreadType.`���̺߳�`DownloadThreadType.DOUBLETHREAD`˫�߳�������
3. `setListener(new Response.Listener<EscapeReceipt>()`���ü�������ʵʱ��������Ϣͬ�����ء�
4. `addRequestQueue(requestQueue)`��������ӵ����ض����

### 3����AndroidManifest�����Ȩ������
```java
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_INTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
```