package com.status.matathi;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomeImageView extends ImageView {

    public CustomeImageView(Context context) {
        super(context);
    }

    public CustomeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
