package cn.yzl.reactnative.phonepicker;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

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
     * @param callback(name,phone,errorCode)
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
                startPicker(callback);
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
