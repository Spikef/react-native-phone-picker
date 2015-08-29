'use strict';

var React = require('react-native');
var { NativeModules } = React;
var RNPhonePicker = NativeModules.RNPhonePicker;

var PhonePicker = {
    select: function(callback) {
        RNPhonePicker.selectPhoneNumber(callback)
    }
};

module.exports = PhonePicker;
