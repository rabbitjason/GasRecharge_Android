package com.ginkgotech.gasrecharge.model;

import android.content.Context;

import com.ginkgotech.gasrecharge.Constant;
import com.ginkgotech.gasrecharge.GasUtils;
import com.ginkgotech.gasrecharge.NetworkServer;

/**
 * Created by lipple-server on 16/11/10.
 */

public class GasSaveModel {

    private Context mContext;

    public String userCode;
    public Card card;

    public Pos pos;

    public String saveStatus;

    public String userName;
    public String userAddress;
    public String gasCount;


    public GasSaveModel(Context context) {
        this.mContext = context;
    }

    private String packageData() {
        String sendData = "";

        sendData = Constant.TRANSACTION_CODE_SAVE + "|" + card.getCardType() + "|" + userCode + "|"
                + GasUtils.bytesToHexString(card.getOldPassword()) + "|"
                + GasUtils.bytesToHexString(card.getNewpassword()) + "|"
                + GasUtils.bytesToHexString(card.getCardData()) + "|"
                +  GasUtils.getCurrentDate() + "|" + GasUtils.getCurrentTime() + "|"
                + pos.getAgentCode() + "|" + pos.getTerminateCode() + "|" + pos.getMachineCode()
                + "|" + saveStatus;

        int len = sendData.length() + 5;
        sendData = String.format("%04d", len) + "|" + sendData;
        return sendData;
    }

    public boolean ready() {
        //todo read pos config information
        pos = new Pos("200001", "0123456789", "75621477");
        //todo card = read CardData;
        saveStatus = "0";
        NetworkServer.getInstance().connect();
        return true;
    }

    public void request() {
        NetworkServer.getInstance().write(packageData());
    }

    public void onMessage(String recv) {
        String[] fields = recv.split("\\|");
        int pos = 5;
        userName = fields[pos];
        userAddress = fields[++pos];
        gasCount = fields[++pos];
    }

}
