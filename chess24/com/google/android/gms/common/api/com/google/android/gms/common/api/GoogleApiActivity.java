/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 */
package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.internal.zzaap;

public class GoogleApiActivity
extends Activity
implements DialogInterface.OnCancelListener {
    protected int zzaxL = 0;

    public static PendingIntent zza(Context context, PendingIntent pendingIntent, int n) {
        return GoogleApiActivity.zza(context, pendingIntent, n, true);
    }

    public static PendingIntent zza(Context context, PendingIntent pendingIntent, int n, boolean bl) {
        return PendingIntent.getActivity((Context)context, (int)0, (Intent)GoogleApiActivity.zzb(context, pendingIntent, n, bl), (int)134217728);
    }

    private void zza(int n, zzaap zzaap2) {
        switch (n) {
            default: {
                return;
            }
            case 0: {
                zzaap2.zza(new ConnectionResult(13, null), this.getIntent().getIntExtra("failing_client_id", -1));
                return;
            }
            case -1: 
        }
        zzaap2.zzuW();
    }

    public static Intent zzb(Context context, PendingIntent pendingIntent, int n, boolean bl) {
        context = new Intent(context, GoogleApiActivity.class);
        context.putExtra("pending_intent", (Parcelable)pendingIntent);
        context.putExtra("failing_client_id", n);
        context.putExtra("notify_manager", bl);
        return context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzuL() {
        Object object;
        String string;
        block8 : {
            block7 : {
                block6 : {
                    object = this.getIntent().getExtras();
                    if (object != null) break block6;
                    string = "Activity started without extras";
                    break block7;
                }
                string = (PendingIntent)object.get("pending_intent");
                object = (Integer)object.get("error_code");
                if (string != null || object != null) break block8;
                string = "Activity started without resolution";
            }
            Log.e((String)"GoogleApiActivity", (String)string);
            this.finish();
            return;
        }
        if (string == null) {
            GoogleApiAvailability.getInstance().showErrorDialogFragment(this, object.intValue(), 2, this);
            this.zzaxL = 1;
            return;
        }
        try {
            this.startIntentSenderForResult(string.getIntentSender(), 1, null, 0, 0, 0);
            this.zzaxL = 1;
            return;
        }
        catch (IntentSender.SendIntentException sendIntentException) {
            Log.e((String)"GoogleApiActivity", (String)"Failed to launch pendingIntent", (Throwable)sendIntentException);
            this.finish();
            return;
        }
    }

    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 1) {
            boolean bl = this.getIntent().getBooleanExtra("notify_manager", true);
            this.zzaxL = 0;
            this.setResultCode(n2);
            if (bl) {
                this.zza(n2, zzaap.zzax((Context)this));
            }
        } else if (n == 2) {
            this.zzaxL = 0;
            this.setResultCode(n2);
        }
        this.finish();
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.zzaxL = 0;
        this.setResult(0);
        this.finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zzaxL = bundle.getInt("resolution");
        }
        if (this.zzaxL != 1) {
            this.zzuL();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("resolution", this.zzaxL);
        super.onSaveInstanceState(bundle);
    }

    protected void setResultCode(int n) {
        this.setResult(n);
    }
}
