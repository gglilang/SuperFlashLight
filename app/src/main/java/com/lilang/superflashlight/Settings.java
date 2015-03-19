package com.lilang.superflashlight;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by 朗 on 2015/3/15.
 */
public class Settings extends PoliceLight implements SeekBar.OnSeekBarChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSeekBarPoliceLight.setOnSeekBarChangeListener(this);
        mSeekBarWarningLight.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekBar_warning_light:
                mCurrentWarningLightInterval = progress + 100;

                break;
            case R.id.seekBar_police_light:
                mCurrentPoliceLightInterval = progress + 50;
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private boolean shortcutInScreen() {
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.cyanogenmod.trebuchet.settings/favorites"), null, "intent like ?", new String[]{"%component=com.lilang.superflashlight.MainActivity%"}, null);

        if(cursor.getCount() > 0){
            return true;
        }else {
            return false;
        }
    }



    public void onClick_AddShortcut(View view) {
        if(!shortcutInScreen()) {
            Intent installShortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            installShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "超级手电筒");
            Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);

            Intent flashLightIntent = new Intent();
            flashLightIntent.setClassName("com.lilang.superflashlight", "com.lilang.superflashlight.MainActivity");
            flashLightIntent.setAction(Intent.ACTION_MAIN);
            flashLightIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            installShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, flashLightIntent);

            sendBroadcast(installShortcut);
            Toast.makeText(this, "已成功将快捷方式添加到桌面", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "快捷方式已经添加到桌面上，无法再添加快捷方式", Toast.LENGTH_LONG).show();
        }
    }

    public void onClick_RemoveShortcut(View view) {
        if(shortcutInScreen()) {
            Intent uninstallShortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
            uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "超级手电筒");

            Intent flashlightIntent = new Intent();
            flashlightIntent.setClassName("com.lilang.superflashlight", "com.lilang.superflashlight.MainActivity");

            uninstallShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, flashlightIntent);

            flashlightIntent.setAction(Intent.ACTION_MAIN);
            flashlightIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            sendBroadcast(uninstallShortcut);
        }
        else{
            Toast.makeText(this, "没有快捷方式，无法删除！", Toast.LENGTH_LONG).show();
        }
    }
}
