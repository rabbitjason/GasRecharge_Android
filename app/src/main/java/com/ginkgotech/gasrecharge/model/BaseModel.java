package com.ginkgotech.gasrecharge.model;

/**
 * Created by lipple-server on 16/10/29.
 */

public abstract class BaseModel implements BusinessResponse {

    public int len;

    public String code;

    public abstract String packageData();

    public abstract void parseData(String data);

}
