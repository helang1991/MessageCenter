// IMyAidlCallBackInterface.aidl
package com.helang.lib;

// Declare any non-default types here with import statements


//aidl的回调接口


interface IMyAidlCallBackInterface {
    //回调Client中的接口
    void callback(String tag,String message);
}
