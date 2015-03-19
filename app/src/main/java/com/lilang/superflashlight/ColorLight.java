package com.lilang.superflashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.lilang.superflashlight.widget.HideTextView;

/**
 * Created by 朗 on 2015/3/14.
 */
public class ColorLight extends Blub implements ColorPickerDialog.OnColorChangeListener{
    protected int mCurrentColorLight = Color.RED;
    protected HideTextView mHideTextViewColorLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHideTextViewColorLight = (HideTextView) findViewById(R.id.textView_hide_color_light);
    }

    public void onClick_ShowColorPicker(View view) {
        new ColorPickerDialog(this, this, Color.RED).show();
        mHideTextViewColorLight.hide();
    }

    @Override
    public void colorChanged(int color) {
        mUIColorLight.setBackgroundColor(color);
        mCurrentColorLight = color;
    }
}
