package com.monet.furansujin;

import android.content.Context;

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
    private static final String mKeyOfChannelFileName = "channel_";

    public synchronized static String getChannel(Context ctx) {
        if (null == mChannel) {
            mChannel = getChannelImpl(ctx);
        }
        return mChannel;
    }

    private static String getChannelImpl(Context ctx) {
        String fileName = getChannelFileName(ctx);
        String channel = parseFileName2Channel(fileName);

        if (null == channel) {
            channel = mDefaultChannel;
        }

        return channel;
    }

    private static String getChannelFileName(Context ctx) {
        if (null == ctx) {
            return null;
        }

        String sourceDir = ctx.getApplicationInfo().sourceDir;
        String fileName = null;
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
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
        String tryno = null;
        if (null != fileName) {
            String[] split = fileName.split("_");
            if (split != null && split.length >= 2) {
                tryno = fileName.substring(split[0].length() + 1);
            }
        }
        return tryno;
    }
}
