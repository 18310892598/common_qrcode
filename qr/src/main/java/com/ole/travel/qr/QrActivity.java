package com.ole.travel.qr;

import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.ole.travel.qr.zxing.OnScannerCompletionListener;
import com.ole.travel.qr.zxing.ScannerView;

public class QrActivity extends AppCompatActivity implements OnScannerCompletionListener, View.OnClickListener {

    private String TAG = "ScannerActivity";

    TextView mFlashLightTv;
    ScannerView mScannerView;
    private boolean mLightState;

    //光线传感器相关
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private LightSensorListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initData();
        initView();
    }

    private void initData() {
        //光线传感器使用注册
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


//        TextView mFlashLightTv;
//        TextView mSearchCarTv;
//        ScannerView mScannerView;
//        private boolean mLightState;


        mFlashLightTv = findViewById(R.id.flash_light_tv);
        mFlashLightTv.setOnClickListener(this);
        mScannerView = findViewById(R.id.scanner_view);

        mScannerView.setLaserFrameSize(200, 200)
                .setLaserLineResId(R.drawable.sweep_line_light_green)//线图
                .toggleLight(false)
                .setScanMode(BarcodeFormat.QR_CODE)
                .setDrawText("扫描识别", 16, 0x000000, true, 30)
                .setLaserFrameTopMargin(100);
        mScannerView.setOnScannerCompletionListener(this);
        mScannerView.setLaserColor(0xff00ff00);
        mScannerView.setLaserFrameBoundColor(0xff00ff00);
        mScannerView.setLaserFrameColor(0xff00ff00);
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
        Log.i(TAG, "result:" + result);
        if (!TextUtils.isEmpty(result)) {
            mScannerView.onPause();
            mHandler.sendEmptyMessageDelayed(0, 1000);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "识别失败", Toast.LENGTH_SHORT).show();
            mScannerView.onPause();
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }
        Log.e(TAG, "handleData: " + result);
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
        mFlashLightTv.setText(isLight ? "关闭" : "打开");
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mScannerView.onResume();
                    initData();
                    break;

                case 1:
                    unInitData();
                    break;
            }
        }
    };


    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.flash_light_tv) {
            handleFlashLight(!mLightState);
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
            Log.i(TAG, "lux:" + lux + "    retval:" + retval);
            if (retval > 0) {
                handleFlashLight(false);
            } else {
                handleFlashLight(true);
            }
        }
    }
}