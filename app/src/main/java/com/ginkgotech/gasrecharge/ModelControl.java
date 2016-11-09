package com.ginkgotech.gasrecharge;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ginkgotech.gasrecharge.model.BusinessResponse;
import com.ginkgotech.gasrecharge.model.CardQueryModel;
import com.ginkgotech.gasrecharge.model.GasAlipayModel;
import com.ginkgotech.gasrecharge.model.GasPayModel;

/**
 * Created by lipple-server on 16/11/7.
 */

public class ModelControl implements BusinessResponse {
    public static final String TAG = "ModelControl";

    public static final int ACTION_QUERY = 101;
    public static final int ACTION_ALIPAY = 102;
    public static final int ACTION_PAY = 103;
    public static final int ACTION_SAVE =  104;

    public static final int MSG_RESPONSE_FAILED = 200;
    public static final int MSG_RESPONSE_SUCCESSFULLY = 201;
    public static final int MSG_NET_CONNECT_FAILED = 202;

    private int currentAction = 0;

    private CardQueryModel cardQueryModel;
    private GasAlipayModel gasAlipayModel;
    private GasPayModel gasPayModel;

    private Context mContext;

    private byte [] recvBuf = new byte[4096];

    private Handler msgHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (MSG_RESPONSE_FAILED == msg.what) {
                String responseDesc = (String)msg.obj;
                GasUtils.showMessageDialog(mContext, "信息", responseDesc);
            } else if (MSG_RESPONSE_SUCCESSFULLY == msg.what) {
                byte[] recv = (byte[])msg.obj;
                onHandleMessage(recv);
            } else if (MSG_NET_CONNECT_FAILED == msg.what) {
                GasUtils.showMessageDialog(mContext, "信息","网络连接失败！");
            }
        }
    };

    private static volatile ModelControl instance;

    private ModelControl() {

    }

    //定义一个共有的静态方法，返回该类型实例
    public static ModelControl getInstance() {
        // 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
        if (instance == null) {
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (NetworkServer.class) {
                //未初始化，则初始instance变量
                if (instance == null) {
                    instance = new ModelControl();
                }
            }
        }
        return instance;
    }

    private void onHandleMessage(byte [] recv) {
        if (ACTION_QUERY == currentAction) {
            cardQueryModel.onMessage(new String(recv));
            CardInfoActivity.cardQueryModel = cardQueryModel;
            Intent intent = new Intent(mContext, CardInfoActivity.class);
            mContext.startActivity(intent);
        } else if (ACTION_ALIPAY == currentAction) {
            gasAlipayModel.onMessage(recv);
            QRCodeActivity.QRCodeData = gasAlipayModel.QRCodeData;
            Intent intent = new Intent(mContext, QRCodeActivity.class);
            mContext.startActivity(intent);
        }
    }

    public void init(Context context) {
        this.mContext = context;

        NetworkServer.getInstance().setBusinessResponse(this);

        cardQueryModel = new CardQueryModel(mContext);

        gasAlipayModel = new GasAlipayModel(mContext);

        gasPayModel = new GasPayModel(mContext);

    }

    public void queryCardInfo() {
        currentAction = ACTION_QUERY;
        cardQueryModel.ready();

    }

    public void aliPay(int gasCount, double gasPrice) {
        currentAction = ACTION_ALIPAY;
        gasAlipayModel.userCode = cardQueryModel.customer.getUserCode();
        gasAlipayModel.pos = cardQueryModel.pos;
        gasAlipayModel.cardType = cardQueryModel.card.getCardType();
        gasAlipayModel.gasCount = gasCount;
        gasAlipayModel.gasPrice = gasPrice;
        gasAlipayModel.ready();
    }

    public void pay() {
        currentAction = ACTION_PAY;
        gasPayModel.userCode = gasAlipayModel.userCode;
        gasPayModel.gasCount = gasAlipayModel.gasCount;
        gasPayModel.gasPrice = gasAlipayModel.gasPrice;
        gasPayModel.payCode = gasAlipayModel.payCode;
        gasPayModel.alipayCode = gasAlipayModel.alipayCode;

        gasPayModel.ready();
    }

    @Override
    public void OnWriteCompleted(Exception ex) {
        if (ex != null) {
            Log.v(TAG, "write failed!");
        } else {
            Log.v(TAG, "write successfully!");
        }
    }

    @Override
    public void OnDataAvailable(byte [] recv) {

        String[] fields = (new String(recv)).split("\\|");
        String responseCode = fields[1];
        Message msg = new Message();
        if (!responseCode.equals("0")) {
            msg.what = MSG_RESPONSE_FAILED;
            msg.obj = fields[2];
            return;
        } else {
            msg.what = MSG_RESPONSE_SUCCESSFULLY;
            msg.obj = recv;
        }
        msgHandler.sendMessage(msg);
    }

    @Override
    public void OnCloseCompleted(Exception ex) {

        if (ex != null) {
            Log.v(TAG, "close failed!");
        } else {
            Log.v(TAG, "close successfully!");
        }
    }

    @Override
    public void OnEndCompleted(Exception ex) {

        if (ex != null) {
            Log.v(TAG, "end failed!");
        } else {
            Log.v(TAG, "end successfully!");
        }
    }

    @Override
    public void OnConnectCompleted(Exception ex) {

        if (ex == null) {
            switch (currentAction) {
                case ACTION_QUERY:
                    cardQueryModel.request();
                    break;
                case ACTION_ALIPAY:
                    gasAlipayModel.request();
                    break;
                default:
                    break;
            }
        } else {
            msgHandler.sendEmptyMessage(MSG_NET_CONNECT_FAILED);
            Log.v(TAG, "[Client] Failed connection!");
        }
    }
}
