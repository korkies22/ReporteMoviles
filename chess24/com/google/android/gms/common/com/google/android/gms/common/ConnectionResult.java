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
package com.google.android.gms.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.zzb;

public final class ConnectionResult
extends zza {
    public static final int API_UNAVAILABLE = 16;
    public static final int CANCELED = 13;
    public static final Parcelable.Creator<ConnectionResult> CREATOR;
    public static final int DEVELOPER_ERROR = 10;
    @Deprecated
    public static final int DRIVE_EXTERNAL_STORAGE_REQUIRED = 1500;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPTED = 15;
    public static final int INVALID_ACCOUNT = 5;
    public static final int LICENSE_CHECK_FAILED = 11;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;
    public static final int RESTRICTED_PROFILE = 20;
    public static final int SERVICE_DISABLED = 3;
    public static final int SERVICE_INVALID = 9;
    public static final int SERVICE_MISSING = 1;
    public static final int SERVICE_MISSING_PERMISSION = 19;
    public static final int SERVICE_UPDATING = 18;
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_FAILED = 17;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final int TIMEOUT = 14;
    public static final ConnectionResult zzawX;
    private final PendingIntent mPendingIntent;
    final int mVersionCode;
    private final int zzauz;
    private final String zzawY;

    static {
        zzawX = new ConnectionResult(0);
        CREATOR = new zzb();
    }

    public ConnectionResult(int n) {
        this(n, null, null);
    }

    ConnectionResult(int n, int n2, PendingIntent pendingIntent, String string) {
        this.mVersionCode = n;
        this.zzauz = n2;
        this.mPendingIntent = pendingIntent;
        this.zzawY = string;
    }

    public ConnectionResult(int n, PendingIntent pendingIntent) {
        this(n, pendingIntent, null);
    }

    public ConnectionResult(int n, PendingIntent pendingIntent, String string) {
        this(1, n, pendingIntent, string);
    }

    static String getStatusString(int n) {
        if (n != 99) {
            if (n != 1500) {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                StringBuilder stringBuilder = new StringBuilder(31);
                                stringBuilder.append("UNKNOWN_ERROR_CODE(");
                                stringBuilder.append(n);
                                stringBuilder.append(")");
                                return stringBuilder.toString();
                            }
                            case 21: {
                                return "API_VERSION_UPDATE_REQUIRED";
                            }
                            case 20: {
                                return "RESTRICTED_PROFILE";
                            }
                            case 19: {
                                return "SERVICE_MISSING_PERMISSION";
                            }
                            case 18: {
                                return "SERVICE_UPDATING";
                            }
                            case 17: {
                                return "SIGN_IN_FAILED";
                            }
                            case 16: {
                                return "API_UNAVAILABLE";
                            }
                            case 15: {
                                return "INTERRUPTED";
                            }
                            case 14: {
                                return "TIMEOUT";
                            }
                            case 13: 
                        }
                        return "CANCELED";
                    }
                    case 11: {
                        return "LICENSE_CHECK_FAILED";
                    }
                    case 10: {
                        return "DEVELOPER_ERROR";
                    }
                    case 9: {
                        return "SERVICE_INVALID";
                    }
                    case 8: {
                        return "INTERNAL_ERROR";
                    }
                    case 7: {
                        return "NETWORK_ERROR";
                    }
                    case 6: {
                        return "RESOLUTION_REQUIRED";
                    }
                    case 5: {
                        return "INVALID_ACCOUNT";
                    }
                    case 4: {
                        return "SIGN_IN_REQUIRED";
                    }
                    case 3: {
                        return "SERVICE_DISABLED";
                    }
                    case 2: {
                        return "SERVICE_VERSION_UPDATE_REQUIRED";
                    }
                    case 1: {
                        return "SERVICE_MISSING";
                    }
                    case 0: {
                        return "SUCCESS";
                    }
                    case -1: 
                }
                return "UNKNOWN";
            }
            return "DRIVE_EXTERNAL_STORAGE_REQUIRED";
        }
        return "UNFINISHED";
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ConnectionResult)) {
            return false;
        }
        object = (ConnectionResult)object;
        if (this.zzauz == object.zzauz && zzaa.equal((Object)this.mPendingIntent, (Object)object.mPendingIntent) && zzaa.equal(this.zzawY, object.zzawY)) {
            return true;
        }
        return false;
    }

    public int getErrorCode() {
        return this.zzauz;
    }

    @Nullable
    public String getErrorMessage() {
        return this.zzawY;
    }

    @Nullable
    public PendingIntent getResolution() {
        return this.mPendingIntent;
    }

    public boolean hasResolution() {
        if (this.zzauz != 0 && this.mPendingIntent != null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return zzaa.hashCode(new Object[]{this.zzauz, this.mPendingIntent, this.zzawY});
    }

    public boolean isSuccess() {
        if (this.zzauz == 0) {
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
        return zzaa.zzv(this).zzg("statusCode", ConnectionResult.getStatusString(this.zzauz)).zzg("resolution", (Object)this.mPendingIntent).zzg("message", this.zzawY).toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        zzb.zza(this, parcel, n);
    }
}
