package com.freeload.jason.toolbox;

import java.io.Serializable;
import java.util.ArrayList;

public class EscapeReceipt implements Serializable {

    private ArrayList<DownloadReceipt> mListReceipt;

    public EscapeReceipt() {
        mListReceipt = new ArrayList<DownloadReceipt>();
    }

    public void setDownloadReceipt(DownloadReceipt receipt) {
        if (null == receipt) {
            return;
        }

        long size = 0;
        long totalSize = 0;
        int pos = 0;
        for (; pos < mListReceipt.size(); ++pos) {
            DownloadReceipt localReceipt = mListReceipt.get(pos);
            if (localReceipt.getDownloadPosition() == receipt.getDownloadPosition()) {
                size = localReceipt.getDownloadedSize();
                totalSize = localReceipt.getDownloadTotalSize();

                if (receipt.getDownloadTotalSize() == 0) {
                    receipt.setDownloadedSize(size);
                    receipt.setDownloadTotalSize(totalSize);
                }
                mListReceipt.remove(pos);
                mListReceipt.add(receipt);
                return;
            }
        }
        mListReceipt.add(receipt);
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < mListReceipt.size(); ++i) {
            str += "downloadSize" + mListReceipt.get(i).getDownloadPosition() + ":" + mListReceipt.get(i).getDownloadedSize() +
                    ",downloadTotalSize" + mListReceipt.get(i).getDownloadPosition() + ":" + mListReceipt.get(i).getDownloadTotalSize() +
                    ",downloadState:" + mListReceipt.get(i).getDownloadState() + ";";
        }
        return str;
    }
}
