package com.lilang.superflashlight;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.lilang.superflashlight.widget.HideTextView;

/**
 * Created by 朗 on 2015/3/15.
 */
public class PoliceLight extends ColorLight {
    protected boolean mPoliceState;
    protected HideTextView mHideTextViewPoliceLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHideTextViewPoliceLight = (HideTextView) findViewById(R.id.textView_hide_police_light);
    }

    class PoliceThread extends Thread{
        @Override
        public void run() {
            mPoliceState = true;
            while (mPoliceState){
                mHandler.sendEmptyMessage(Color.BLUE);
                sleepExt(100);
                mHandler.sendEmptyMessage(Color.BLACK);
                sleepExt(100);
                mHandler.sendEmptyMessage(Color.RED);
                sleepExt(100);
                mHandler.sendEmptyMessage(Color.BLACK);
                sleepExt(100);
            }
            super.run();
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int color = msg.what;
            mUIPoliceLight.setBackgroundColor(color);
        }
    };
}
