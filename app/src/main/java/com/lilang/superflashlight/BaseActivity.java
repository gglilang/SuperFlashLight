package com.lilang.superflashlight;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by 朗 on 2015/3/12.
 */
public class BaseActivity extends Activity {

    protected enum UIType {
        UI_TYPE_MAIN, UI_TYPE_FLASHLIGHT, UI_TYPE_WARNINGLIGHT,
        UI_TYPE_MORSE, UI_TYPE_BLUB, UI_TYPE_COLOR,
        UI_TYPE_POLICE, UI_TYPE_SETTINGS
    }

    protected int mCurrentWarningLightInterval = 500;
    protected int mCurrentPoliceLightInterval = 100;

    protected ImageView mImageViewFlashLight;
    protected ImageView mImageViewFlashLightController;
    protected ImageView mImageViewWarningLight1;
    protected ImageView mImageViewWarningLight2;
    protected EditText mEditTextMorseCode;
    protected ImageView mImageViewBulb;

    protected SeekBar mSeekBarWarningLight;
    protected SeekBar mSeekBarPoliceLight;
    protected Button mButtonAddShortcut;
    protected Button mButtonRemoveShortcut;

    protected Camera camera;
    protected Parameters parameters;

    protected FrameLayout mUIFlashLight;
    protected LinearLayout mUIMain;
    protected LinearLayout mUIWarningLight;
    protected LinearLayout mUIMorse;
    protected FrameLayout mUIBulb;
    protected FrameLayout mUIColorLight;
    protected FrameLayout mUIPoliceLight;
    protected LinearLayout mUISettings;

    protected UIType mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
    protected UIType mLastUIType = UIType.UI_TYPE_FLASHLIGHT;

    protected int mDefaultScreenBrightness;

    protected int mFinishCount = 0;

    protected SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUIFlashLight = (FrameLayout) findViewById(R.id.framelayout_flashlight);
        mUIMain = (LinearLayout) findViewById(R.id.linearLayout_main);
        mUIWarningLight = (LinearLayout) findViewById(R.id.linearLayout_warning_light);
        mUIMorse = (LinearLayout) findViewById(R.id.linearLayout_morse);
        mUIBulb = (FrameLayout) findViewById(R.id.FrameLayout_bulb);
        mUIColorLight = (FrameLayout) findViewById(R.id.frameLayout_color_light);
        mUIPoliceLight = (FrameLayout) findViewById(R.id.frameLayout_police_light);
        mUISettings = (LinearLayout) findViewById(R.id.linearLayout_settings);

        mImageViewFlashLight = (ImageView) findViewById(R.id.imageView_flashlight);
        mImageViewFlashLightController = (ImageView) findViewById(R.id.imageView_flashlight_controller);
        mImageViewWarningLight1 = (ImageView) findViewById(R.id.imageView_warning_light1);
        mImageViewWarningLight2 = (ImageView) findViewById(R.id.imageView_warning_light2);
        mEditTextMorseCode = (EditText) findViewById(R.id.editText_morse_code);
        mImageViewBulb = (ImageView) findViewById(R.id.imageView_bulb);
        mSeekBarWarningLight = (SeekBar) findViewById(R.id.seekBar_warning_light);
        mSeekBarPoliceLight = (SeekBar) findViewById(R.id.seekBar_police_light);
        mButtonAddShortcut = (Button) findViewById(R.id.button_add_shortcut);
        mButtonRemoveShortcut = (Button) findViewById(R.id.button_remove_shortcut);

        mSharedPreferences = getSharedPreferences("config", MODE_PRIVATE);

        mDefaultScreenBrightness = getScreenBrightness();


        mCurrentWarningLightInterval = mSharedPreferences.getInt("warning_light_interval", 200);
        mCurrentPoliceLightInterval = mSharedPreferences.getInt("police_light_interval", 100);
        mSeekBarWarningLight.setProgress(mCurrentWarningLightInterval - 100);
        mSeekBarPoliceLight.setProgress(mCurrentPoliceLightInterval - 50);
    }

    protected void hideAllUI() {

        mUIFlashLight.setVisibility(View.GONE);
        mUIMain.setVisibility(View.GONE);
        mUIWarningLight.setVisibility(View.GONE);
        mUIMorse.setVisibility(View.GONE);
        mUIBulb.setVisibility(View.GONE);
        mUIColorLight.setVisibility(View.GONE);
        mUIPoliceLight.setVisibility(View.GONE);
        mUISettings.setVisibility(View.GONE);
    }

    protected void screenBrightness(float value) {

        try {
            WindowManager.LayoutParams layout = getWindow().getAttributes();
            layout.screenBrightness = value;
            getWindow().setAttributes(layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected int getScreenBrightness() {
        int value = 0;
        try {
            value = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mFinishCount = 0;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void finish() {
        mFinishCount++;
        if (mFinishCount == 1) {
            Toast.makeText(this, "再按一次退出！", Toast.LENGTH_LONG).show();
        } else {
            if (mFinishCount == 2) {
                super.finish();
            }
        }
    }
}
