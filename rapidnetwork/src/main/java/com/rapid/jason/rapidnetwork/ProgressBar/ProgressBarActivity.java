package com.rapid.jason.rapidnetwork.ProgressBar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rapid.jason.rapidnetwork.R;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


public class ProgressBarActivity extends ActionBarActivity {

    private Button button = null;
    private Button button6 = null;

    private int number = 0;

    private ProgressbarView mScanPathView;
    private EditText startedit;
    private EditText centeredit;
    private EditText endedit;
    private EditText duedit;

    private DownloadProgressView progressBar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_bar);

        mScanPathView = (ProgressbarView) findViewById(R.id.path_layout);
        mScanPathView.startProgressbarAnimation();

        button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number += 5;
                progressBar.setProgressSize(number);

                String startime = startedit.getText().toString();
                String centerime = centeredit.getText().toString();
                String endime = endedit.getText().toString();
                String dutime = duedit.getText().toString();

                mScanPathView.setProgress(number, dutime, startime, centerime, endime);
            }
        });

        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScanPathView.setStopProgress();
            }
        });

        progressBar = (DownloadProgressView) findViewById(R.id.progressBar);
        number = 0;

        startedit = (EditText) findViewById(R.id.startcolor);
        centeredit = (EditText) findViewById(R.id.centercolor);
        endedit = (EditText) findViewById(R.id.endcolor);
        duedit = (EditText) findViewById(R.id.dutime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_process_bar, menu);
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