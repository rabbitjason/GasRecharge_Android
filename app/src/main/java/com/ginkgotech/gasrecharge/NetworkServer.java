package com.ginkgotech.gasrecharge;

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

    public static String SERVER_IP = "120.25.121.156";
    public static int SERVER_PORT = 10020;

    public static AsyncSocket socket;

    public  void connect1() {
        AsyncServer.getDefault().connectSocket(SERVER_IP, SERVER_PORT, new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, AsyncSocket socket) {
                handleConnectCompleted(ex, socket);
            }
        });
    }

    private void handleConnectCompleted(Exception ex, final AsyncSocket socket) {
        if(ex != null) throw new RuntimeException(ex);

//        Util.writeAll(socket, protocol.getBytes(), new CompletedCallback() {
//            @Override
//            public void onCompleted(Exception ex) {
//                if (ex != null) throw new RuntimeException(ex);
//                System.out.println("[Client] Successfully wrote message");
//            }
//        });

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

}
