package com.ginkgotech.gasrecharge.model;

import android.content.Context;

import com.ginkgotech.gasrecharge.Constant;
import com.ginkgotech.gasrecharge.GasUtils;
import com.ginkgotech.gasrecharge.NetworkServer;

/**
 * Created by lipple-server on 16/11/8.
 */

public class GasPayModel {

    private Context mContext;

    public Pos pos;
    public String userCode;
    public int gasCount;
    public double gasPrice;
    public String payCode;
    public String alipayCode;
    public Card card;

    public GasPayModel(Context context) {
        this.mContext = context;
    }

    private String packageData() {
        String sendData = "";

        sendData = Constant.TRANSACTION_CODE_PAY + "|" + card.getCardType() + "|" + userCode + "|"
                + String.valueOf(gasCount) + "|" + String.valueOf(gasPrice) + "|"
                + "0" + "|" + GasUtils.bytesToHexString(card.getCardData()) + "|"
                +  GasUtils.getCurrentDate() + "|" + GasUtils.getCurrentTime() + "|"
                + pos.getAgentCode() + "|" + pos.getTerminateCode() + "|" + pos.getMachineCode()
                + "|" + payCode + "|" + alipayCode;

        int len = sendData.length() + 5;
        sendData = String.format("%04d", len) + "|" + sendData;
        return sendData;
    }

    public boolean ready() {
        //todo read pos config information
        pos = new Pos("200001", "0123456789", "75621477");
        card = new Card();
        NetworkServer.getInstance().connect();
        return true;
    }

    public void request() {
        NetworkServer.getInstance().write(packageData());
    }

    public void onMessage(String recv) {
    }
}
