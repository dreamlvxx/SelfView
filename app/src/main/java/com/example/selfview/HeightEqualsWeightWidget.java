package com.example.selfview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 继承一个【已有的view】
 * 重写onMeasure，修改布局的大小
 */
public class HeightEqualsWeightWidget extends ImageView {
    public HeightEqualsWeightWidget(Context context) {
        super(context);
    }

    public HeightEqualsWeightWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeightEqualsWeightWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        if (w > h){
            w = h;
        }else{
            h = w;
        }
        setMeasuredDimension(w,h);
    }
}
