package com.example.selfview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * 完全自定义一个ViewGroup
 */
public class SelfViewGroup extends ViewGroup {
    public SelfViewGroup(Context context) {
        super(context);
    }

    public SelfViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    ArrayList<Integer> left = new ArrayList<>();
    ArrayList<Integer> top = new ArrayList<>();
    ArrayList<Integer> right = new ArrayList<>();
    ArrayList<Integer> bottom = new ArrayList<>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int usedHeight = 0; //ViewGroup自己的可用空间已经用了多少
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams lp = child.getLayoutParams();

            /**
             * //根据parent给的一个限制，得到自己的可用size
             * 根据mode的不同，自己的可用空间分如下情况:
             * 1.EXACTLY/AT_MOST --  可用空间就是MeasureSpec中的size
             * 2.UNSPECIFIED -- 可用空间就是无限大
             */
            int selfHeightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int selfHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);


            int childHeightSpec;//给子view计算的时候的一个限制
            int chileWidthSpec = 0;//给子view计算的时候的一个限制

            switch (lp.height){
                case MATCH_PARENT:
                    if (selfHeightSpecMode == MeasureSpec.EXACTLY || selfHeightSpecMode == MeasureSpec.AT_MOST){
                        childHeightSpec = MeasureSpec.makeMeasureSpec(selfHeightSpecSize - usedHeight,MeasureSpec.EXACTLY);
                    }else{
                        childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
                    }
                    break;
                case WRAP_CONTENT:
                    if (selfHeightSpecMode == MeasureSpec.EXACTLY || selfHeightSpecMode == MeasureSpec.AT_MOST){
                        childHeightSpec = MeasureSpec.makeMeasureSpec(selfHeightSpecSize - usedHeight,MeasureSpec.AT_MOST);
                    }else{
                        childHeightSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
                    }
                    break;
                default:
                    Log.i("xx", "onMeasure: default");
                    childHeightSpec = MeasureSpec.makeMeasureSpec(lp.height,MeasureSpec.EXACTLY);
                    chileWidthSpec = MeasureSpec.makeMeasureSpec(lp.width,MeasureSpec.EXACTLY);
                    break;
            }
            left.add(0);
            right.add(MeasureSpec.getSize(chileWidthSpec));
            top.add(usedHeight);
            bottom.add(usedHeight + MeasureSpec.getSize(childHeightSpec));

            usedHeight += MeasureSpec.getSize(childHeightSpec);
            Log.i("xx", "onMeasure: h=" + MeasureSpec.getSize(childHeightSpec) + "used=" + usedHeight);
            child.measure(chileWidthSpec,childHeightSpec);
        }
        setMeasuredDimension(dp2px(getContext(),75),usedHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.layout(left.get(i),top.get(i),right.get(i),bottom.get(i));
        }
    }

    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
