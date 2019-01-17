package com.jikexueyuan.ui.uioptimization;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/15 0015.
 */

public class MainActivity extends FragmentActivity {

    private ListView mListView;
    private MyAdapter myAdapter;

    private LinearLayout mProgress;
    private LinearLayout mContentLy;

    private final int NETWOEK_FINISHED = 1000;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NETWOEK_FINISHED:
                    showNetFinishedView();
                    break;
            }
        }
    };

    private Fragment splashFragment;
    private ViewStub mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
//
//        initView();
//
//        doNetWork();
        //1、显示Splash界面
        splashFragment = new SplashFragment();
        mainLayout = (ViewStub) findViewById(R.id.main_content_viewstub);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.splash_container, splashFragment);
        fragmentTransaction.commit();


        //2、开始实现业务逻辑，数据初始化工作
        doNetWork();
        //3、加载主体布局
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {

                View mainView = mainLayout.inflate();
                initView(mainView);
            }
        });
        //4、延时一段时间，显示我们主界面（去除Splash界面）
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.remove(splashFragment);
                        fragmentTransaction.commit();

                    }
                }, 2000);
            }
        });

    }

    private void initView(View view) {
        mProgress = (LinearLayout) view.findViewById(R.id.main_progress);
        mListView = (ListView) view.findViewById(R.id.main_content_list);
        mContentLy = (LinearLayout) view.findViewById(R.id.main_content_ly);
    }

    //显示网络请求数据界面
    private void showNetingView() {
        mProgress.setVisibility(View.VISIBLE);
        mContentLy.setVisibility(View.GONE);
    }


    //显示网络请求数据结束界面
    private void showNetFinishedView() {
        mProgress.setVisibility(View.GONE);
        mContentLy.setVisibility(View.VISIBLE);

        List<GoodModel> data = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GoodModel good = new GoodModel("商品名称" + i, 200 + i, 300 + i);
            data.add(good);
        }


        myAdapter = new MyAdapter(MainActivity.this, data);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, GoodDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 开启子线程请求网络数据
     **/
    private void doNetWork() {

//        showNetingView();

        new Thread(new Runnable() {
            @Override
            public void run() {

                //假装去服务器取数据
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //向handler发送消息
                Message netMessage = mHandler.obtainMessage();
                netMessage.what = NETWOEK_FINISHED;
                mHandler.sendMessage(netMessage);

            }
        }).start();

    }


}
