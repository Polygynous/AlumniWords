package com.augenstern.alumniwords;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;

public class MScrollView extends ScrollView {

    View v1;
    View v2;
    View v3;

    public MScrollView(Context context) {
        super(context);
        init();
    }

    public MScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    private void init() {
        v2 = findViewById(R.id.rv_container);
    }

    public void setV1(View v1) {
        this.v1 = v1;
    }

    public void setV3(View v3) {
        this.v3 = v3;
    }

    @Override
    public void computeScroll() {
        if (getScrollY() >= v2.getTop()) {
            v1.setVisibility(View.VISIBLE);
            v3.setVisibility(View.VISIBLE);
        } else {
            v1.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
        }
        super.computeScroll();
    }
}
