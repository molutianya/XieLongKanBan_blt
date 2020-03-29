package com.example.a86156.lunbo_2;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    private TabHost mTabHost;  //选项卡
    TabHost.TabSpec tabSpec1;  //第一个页面
    TabHost.TabSpec tabSpec2;  //第二个页面
    Handler handler;  //定时轮播
    static int cout = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        mTabHost = getTabHost();
        //初始化
        mTabHost.setup();

        //机台有效利用率
        tabSpec1 = mTabHost.newTabSpec("tab1").setIndicator("柏拉图看板").setContent(new Intent(this, Tab1Activity.class));
        mTabHost.addTab(tabSpec1);
        tabSpec2 = mTabHost.newTabSpec("tab2").setIndicator("工单看板").setContent(new Intent(MainActivity.this, Tab2Activity.class));
        mTabHost.addTab(tabSpec2);


        //实现定时轮播
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (cout) {
                    case 0:
                        mTabHost.setCurrentTab(0);
                        cout = 1;
                        break;
                    case 1:
                        mTabHost.setCurrentTab(1);
                        cout = 0;
                        break;
                    default:
                        break;
                }
                handler.postDelayed(this, 10000);

            }
        }, 3000);

    }


    }




