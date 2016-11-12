package com.ginkgotech.gasrecharge.model;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/2.
 */

public class Pos {

    private String agentCode;
    private String terminateCode;
    private String machineCode;

    public Pos() {

    }

    public Pos(String agentCode, String terminateCode, String machineCode) {
        this.agentCode = agentCode;
        this.terminateCode = terminateCode;
        this.machineCode = getSerialNumber();
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

    public static String getSerialNumber(){
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }
}
