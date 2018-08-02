package com.helang.app1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.helang.lib.IMyAidlCallBackInterface;
import com.helang.lib.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button bt_send;
    private TextView text;

    private IMyAidlInterface iMyAidlInterface;
    private ServiceCallBack serviceCallBack;

    private MyServiceConnection myServiceConnection;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_send = findViewById(R.id.bt_send);
        editText = findViewById(R.id.editText);
        text = findViewById(R.id.text);

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iMyAidlInterface != null){
                    try {
                        iMyAidlInterface.sendMessage("app1",editText.getText().toString().trim());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        bindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService();
    }

    private void bindService(){
        myServiceConnection = new MyServiceConnection();
        serviceCallBack = new ServiceCallBack();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.helang.messagecenterdemo",
                "com.helang.messagecenterdemo.MyService"));
        startService(intent);//开启远程服务,8.0之后不允许直接开启后台远程后台服务，需要绑定前台进程，我这里就简单了事
        bindService(intent,myServiceConnection,BIND_AUTO_CREATE);//绑定服务

    }

    private void unbindService(){
        if (myServiceConnection != null){
            try {
                iMyAidlInterface.unregisterListener(serviceCallBack);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            unbindService(myServiceConnection);
        }
    }

    /**
     * 连接Service
     */
    class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    //注册回调
                    if (iMyAidlInterface != null){
                        try {
                            iMyAidlInterface.registerListener(serviceCallBack);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }

    /**
     * service回到client的类
     */
    class ServiceCallBack extends IMyAidlCallBackInterface.Stub{

        @Override
        public void callback(final String tag, final String message) throws RemoteException {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.append("tag="+tag+"  message="+message+"\n");
                }
            });
        }
    }
}
