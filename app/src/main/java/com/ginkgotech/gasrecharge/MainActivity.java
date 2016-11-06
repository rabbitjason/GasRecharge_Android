package com.ginkgotech.gasrecharge;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ginkgotech.gasrecharge.model.BusinessResponse;
import com.ginkgotech.gasrecharge.model.CardQueryModel;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback;


public class MainActivity extends AppCompatActivity implements BusinessResponse {
    public static final String TAG = "MainActivity";

    public static final int ACTION_QUERY = 101;
    public static final int ACTION_SAVE =  102;

    public static final int MSG_RESPONSE_FAILED = 200;
    public static final int MSG_RESPONSE_SUCCESSFULLY = 201;

    private String protocol = "0567|2001|1|a2131091ffff8115ffffffffffffffffffff01ffffd27600000400ffffffffff7050635a005c5e00320100000010061805ef51000000000202ef0000000000000000000000000000000000000000030094ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff00ffffffffffffffffffffffff01201606e4000000000000000000000000000000000000000000000000000000000000000000000000000001000800000000099990929400ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff|20160905|171025|200001|0123456789|75621477";

    private CardQueryModel cardQueryModel;

    private ImageButton btnQuery, btnPay;
    private TextView tvContacts;

    private int action = 0;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (MSG_RESPONSE_FAILED == msg.what) {
                String responseDesc = (String)msg.obj;
                GasUtils.showMessageDialog(MainActivity.this,"信息",responseDesc);
            } else if (MSG_RESPONSE_SUCCESSFULLY == msg.what) {
                String recv = (String)msg.obj;
                onHandleMessage(recv);
            }
        }
    };

    private void onHandleMessage(String recv) {
        if (ACTION_QUERY == action) {
            cardQueryModel.onMessage(recv);
            CardInfoActivity.cardQueryModel = cardQueryModel;
            Intent intent = new Intent(MainActivity.this, CardInfoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cardQueryModel = new CardQueryModel(this);
        cardQueryModel.setBusinessResponse(this);

        initView();

    }

    private void initView() {

        btnQuery = (ImageButton) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardQueryModel.ready();
                action = ACTION_QUERY;
            }
        });

        btnPay = (ImageButton) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GasInputActivity.class);
                startActivity(intent);
            }
        });

        tvContacts = (TextView) findViewById(R.id.tvContacts);
        tvContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });
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
    public void OnDataAvailable(String recv) {
        String[] fields = recv.split("\\|");
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
        handler.sendMessage(msg);

    }

    @Override
    public void OnCloseCompleted(Exception ex) {
        if (ex != null) {
            Log.v(TAG, "[Client] Failed closed connection");
        } else {
            Log.v(TAG, "[Client] Successfully closed connection");
        }
    }

    @Override
    public void OnEndCompleted(Exception ex) {
        if (ex != null) {
            Log.v(TAG, "[Client] Failed end connection");
        } else {
            Log.v(TAG, "[Client] Successfully end connection!");
        }
    }

    @Override
    public void OnConnectCompleted(Exception ex) {
        if (ex == null) {
            switch (action) {
                case ACTION_QUERY:
                    cardQueryModel.request();
                    break;
                default:
                    break;
            }
        } else {
            GasUtils.showMessageDialog(MainActivity.this, "信息","网络连接失败！");
            Log.v(TAG, "[Client] Failed connection!");
        }

    }
}
