package com.ordersapp.reactnative;

import android.content.Context;
import android.widget.Toast;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.ordersapp.MainApplication;

public class OrdersModule extends ReactContextBaseJavaModule {
    private Context context;

    public OrdersModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    @Override
    public String getName() {
        return "OrdersModule";
    }

    @ReactMethod
    public void getOrderData(Callback successCallBack) {
        try {
            successCallBack.invoke(MainApplication.getAppInstance().getOrderData());
        } catch (Exception e) {
            Toast.makeText(context, "Unable to Fetch Order Info", Toast.LENGTH_SHORT).show();
        }
    }

    @ReactMethod
    public void changeOrderStatus(int id) {
        MainApplication.getAppInstance().changeOrderStatus(id);
    }
}
