package com.lilang.superflashlight;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;

import com.lilang.superflashlight.widget.HideTextView;

/**
 * Created by 朗 on 2015/3/14.
 */
public class Blub extends Morse {
    protected boolean mBulbCrossFadeFlag;
    protected TransitionDrawable mDrawable;
    protected HideTextView mHideTextViewBulb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDrawable = (TransitionDrawable) mImageViewBulb.getDrawable();
        mHideTextViewBulb = (HideTextView) findViewById(R.id.textView_hide_bulb);
    }

    public void onClick_BulbCrossFade(View view) {
        if(!mBulbCrossFadeFlag){
            mDrawable.startTransition(500);
            mBulbCrossFadeFlag = true;
            screenBrightness(1f);
        }else {
            mDrawable.reverseTransition(500);
            mBulbCrossFadeFlag = false;
            screenBrightness(0f);
        }
    }
}
