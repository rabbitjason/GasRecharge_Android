package com.ginkgotech.gasrecharge.model;

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

    private String rawData;

    public CardQueryModel() {
        len = 0;
        code = "2001";
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
    @Override
    public String packageData() {
        return null;
    }

    @Override
    public void parseData(String data) {

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
