package com.ginkgotech.gasrecharge.model;

/**
 * Created by lipple-server on 16/10/29.
 */

public class CardQueryModel extends BaseModel {

    public BusinessResponse mBusinessResponse;

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

    public void query() {

    }
}
