package com.ginkgotech.gasrecharge;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.ginkgotech.gasrecharge.model.GasSaveModel;

public class SaveStatusActivity extends AppCompatActivity {

    public static GasSaveModel gasSaveModel;

    private TextView tvResult, tvTips, tvName, tvAddress, tvGasCount, tvReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_status);

        initView();
    }

    private void initView() {
        tvResult = (TextView) findViewById(R.id.tvResult);

        tvTips = (TextView) findViewById(R.id.tvTips);

        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(gasSaveModel.userName);

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvAddress.setText(gasSaveModel.userAddress);

        tvGasCount = (TextView) findViewById(R.id.tvGasCount);
        tvGasCount.setText(gasSaveModel.gasCount);

        tvReturn = (TextView) findViewById(R.id.tvReturn);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelControl.getInstance().finishAll();
                finish();
            }
        });

        if (!gasSaveModel.responseCode.equals("0")) {
            tvResult.setText("对不起，圈存失败");
            tvResult.setTextColor(Color.RED);
            tvTips.setText("圈存失败，请返回重新选择[用户圈存]\n如有问题，联系客服！");
            tvReturn.setText("返回");
        }

    }
}
