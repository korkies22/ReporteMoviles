/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$OnFinished
 *  android.content.IntentSender$SendIntentException
 *  android.os.Handler
 */
package android.support.v4.content.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Handler;

static final class ShortcutManagerCompat
extends BroadcastReceiver {
    final /* synthetic */ IntentSender val$callback;

    ShortcutManagerCompat(IntentSender intentSender) {
        this.val$callback = intentSender;
    }

    public void onReceive(Context context, Intent intent) {
        try {
            this.val$callback.sendIntent(context, 0, null, null, null);
            return;
        }
        catch (IntentSender.SendIntentException sendIntentException) {
            return;
        }
    }
}
