package com.ginkgotech.gasrecharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ginkgotech.gasrecharge.model.PriceTable;

import org.w3c.dom.Text;

public class GasInputActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCount, tvPrice, tvReturn, tvAction;
    private StringBuffer strCount;

    private View btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight;
    private View btnNine, btnZero, btnDel;

    private double gasPrice = 0.0;
    private int gasCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_input);

        PriceTable.getInstance().initTable();

        strCount = new StringBuffer();

        tvCount = (TextView) findViewById(R.id.tvCount);
        tvPrice = (TextView) findViewById(R.id.tvPrice);

        btnOne = findViewById(R.id.btnOne);
        btnOne.setOnClickListener(this);

        btnTwo = findViewById(R.id.btnTwo);
        btnTwo.setOnClickListener(this);

        btnThree = findViewById(R.id.btnThree);
        btnThree.setOnClickListener(this);

        btnFour = findViewById(R.id.btnFour);
        btnFour.setOnClickListener(this);

        btnFive = findViewById(R.id.btnFive);
        btnFive.setOnClickListener(this);

        btnSix = findViewById(R.id.btnSix);
        btnSix.setOnClickListener(this);

        btnSeven = findViewById(R.id.btnSeven);
        btnSeven.setOnClickListener(this);

        btnEight = findViewById(R.id.btnEight);
        btnEight.setOnClickListener(this);

        btnNine = findViewById(R.id.btnNine);
        btnNine.setOnClickListener(this);

        btnZero = findViewById(R.id.btnZero);
        btnZero.setOnClickListener(this);

        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

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
                if (gasCount > 0) {
                    ModelControl.getInstance().aliPay(gasCount, gasPrice);
                }
            }
        });

    }

    private void updatePrice(String count) {
        if (!TextUtils.isEmpty(count)) {
            gasCount = Integer.valueOf(count);
            gasPrice = PriceTable.getInstance().getPrice("0026", 360, gasCount);
            tvPrice.setText(String.format("应付金额: %.2f (元)", gasPrice));
        } else {
            tvPrice.setText("");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOne:
                strCount.append("1");
                tvCount.setText(strCount.toString().trim());
                break;
            case R.id.btnTwo:
                strCount.append("2");
                tvCount.setText(strCount.toString().trim());
                break;

            case R.id.btnThree:
                strCount.append("3");
                tvCount.setText(strCount.toString().trim());
                break;

            case R.id.btnFour:
                strCount.append("4");
                tvCount.setText(strCount.toString().trim());
                break;

            case R.id.btnFive:
                strCount.append("5");
                tvCount.setText(strCount.toString().trim());
                break;

            case R.id.btnSix:
                strCount.append("6");
                tvCount.setText(strCount.toString().trim());
                break;

            case R.id.btnSeven:
                strCount.append("7");
                tvCount.setText(strCount.toString().trim());
                break;

            case R.id.btnEight:
                strCount.append("8");
                tvCount.setText(strCount.toString().trim());
                break;

            case R.id.btnNine:
                strCount.append("9");
                tvCount.setText(strCount.toString().trim());
                break;

            case R.id.btnZero:
                strCount.append("0");
                tvCount.setText(strCount.toString().trim());
                break;

            case R.id.btnDel:
                if (strCount.length() - 1 >= 0) {
                    strCount.delete(strCount.length() - 1, strCount.length());
                    tvCount.setText(strCount.toString().trim());
                }
                break;

            default:
                break;

        }
        updatePrice(tvCount.getText().toString());
    }
}
