package com.rapid.jason.rapidnetwork.DownloadFile;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rapid.jason.rapidnetwork.FreeLoad.core.RequestQueue;
import com.rapid.jason.rapidnetwork.FreeLoad.core.Response;
import com.rapid.jason.rapidnetwork.FreeLoad.dbtool.FreeloadDbManager;
import com.rapid.jason.rapidnetwork.FreeLoad.toolbox.DownloadRequest;
import com.rapid.jason.rapidnetwork.FreeLoad.toolbox.Freeload;
import com.rapid.jason.rapidnetwork.R;

import de.greenrobot.event.EventBus;

public class DownloadActivity extends Activity {

    private Button button = null;
    private Button button2 = null;

    private String Url = "http://gdown.baidu.com/data/wisegame/f70d2a17410e25a8/shoujiyingyongshichang_1.apk";

    private RequestQueue requestQueue = null;

    private TextView text = null;
    private TextView text1 = null;

    private FreeloadDbManager dbManager = null;
    private DownloadFileDb fileDownload = null;

    private DownloadRequest request = null;

    private int mDownloadSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        EventBus.getDefault().register(this);
        requestQueue = Freeload.newRequestQueue(this);

        dbManager = new FreeloadDbManager(this, "freeload.db", 1, DownloadFileDb.class);

        text = (TextView) findViewById(R.id.textView);
        text1 = (TextView) findViewById(R.id.textView1);

        fileDownload = new DownloadFileDb(1, "shoujiyingyongshichang_1.apk", 0, 1);
        dbManager.insert(fileDownload);

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                request.cancel();
            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new DownloadRequest(1, Url, mDownloadSize, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        EventBus.getDefault().post(new DownloadEvent(response));
                        return;
                    }

                    @Override
                    public void onProgressChange(long fileSize, long downloadedSize) {
                        text.setText("" + fileSize);
                        text1.setText("" + downloadedSize);

//                        ContentValues values = new ContentValues();
//                        values.put("downloadstart", downloadedSize);
//                        dbManager.updateById(DownloadFileDb.class, values, 1);

                        mDownloadSize = (int) downloadedSize;

                        return;
                    }
                });
                requestQueue.add(request);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(DownloadEvent event) {
        String msg = event.getMsg();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void onEvent(DownloadEvent event) {
        String msg = event.getMsg();
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
