/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 */
package android.support.v7.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatDelegateImplV14;
import android.support.v7.app.TwilightManager;

@VisibleForTesting
final class AppCompatDelegateImplV14.AutoNightModeManager {
    private BroadcastReceiver mAutoTimeChangeReceiver;
    private IntentFilter mAutoTimeChangeReceiverFilter;
    private boolean mIsNight;
    private TwilightManager mTwilightManager;

    AppCompatDelegateImplV14.AutoNightModeManager(TwilightManager twilightManager) {
        this.mTwilightManager = twilightManager;
        this.mIsNight = twilightManager.isNight();
    }

    final void cleanup() {
        if (this.mAutoTimeChangeReceiver != null) {
            AppCompatDelegateImplV14.this.mContext.unregisterReceiver(this.mAutoTimeChangeReceiver);
            this.mAutoTimeChangeReceiver = null;
        }
    }

    final void dispatchTimeChanged() {
        boolean bl = this.mTwilightManager.isNight();
        if (bl != this.mIsNight) {
            this.mIsNight = bl;
            AppCompatDelegateImplV14.this.applyDayNight();
        }
    }

    final int getApplyableNightMode() {
        this.mIsNight = this.mTwilightManager.isNight();
        if (this.mIsNight) {
            return 2;
        }
        return 1;
    }

    final void setup() {
        this.cleanup();
        if (this.mAutoTimeChangeReceiver == null) {
            this.mAutoTimeChangeReceiver = new BroadcastReceiver(){

                public void onReceive(Context context, Intent intent) {
                    AutoNightModeManager.this.dispatchTimeChanged();
                }
            };
        }
        if (this.mAutoTimeChangeReceiverFilter == null) {
            this.mAutoTimeChangeReceiverFilter = new IntentFilter();
            this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_SET");
            this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_TICK");
        }
        AppCompatDelegateImplV14.this.mContext.registerReceiver(this.mAutoTimeChangeReceiver, this.mAutoTimeChangeReceiverFilter);
    }

}
