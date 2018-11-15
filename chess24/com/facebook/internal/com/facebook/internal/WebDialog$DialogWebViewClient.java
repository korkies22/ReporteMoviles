/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.net.http.SslError
 *  android.os.Bundle
 *  android.webkit.SslErrorHandler
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 */
package com.facebook.internal;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.facebook.FacebookDialogException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookServiceException;
import com.facebook.internal.Utility;
import com.facebook.internal.WebDialog;

private class WebDialog.DialogWebViewClient
extends WebViewClient {
    private WebDialog.DialogWebViewClient() {
    }

    public void onPageFinished(WebView webView, String string) {
        super.onPageFinished(webView, string);
        if (!WebDialog.this.isDetached) {
            WebDialog.this.spinner.dismiss();
        }
        WebDialog.this.contentFrameLayout.setBackgroundColor(0);
        WebDialog.this.webView.setVisibility(0);
        WebDialog.this.crossImageView.setVisibility(0);
        WebDialog.this.isPageFinished = true;
    }

    public void onPageStarted(WebView webView, String string, Bitmap bitmap) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Webview loading URL: ");
        stringBuilder.append(string);
        Utility.logd(WebDialog.LOG_TAG, stringBuilder.toString());
        super.onPageStarted(webView, string, bitmap);
        if (!WebDialog.this.isDetached) {
            WebDialog.this.spinner.show();
        }
    }

    public void onReceivedError(WebView webView, int n, String string, String string2) {
        super.onReceivedError(webView, n, string, string2);
        WebDialog.this.sendErrorToListener(new FacebookDialogException(string, n, string2));
    }

    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
        sslErrorHandler.cancel();
        WebDialog.this.sendErrorToListener(new FacebookDialogException(null, -11, null));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean shouldOverrideUrlLoading(WebView object, String object2) {
        Object object3;
        int n;
        Bundle bundle;
        block15 : {
            object = new StringBuilder();
            object.append("Redirect URL: ");
            object.append((String)object2);
            Utility.logd(WebDialog.LOG_TAG, object.toString());
            if (object2.startsWith(WebDialog.this.expectedRedirectUrl)) {
                bundle = WebDialog.this.parseResponseUri((String)object2);
                object2 = object = bundle.getString("error");
                if (object == null) {
                    object2 = bundle.getString("error_type");
                }
                object = object3 = bundle.getString("error_msg");
                if (object3 == null) {
                    object = bundle.getString("error_message");
                }
                object3 = object;
                if (object == null) {
                    object3 = bundle.getString("error_description");
                }
                if (!Utility.isNullOrEmpty((String)(object = bundle.getString("error_code")))) {
                    n = Integer.parseInt((String)object);
                    break block15;
                }
            } else {
                if (object2.startsWith(WebDialog.CANCEL_URI)) {
                    WebDialog.this.cancel();
                    return true;
                }
                if (object2.contains(WebDialog.DISPLAY_TOUCH)) {
                    return false;
                }
                WebDialog.this.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)object2)));
                return true;
                catch (NumberFormatException numberFormatException) {}
            }
            n = -1;
        }
        if (Utility.isNullOrEmpty((String)object2) && Utility.isNullOrEmpty((String)object3) && n == -1) {
            WebDialog.this.sendSuccessToListener(bundle);
            return true;
        }
        if (object2 != null && (object2.equals("access_denied") || object2.equals("OAuthAccessDeniedException"))) {
            WebDialog.this.cancel();
            return true;
        }
        if (n == 4201) {
            WebDialog.this.cancel();
            return true;
        }
        object = new FacebookRequestError(n, (String)object2, (String)object3);
        WebDialog.this.sendErrorToListener(new FacebookServiceException((FacebookRequestError)object, (String)object3));
        return true;
        catch (ActivityNotFoundException activityNotFoundException) {
            return false;
        }
    }
}
