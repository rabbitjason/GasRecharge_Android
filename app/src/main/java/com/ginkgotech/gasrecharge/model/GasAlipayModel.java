package com.ginkgotech.gasrecharge.model;

import android.content.Context;

import com.ginkgotech.gasrecharge.Constant;
import com.ginkgotech.gasrecharge.GasUtils;

/**
 * Created by Administrator on 2016/11/5.
 */

public class GasAlipayModel {

    private Context mContext;

    public Pos pos;

    public String cardType;
    public String userCode;
    public int gasCount;
    public float gasPrice;
    public String payCode;         //缴费流水号
    public String alipayCode;      //支付宝订单号
    public byte []QRCodeData = new byte[4096]; //二维码图片

    public GasAlipayModel(Context context) {
        this.mContext = context;
    }


    private String packageQueryData() {
        String sendData = "";
        sendData = Constant.TRANSACTION_CODE_ALIPAY + "|" + cardType + "|";
        sendData += String.valueOf(gasCount) + "|" + String.valueOf(gasPrice) + "|";
        sendData += GasUtils.getCurrentDate() + "|" + GasUtils.getCurrentTime() + "|"
                + pos.getAgentCode() + "|" + pos.getTerminateCode() + "|" + pos.getMachineCode();

        return sendData;
    }
}
