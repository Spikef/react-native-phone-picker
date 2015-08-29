# react-native-phone-picker

React Native component for select phone number from address book

用于React Native的通讯录手机号选取模块

![image](https://github.com/Spikef/react-native-phone-picker/raw/master/screenshots.gif)

## Usage

```javascript
npm install react-native-phone-picker --save
```

In XCode, in the project navigator, right click Libraries ➜ Add Files to [your project's name], Go to node_modules ➜ react-native-clipboard and add the `RNPhonePicker.xcodeproj` file

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

