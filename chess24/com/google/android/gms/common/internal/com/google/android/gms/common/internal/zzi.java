/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.google.android.gms.internal.zzaax;

public abstract class zzi
implements DialogInterface.OnClickListener {
    public static zzi zza(final Activity activity, Intent intent, final int n) {
        return new zzi(){

            @Override
            public void zzxm() {
                if (Intent.this != null) {
                    activity.startActivityForResult(Intent.this, n);
                }
            }
        };
    }

    public static zzi zza(final @NonNull Fragment fragment, Intent intent, final int n) {
        return new zzi(){

            @Override
            public void zzxm() {
                if (Intent.this != null) {
                    fragment.startActivityForResult(Intent.this, n);
                }
            }
        };
    }

    public static zzi zza(final @NonNull zzaax zzaax2, Intent intent, final int n) {
        return new zzi(){

            @TargetApi(value=11)
            @Override
            public void zzxm() {
                if (Intent.this != null) {
                    zzaax2.startActivityForResult(Intent.this, n);
                }
            }
        };
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void onClick(DialogInterface var1_1, int var2_2) {
        this.zzxm();
lbl3: // 2 sources:
        do {
            var1_1.dismiss();
            return;
            break;
        } while (true);
        {
            catch (Throwable var3_3) {
            }
            catch (ActivityNotFoundException var3_4) {}
            {
                Log.e((String)"DialogRedirect", (String)"Failed to start resolution intent", (Throwable)var3_4);
                ** continue;
            }
        }
        var1_1.dismiss();
        throw var3_3;
    }

    protected abstract void zzxm();

}
