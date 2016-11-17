package com.ginkgotech.gasrecharge;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PayStatusActivity extends AppCompatActivity {

    public static final String STATUS_CODE = "STATUS_CODE";

    private TextView tvReturn;

    private View llOk, llError, llTip;

    private String statusCode = "unknown";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_status);

        tvReturn = (TextView) findViewById(R.id.tvReturn);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusCode.equals("0")) {
                    ModelControl.getInstance().save();
                } else {
                    finish();
                }
            }
        });

        llOk = findViewById(R.id.llOk);

        llError = findViewById(R.id.llError);

        llTip = findViewById(R.id.llTip);

        statusCode = getIntent().getStringExtra(STATUS_CODE);
        if (statusCode.equals("0")) {
            llOk.setVisibility(View.VISIBLE);
            tvReturn.setText("圈存");
        } else if (statusCode.equals("1")) {
            llError.setVisibility(View.VISIBLE);
        } else {
            llTip.setVisibility(View.VISIBLE);
        }

        ModelControl.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ModelControl.getInstance().removeActivity(this);
    }
}
