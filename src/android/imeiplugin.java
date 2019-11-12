package it.thop;

import android.Manifest;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PermissionHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.telephony.TelephonyManager;
import android.content.Context;
import android.util.Log;

public class imeiplugin extends CordovaPlugin {
    private CallbackContext mCallbackContext;

	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        mCallbackContext = callbackContext;
        if (action.equals("getImei")) {
            if(PermissionHelper.hasPermission(this, Manifest.permission.READ_PHONE_STATE)) {
                this.DeviceImeiNumber(callbackContext);
            } else {
                PermissionHelper.requestPermission(this, 0, Manifest.permission.READ_PHONE_STATE);
            }
            return true;
        }
        return false;
    }

    public void DeviceImeiNumber(CallbackContext callbackContext) {
        Context context = this.cordova.getActivity().getApplicationContext();
        TelephonyManager tManager = (TelephonyManager)cordova.getActivity().getSystemService(context.TELEPHONY_SERVICE);
        callbackContext.success(tManager.getDeviceId());
    }

    private void getImei(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions,
                                          int[] grantResults) throws JSONException
    {

        if(requestCode == 0) {
            this.DeviceImeiNumber(mCallbackContext);
        }
    } 
} 
