package com.rapid.jason.rapidnetwork;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.rapid.jason.rapidnetwork.DownloadFile.DownloadActivity;
import com.rapid.jason.rapidnetwork.FloatWindow.FloatWinActivity;
import com.rapid.jason.rapidnetwork.ListWordCard.ListWordActivity;
import com.rapid.jason.rapidnetwork.NetworkIcon.ApkIconActivity;
import com.rapid.jason.rapidnetwork.ProgressBar.ProgressBarActivity;
import com.rapid.jason.rapidnetwork.TraversalFile.TraversalFileActivity;

public class MainActivity extends ActionBarActivity {

    Button btnVolleyNetIcon = null;
    Button btnVolleyTraversalFile = null;

    Button btnVolleySlidingTabs = null;
    Button btnVolleyFreeload = null;
    Button btnListWordCards = null;

    Button btnFloatWin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(MainActivity.this, ProgressBarActivity.class);
//        startActivity(intent);

        btnVolleyNetIcon = (Button) findViewById(R.id.volley_neticon_btn);
        btnVolleyNetIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ApkIconActivity.class);
                startActivity(intent);
            }
        });

        btnVolleyTraversalFile = (Button) findViewById(R.id.volley_traversal_file_btn);
        btnVolleyTraversalFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TraversalFileActivity.class);
                startActivity (intent);
            }
        });

//        btnVolleySlidingTabs = (Button) findViewById(R.id.volley_sliding_tabs_btn);
//        btnVolleySlidingTabs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SlidingTabActivity.class);
//                startActivity (intent);
//            }
//        });
        btnVolleyFreeload = (Button) findViewById(R.id.volley_free_load_btn);
        btnVolleyFreeload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
                startActivity (intent);
            }
        });

        btnFloatWin = (Button) findViewById(R.id.float_win_btn);
        btnFloatWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FloatWinActivity.class);
                startActivity (intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
