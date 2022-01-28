package com.ordersapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.facebook.react.BuildConfig;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;
import com.ordersapp.db.OrdersDatabase;
import com.ordersapp.di.AppComponent;
import com.ordersapp.di.DaggerAppComponent;
import com.ordersapp.di.NetworkModule;
import com.ordersapp.reactnative.OrdersPackage;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainApplication extends Application implements ReactApplication {
    private String orderObj;
    private static MainApplication instance;

    public static MainApplication getAppInstance() {
        return instance;
    }

    public MutableLiveData<Boolean> getIsOrderUpdated() {
        return isOrderUpdated;
    }

    MutableLiveData<Boolean> isOrderUpdated = new MutableLiveData<>();


    private final ReactNativeHost mReactNativeHost =
            new ReactNativeHost(this) {
                @Override
                public boolean getUseDeveloperSupport() {
                    return BuildConfig.DEBUG;
                }

                @Override
                protected List<ReactPackage> getPackages() {
                    @SuppressWarnings("UnnecessaryLocalVariable")
                    List<ReactPackage> packages = new PackageList(this).getPackages();
                    // Packages that cannot be autolinked yet can be added manually here, for example:
                    // packages.add(new MyReactNativePackage());
                    packages.add(new OrdersPackage());
                    return packages;
                }

                @Override
                protected String getJSMainModuleName() {
                    return "index";
                }
            };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    AppComponent appComponent;
    String baseUrl = "https://jsonplaceholder.typicode.com";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SoLoader.init(this, /* native exopackage */ false);
        initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
        appComponent = DaggerAppComponent.builder().networkModule(new NetworkModule(baseUrl)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    /**
     * Loads Flipper in React Native templates. Call this in the onCreate method with something like
     * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
     *
     * @param context
     * @param reactInstanceManager
     */
    private static void initializeFlipper(
            Context context, ReactInstanceManager reactInstanceManager) {
        if (BuildConfig.DEBUG) {
            try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
                Class<?> aClass = Class.forName("com.ordersapp.ReactNativeFlipper");
                aClass
                        .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
                        .invoke(null, context, reactInstanceManager);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public String getOrderData() {
        return orderObj;
    }

    public void setOrderData(String data) {
        this.orderObj = data;
    }

    public void changeOrderStatus(int id) {
        OrdersDatabase.getDatabase(this).wordDao().updateOder(id, true).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((integer, throwable) -> {
            Log.e("integer", "" + integer);
            isOrderUpdated.postValue(true);
        });
    }
}
