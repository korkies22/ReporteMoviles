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
import com.facebook.CustomTabMainActivity;

public class CustomTabActivity
extends Activity {
    public static final String CUSTOM_TAB_REDIRECT_ACTION;
    private static final int CUSTOM_TAB_REDIRECT_REQUEST_CODE = 2;
    public static final String DESTROY_ACTION;
    private BroadcastReceiver closeReceiver;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CustomTabActivity.class.getSimpleName());
        stringBuilder.append(".action_customTabRedirect");
        CUSTOM_TAB_REDIRECT_ACTION = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(CustomTabActivity.class.getSimpleName());
        stringBuilder.append(".action_destroy");
        DESTROY_ACTION = stringBuilder.toString();
    }

    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n2 == 0) {
            intent = new Intent(CUSTOM_TAB_REDIRECT_ACTION);
            intent.putExtra(CustomTabMainActivity.EXTRA_URL, this.getIntent().getDataString());
            LocalBroadcastManager.getInstance((Context)this).sendBroadcast(intent);
            this.closeReceiver = new BroadcastReceiver(){

                public void onReceive(Context context, Intent intent) {
                    CustomTabActivity.this.finish();
                }
            };
            LocalBroadcastManager.getInstance((Context)this).registerReceiver(this.closeReceiver, new IntentFilter(DESTROY_ACTION));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bundle = new Intent((Context)this, CustomTabMainActivity.class);
        bundle.setAction(CUSTOM_TAB_REDIRECT_ACTION);
        bundle.putExtra(CustomTabMainActivity.EXTRA_URL, this.getIntent().getDataString());
        bundle.addFlags(603979776);
        this.startActivityForResult((Intent)bundle, 2);
    }

    protected void onDestroy() {
        LocalBroadcastManager.getInstance((Context)this).unregisterReceiver(this.closeReceiver);
        super.onDestroy();
    }

}
