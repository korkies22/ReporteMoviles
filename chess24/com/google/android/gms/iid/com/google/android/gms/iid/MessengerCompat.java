/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Binder
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package com.google.android.gms.iid;

import android.annotation.TargetApi;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.iid.zzb;

public class MessengerCompat
implements ReflectedParcelable {
    public static final Parcelable.Creator<MessengerCompat> CREATOR = new Parcelable.Creator<MessengerCompat>(){

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return this.zzgn(parcel);
        }

        public /* synthetic */ Object[] newArray(int n) {
            return this.zzjB(n);
        }

        public MessengerCompat zzgn(Parcel parcel) {
            if ((parcel = parcel.readStrongBinder()) != null) {
                return new MessengerCompat((IBinder)parcel);
            }
            return null;
        }

        public MessengerCompat[] zzjB(int n) {
            return new MessengerCompat[n];
        }
    };
    Messenger zzbho;
    zzb zzbhp;

    public MessengerCompat(Handler handler) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.zzbho = new Messenger(handler);
            return;
        }
        this.zzbhp = new zza(this, handler);
    }

    public MessengerCompat(IBinder iBinder) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.zzbho = new Messenger(iBinder);
            return;
        }
        this.zzbhp = zzb.zza.zzcZ(iBinder);
    }

    public static int zzc(Message message) {
        if (Build.VERSION.SDK_INT >= 21) {
            return MessengerCompat.zzd(message);
        }
        return message.arg2;
    }

    @TargetApi(value=21)
    private static int zzd(Message message) {
        return message.sendingUid;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        try {
            boolean bl = this.getBinder().equals((Object)((MessengerCompat)object).getBinder());
            return bl;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public IBinder getBinder() {
        if (this.zzbho != null) {
            return this.zzbho.getBinder();
        }
        return this.zzbhp.asBinder();
    }

    public int hashCode() {
        return this.getBinder().hashCode();
    }

    public void send(Message message) throws RemoteException {
        if (this.zzbho != null) {
            this.zzbho.send(message);
            return;
        }
        this.zzbhp.send(message);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeToParcel(Parcel parcel, int n) {
        IBinder iBinder = this.zzbho != null ? this.zzbho.getBinder() : this.zzbhp.asBinder();
        parcel.writeStrongBinder(iBinder);
    }

    private final class zza
    extends zzb.zza {
        Handler handler;

        zza(MessengerCompat messengerCompat, Handler handler) {
            this.handler = handler;
        }

        @Override
        public void send(Message message) throws RemoteException {
            message.arg2 = Binder.getCallingUid();
            this.handler.dispatchMessage(message);
        }
    }

}
