package com.lilang.superflashlight;

import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;

import java.io.IOException;

/**
 * Created by 朗 on 2015/3/12.
 */
public class FlashLight extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageViewFlashLight.setTag(false);
        Point point = new Point();

        getWindowManager().getDefaultDisplay().getSize(point);

        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) mImageViewFlashLightController.getLayoutParams();
        layoutParams.height = point.y * 3/4;
        layoutParams.width = point.x / 3;

        mImageViewFlashLightController.setLayoutParams(layoutParams);
    }

    public void onClick_FlashLight(View view){

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Toast.makeText(this, "当前设备没有闪光灯", Toast.LENGTH_LONG).show();
            return;
        }

        if(((Boolean)mImageViewFlashLight.getTag()) == false){
            openFlashLight();
        }else{
            closeFlashLight();
        }
    }

    //打开闪光灯
    protected void openFlashLight(){

        TransitionDrawable drawable = (TransitionDrawable) mImageViewFlashLight.getDrawable();
        drawable.startTransition(200);
        mImageViewFlashLight.setTag(true);

        try{
            camera = Camera.open();
            int textureId = 0;
            camera.setPreviewTexture(new SurfaceTexture(textureId));
            camera.startPreview();

            parameters = camera.getParameters();
            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭闪光灯
    protected void closeFlashLight(){
        TransitionDrawable drawable = (TransitionDrawable)mImageViewFlashLight.getDrawable();
        if(((Boolean)mImageViewFlashLight.getTag())){

            drawable.reverseTransition(200);
            mImageViewFlashLight.setTag(false);

            if(camera != null){

                parameters = camera.getParameters();
                parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
                camera.setParameters(parameters);
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeFlashLight();
    }
}
