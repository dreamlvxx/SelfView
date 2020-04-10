package com.example.selfview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class SelfView extends View {
    public SelfView(Context context) {
        super(context);
    }

    public SelfView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 通过resolveSize来进行宽高的自定义，完全自己决定，
     * resolveSize是在满足parent的条件下，进行的一个过滤
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = resolveSize(dp2px(getContext(), 75), widthMeasureSpec);
        int h = resolveSize(dp2px(getContext(), 75), heightMeasureSpec);
        setMeasuredDimension(w, h);
    }

    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
