package com.ldl.commonlibrary.weiget.weiget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Scroller;

public class LeftRightButton extends android.support.v7.widget.AppCompatButton {

    private Scroller mScroller;

    public LeftRightButton(Context context) {
        this(context, null);
    }

    public LeftRightButton(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LeftRightButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int downOffset = mScroller.getCurrX()-0;
                Log.d("ldl", "#downOffset#" + downOffset + "#getCurrX#" + mScroller.getCurrX());
                mScroller.startScroll(getScrollX(), getScrollY(), 200 - downOffset, 0,500);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                int upOffset =  mScroller.getCurrX() - 200;
                Log.d("ldl", "#upOffset#" + upOffset + "#getCurrX#" +  mScroller.getCurrX());
                mScroller.startScroll(getScrollX(), getScrollY(),-200-upOffset, 0,500);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
