package com.rapid.jason.rapidnetwork.DownloadFile;

import android.os.Environment;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rapid.jason.rapidnetwork.FreeLoad.core.RequestQueue;
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

    private RequestQueue requestQueue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        EventBus.getDefault().register(this);
        requestQueue = Freeload.newRequestQueue(this);

        String DownloadPath = Environment.getExternalStorageDirectory().getAbsolutePath();;
        DownloadPath += "/try/";
        mDownloadFile = new File(DownloadPath);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadRequest request = new DownloadRequest(Url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        return;
                    }
                });
                requestQueue.add(request);
            }
        });
    }

    @Override
    public void onDestroy() {
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
