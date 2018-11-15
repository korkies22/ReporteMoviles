/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.zzh;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;

public final class Status
extends zza
implements Result,
ReflectedParcelable {
    public static final Parcelable.Creator<Status> CREATOR;
    public static final Status zzayh;
    public static final Status zzayi;
    public static final Status zzayj;
    public static final Status zzayk;
    public static final Status zzayl;
    public static final Status zzaym;
    public static final Status zzayn;
    private final PendingIntent mPendingIntent;
    final int mVersionCode;
    private final int zzauz;
    private final String zzawY;

    static {
        zzayh = new Status(0);
        zzayi = new Status(14);
        zzayj = new Status(8);
        zzayk = new Status(15);
        zzayl = new Status(16);
        zzaym = new Status(17);
        zzayn = new Status(18);
        CREATOR = new zzh();
    }

    public Status(int n) {
        this(n, null);
    }

    Status(int n, int n2, String string, PendingIntent pendingIntent) {
        this.mVersionCode = n;
        this.zzauz = n2;
        this.zzawY = string;
        this.mPendingIntent = pendingIntent;
    }

    public Status(int n, String string) {
        this(1, n, string, null);
    }

    public Status(int n, String string, PendingIntent pendingIntent) {
        this(1, n, string, pendingIntent);
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof Status;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (Status)object;
        bl = bl2;
        if (this.mVersionCode == object.mVersionCode) {
            bl = bl2;
            if (this.zzauz == object.zzauz) {
                bl = bl2;
                if (zzaa.equal(this.zzawY, object.zzawY)) {
                    bl = bl2;
                    if (zzaa.equal((Object)this.mPendingIntent, (Object)object.mPendingIntent)) {
                        bl = true;
                    }
                }
            }
        }
        return bl;
    }

    public PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    @Override
    public Status getStatus() {
        return this;
    }

    public int getStatusCode() {
        return this.zzauz;
    }

    @Nullable
    public String getStatusMessage() {
        return this.zzawY;
    }

    public boolean hasResolution() {
        if (this.mPendingIntent != null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return zzaa.hashCode(new Object[]{this.mVersionCode, this.zzauz, this.zzawY, this.mPendingIntent});
    }

    public boolean isCanceled() {
        if (this.zzauz == 16) {
            return true;
        }
        return false;
    }

    public boolean isInterrupted() {
        if (this.zzauz == 14) {
            return true;
        }
        return false;
    }

    public boolean isSuccess() {
        if (this.zzauz <= 0) {
            return true;
        }
        return false;
    }

    public void startResolutionForResult(Activity activity, int n) throws IntentSender.SendIntentException {
        if (!this.hasResolution()) {
            return;
        }
        activity.startIntentSenderForResult(this.mPendingIntent.getIntentSender(), n, null, 0, 0, 0);
    }

    public String toString() {
        return zzaa.zzv(this).zzg("statusCode", this.zzuU()).zzg("resolution", (Object)this.mPendingIntent).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzh.zza(this, parcel, n);
    }

    PendingIntent zzuT() {
        return this.mPendingIntent;
    }

    public String zzuU() {
        if (this.zzawY != null) {
            return this.zzawY;
        }
        return CommonStatusCodes.getStatusCodeString(this.zzauz);
    }
}
