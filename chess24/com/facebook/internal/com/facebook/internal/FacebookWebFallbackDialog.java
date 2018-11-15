/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.webkit.WebView
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import com.facebook.internal.BundleJSONConverter;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookWebFallbackDialog
extends WebDialog {
    private static final int OS_BACK_BUTTON_RESPONSE_TIMEOUT_MILLISECONDS = 1500;
    private static final String TAG = "com.facebook.internal.FacebookWebFallbackDialog";
    private boolean waitingForDialogToClose;

    private FacebookWebFallbackDialog(Context context, String string, String string2) {
        super(context, string);
        this.setExpectedRedirectUrl(string2);
    }

    public static FacebookWebFallbackDialog newInstance(Context context, String string, String string2) {
        WebDialog.initDefaultTheme(context);
        return new FacebookWebFallbackDialog(context, string, string2);
    }

    @Override
    public void cancel() {
        WebView webView = this.getWebView();
        if (this.isPageFinished() && !this.isListenerCalled() && webView != null && webView.isShown()) {
            if (this.waitingForDialogToClose) {
                return;
            }
            this.waitingForDialogToClose = true;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("javascript:");
            stringBuilder.append("(function() {  var event = document.createEvent('Event');  event.initEvent('fbPlatformDialogMustClose',true,true);  document.dispatchEvent(event);})();");
            webView.loadUrl(stringBuilder.toString());
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){

                @Override
                public void run() {
                    FacebookWebFallbackDialog.super.cancel();
                }
            }, 1500L);
            return;
        }
        super.cancel();
    }

    @Override
    protected Bundle parseResponseUri(String string) {
        Bundle bundle = Utility.parseUrlQueryString(Uri.parse((String)string).getQuery());
        string = bundle.getString("bridge_args");
        bundle.remove("bridge_args");
        if (!Utility.isNullOrEmpty(string)) {
            try {
                bundle.putBundle("com.facebook.platform.protocol.BRIDGE_ARGS", BundleJSONConverter.convertToBundle(new JSONObject(string)));
            }
            catch (JSONException jSONException) {
                Utility.logd(TAG, "Unable to parse bridge_args JSON", (Throwable)jSONException);
            }
        }
        String string2 = bundle.getString("method_results");
        bundle.remove("method_results");
        if (!Utility.isNullOrEmpty(string2)) {
            string = string2;
            if (Utility.isNullOrEmpty(string2)) {
                string = "{}";
            }
            try {
                bundle.putBundle("com.facebook.platform.protocol.RESULT_ARGS", BundleJSONConverter.convertToBundle(new JSONObject(string)));
            }
            catch (JSONException jSONException) {
                Utility.logd(TAG, "Unable to parse bridge_args JSON", (Throwable)jSONException);
            }
        }
        bundle.remove("version");
        bundle.putInt("com.facebook.platform.protocol.PROTOCOL_VERSION", NativeProtocol.getLatestKnownVersion());
        return bundle;
    }

}
