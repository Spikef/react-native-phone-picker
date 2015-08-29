'use strict';

var RNPhonePicker = require('NativeModules').RNPhonePicker;

var PhonePicker = {
    select: function(callback) {
        RNPhonePicker.selectPhoneNumber(callback)
    }
};

module.exports = PhonePicker;
