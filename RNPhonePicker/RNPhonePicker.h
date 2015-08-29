//
//  RNPhonePicker.h
//  RNPhonePicker
//
//  Created by Spikef on 15/8/28.
//  Copyright (c) 2015年 envirs.com . All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RCTBridgeModule.h"

@interface RNPhonePicker : NSObject <RCTBridgeModule, ABPeoplePickerNavigationControllerDelegate, UINavigationControllerDelegate>

@property (nonatomic, strong) RCTResponseSenderBlock callback;

@end