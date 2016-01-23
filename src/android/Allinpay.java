package xyz.berial.allinpay;

import com.allinpay.appayassistex.APPayAssistEx;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class Allinpay extends CordovaPlugin {

    private String _serverMode = "01";
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("pay")) {
            final String message = args.getString(0);
            _pay(message, callbackContext);
            return true;
        } else if("setServerMode".equals(action)){
            final String message = args.getString(0);
            _setServerMode(message);
        }
        return false;
    }

    private void _setServerMode(String message) {
        if (message != null && message.length() == 2) {
            _serverMode = message;
        }
    }
    
    private void _pay(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            APPayAssistEx.startPay(cordova.getActivity(), message, _serverMode);
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}