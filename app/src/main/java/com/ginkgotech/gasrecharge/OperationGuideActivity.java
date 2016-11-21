package com.ginkgotech.gasrecharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OperationGuideActivity extends AppCompatActivity {

    private ImageView imgOption;

    private TextView tvReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_guide);

        imgOption = (ImageView) findViewById(R.id.imgOption);

        imgOption.setImageBitmap(GasUtils.loadBitmap(getResources(), R.mipmap.operation, 650, 30));
        imgOption.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        tvReturn = (TextView) findViewById(R.id.tvReturn);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
