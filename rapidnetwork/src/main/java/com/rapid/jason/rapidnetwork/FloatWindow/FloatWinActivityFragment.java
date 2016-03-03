package com.rapid.jason.rapidnetwork.FloatWindow;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rapid.jason.rapidnetwork.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FloatWinActivityFragment extends Fragment {

    private Handler mHandler = null;
    private Button mButton = null;

    private WindowUtils windowUtils = null;

    private FloatWinCalendar floatWinCalendar = null;

    public FloatWinActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_float_win, container, false);

        mHandler = new Handler();
        windowUtils = new WindowUtils();

        floatWinCalendar = new FloatWinCalendar();

        mButton = (Button) view.findViewById(R.id.button3);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        windowUtils.showPopupWindow(FloatWinActivityFragment.this.getActivity());
                        //floatWinCalendar.showFloatWinCalendar(FloatWinActivityFragment.this.getActivity());
                    }
                }, 1000);
            }
        });
        return view;
    }
}
