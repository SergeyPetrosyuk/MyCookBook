package com.mlsdev.serhiy.mycookbook.ui.vidget;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by android on 31.03.15.
 */
public class ObservableScrollView extends ScrollView {
    private Toolbar mActionBar;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        ObjectAnimator actionBarmover = ObjectAnimator.ofFloat(mActionBar, "translationY", t * (-1));
//        actionBarmover.setDuration(0);
//        actionBarmover.start();
//
//        ObjectAnimator containerBarmover = ObjectAnimator.ofFloat(this, "translationY", t * (-1));
//        containerBarmover.setDuration(0);
//        containerBarmover.start();
    }

    public void setActionBar(Toolbar mActionBar) {
        this.mActionBar = mActionBar;
    }
}
