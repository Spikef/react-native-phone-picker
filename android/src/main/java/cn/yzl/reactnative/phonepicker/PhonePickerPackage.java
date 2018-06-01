package cn.yzl.reactnative.phonepicker;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by YZL on 2018/6/1.
 */

public class PhonePickerPackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(new PhonePickerModule(reactContext));
    }

//    @Override
//    public List<Class<? extends JavaScriptModule>> createJSModules() {
//        return Collections.emptyList();
//    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
