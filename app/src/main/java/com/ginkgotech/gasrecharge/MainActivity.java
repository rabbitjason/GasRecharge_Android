package com.ginkgotech.gasrecharge;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.centerm.smartpos.aidl.memcard.AidlMemCard;
import com.centerm.smartpos.aidl.sys.AidlDeviceManager;
import com.centerm.smartpos.util.LogUtil;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private ImageButton btnQuery, btnPay, btnSave, btnHelp;
    private TextView tvContacts;

    public AidlDeviceManager manager = null;
    private AidlMemCard memCard;

    /**
     * 服务连接桥
     */
    public ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            manager = null;
            LogUtil.print("服务绑定失败");
            LogUtil.print("manager = " + manager);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            manager = AidlDeviceManager.Stub.asInterface(service);
            LogUtil.print("服务绑定成功");
            LogUtil.print("manager = " + manager);
            if (null != manager) {
                onDeviceConnected(manager);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();

        ModelControl.getInstance().init(this);

        //bindService();

    }

    public void onDeviceConnected(AidlDeviceManager deviceManager) {
//        try {
//            memCard = AidlMemCard.Stub.asInterface(deviceManager.getDevice(Constant.DEVICE_TYPE.DEVICE_TYPE_MEM4442));
//            ModelControl.getInstance().setMemCard(memCard);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    public void bindService() {
        Intent intent = new Intent();
        intent.setPackage("com.centerm.smartposservice");
        intent.setAction("com.centerm.smartpos.service.MANAGER_SERVICE");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

    }

    private void initView() {

        btnQuery = (ImageButton) findViewById(R.id.btnQuery);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelControl.getInstance().queryCardInfo();
            }
        });

        btnPay = (ImageButton) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelControl.getInstance().queryCardInfo();
            }
        });

        btnSave = (ImageButton) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelControl.getInstance().queryCardInfo();
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

        btnHelp = (ImageButton) findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OperationGuideActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unbindService(conn);
    }
}
