package com.monet.furansujin;

import android.text.TextUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 渠道号工具
 * 原理: 通过读取apk包中的META-INF文件夹下的一个空文件的名字,来取得渠道号.
 * 优点: 无需重新编译apk包,使用脚本即可快速批量修改渠道号.
 * 用法:
 */
public class ChannelUtils {

    private volatile static ChannelUtils s_instance = null;
    private String mChannel = null;

    public static String getChannel(final String apkPath) {
        return getInstance(apkPath).getChannel();
    }

    private String getChannel() {
        return mChannel;
    }

    private void setChannel(final String channel) {
        mChannel = channel;
    }

    private ChannelUtils(final String apkPath) {
        setChannel(ChannelUtilsImpl.getChannelImpl(apkPath));
    }

    private static ChannelUtils getInstance(final String apkPath) {
        if (null == s_instance) {
            synchronized (ChannelUtils.class) {
                if (null == s_instance) {
                    s_instance = new ChannelUtils(apkPath);
                }
            }
        }
        return s_instance;
    }

    private static class ChannelUtilsImpl {

        private static final String DEFAULT_CHANNEL = "0";
        private static final String SPLITTER = "_";
        private static final String CHANNEL_FILE_FLAG = "META-INF/07c1" + SPLITTER;

        private static String getChannelImpl(final String apkPath) {
            String fileName = getChannelFileName(apkPath);
            String channel = parseFileName2Channel(fileName);

            if (TextUtils.isEmpty(channel)) {
                channel = DEFAULT_CHANNEL;
            }

            return channel;
        }

        private static String getChannelFileName(final String apkPath) {
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
                    if (entryName.startsWith(CHANNEL_FILE_FLAG)) {
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

        private static String parseFileName2Channel(final String fileName) {
            String channel = null;
            if (!TextUtils.isEmpty(fileName)) {
                String[] split = fileName.split(SPLITTER);
                if (split.length >= 2) {
                    channel = fileName.substring(split[0].length() + 1);
                }
            }
            return channel;
        }
    }
}
