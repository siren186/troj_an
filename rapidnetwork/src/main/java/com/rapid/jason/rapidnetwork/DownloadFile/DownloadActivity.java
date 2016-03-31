package com.rapid.jason.rapidnetwork.DownloadFile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.freeload.jason.core.DownloadThreadType;
import com.freeload.jason.core.RequestQueue;
import com.freeload.jason.core.Response;
import com.freeload.jason.toolbox.DownloadRequestManager;
import com.freeload.jason.toolbox.EscapeReceipt;
import com.freeload.jason.toolbox.Freeload;
import com.rapid.jason.rapidnetwork.R;

import de.greenrobot.event.EventBus;

public class DownloadActivity extends Activity {

    private Button button = null;
    private Button button2 = null;

    private String Url = "http://gdown.baidu.com/data/wisegame/f70d2a17410e25a8/shoujiyingyongshichang_1.apk";

    private RequestQueue requestQueue = null;

    private TextView text = null;
    private TextView text1 = null;

    private DownloadRequestManager request = null;

    private int mDownloadSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        EventBus.getDefault().register(this);
        requestQueue = Freeload.newRequestQueue(this);

        text = (TextView) findViewById(R.id.textView);
        text1 = (TextView) findViewById(R.id.textView1);

        //fileDownload = new DownloadFileDb(1, "shoujiyingyongshichang_1.apk", 0, 1);

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //request.cancel();
            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = DownloadRequestManager.create(1, Url)
                        .setDownloadThreadType(DownloadThreadType.DOUBLETHREAD)
                        .setListener(new Response.Listener<EscapeReceipt>() {
                            @Override
                            public void onProgressChange(EscapeReceipt s) {
                                System.out.println(s.toString());
                            }
                        })
                        .addRequestQueue(requestQueue);
            }
        });

//        Observable.create(new Observable.OnSubscribe<ArrayList<String>>() {
//                    @Override
//                    public void call(Subscriber<? super ArrayList<String>> subscriber) {
//                        System.out.println("create call");
//                        ArrayList<String> T = new ArrayList<String>();
//                        T.add("hello");
//                        T.add("shit");
//                        T.add("fuck");
//                        T.add("");
//                        T.add("ppp");
//                        subscriber.onNext(T);
//                        subscriber.onCompleted();
//                    }
//                })
//                .doOnNext(new Action1<ArrayList<String>>() {
//                    @Override
//                    public void call(ArrayList<String> strings) {
//                        System.out.println("doOnNext 1 call");
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .doOnNext(new Action1<ArrayList<String>>() {
//                    @Override
//                    public void call(ArrayList<String> strings) {
//                        System.out.println("doOnNext 2 call");
//                        try {
//                            Thread.sleep(10000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(new Func1<ArrayList<String>, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(ArrayList<String> strings) {
//                        return Observable.from(strings);
//                    }
//                })
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s) {
//                        return null;
//                    }
//                })
//                .filter(new Func1<String, Boolean>() {
//                    @Override
//                    public Boolean call(String s) {
//                        return !"".equals(s);
//                    }
//                })
//                .take(2)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println("subscribe call");
//                        System.out.println(s);
//                    }
//                });
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
