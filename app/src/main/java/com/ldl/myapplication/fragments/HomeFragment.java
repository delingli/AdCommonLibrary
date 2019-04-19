package com.ldl.myapplication.fragments;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ldl.myapplication.R;
import com.ldl.myapplication.base.BaseFragment;

public class HomeFragment extends BaseFragment {

    private RecyclerView mRecycleView;

    @Override
    protected void initData() {

    }

    public static Fragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    Handler mHandler = new Handler(Looper.myLooper());

    @Override
    protected void initView(View view) {
        mRecycleView = view.findViewById(R.id.recycleView);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_home;
    }
}
