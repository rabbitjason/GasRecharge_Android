package com.ginkgotech.gasrecharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SaveStatusActivity extends AppCompatActivity {

    private TextView tvResult, tvTips, tvName, tvAddress, tvGasCount, tvReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_status);
    }

    private void initView() {
        tvResult = (TextView) findViewById(R.id.tvResult);

        tvTips = (TextView) findViewById(R.id.tvTips);

        tvName = (TextView) findViewById(R.id.tvName);

        tvAddress = (TextView) findViewById(R.id.tvAddress);

        tvGasCount = (TextView) findViewById(R.id.tvGasCount);

        tvReturn = (TextView) findViewById(R.id.tvReturn);

    }
}
