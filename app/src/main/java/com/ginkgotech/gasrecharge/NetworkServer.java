package com.ginkgotech.gasrecharge;

import android.util.Log;

import com.ginkgotech.gasrecharge.model.BusinessResponse;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback;

/**
 * Created by Administrator on 2016/10/29.
 */

public class NetworkServer {
    public static final String TAG = "NetworkServer";
    public static String SERVER_IP = "120.25.121.156";
    public static int SERVER_PORT = 10020;

    private AsyncSocket mSocket;

    private BusinessResponse mBusinessResponse;

    public NetworkServer() {

    }

    public void setBusinessResponse(BusinessResponse businessResponse) {
        this.mBusinessResponse = businessResponse;
    }

    public void close() {
        mSocket.close();
    }
    public void connect() {
        AsyncServer.getDefault().connectSocket(SERVER_IP, SERVER_PORT, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, AsyncSocket socket) {
                handleConnectCompleted(ex, socket);

            }
        });
    }

    public void write(String sendData) {
        if (mSocket == null) {
            Log.v(TAG, "[Client] write failed! socket is null.");
            return;
        }

        Util.writeAll(mSocket, sendData.getBytes(), new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                mBusinessResponse.OnWriteCompleted(ex);
//                if (ex != null) throw new RuntimeException(ex);
//                System.out.println("[Client] Successfully wrote message");
            }
        });
    }

    private void handleConnectCompleted(Exception ex, final AsyncSocket socket) {
//        if(ex != null) throw new RuntimeException(ex);
        if (ex != null) {
            mBusinessResponse.OnConnectCompleted(ex);
            return;
        }

//        Util.writeAll(socket, protocol.getBytes(), new CompletedCallback() {
//            @Override
//            public void onCompleted(Exception ex) {
//                if (ex != null) throw new RuntimeException(ex);
//                System.out.println("[Client] Successfully wrote message");
//            }
//        });

        this.mSocket = socket;

        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                //System.out.println("[Client] Received Message " + new String(bb.getAllByteArray()));
                mBusinessResponse.OnDataAvailable(new String(bb.getAllByteArray()));
            }
        });

        socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                mBusinessResponse.OnCloseCompleted(ex);
                //if(ex != null) throw new RuntimeException(ex);
                //System.out.println("[Client] Successfully closed connection");
            }
        });

        socket.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                mBusinessResponse.OnEndCompleted(ex);
                //if(ex != null) throw new RuntimeException(ex);
                //System.out.println("[Client] Successfully end connection");
            }
        });

        mBusinessResponse.OnConnectCompleted(ex);
    }

}
