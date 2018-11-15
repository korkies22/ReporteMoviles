/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.facebook.internal;

import android.content.Intent;
import java.util.UUID;

public class AppCall {
    private static AppCall currentPendingCall;
    private UUID callId;
    private int requestCode;
    private Intent requestIntent;

    public AppCall(int n) {
        this(n, UUID.randomUUID());
    }

    public AppCall(int n, UUID uUID) {
        this.callId = uUID;
        this.requestCode = n;
    }

    public static AppCall finishPendingCall(UUID uUID, int n) {
        synchronized (AppCall.class) {
            block6 : {
                AppCall appCall = AppCall.getCurrentPendingCall();
                if (appCall == null) break block6;
                if (!appCall.getCallId().equals(uUID)) break block6;
                if (appCall.getRequestCode() != n) {
                    break block6;
                }
                AppCall.setCurrentPendingCall(null);
                return appCall;
            }
            return null;
            finally {
            }
        }
    }

    public static AppCall getCurrentPendingCall() {
        return currentPendingCall;
    }

    private static boolean setCurrentPendingCall(AppCall appCall) {
        synchronized (AppCall.class) {
            AppCall appCall2 = AppCall.getCurrentPendingCall();
            currentPendingCall = appCall;
            boolean bl = appCall2 != null;
            return bl;
        }
    }

    public UUID getCallId() {
        return this.callId;
    }

    public int getRequestCode() {
        return this.requestCode;
    }

    public Intent getRequestIntent() {
        return this.requestIntent;
    }

    public boolean setPending() {
        return AppCall.setCurrentPendingCall(this);
    }

    public void setRequestCode(int n) {
        this.requestCode = n;
    }

    public void setRequestIntent(Intent intent) {
        this.requestIntent = intent;
    }
}
