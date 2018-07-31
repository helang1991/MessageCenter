package com.helang.messagecenterdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.helang.lib.IMyAidlCallBackInterface;
import com.helang.lib.IMyAidlInterface;

/**
 * 消息服务中心(记得在 manifest.xml 加上 android:exported="true")
 */
public class MyService extends Service {
    private final static String TAG = MyService.class.getSimpleName();
    private RemoteCallbackList<IMyAidlCallBackInterface> callbackList = new RemoteCallbackList<>();//回调的关键（API>=17,才能使用）

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    /**
     * 实现iBinder
     */
    private IMyAidlInterface.Stub iBinder = new IMyAidlInterface.Stub() {
        @Override
        public void sendMessage(String tag, String message) throws RemoteException {
            callbackList.beginBroadcast();
            sendMessageToAllClient(tag,message);
            Log.d(TAG,"tag="+tag+"  message="+message);
            callbackList.finishBroadcast();
        }

        @Override
        public void registerListener(IMyAidlCallBackInterface listener) throws RemoteException {
            callbackList.register(listener);//注册回调listener
            Log.d(TAG,"registerListener");
        }

        @Override
        public void unregisterListener(IMyAidlCallBackInterface listener) throws RemoteException {
            callbackList.unregister(listener);//取消回调listener
            Log.d(TAG,"unregisterListener");
        }
    };

    /**
     * 发送消息给全部的client（你也可以指定发送给某个client,也可
     * 以根据自己的业务来封装一下Bean，记得要实现Parcelable接口来序列化
     * @param tag
     * @param message
     */
    private void sendMessageToAllClient(String tag,String message){
       for (int i = 0 ; i < callbackList.getRegisteredCallbackCount();i++){
           try {
               callbackList.getBroadcastItem(i).callback(tag,message);
           } catch (RemoteException e) {
               e.printStackTrace();
           }
       }
    }
}
