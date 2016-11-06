package com.ginkgotech.gasrecharge.model;

/**
 * Created by Administrator on 2016/11/2.
 */

public class Customer {

    private String userCode;
    private String userName;
    private String userAddress;
    private long gasCount;
    private long ladderGasCount;
    private float unitPrice;
    private String lastDate;
    private int buyTimes;
    private String userType;
    private String meterCode;
    private float advancePayment;
    private int unSaveGasCount;

    public Customer() {

    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public long getGasCount() {
        return gasCount;
    }

    public void setGasCount(long gasCount) {
        this.gasCount = gasCount;
    }

    public long getLadderGasCount() {
        return ladderGasCount;
    }

    public void setLadderGasCount(long ladderGasCount) {
        this.ladderGasCount = ladderGasCount;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public int getBuyTimes() {
        return buyTimes;
    }

    public void setBuyTimes(int buyTimes) {
        this.buyTimes = buyTimes;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public float getAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(float advancePayment) {
        this.advancePayment = advancePayment;
    }

    public int getUnSaveGasCount() {
        return unSaveGasCount;
    }

    public void setUnSaveGasCount(int unSaveGasCount) {
        this.unSaveGasCount = unSaveGasCount;
    }
}
