package com.ginkgotech.gasrecharge.model;

/**
 * Created by Administrator on 2016/11/2.
 */

public class Card {
    private String cardType;
    private byte []cardData = new byte[512];
    private byte []oldPassword = new byte[6];
    private byte []newpassword = new byte[6];

}
