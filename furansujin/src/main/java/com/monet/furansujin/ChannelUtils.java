package com.monet.furansujin;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 渠道工具
 * 原理: 通过读取apk包中的META-INF文件夹下的一个空文件夹的名字,来取得渠道号.
 * 优点: 无需重新编译apk包,使用脚本即可快速批量修改渠道号.
 * 用法:
 */
public class ChannelUtils {

    private static String mChannel = null;
    private static final String mDefaultChannel = "0";
    private static final String mMark = "_";
    private static final String mKeyOfChannelFileName = "07C14759-1E80-4f5f-8C84-FCF7BDDB6CA3" + mMark;

    public synchronized static String getChannel(String apkPath) {
        if (TextUtils.isEmpty(mChannel)) {
            mChannel = getChannelImpl(apkPath);
        }
        return mChannel;
    }

    private static String getChannelImpl(String apkPath) {
        String fileName = getChannelFileName(apkPath);
        String channel = parseFileName2Channel(fileName);

        if (TextUtils.isEmpty(channel)) {
            channel = mDefaultChannel;
        }

        return channel;
    }

    private static String getChannelFileName(String apkPath) {
        if (TextUtils.isEmpty(apkPath)) {
            return null;
        }

        String fileName = null;
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(apkPath);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(mKeyOfChannelFileName)) {
                    fileName = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return fileName;
    }

    private static String parseFileName2Channel(String fileName) {
        String channel = null;
        if (!TextUtils.isEmpty(fileName)) {
            String[] split = fileName.split(mMark);
            if (split.length >= 2) {
                channel = fileName.substring(split[0].length() + 1);
            }
        }
        return channel;
    }
}
