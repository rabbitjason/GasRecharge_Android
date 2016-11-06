package com.ginkgotech.gasrecharge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ginkgotech.gasrecharge.model.CardQueryModel;
import com.ginkgotech.gasrecharge.model.Customer;

import org.w3c.dom.Text;

public class CardInfoActivity extends AppCompatActivity {

    public static CardQueryModel cardQueryModel;

    private TextView tvName, tvAddress, tvGasCount, tvUserType;
    private TextView tvUnSaveGasCount, tvLastDate,tvLadderGasCount;
    private TextView tvTip, tvReturn, tvAction;
    private View viewDivide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        final Customer customer = cardQueryModel.customer;

        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(customer.getUserName());

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvAddress.setText(customer.getUserAddress());

        tvGasCount = (TextView) findViewById(R.id.tvGasCount);

        tvUserType = (TextView) findViewById(R.id.tvUserType);
        tvUserType.setText(customer.getUserType());

        tvUnSaveGasCount = (TextView) findViewById(R.id.tvUnSaveGasCount);

        tvLastDate = (TextView) findViewById(R.id.tvLastDate);
        tvLastDate.setText(customer.getLastDate());

        tvLadderGasCount = (TextView) findViewById(R.id.tvLadderGasCount);
        tvLadderGasCount.setText(String.valueOf(customer.getLadderGasCount()));

        tvTip = (TextView) findViewById(R.id.tvTip);

        tvReturn = (TextView) findViewById(R.id.tvReturn);
        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewDivide = findViewById(R.id.viewDivide);
        tvAction = (TextView) findViewById(R.id.tvAction);
        if (customer.getGasCount() != 0) {
            tvGasCount.setText(String.valueOf(customer.getGasCount()));
            tvTip.setText("卡上有气量无法购气，请将卡上气量转入表后再购气");
            tvAction.setVisibility(View.GONE);
            viewDivide.setVisibility(View.GONE);
        } else {
            if (customer.getUnSaveGasCount() == 0) {
                tvTip.setText("您没有未圈存的气量，请点击购气！");
                tvAction.setText("购气");
                tvAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (customer.getGasCount() == 0) {
                            Intent intent = new Intent(CardInfoActivity.this, GasInputActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            } else {
                tvUnSaveGasCount.setText(String.valueOf(customer.getGasCount()));
                tvTip.setText("您有未圈存的气量，请点击圈存！");
                tvAction.setText("圈存");
                tvAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (customer.getGasCount() == 0) {
                            Intent intent = new Intent(CardInfoActivity.this, GasInputActivity.class);

                        }
                    }
                });
            }

        }

    }


}
