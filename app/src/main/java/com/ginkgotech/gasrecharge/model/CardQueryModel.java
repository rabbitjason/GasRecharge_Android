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

    public Pos pos;

    public Card card;

    public Customer customer;

    public CardQueryModel(Context context) {
        this.mContext = context;
    }

    public void setBusinessResponse(BusinessResponse businessResponse) {
        this.mBusinessResponse = businessResponse;

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
        card.setCardData(GasUtils.hexStringToBytes("A2131091FFFF8115FFFFFFFFFFFFFFFFFFFF01FFFFD27600000400FFFFFFFFFF5250635A005F3E003201000000100A1305EF51000000000303EF0000000000000000000000000000000000000000030094FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00FFFFFFFFFFFFFFFFFFFFFFFF01201610D9000000000000000000000000000000000000000000000000000000000000000000000000000001000800000000099990956200FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"));

        NetworkServer.getInstance().connect();

        return true;
    }

    //73|0|先锋读卡成功|1|99909562|丁天智|青山绿水花园8-3-903|0|360|2.53|20160826|0|0026||0.72|0|
    public void onMessage(String recv) {
        String[] fields = recv.split("\\|");
        customer = new Customer();
        int pos = 3;
        card.setCardType(fields[pos]);
        customer.setUserCode(fields[++pos]);
        customer.setUserName(fields[++pos]);
        customer.setUserAddress(fields[++pos]);
        customer.setGasCount(Long.valueOf(fields[++pos]));
        customer.setLadderGasCount(Long.valueOf(fields[++pos]));
        customer.setUnitPrice(Float.valueOf(fields[++pos]));
        customer.setLastDate(fields[++pos]);
        customer.setBuyTimes(Integer.valueOf(fields[++pos]));
        customer.setUserType(fields[++pos]);
        customer.setMeterCode(fields[++pos]);
        customer.setAdvancePayment(Float.valueOf(fields[++pos]));
        customer.setUnSaveGasCount(Integer.valueOf(fields[++pos]));
    }

}
