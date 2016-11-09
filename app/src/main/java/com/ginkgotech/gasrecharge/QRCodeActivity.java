package com.ginkgotech.gasrecharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QRCodeActivity extends AppCompatActivity {

    public static byte []QRCodeData; //二维码图片

    private TextView tvReturn, tvAction;

    private ImageView imgQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        initView();
    }

    private void initView() {
        tvReturn = (TextView) findViewById(R.id.tvReturn);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvAction = (TextView) findViewById(R.id.tvAction);
        tvAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelControl.getInstance().pay();
                finish();
            }
        });

        imgQRCode = (ImageView) findViewById(R.id.imgQRCode);
        imgQRCode.setImageBitmap(GasUtils.byteToBitmap(QRCodeData));
        ModelControl.getInstance().pay();
    }
}
