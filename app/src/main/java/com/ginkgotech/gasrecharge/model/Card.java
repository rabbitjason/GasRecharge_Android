package com.ginkgotech.gasrecharge.model;

/**
 * Created by Administrator on 2016/11/2.
 */

public class Card {
    private String cardType;
    private byte []cardData = new byte[512];
    private byte []oldPassword = new byte[6];
    private byte []newpassword = new byte[6];

    public Card() {

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

    public byte[] getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(byte[] oldPassword) {
        this.oldPassword = oldPassword;
    }

    public byte[] getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(byte[] newpassword) {
        this.newpassword = newpassword;
    }
}
