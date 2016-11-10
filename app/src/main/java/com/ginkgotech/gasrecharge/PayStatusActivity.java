package com.ginkgotech.gasrecharge;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PayStatusActivity extends AppCompatActivity {

    private TextView tvReturn;

    private TimeCount time;
    //默认为10秒
    private static int duration = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_status);

        tvReturn = (TextView) findViewById(R.id.tvReturn);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time.cancel();
    }

    private void startTimeCount() {
        time = new PayStatusActivity.TimeCount(duration, 1000);
        time.start();
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            time.cancel();
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }
    }
}
