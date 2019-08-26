package com.ole.travel.testqrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ole.travel.qr.Constants;
import com.ole.travel.qr.QrActivity;


public class MainActivity extends AppCompatActivity {

    Button btScan;
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.hello);
        btScan=findViewById(R.id.bt);
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, QrActivity.class), 0x2b);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0x2b && resultCode == RESULT_OK) {
            tv.setText(data.getStringExtra(Constants.KEY_QR_RESULT));
        }
    }
}
