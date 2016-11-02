package com.ginkgotech.gasrecharge.model;

import com.ginkgotech.gasrecharge.GasUtils;
import com.ginkgotech.gasrecharge.NetworkServer;

/**
 * Created by lipple-server on 16/10/29.
 */

public class CardQueryModel extends BaseModel {

    public BusinessResponse mBusinessResponse;

    private String cardType;
    private byte []cardData = new byte[512];
    private String date;
    private String time;
    private String agentCode;
    private String terminateCode;
    private String machineCode;

    private String rawData="";

    private NetworkServer networkServer;

    public CardQueryModel() {
        len = 0;
        code = "2001";
        cardType = "1";
        cardData = GasUtils.hexStringToBytes("a2131091ffff8115ffffffffffffffffffff01ffffd27600000400ffffffffff7050635a005c5e00320100000010061805ef51000000000202ef0000000000000000000000000000000000000000030094ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff00ffffffffffffffffffffffff01201606e4000000000000000000000000000000000000000000000000000000000000000000000000000001000800000000099990929400ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
        date = GasUtils.getCurrentDate();
        time = GasUtils.getCurrentTime();
        agentCode = "200001";
        terminateCode = "0123456789";
        machineCode = "75621477";
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getRawData() {
        return this.rawData;
    }


    public void setBusinessResponse(BusinessResponse businessResponse) {
        this.mBusinessResponse = businessResponse;
    }

    public void request() {

    }

    @Override
    public String packageData() {
        String sendData = "";
        sendData = code + "|" + cardType + "|";
        sendData += GasUtils.bytesToHexString(getCardData()) + "|";
        sendData += GasUtils.getCurrentDate() + "|" + GasUtils.getCurrentTime() + "|" + agentCode + "|" + terminateCode + "|" + machineCode;
        int len = sendData.length() + 5;
        sendData = String.format("%04d", len) + "|" + sendData;
        return sendData;
    }

    @Override
    public void parseData(String recvData) {
        String[] fields = recvData.split("|");

    }

    @Override
    public void OnWriteCompleted(Exception ex) {

    }

    @Override
    public void OnDataAvailable(String recv) {

    }

    @Override
    public void OnCloseCompleted(Exception ex) {

    }

    @Override
    public void OnEndCompleted(Exception ex) {

    }

    @Override
    public void OnConnectCompleted(Exception ex) {

    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public byte[] getCardData() {
        return cardData;
    }

    public void setCardData(byte[] cardData) {
        this.cardData = cardData;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getTerminateCode() {
        return terminateCode;
    }

    public void setTerminateCode(String terminateCode) {
        this.terminateCode = terminateCode;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }
}
