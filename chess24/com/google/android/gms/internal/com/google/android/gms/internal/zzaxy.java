/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.zzl;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzad;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzaxn;
import com.google.android.gms.internal.zzaxo;
import com.google.android.gms.internal.zzaxu;
import com.google.android.gms.internal.zzaxv;
import com.google.android.gms.internal.zzaxz;
import com.google.android.gms.internal.zzayb;

public class zzaxy
extends com.google.android.gms.common.internal.zzl<zzaxv>
implements zzaxn {
    private Integer zzaEe;
    private final zzg zzazs;
    private final Bundle zzbCf;
    private final boolean zzbCq;

    public zzaxy(Context context, Looper looper, boolean bl, zzg zzg2, Bundle bundle, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, zzg2, connectionCallbacks, onConnectionFailedListener);
        this.zzbCq = bl;
        this.zzazs = zzg2;
        this.zzbCf = bundle;
        this.zzaEe = zzg2.zzxl();
    }

    public zzaxy(Context context, Looper looper, boolean bl, zzg zzg2, zzaxo zzaxo2, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, bl, zzg2, zzaxy.zza(zzg2), connectionCallbacks, onConnectionFailedListener);
    }

    private zzad zzOn() {
        Account account = this.zzazs.zzwU();
        GoogleSignInAccount googleSignInAccount = "<<default account>>".equals(account.name) ? zzl.zzaa(this.getContext()).zzrc() : null;
        return new zzad(account, this.zzaEe, googleSignInAccount);
    }

    public static Bundle zza(zzg zzg2) {
        zzaxo zzaxo2 = zzg2.zzxk();
        Integer n = zzg2.zzxl();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", (Parcelable)zzg2.getAccount());
        if (n != null) {
            bundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", n.intValue());
        }
        if (zzaxo2 != null) {
            bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", zzaxo2.zzOf());
            bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", zzaxo2.zzqK());
            bundle.putString("com.google.android.gms.signin.internal.serverClientId", zzaxo2.zzqN());
            bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
            bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", zzaxo2.zzqM());
            bundle.putString("com.google.android.gms.signin.internal.hostedDomain", zzaxo2.zzqO());
            bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", zzaxo2.zzOg());
            if (zzaxo2.zzOh() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.authApiSignInModuleVersion", zzaxo2.zzOh().longValue());
            }
            if (zzaxo2.zzOi() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.realClientLibraryVersion", zzaxo2.zzOi().longValue());
            }
        }
        return bundle;
    }

    @Override
    public void connect() {
        this.zza(new zzf.zzi(this));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void zzOe() {
        try {
            ((zzaxv)this.zzwW()).zzmK(this.zzaEe);
            return;
        }
        catch (RemoteException remoteException) {}
        Log.w((String)"SignInClientImpl", (String)"Remote service probably died when clearAccountFromSessionStore is called");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void zza(zzr zzr2, boolean bl) {
        try {
            ((zzaxv)this.zzwW()).zza(zzr2, this.zzaEe, bl);
            return;
        }
        catch (RemoteException remoteException) {}
        Log.w((String)"SignInClientImpl", (String)"Remote service probably died when saveDefaultAccount is called");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void zza(zzaxu zzaxu2) {
        zzac.zzb(zzaxu2, (Object)"Expecting a valid ISignInCallbacks");
        try {
            zzad zzad2 = this.zzOn();
            ((zzaxv)this.zzwW()).zza(new zzaxz(zzad2), zzaxu2);
            return;
        }
        catch (RemoteException remoteException) {
            Log.w((String)"SignInClientImpl", (String)"Remote service probably died when signIn is called");
            try {
                zzaxu2.zzb(new zzayb(8));
                return;
            }
            catch (RemoteException remoteException2) {}
            Log.wtf((String)"SignInClientImpl", (String)"ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", (Throwable)remoteException);
            return;
        }
    }

    protected zzaxv zzeZ(IBinder iBinder) {
        return zzaxv.zza.zzeY(iBinder);
    }

    @Override
    protected String zzeu() {
        return "com.google.android.gms.signin.service.START";
    }

    @Override
    protected String zzev() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }

    @Override
    protected /* synthetic */ IInterface zzh(IBinder iBinder) {
        return this.zzeZ(iBinder);
    }

    @Override
    public boolean zzqD() {
        return this.zzbCq;
    }

    @Override
    protected Bundle zzql() {
        String string = this.zzazs.zzxh();
        if (!this.getContext().getPackageName().equals(string)) {
            this.zzbCf.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zzazs.zzxh());
        }
        return this.zzbCf;
    }
}
