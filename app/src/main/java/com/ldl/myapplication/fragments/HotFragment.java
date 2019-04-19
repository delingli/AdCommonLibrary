package com.ldl.myapplication.fragments;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.ldl.myapplication.R;
import com.ldl.myapplication.base.BaseFragment;

public class HotFragment extends BaseFragment {
    @Override
    protected void initData() {

    }
Handler mHandler=new Handler(Looper.getMainLooper(), new Handler.Callback() {
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
});

    @Override
    protected void initView(View view) {
        Message obtain = Message.obtain();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },100);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_hot;
    }
}
