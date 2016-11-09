package com.ginkgotech.gasrecharge;

import android.icu.text.DateFormat;
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

import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2016/10/29.
 */

public class NetworkServer {
    public static final String TAG = "NetworkServer";
    public static String SERVER_IP = "120.25.121.156";
    public static int SERVER_PORT = 10020;

    private AsyncSocket mSocket;

    private BusinessResponse mBusinessResponse;

    private byte [] recvBuffer;

    private static volatile NetworkServer instance;

    private NetworkServer() {

    }

    //定义一个共有的静态方法，返回该类型实例
    public static NetworkServer getInstance() {
        // 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
        if (instance == null) {
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (NetworkServer.class) {
                //未初始化，则初始instance变量
                if (instance == null) {
                    instance = new NetworkServer();
                }
            }
        }
        return instance;
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

        this.mSocket = socket;

        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                //System.out.println("[Client] Received Message " + new String(bb.getAllByteArray()));
                //ByteBuffer[] byteBuffers = bb.getAllArray();
                recvBuffer = bb.getAllByteArray();
                boolean hasRemaining = bb.hasRemaining();
                if (!hasRemaining) {
                    mBusinessResponse.OnDataAvailable(recvBuffer);
                }
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
