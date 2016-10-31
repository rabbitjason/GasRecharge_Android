package com.ginkgotech.gasrecharge;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    public static final int MSG_UPDATE_UI = 1;
    private String protocol = "0567|2001|1|a2131091ffff8115ffffffffffffffffffff01ffffd27600000400ffffffffff7050635a005c5e00320100000010061805ef51000000000202ef0000000000000000000000000000000000000000030094ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff00ffffffffffffffffffffffff01201606e4000000000000000000000000000000000000000000000000000000000000000000000000000001000800000000099990929400ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff|20160905|171025|200001|0123456789|75621477";

    private CardQueryModel cardQueryModel;

    private NetworkServer networkServer;

    private Button btnCareQuery;

    private TextView tvCareInfo;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (MSG_UPDATE_UI == msg.what) {
                tvCareInfo.setText(cardQueryModel.getRawData());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCareQuery = (Button) findViewById(R.id.btnCardQuery);
        btnCareQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCareInfo.setText("Click Care Query!");
                networkServer.connect();
            }
        });

        tvCareInfo = (TextView) findViewById(R.id.tvCardInfo);

        networkServer = new NetworkServer();
        networkServer.setBusinessResponse(this);

        cardQueryModel = new CardQueryModel();
    }



    private void connectServer() {
        AsyncServer.getDefault().connectSocket("120.25.121.156", 10020, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, AsyncSocket socket) {
                handleConnectCompleted(ex, socket);
            }
        });
    }

    private void handleConnectCompleted(Exception ex, final AsyncSocket socket) {
        if(ex != null) throw new RuntimeException(ex);

        Util.writeAll(socket, protocol.getBytes(), new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) throw new RuntimeException(ex);
                System.out.println("[Client] Successfully wrote message");
            }
        });

        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                System.out.println("[Client] Received Message " + new String(bb.getAllByteArray()));
            }
        });

        socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if(ex != null) throw new RuntimeException(ex);
                System.out.println("[Client] Successfully closed connection");
            }
        });

        socket.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if(ex != null) throw new RuntimeException(ex);
                System.out.println("[Client] Successfully end connection");
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
        cardQueryModel.setRawData(recv);
        //tvCareInfo.setText(recv);
        handler.sendEmptyMessage(MSG_UPDATE_UI);
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
            networkServer.write(protocol);
        } else {
            Log.v(TAG, "[Client] Failed connection!");
        }

    }
}
