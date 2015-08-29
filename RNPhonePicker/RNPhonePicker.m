//
//  RNPhonePicker.m
//  RNPhonePicker
//
//  Created by Spikef on 15/8/28.
//  Copyright (c) 2015年 envirs.com . All rights reserved.
//

#import <AddressBook/AddressBook.h>
#import <AddressBookUI/AddressBookUI.h>
#import <UIKit/UIKit.h>
#import "RNPhonePicker.h"

@implementation RNPhonePicker

RCT_EXPORT_MODULE();

// 接口方法，显示通讯录选择
RCT_EXPORT_METHOD(selectPhoneNumber:(RCTResponseSenderBlock) callback)
{
    self.callback = callback;       // save the callback
    
    ABPeoplePickerNavigationController *picker;
    picker = [[ABPeoplePickerNavigationController alloc] init];
    picker.peoplePickerDelegate = self;
    
    if([[UIDevice currentDevice].systemVersion floatValue] >= 8.0){
        picker.predicateForSelectionOfPerson = [NSPredicate predicateWithValue:false];
    }
    
    UIViewController *vc = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
    [vc presentViewController:picker animated:YES completion:nil];
}

// 进入详情页 iOS7
- (BOOL)peoplePickerNavigationController: (ABPeoplePickerNavigationController *)picker
      shouldContinueAfterSelectingPerson:(ABRecordRef)person NS_DEPRECATED_IOS(2_0, 8_0){
    return YES;
}

// 进入详情页 iOS8
- (void)peoplePickerNavigationController:(ABPeoplePickerNavigationController*)picker didSelectPerson:(ABRecordRef)person NS_AVAILABLE_IOS(8_0) {
    
    ABPersonViewController *personViewController = [[ABPersonViewController alloc] init];
    personViewController.displayedPerson = person;
    
    [picker pushViewController:personViewController animated:YES];
};


// 选择号码
- (BOOL)peoplePickerNavigationController: (ABPeoplePickerNavigationController *)picker
      shouldContinueAfterSelectingPerson:(ABRecordRef)person
                                property:(ABPropertyID)property
                              identifier:(ABMultiValueIdentifier)identifier
NS_DEPRECATED_IOS(2_0, 8_0)

{
    NSLog(@"asfdf");
    if (property == kABPersonPhoneProperty) {
        ABMutableMultiValueRef phoneMulti = ABRecordCopyValue(person, property);
        long index = ABMultiValueGetIndexForIdentifier(phoneMulti,identifier);
        NSString *phone = (NSString*)CFBridgingRelease(ABMultiValueCopyValueAtIndex(phoneMulti, index));
        
        self.callback(@[phone]);
        
        UIViewController *vc = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
        [vc dismissViewControllerAnimated:YES completion:nil];
    }
    
    return NO;    // 禁止打电话等其它操作
}

// 选择号码
- (void)peoplePickerNavigationController:(ABPeoplePickerNavigationController*)picker didSelectPerson:(ABRecordRef)person
                                property:(ABPropertyID)property
                              identifier:(ABMultiValueIdentifier)identifier
NS_AVAILABLE_IOS(8_0)
{
    if (property == kABPersonPhoneProperty) {
        ABMutableMultiValueRef phoneMulti = ABRecordCopyValue(person, property);
        long index = ABMultiValueGetIndexForIdentifier(phoneMulti,identifier);
        NSString *phone = (NSString*)CFBridgingRelease(ABMultiValueCopyValueAtIndex(phoneMulti, index));
        
        self.callback(@[phone]);
        
        UIViewController *vc = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
        [vc dismissViewControllerAnimated:YES completion:nil];
    }
}

// 点击取消按钮
- (void)peoplePickerNavigationControllerDidCancel:(ABPeoplePickerNavigationController *)picker

{
    UIViewController *vc = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
    [vc dismissViewControllerAnimated:YES completion:nil];
}

@end
