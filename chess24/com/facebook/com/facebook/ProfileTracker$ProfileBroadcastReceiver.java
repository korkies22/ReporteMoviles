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
import com.facebook.Profile;
import com.facebook.ProfileTracker;

private class ProfileTracker.ProfileBroadcastReceiver
extends BroadcastReceiver {
    private ProfileTracker.ProfileBroadcastReceiver() {
    }

    public void onReceive(Context object, Intent object2) {
        if ("com.facebook.sdk.ACTION_CURRENT_PROFILE_CHANGED".equals(object2.getAction())) {
            object = (Profile)object2.getParcelableExtra("com.facebook.sdk.EXTRA_OLD_PROFILE");
            object2 = (Profile)object2.getParcelableExtra("com.facebook.sdk.EXTRA_NEW_PROFILE");
            ProfileTracker.this.onCurrentProfileChanged((Profile)object, (Profile)object2);
        }
    }
}
