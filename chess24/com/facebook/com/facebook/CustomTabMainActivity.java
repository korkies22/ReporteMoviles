/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 */
package com.facebook;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.CustomTabActivity;
import com.facebook.FacebookSdk;
import com.facebook.internal.CustomTab;

public class CustomTabMainActivity
extends Activity {
    public static final String EXTRA_CHROME_PACKAGE;
    public static final String EXTRA_PARAMS;
    public static final String EXTRA_URL;
    private static final String OAUTH_DIALOG = "oauth";
    public static final String REFRESH_ACTION;
    private BroadcastReceiver redirectReceiver;
    private boolean shouldCloseCustomTab = true;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CustomTabMainActivity.class.getSimpleName());
        stringBuilder.append(".extra_params");
        EXTRA_PARAMS = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(CustomTabMainActivity.class.getSimpleName());
        stringBuilder.append(".extra_chromePackage");
        EXTRA_CHROME_PACKAGE = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(CustomTabMainActivity.class.getSimpleName());
        stringBuilder.append(".extra_url");
        EXTRA_URL = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(CustomTabMainActivity.class.getSimpleName());
        stringBuilder.append(".action_refresh");
        REFRESH_ACTION = stringBuilder.toString();
    }

    public static final String getRedirectUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fb");
        stringBuilder.append(FacebookSdk.getApplicationId());
        stringBuilder.append("://authorize");
        return stringBuilder.toString();
    }

    private void sendResult(int n, Intent intent) {
        LocalBroadcastManager.getInstance((Context)this).unregisterReceiver(this.redirectReceiver);
        if (intent != null) {
            this.setResult(n, intent);
        } else {
            this.setResult(n);
        }
        this.finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (CustomTabActivity.CUSTOM_TAB_REDIRECT_ACTION.equals(this.getIntent().getAction())) {
            this.setResult(0);
            this.finish();
            return;
        }
        if (bundle == null) {
            bundle = this.getIntent().getBundleExtra(EXTRA_PARAMS);
            String string = this.getIntent().getStringExtra(EXTRA_CHROME_PACKAGE);
            new CustomTab(OAUTH_DIALOG, bundle).openCustomTab(this, string);
            this.shouldCloseCustomTab = false;
            this.redirectReceiver = new BroadcastReceiver(){

                public void onReceive(Context context, Intent intent) {
                    context = new Intent((Context)CustomTabMainActivity.this, CustomTabMainActivity.class);
                    context.setAction(CustomTabMainActivity.REFRESH_ACTION);
                    context.putExtra(CustomTabMainActivity.EXTRA_URL, intent.getStringExtra(CustomTabMainActivity.EXTRA_URL));
                    context.addFlags(603979776);
                    CustomTabMainActivity.this.startActivity((Intent)context);
                }
            };
            LocalBroadcastManager.getInstance((Context)this).registerReceiver(this.redirectReceiver, new IntentFilter(CustomTabActivity.CUSTOM_TAB_REDIRECT_ACTION));
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (REFRESH_ACTION.equals(intent.getAction())) {
            Intent intent2 = new Intent(CustomTabActivity.DESTROY_ACTION);
            LocalBroadcastManager.getInstance((Context)this).sendBroadcast(intent2);
            this.sendResult(-1, intent);
            return;
        }
        if (CustomTabActivity.CUSTOM_TAB_REDIRECT_ACTION.equals(intent.getAction())) {
            this.sendResult(-1, intent);
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.shouldCloseCustomTab) {
            this.sendResult(0, null);
        }
        this.shouldCloseCustomTab = true;
    }

}
