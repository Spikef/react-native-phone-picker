# react-native-phone-picker

# 我的扩展

## 扩展Android 实现

- 动态申请权限
- 在Android的CallBack中,是3个参数

```js
//电话号码,名字,error:("permission error") 权限被拒绝,目前就这一个错误提示
callback(phone,name,error){

}
```


## link-Android
- android/settings.gradle
```
include ':react-native-phone-picker'
project(':react-native-phone-picker').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-phone-picker/android/')
```
- android/app/build.gradle
```

android{
    defaultConfig {
        // 方法数超限
        multiDexEnabled true
    }
}

dependencies {
...
//添加
    compile project(':react-native-phone-picker')
}
```

- MainApplication.java

```java
//添加
import android.support.multidex.MultiDexApplication;
//注意,方法数可能超限,
//将Application 改为 MultiDexApplication
public class MainApplication extends MultiDexApplication implements ReactApplication {


    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MyReactPackage(),
                    //添加
                    new PhonePickerPackage()
            );
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }
    };
}

```

## 问题

由于时间有限,所以没有优化,采用了几个第三方库,是以`compile`存在的
增加了太多的方法,以后我可能会再优化一下
- rxJava 2
- rxAndroid
- rxPermisson

```gradle
    compile 'io.reactivex.rxjava2:rxjava:2.1.3'
    compile 'com.tbruyelle.rxpermissions2:rxpermissions:0.9.4@aar'
    compile 'io.reactivex.rxjava2:rxandroid:2.0.2'
```

# 原README

React Native component for select phone number from address book

用于React Native的通讯录手机号选取模块

![image](https://github.com/Spikef/react-native-phone-picker/raw/master/screenshots.gif)

## Usage

```javascript
npm install react-native-phone-picker --save
```

In XCode, in the project navigator, right click Libraries ➜ Add Files to [your project's name], Go to node_modules ➜ react-native-phone-picker and add the `RNPhonePicker.xcodeproj` file

In XCode, in the project navigator, select your project. Add the lib*.a from the RNPhonePicker project to your project's Build Phases ➜ Link Binary With Libraries Click `RNPhonePicker.xcodeproj` file you added before in the project navigator and go the Build Settings tab. Make sure 'All' is toggled on (instead of 'Basic'). Look for Header Search Paths and make sure it contains  both $(SRCROOT)/../react-native/React and $(SRCROOT)/../../React - mark both as recursive.

Run your project (Cmd+R)

## Examples

```javascript

var PhonePicker = require('react-native-phone-picker');
PhonePicker.select(function(phone) {
    if (phone) {
        phone = phone.replace(/[^\d]/g, '');
        if (/^1[3|4|5|6|7|8|9][0-9]\d{8}$/.test(phone)) {
            console.log(phone);
        }
    }
})
```

