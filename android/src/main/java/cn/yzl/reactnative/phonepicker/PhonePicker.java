/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.yzl.reactnative.phonepicker;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.annotation.NonNull;

import com.facebook.react.bridge.Callback;

public class PhonePicker {

    static final String TAG = "REACT_NATIVE_PHONE_PICKER";

    PhonePickerFragment phonePickerFragment;

    public PhonePicker(@NonNull Activity activity) {
        phonePickerFragment = getRxPermissionsFragment(activity);
    }

    private PhonePickerFragment getRxPermissionsFragment(Activity activity) {
        PhonePickerFragment phonePickerFragment = findRxPermissionsFragment(activity);
        boolean isNewInstance = phonePickerFragment == null;
        if (isNewInstance) {
            phonePickerFragment = new PhonePickerFragment();
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(phonePickerFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return phonePickerFragment;
    }

    private PhonePickerFragment findRxPermissionsFragment(Activity activity) {
        return (PhonePickerFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }

    public void setCallback(Callback callback) {
        if (phonePickerFragment != null) {
            this.phonePickerFragment.setCallBack(callback);
        }
    }

    public void startPicker() {
        if (phonePickerFragment == null
                || phonePickerFragment.isDetached()) {
            return;
        }
        phonePickerFragment.startPicker();
    }
}