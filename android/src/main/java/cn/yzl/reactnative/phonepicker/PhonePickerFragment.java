package cn.yzl.reactnative.phonepicker;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.facebook.react.bridge.Callback;

public class PhonePickerFragment extends Fragment {

    private static final int PHONE_PICKER_REQUEST_CODE = 43;

    private static final int PERMISSION_REQUEST_CODE = 44;

    private Callback callBack;

    public PhonePickerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                if (callBack != null) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)) {
                        //不再询问
                        if (callBack != null) {
                            callBack.invoke(null, null, ErrorCode.PERMISSON_NOASK);
                            callBack = null;
                        }
                    } else {
                        callBack.invoke(null, null, ErrorCode.PERMISSON_FAIL);
                        callBack = null;
                    }
                }
            } else {
                startPickerReal();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PHONE_PICKER_REQUEST_CODE) {
                Uri uri = data.getData();
                if (uri != null) {
                    Cursor cursor = getActivity().getContentResolver()
                            .query(uri, null,
                                    null, null, null);
                    cursor.moveToFirst();
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor phoneCursor = getActivity().getContentResolver()
                            .query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                                    null,
                                    null);
                    phoneCursor.moveToFirst();

                    String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    if (number != null || number.isEmpty()) {
                        if (name == null) {
                            name = "";
                        }
                        number = number.replace(" ", "");
                        if (callBack != null) {
                            callBack.invoke(number, name, ErrorCode.SUCCESS);
                            callBack = null;
                        }
                    }
                }
            }
        }
    }

    public void startPicker() {
        checkPermisson();
    }

    private void startPickerReal() {
        startActivityForResult(new Intent(
                Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PHONE_PICKER_REQUEST_CODE);
    }

    private void checkPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
//                权限未授予
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CODE);
            } else {
                //权限已授予
                startPickerReal();
            }
        } else {
            startPickerReal();
        }
    }

    public void setCallBack(Callback callBack) {
        this.callBack = callBack;
    }
}