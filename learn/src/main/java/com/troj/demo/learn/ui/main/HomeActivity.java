package com.troj.demo.learn.ui.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.troj.demo.learn.R;
import com.troj.demo.learn.ui.main.utils.HomeActivityUtils;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;


public class HomeActivity extends FragmentActivity {

    @BindString(R.string.first_bottom_tab_title) String mBottomTabItemTitle0;
    @BindString(R.string.second_bottom_tab_title) String mBottomTabItemTitle1;
    @BindString(R.string.third_bottom_tab_title) String mBottomTabItemTitle2;
    @BindString(R.string.fourth_bottom_tab_title) String mBottomTabItemTitle3;
    @Bind(android.R.id.tabhost) FragmentTabHost mFragmentTabHost;
    @BindDrawable(R.drawable.selector_bottom_tab_item_0_icon) Drawable mBottomTabItemIcon0;
    @BindDrawable(R.drawable.selector_bottom_tab_item_1_icon) Drawable mBottomTabItemIcon1;
    @BindDrawable(R.drawable.selector_bottom_tab_item_2_icon) Drawable mBottomTabItemIcon2;
    @BindDrawable(R.drawable.selector_bottom_tab_item_3_icon) Drawable mBottomTabItemIcon3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void onBackPressed() {
        if (!HomeActivityUtils.handleExitApp(getApplicationContext())) {
            super.onBackPressed();
            finish();
        }
    }

    private void initView() {
        HomeActivityUtils.FragmentInfo[] fragmentInfos = new HomeActivityUtils.FragmentInfo[] {
                new HomeActivityUtils.FragmentInfo(MainFragment0.class, mBottomTabItemTitle0, mBottomTabItemIcon0),
                new HomeActivityUtils.FragmentInfo(MainFragment1.class, mBottomTabItemTitle1, mBottomTabItemIcon1),
                new HomeActivityUtils.FragmentInfo(MainFragment2.class, mBottomTabItemTitle2, mBottomTabItemIcon2),
                new HomeActivityUtils.FragmentInfo(MainFragment3.class, mBottomTabItemTitle3, mBottomTabItemIcon3)
        };

        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.activity_home_main_content);
        mFragmentTabHost.getTabWidget().setDividerDrawable(null); // 去掉分割线

        for (HomeActivityUtils.FragmentInfo info : fragmentInfos) {
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(info.getTitle()).setIndicator(getViewOfBottomTabItem(info));
            mFragmentTabHost.addTab(tabSpec, info.getFragmentClass(), null);
        }
    }

    private View getViewOfBottomTabItem(HomeActivityUtils.FragmentInfo info) {
        View view = View.inflate(getApplicationContext(), R.layout.bottom_tab_item_layout, null);
        TextView tv = (TextView) view.findViewById(R.id.bottom_tab_item_layout_tv);
        tv.setText(info.getTitle());
        tv.setCompoundDrawables(null, info.getIcon(), null, null);
        return view;
    }
}
