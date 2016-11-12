package com.ginkgotech.gasrecharge.model;

import android.graphics.Color;
import android.os.RemoteException;
import android.view.View;

import com.centerm.smartpos.aidl.memcard.AidlMemCard;
import com.centerm.smartpos.util.HexUtil;

/**
 * Created by lipple-server on 16/11/12.
 */

public class Card4442  {

    private AidlMemCard memCard;

    public Card4442() {

    }

    public void init(AidlMemCard memCard) {
        this.memCard = memCard;
    }

    // 复位
    public void Reset() {
        boolean flag = false;
        try {
            flag = memCard.reset();
            if (flag) {
                //showMessage("复位成功", Color.BLACK);
            } else {
                //showMessage("复位失败", Color.RED);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            //howMessage("复位异常" + e.getLocalizedMessage(), Color.RED);
        }
    }
    // 打开
    public void Open() {
        try {
            memCard.open();
            //showMessage("打开MEM4442设备成功", Color.BLACK);
        } catch (RemoteException e) {
            e.printStackTrace();
            //showMessage("打开MEM4442设备失败" + e.getLocalizedMessage(), Color.RED);
        }
    }
    // 关闭
    public void Close() {
        try {
            memCard.close();
            //showMessage("关闭MEM4442设备成功", Color.BLACK);
        } catch (RemoteException e) {
            e.printStackTrace();
            //showMessage("关闭MEM4442设备失败" + e.getLocalizedMessage(), Color.RED);
        }
    }
    // 读操作
    public String Read() {
        byte[] readValue = null;
        String message = null;
        try {
            readValue = memCard.read((byte) 0x00, (byte) 0xFF);
            if (null != readValue) {
                message = HexUtil.bytesToHexString(readValue);
                //showMessage("读操作结果" + HexUtil.bytesToHexString(readValue), Color.BLACK);
            } else {
                //showMessage("读操作返回null", Color.RED);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            //showMessage("读操作异常" + e.getLocalizedMessage(), Color.RED);
        }
        return message;
    }

    // 检测卡是否在位
    public boolean GetStatus() {
        byte status = 0x00;
        try {
            status = memCard.status();
            if (status == 0x01)
            {
                return true;
            }

        } catch (RemoteException e) {
            e.printStackTrace();
            //showMessage("检测卡是否在位异常" + e.getLocalizedMessage(), Color.RED);
        }
        return false;
    }
}
