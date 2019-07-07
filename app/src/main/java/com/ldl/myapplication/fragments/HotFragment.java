package com.ldl.myapplication.fragments;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.ldl.commonlibrary.weiget.givelike.DLCustomGiveLikeView;
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
        DLCustomGiveLikeView cGiveLike = view.findViewById(R.id.cGiveLike);
        cGiveLike.setCount(99);
        cGiveLike.setSelected(true);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_hot;
    }
}
