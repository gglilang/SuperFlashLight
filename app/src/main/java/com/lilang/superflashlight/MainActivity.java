package com.lilang.superflashlight;


import android.graphics.Color;
import android.view.View;

public class MainActivity extends Settings {

    public void onClick_ToFlashLight(View view) {
        hideAllUI();
        mUIFlashLight.setVisibility(View.VISIBLE);
        mLastUIType = UIType.UI_TYPE_FLASHLIGHT;
        mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
    }

    public void onClick_ToWarningLight(View view) {

        hideAllUI();
        mUIWarningLight.setVisibility(View.VISIBLE);
        mLastUIType = UIType.UI_TYPE_WARNINGLIGHT;
        mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
        screenBrightness(1f);
        new WarningLightThread().start();
    }

    public void onClick_ToMorse(View view) {
        hideAllUI();
        mUIMorse.setVisibility(View.VISIBLE);
        mLastUIType = UIType.UI_TYPE_MORSE;
        mCurrentUIType = UIType.UI_TYPE_MORSE;
    }

    public void onClick_ToBulb(View view) {
        hideAllUI();
        mUIBulb.setVisibility(View.VISIBLE);
        mHideTextViewBulb.hide();
        mHideTextViewBulb.setTextColor(Color.BLACK);
        mLastUIType = UIType.UI_TYPE_BLUB;
        mCurrentUIType = UIType.UI_TYPE_BLUB;
    }

    public void onClick_ToColor(View view) {
        hideAllUI();
        mUIColorLight.setVisibility(View.VISIBLE);
        screenBrightness(1f);
        mLastUIType = UIType.UI_TYPE_COLOR;
        mCurrentUIType = UIType.UI_TYPE_COLOR;

        mHideTextViewColorLight.setTextColor(Color.rgb(Color.red(255 - mCurrentColorLight), Color.green(255 - mCurrentColorLight), Color.blue(255 - mCurrentColorLight)));
    }

    public void onClick_ToPoliceLight(View view) {
        hideAllUI();
        mUIPoliceLight.setVisibility(View.VISIBLE);
        screenBrightness(1f);
        mLastUIType = UIType.UI_TYPE_POLICE;
        mCurrentUIType = UIType.UI_TYPE_POLICE;
        mHideTextViewPoliceLight.hide();
        new PoliceThread().start();
    }

    public void onClick_ToSettings(View view) {
        hideAllUI();
        mUISettings.setVisibility(View.VISIBLE);
        mLastUIType = UIType.UI_TYPE_SETTINGS;
        mCurrentUIType = UIType.UI_TYPE_SETTINGS;
    }

    public void onClick_Controller(View v) {
        hideAllUI();
        if (mCurrentUIType != UIType.UI_TYPE_MAIN) {
            mUIMain.setVisibility(View.VISIBLE);
            mCurrentUIType = UIType.UI_TYPE_MAIN;
            mWarningLightFlicker = false;
            screenBrightness(mDefaultScreenBrightness / 255f);
            if (mBulbCrossFadeFlag) {
                mDrawable.reverseTransition(0);
            }
            mBulbCrossFadeFlag = false;
            mPoliceState = false;

            mSharedPreferences.edit().putInt("warning_light_interval", mCurrentWarningLightInterval).putInt("police_light_interval", mCurrentPoliceLightInterval).commit();

        } else {
            switch (mLastUIType) {
                case UI_TYPE_FLASHLIGHT:
                    mUIFlashLight.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_FLASHLIGHT;
                    break;
                case UI_TYPE_WARNINGLIGHT:
                    mUIWarningLight.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_WARNINGLIGHT;
                    screenBrightness(1f);
                    new WarningLightThread().start();
                    break;
                case UI_TYPE_MORSE:
                    mUIMorse.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_MORSE;
                    break;
                case UI_TYPE_BLUB:
                    mUIBulb.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_BLUB;
                    break;
                case UI_TYPE_POLICE:
                    mUIPoliceLight.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_POLICE;
                    new PoliceThread().start();
                    break;
                case UI_TYPE_SETTINGS:
                    mUISettings.setVisibility(View.VISIBLE);
                    mCurrentUIType = UIType.UI_TYPE_SETTINGS;
                    break;
            }
        }
    }
}
