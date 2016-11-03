package com.ginkgotech.gasrecharge.model;

import android.content.Context;

import com.ginkgotech.gasrecharge.Constant;
import com.ginkgotech.gasrecharge.GasUtils;
import com.ginkgotech.gasrecharge.NetworkServer;

/**
 * Created by lipple-server on 16/10/29.
 */

public class CardQueryModel {

    public BusinessResponse mBusinessResponse;

    private Context mContext;

    private Pos pos;

    private Customer customer;

    private Card card;

    public CardQueryModel(Context context) {
        this.mContext = context;
    }

    public void setBusinessResponse(BusinessResponse businessResponse) {
        this.mBusinessResponse = businessResponse;
        NetworkServer.getInstance().setBusinessResponse(mBusinessResponse);
    }

    public void request() {

        NetworkServer.getInstance().write(packageQueryData());

    }

    private String packageQueryData() {
        String sendData = "";
        sendData = Constant.TRANSACTION_CODE_QUERY + "|" + card.getCardType() + "|";
        sendData += GasUtils.bytesToHexString(card.getCardData()) + "|";
        sendData += GasUtils.getCurrentDate() + "|" + GasUtils.getCurrentTime() + "|"
                + pos.getAgentCode() + "|" + pos.getTerminateCode() + "|" + pos.getMachineCode();

        int len = sendData.length() + 5;
        sendData = String.format("%04d", len) + "|" + sendData;
        return sendData;
    }

    public boolean ready() {
        //todo read pos config information
        pos = new Pos("200001", "0123456789", "75621477");

        //todo read card information
        card = new Card();
        card.setCardType("1");
        card.setCardData(GasUtils.hexStringToBytes("a2131091ffff8115ffffffffffffffffffff01ffffd27600000400ffffffffff7050635a005c5e00320100000010061805ef51000000000202ef0000000000000000000000000000000000000000030094ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff00ffffffffffffffffffffffff01201606e4000000000000000000000000000000000000000000000000000000000000000000000000000001000800000000099990929400ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"));

        return true;
    }

}
