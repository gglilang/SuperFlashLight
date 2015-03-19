package com.lilang.superflashlight.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 朗 on 2015/3/14.
 */
public class HideTextView extends TextView {
    public HideTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected Handler mHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                setVisibility(GONE);
            }else{
                setVisibility(VISIBLE);
            }
        }
    };

    class TextViewThread extends Thread{
        @Override
        public void run() {
            super.run();
            mHander.sendEmptyMessage(1);
            try{
                Thread.sleep(3000);
                mHander.sendEmptyMessage(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void hide(){
        new TextViewThread().start();
    }
}
