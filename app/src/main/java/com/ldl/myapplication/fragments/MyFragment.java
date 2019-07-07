package com.ldl.myapplication.fragments;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ldl.commonlibrary.weiget.utils.HiddenAnimUtils;
import com.ldl.myapplication.R;
import com.ldl.myapplication.base.BaseFragment;

public class MyFragment extends BaseFragment {

    private LinearLayout ll_hiden;
    private ImageView iv_test;
    private int hidenHeight;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

        ImageView iv_test = view.findViewById(R.id.iv_test);
        ll_hiden = view.findViewById(R.id.ll_hiden);
        initOnLayoutListener(view);

        iv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiddenAnimUtils.newInstance(getContext(), ll_hiden,v,hidenHeight).toggle();

            }
        });
        ll_hiden.setVisibility(View.VISIBLE);
    }

    private void initOnLayoutListener(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                hidenHeight = ll_hiden.getMeasuredHeight();
                ll_hiden.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else{
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_my;
    }
}
