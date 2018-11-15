/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Parcelable
 */
package com.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

private class AccessTokenTracker.CurrentAccessTokenBroadcastReceiver
extends BroadcastReceiver {
    private AccessTokenTracker.CurrentAccessTokenBroadcastReceiver() {
    }

    public void onReceive(Context object, Intent object2) {
        if ("com.facebook.sdk.ACTION_CURRENT_ACCESS_TOKEN_CHANGED".equals(object2.getAction())) {
            object = (AccessToken)object2.getParcelableExtra("com.facebook.sdk.EXTRA_OLD_ACCESS_TOKEN");
            object2 = (AccessToken)object2.getParcelableExtra("com.facebook.sdk.EXTRA_NEW_ACCESS_TOKEN");
            AccessTokenTracker.this.onCurrentAccessTokenChanged((AccessToken)object, (AccessToken)object2);
        }
    }
}
