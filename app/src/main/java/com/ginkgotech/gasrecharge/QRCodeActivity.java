package com.ginkgotech.gasrecharge;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QRCodeActivity extends AppCompatActivity {

    public static byte []QRCodeData; //二维码图片

    private TextView tvReturn, tvAction;

    private ImageView imgQRCode;

    private boolean isSkip = false;

    //默认为180秒
    private static int duration = 180000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        initView();
        ModelControl.getInstance().addActivity(this);
    }

    private void initView() {
        tvReturn = (TextView) findViewById(R.id.tvReturn);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSkip = true;
                finish();
            }
        });

        tvAction = (TextView) findViewById(R.id.tvAction);

        imgQRCode = (ImageView) findViewById(R.id.imgQRCode);
        imgQRCode.setImageBitmap(GasUtils.byteToBitmap(QRCodeData));
        autoClosed();
        ModelControl.getInstance().pay();
    }

    private void autoClosed() {
        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {
                if (isSkip) {
                    return;
                }
                QRCodeActivity.this.finish();
                ModelControl.getInstance().showPayStatus();
            }
        }, duration);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isSkip = true;
        ModelControl.getInstance().removeActivity(this);
    }
}
