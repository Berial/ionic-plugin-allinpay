package xyz.berial.allinpay;

import android.content.Intent;

import com.allinpay.appayassistex.APPayAssistEx;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class echoes a string called from JavaScript.
 */
public class Allinpay extends CordovaPlugin {

    private String _serverMode = "01";
    private CallbackContext _callbackContext;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        cordova.setActivityResultCallback(this);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("pay")) {
            final String message = args.getString(0);
            _pay(message, callbackContext);
            return true;
        } else if ("setServerMode".equals(action)) {
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
        _callbackContext = callbackContext;
        if (message != null && message.length() > 0) {
            APPayAssistEx.startPay(cordova.getActivity(), message, _serverMode);
        } else {
            callbackContext.error(1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (APPayAssistEx.REQUESTCODE == requestCode && intent != null) {
            String payRes = null;
            try {
                JSONObject resultJson = new JSONObject(intent.getExtras().getString("result"));
                payRes = resultJson.getString(APPayAssistEx.KEY_PAY_RES);
            } catch (Exception ignored) {}
            if (payRes != null && payRes.equals(APPayAssistEx.RES_SUCCESS)) {
                if (_callbackContext != null) {
                    _callbackContext.success(88);
                }
            } else {
                if (_callbackContext != null) {
                    _callbackContext.error(0);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
}