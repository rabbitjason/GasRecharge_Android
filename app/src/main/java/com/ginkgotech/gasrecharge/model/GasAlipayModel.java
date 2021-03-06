package com.ginkgotech.gasrecharge.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ginkgotech.gasrecharge.Constant;
import com.ginkgotech.gasrecharge.GasUtils;
import com.ginkgotech.gasrecharge.NetworkServer;
import com.ginkgotech.gasrecharge.QRCodeActivity;

import java.util.Arrays;


/**
 * Created by Administrator on 2016/11/5.
 */

public class GasAlipayModel {

    public static final String TAG = "GasAlipayModel";

    private Context mContext;

    public Pos pos;

    public String cardType;
    public String userCode;
    public long gasCount;
    public double gasPrice;
    public String payCode;         //缴费流水号
    public String alipayCode;      //支付宝订单号

    public GasAlipayModel(Context context) {
        this.mContext = context;
    }

    private String packageData() {
        String sendData = "";
        sendData = Constant.TRANSACTION_CODE_ALIPAY + "|" + cardType + "|" + userCode + "|";
        sendData += String.valueOf(gasCount) + "|" + String.format("%.2f", gasPrice) + "|";
        sendData += GasUtils.getCurrentDate() + "|" + GasUtils.getCurrentTime() + "|"
                + pos.getAgentCode() + "|" + pos.getTerminateCode() + "|" + pos.getMachineCode()
                + "||";

        int len = sendData.length() + 5;
        sendData = String.format("%04d", len) + "|" + sendData;
        return sendData;
    }

    public boolean ready() {
        //todo read pos config information
        pos = new Pos("200001", "0123456789", "75621477");

        NetworkServer.getInstance().connect();

        return true;
    }

    public void request() {
        NetworkServer.getInstance().write(packageData());
    }

    public void onMessage(byte[] recv) {
        String[] fields = (new String(recv)).split("\\|");
        int pos = 0;
        int index = 0;
        String size = fields[pos];
        index = size.length() + 1;
        String retCode = fields[++pos];
        if (retCode.equals("0")) {
            index += retCode.length() + 1;
            payCode = fields[++pos];
            index += payCode.length() + 1;
            alipayCode = fields[++pos];
            index += alipayCode.length() + 1;
            QRCodeActivity.QRCodeData  = Arrays.copyOfRange(recv, index, recv.length - 1);
            Intent intent = new Intent(mContext, QRCodeActivity.class);
            mContext.startActivity(intent);
        }
    }
}
