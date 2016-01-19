package com.rapid.jason.rapidnetwork.DownloadFile;

import android.os.Environment;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.rapid.jason.rapidnetwork.FreeLoad.core.Response;
import com.rapid.jason.rapidnetwork.FreeLoad.toolbox.DownloadRequest;
import com.rapid.jason.rapidnetwork.FreeLoad.toolbox.Freeload;
import com.rapid.jason.rapidnetwork.R;

import java.io.File;

import de.greenrobot.event.EventBus;

public class DownloadActivity extends ActionBarActivity {

    private Button button = null;

    private File mDownloadFile;

    private String Url = "http://gdown.baidu.com/data/wisegame/f70d2a17410e25a8/shoujiyingyongshichang_1.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        EventBus.getDefault().register(this);

        String DownloadPath = Environment.getExternalStorageDirectory().getAbsolutePath();;
        DownloadPath += "/try/";
        mDownloadFile = new File(DownloadPath);


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download(Url, mDownloadFile);
            }
        });

        Freeload.newRequestQueue(this);

        Response.Listener<String> s = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        };

        DownloadRequest request = new DownloadRequest(Url, s);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(DownloadEvent event) {
        String msg = event.getMsg();
    }

    private void download(final String path, final File savedir) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                FileDownloader loader = new FileDownloader(DownloadActivity.this, path, savedir, 1);
                //progressBar.setMax(loader.getFileSize());//设置进度条的最大刻度为文件的长度

                try {
                    loader.download(new DownloadProgressListener() {
                        @Override
                        public void onDownloadSize(int size) {//实时获知文件已经下载的数据长度
                            Message msg = new Message();
                            msg.what = 1;
                            msg.getData().putInt("size", size);
                            //handler.sendMessage(msg);//发送消息
                        }
                    });
                } catch (Exception e) {
                    //handler.obtainMessage(-1).sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_download, menu);
        return true;
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
