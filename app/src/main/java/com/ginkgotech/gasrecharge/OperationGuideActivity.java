package com.ginkgotech.gasrecharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class OperationGuideActivity extends AppCompatActivity {

    private ImageView imgOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_guide);

        imgOption = (ImageView) findViewById(R.id.imgOption);

        imgOption.setImageBitmap(GasUtils.loadBitmap(getResources(), R.mipmap.operation, 560, 5600));
        imgOption.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
