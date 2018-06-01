package cn.yzl.reactnative.phonepicker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.facebook.react.bridge.Callback;

import java.util.LinkedHashMap;
import java.util.Map;

public class PhonePickerFragment extends Fragment {

    private static final int PHONE_PICKER_REQUEST_CODE = 43;

    private Callback callBack;

    public PhonePickerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
                            callBack.invoke(number, name, null);
                            callBack = null;
                        }
                    }
                }
            }
        }
    }

    public void startPicker() {
        startActivityForResult(new Intent(
                Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), PHONE_PICKER_REQUEST_CODE);
    }

    public void setCallBack(Callback callBack) {
        this.callBack = callBack;
    }
}