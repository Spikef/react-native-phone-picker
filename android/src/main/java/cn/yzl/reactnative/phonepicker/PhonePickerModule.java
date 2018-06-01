package cn.yzl.reactnative.phonepicker;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by YZL on 2018/6/1.
 */

public class PhonePickerModule extends ReactContextBaseJavaModule {

    public static final String TAG = "RNPhonePicker";


    public PhonePickerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return TAG;
    }

    /**
     * @param callback(error,info{name,phone})
     */
    @ReactMethod
    public void selectPhoneNumber(final Callback callback) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null || currentActivity.isDestroyed() || currentActivity.isFinishing()) {
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                checkPermisson(callback);
            }
        });
    }

    private void checkPermisson(final Callback callback) {
        RxPermissions rxPermissions = new RxPermissions(getCurrentActivity());
        rxPermissions.request(android.Manifest.permission.READ_CONTACTS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            startPicker(callback);
                        } else {
                            callback.invoke(null, null, "permission error");
                        }
                    }
                });


    }

    private void startPicker(Callback callback) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null || currentActivity.isDestroyed() || currentActivity.isFinishing()) {
            return;
        }
        PhonePicker phonePicker = new PhonePicker(getCurrentActivity());
        phonePicker.setCallback(callback);
        phonePicker.startPicker();
//        getCurrentActivity().startActivityForResult(new Intent(
//                Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
    }
}
