/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package android.support.v4.content;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import java.util.ArrayList;

private static final class LocalBroadcastManager.BroadcastRecord {
    final Intent intent;
    final ArrayList<LocalBroadcastManager.ReceiverRecord> receivers;

    LocalBroadcastManager.BroadcastRecord(Intent intent, ArrayList<LocalBroadcastManager.ReceiverRecord> arrayList) {
        this.intent = intent;
        this.receivers = arrayList;
    }
}
