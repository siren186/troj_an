package com.rapid.jason.rapidnetwork;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;


public class TraversalFileActivity extends ActionBarActivity {

    private static final String TAG = TraversalFileActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traversal_file);

        getFileListOnDownloadDir();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_traversal_file, menu);
        return true;
    }

    public Vector<String> getFileListOnDownloadDir() {
        Vector<String> fileList;
        fileList = GetFileName("/storage/emulated/0/Shoujikong/Downloadapk");
        return fileList;
    }

    public static Vector<String> GetFileName(String fileAbsolutePath) {
        Vector<String> fileList = new Vector<String>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为MP4结尾
                fileList.add(filename);
            }
        }
        return fileList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
