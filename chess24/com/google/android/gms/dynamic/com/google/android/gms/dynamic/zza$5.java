/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.google.android.gms.dynamic;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

final class zza
implements View.OnClickListener {
    final /* synthetic */ Intent zzaQm;

    zza(Intent intent) {
        this.zzaQm = intent;
    }

    public void onClick(View view) {
        try {
            Context.this.startActivity(this.zzaQm);
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            Log.e((String)"DeferredLifecycleHelper", (String)"Failed to start resolution intent", (Throwable)activityNotFoundException);
            return;
        }
    }
}
