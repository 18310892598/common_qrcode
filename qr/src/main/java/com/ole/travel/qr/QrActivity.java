package com.ole.travel.qr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.ole.travel.qr.zxing.OnScannerCompletionListener;
import com.ole.travel.qr.zxing.ScannerView;
import com.ole.travel.qr.zxing.common.Scanner;

import java.lang.ref.WeakReference;

public class QrActivity extends AppCompatActivity implements OnScannerCompletionListener, View.OnClickListener {


    private ImageView ivBack;
    TextView mFlashLightTv;
    ScannerView mScannerView;
    private boolean mLightState;

    //光线传感器相关
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private LightSensorListener mListener;
    private UiHandle mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestPermission();
        setContentView(R.layout.activity_scanner);
        initData();
        initView();
    }

    private void initData() {
        //光线传感器使用注册
        mHandler = new UiHandle(this);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mListener = new LightSensorListener();
        mSensorManager.registerListener(mListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unInitData() {
        if (null != mListener) {
            mSensorManager.unregisterListener(mListener);
            mSensorManager = null;
            mSensor = null;
            mListener = null;
        }
    }

    private void initView() {


        ivBack = findViewById(R.id.back);
        ivBack.setOnClickListener(this);
        mFlashLightTv = findViewById(R.id.flash_light_tv);
        mFlashLightTv.setOnClickListener(this);
        mScannerView = findViewById(R.id.scanner_view);

        mScannerView
                .setLaserFrameSize(256, 256)
                .setLaserFrameCornerLength(20)
                .setLaserFrameCornerWidth(2)
                .setLaserLineResId(R.drawable.sweep_line_light_green)//线图
                .toggleLight(false)
                .setScanMode(BarcodeFormat.QR_CODE)
                .setDrawText("将二维码/条形码放入框内，即可自动描", 14, 0xFFB1D0FF, true, 16)
                .setLaserFrameTopMargin(100);
        mScannerView.setOnScannerCompletionListener(this);
        mScannerView.setLaserColor(0x00B1D0FF);
        mScannerView.setLaserFrameBoundColor(0xffB1D0FF);
        mScannerView.setLaserFrameColor(0x80ffffff);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mFlashLightTv.getLayoutParams();
        layoutParams.topMargin = Scanner.dp2px(this, 424);
        mFlashLightTv.setLayoutParams(layoutParams);
//        la


//        mScannerView.setLaserFrameBoundColor(getResources().getColor(R.color.frame_bound));
//        mScannerView.setLaserFrameColor(getResources().getColor(R.color.frame_bound));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.onResume();

    }

    @Override
    public void OnScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        vibrate();
        handleData(rawResult);
    }

    private void handleData(Result rawResult) {
        String result = rawResult.getText();
        if (!TextUtils.isEmpty(result)) {
            mScannerView.onPause();
//            mHandler.sendEmptyMessageDelayed(0, 1000);
            Intent intent = new Intent();
            intent.putExtra(Constants.KEY_QR_RESULT, result);
            setResult(RESULT_OK, intent);
            this.finish();
        } else {
            Toast.makeText(this, "识别失败", Toast.LENGTH_SHORT).show();
            mScannerView.onPause();
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    private void vibrate() {
//        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        vibrator.vibrate(200);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.onPause();
    }

    private void handleFlashLight(boolean isLight) {
        mLightState = isLight;
        mScannerView.toggleLight(isLight);
        // 释放光传感器
        mHandler.sendEmptyMessage(1);

        mFlashLightTv.setSelected(isLight);
        mFlashLightTv.setText(isLight ? "轻触关灯" : "轻触开灯");
    }

    void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0x007);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0x007 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "相机权限被拒绝", Toast.LENGTH_SHORT).show();
            this.finish();
        }

    }


    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.flash_light_tv) {
            handleFlashLight(!mLightState);
        } else if (i == R.id.back) {
            this.finish();
        }
    }

    public class LightSensorListener implements SensorEventListener {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        //获取光线的强度
        @Override
        public void onSensorChanged(SensorEvent event) {
            //获取精度
            float acc = event.accuracy;
            //获取光线强度
            float lux = event.values[0];
            int retval = Float.compare(lux, (float) 10.0);
            if (retval > 0) {
                handleFlashLight(false);
            } else {
                handleFlashLight(true);
            }
        }
    }


    public static class UiHandle extends Handler {
        WeakReference<QrActivity> weakRf;

        public UiHandle(QrActivity qrActivity) {
            this.weakRf = new WeakReference<>(qrActivity);

        }

        @Override
        public void handleMessage(@NonNull Message msg) {

            if (null == weakRf && null == weakRf.get()) {
                return;
            }
            switch (msg.what) {
                case 0:
                    weakRf.get().mScannerView.onResume();
                    weakRf.get().initData();
                    break;

                case 1:
                    weakRf.get().unInitData();
                    break;
                default:
                    break;
            }
        }
    }
}