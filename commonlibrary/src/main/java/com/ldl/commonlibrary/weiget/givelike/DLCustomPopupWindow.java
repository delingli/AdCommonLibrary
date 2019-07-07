package com.ldl.commonlibrary.weiget.givelike;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DLCustomPopupWindow extends PopupWindow implements IPopWindow {
    private String mText = TEXT;

    private int mTextColor = TEXT_COLOR;

    private int mTextSize = TEXT_SIZE;

    private int mFromY = FROM_Y_DELTA;

    private int mToY = TO_Y_DELTA;

    private float mFromAlpha = FROM_ALPHA;

    private float mToAlpha = TO_ALPHA;

    private int mDuration = DURATION;

    private int mDistance = DISTANCE;

    private AnimationSet mAnimationSet;

    private boolean mChanged = false;

    private Context mContext = null;

    private TextView mTextView = null;
    private AnimatorSet mAnimatorSet;

    public DLCustomPopupWindow(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        RelativeLayout layout = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        mTextView = new TextView(mContext);
        mTextView.setIncludeFontPadding(false);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        mTextView.setTextColor(mTextColor);
        mTextView.setText(mText);
        mTextView.setLayoutParams(params);
        layout.addView(mTextView);
        setContentView(layout);

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mTextView.measure(w, h);
        setWidth(mTextView.getMeasuredWidth());
        setHeight(mDistance + mTextView.getMeasuredHeight());
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFocusable(false);
        setTouchable(false);
        setOutsideTouchable(false);
        mAnimatorSet = crteateAnimator();
        mAnimationSet = createAnimation();
    }

    /**
     * 设置文本
     *
     * @param text
     */
    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        mText = text;
        mTextView.setText(text);
        mTextView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int w = (int) mTextView.getPaint().measureText(text);
        setWidth(w);
        setHeight(mDistance + getTextViewHeight(mTextView, w));
    }

    private static int getTextViewHeight(TextView textView, int width) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    /**
     * 设置文本颜色
     *
     * @param color
     */
    private void setTextColor(int color) {
        mTextColor = color;
        mTextView.setTextColor(color);
    }

    /**
     * 设置文本大小
     *
     * @param textSize
     */
    private void setTextSize(int textSize) {
        mTextSize = textSize;
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
    }

    /**
     * 设置文本信息
     *
     * @param text
     * @param textColor
     * @param textSize
     */
    public void setTextInfo(String text, int textColor, int textSize) {
        setTextColor(textColor);
        setTextSize(textSize);
        setText(text);
    }

    /**
     * 设置图片
     *
     * @param resId
     */
    public void setImage(int resId) {
        setImage(mContext.getResources().getDrawable(resId));
    }

    /**
     * 设置图片
     *
     * @param drawable
     */
    public void setImage(Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("drawable cannot be null.");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mTextView.setBackground(drawable);
        } else {
            mTextView.setBackgroundDrawable(drawable);
        }
        mTextView.setText("");
        setWidth(drawable.getIntrinsicWidth());
        setHeight(mDistance + drawable.getIntrinsicHeight());
    }

    /**
     * 设置移动距离
     *
     * @param dis
     */
    public void setDistance(int dis) {
        mDistance = dis;
        mToY = dis;
        mChanged = true;
        setHeight(mDistance + mTextView.getMeasuredHeight());
    }

    /**
     * 设置Y轴移动属性
     *
     * @param fromY
     * @param toY
     */
    public void setTranslateY(int fromY, int toY) {
        mFromY = fromY;
        mToY = toY;
        mChanged = true;
    }

    /**
     * 设置透明度属性
     *
     * @param fromAlpha
     * @param toAlpha
     */
    public void setAlpha(float fromAlpha, float toAlpha) {
        mFromAlpha = fromAlpha;
        mToAlpha = toAlpha;
        mChanged = true;
    }

    /**
     * 设置动画时长
     *
     * @param duration
     */
    public void setDuration(int duration) {
        mDuration = duration;
        mChanged = true;
    }

    /**
     * 重置属性
     */
    public void reset() {
        mText = TEXT;
        mTextColor = TEXT_COLOR;
        mTextSize = TEXT_SIZE;
        mFromY = FROM_Y_DELTA;
        mToY = TO_Y_DELTA;
        mFromAlpha = FROM_ALPHA;
        mToAlpha = TO_ALPHA;
        mDuration = DURATION;
        mDistance = DISTANCE;
        mChanged = false;
        mAnimationSet = createAnimation();
    }

    /**
     * 展示
     *
     * @param v
     */
    public void show(View v) {
        if (!isShowing()) {
            int offsetY = -v.getHeight() - getHeight();
            showAsDropDown(v, v.getWidth() / 2 - getWidth() / 2, offsetY);
 /*           if (mAnimationSet == null || mChanged) {
                mAnimationSet = createAnimation();
                mChanged = false;
            }
            mTextView.startAnimation(mAnimationSet);*/
            if (mAnimatorSet != null && !mAnimatorSet.isRunning()) {
                mAnimatorSet.start();
            }
        }
    }

    private AnimatorSet crteateAnimator() {
        float translationX = mTextView.getTranslationX();
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mTextView, "translationY", translationX, translationX - mToY);
        animatorY.start();
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mTextView, "alpha", 1f, 0f);
//    ObjectAnimator scaleYanimator = ObjectAnimator.ofFloat(mTextView, "scaleY", 1, 2, 1);
//    ObjectAnimator scaleXanimator = ObjectAnimator.ofFloat(mTextView, "scaleX", 1, 2, 1);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(animatorY).with(alphaAnimator);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return mAnimatorSet;
    }

    /**
     * 动画
     *
     * @return
     */
    private AnimationSet createAnimation() {
        mAnimationSet = new AnimationSet(true);
        TranslateAnimation translateAnim = new TranslateAnimation(0, 0, mFromY, -mToY);
        AlphaAnimation alphaAnim = new AlphaAnimation(mFromAlpha, mToAlpha);
        mAnimationSet.addAnimation(translateAnim);
        mAnimationSet.addAnimation(alphaAnim);
        mAnimationSet.setDuration(mDuration);
        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isShowing()) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    });
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        return mAnimationSet;
    }
}
