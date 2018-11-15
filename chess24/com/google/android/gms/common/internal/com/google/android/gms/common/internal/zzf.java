/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Bundle
 *  android.os.DeadObjectException
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Looper
 *  android.os.Message
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.annotation.BinderThread;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzn;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzv;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class zzf<T extends IInterface> {
    public static final String[] zzaDT = new String[]{"service_esmobile", "service_googleme"};
    private final Context mContext;
    final Handler mHandler;
    private int zzaDB;
    private long zzaDC;
    private long zzaDD;
    private int zzaDE;
    private long zzaDF;
    private final zzn zzaDG;
    private final Object zzaDH = new Object();
    private zzv zzaDI;
    protected zzf zzaDJ;
    private T zzaDK;
    private final ArrayList<zze<?>> zzaDL = new ArrayList();
    private zzh zzaDM;
    private int zzaDN = 1;
    private final zzb zzaDO;
    private final zzc zzaDP;
    private final int zzaDQ;
    private final String zzaDR;
    protected AtomicInteger zzaDS = new AtomicInteger(0);
    private final com.google.android.gms.common.zzc zzazw;
    private final Object zzrN = new Object();
    private final Looper zzrx;

    protected zzf(Context context, Looper looper, int n, zzb zzb2, zzc zzc2, String string) {
        this(context, looper, zzn.zzaC(context), com.google.android.gms.common.zzc.zzuz(), n, zzac.zzw(zzb2), zzac.zzw(zzc2), string);
    }

    protected zzf(Context context, Looper looper, zzn zzn2, com.google.android.gms.common.zzc zzc2, int n, zzb zzb2, zzc zzc3, String string) {
        this.mContext = zzac.zzb(context, (Object)"Context must not be null");
        this.zzrx = zzac.zzb(looper, (Object)"Looper must not be null");
        this.zzaDG = zzac.zzb(zzn2, (Object)"Supervisor must not be null");
        this.zzazw = zzac.zzb(zzc2, (Object)"API availability must not be null");
        this.mHandler = new zzd(looper);
        this.zzaDQ = n;
        this.zzaDO = zzb2;
        this.zzaDP = zzc3;
        this.zzaDR = string;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zza(int n, T t) {
        boolean bl = false;
        boolean bl2 = n == 3;
        boolean bl3 = t != null;
        if (bl2 == bl3) {
            bl = true;
        }
        zzac.zzas(bl);
        Object object = this.zzrN;
        synchronized (object) {
            this.zzaDN = n;
            this.zzaDK = t;
            switch (n) {
                case 3: {
                    this.zza(t);
                    break;
                }
                case 2: {
                    this.zzwR();
                    break;
                }
                case 1: {
                    this.zzwS();
                    break;
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean zza(int n, int n2, T t) {
        Object object = this.zzrN;
        synchronized (object) {
            if (this.zzaDN != n) {
                return false;
            }
            this.zza(n2, t);
            return true;
        }
    }

    private void zzm(ConnectionResult connectionResult) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzaDS.get(), connectionResult.getErrorCode(), (Object)connectionResult.getResolution()));
    }

    private void zzwR() {
        String string;
        String string2;
        StringBuilder stringBuilder;
        if (this.zzaDM != null) {
            string2 = String.valueOf(this.zzeu());
            string = String.valueOf(this.zzwP());
            stringBuilder = new StringBuilder(70 + String.valueOf(string2).length() + String.valueOf(string).length());
            stringBuilder.append("Calling connect() while still connected, missing disconnect() for ");
            stringBuilder.append(string2);
            stringBuilder.append(" on ");
            stringBuilder.append(string);
            Log.e((String)"GmsClient", (String)stringBuilder.toString());
            this.zzaDG.zzb(this.zzeu(), this.zzwP(), this.zzaDM, this.zzwQ());
            this.zzaDS.incrementAndGet();
        }
        this.zzaDM = new zzh(this.zzaDS.get());
        if (!this.zzaDG.zza(this.zzeu(), this.zzwP(), this.zzaDM, this.zzwQ())) {
            string2 = String.valueOf(this.zzeu());
            string = String.valueOf(this.zzwP());
            stringBuilder = new StringBuilder(34 + String.valueOf(string2).length() + String.valueOf(string).length());
            stringBuilder.append("unable to connect to service: ");
            stringBuilder.append(string2);
            stringBuilder.append(" on ");
            stringBuilder.append(string);
            Log.e((String)"GmsClient", (String)stringBuilder.toString());
            this.zza(16, null, this.zzaDS.get());
        }
    }

    private void zzwS() {
        if (this.zzaDM != null) {
            this.zzaDG.zzb(this.zzeu(), this.zzwP(), this.zzaDM, this.zzwQ());
            this.zzaDM = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void disconnect() {
        this.zzaDS.incrementAndGet();
        Object object = this.zzaDL;
        synchronized (object) {
            int n = this.zzaDL.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.zzaDL.clear();
                    // MONITOREXIT [5, 7, 8] lbl9 : MonitorExitStatement: MONITOREXIT : var3_1
                    object = this.zzaDH;
                    synchronized (object) {
                        this.zzaDI = null;
                    }
                    this.zza(1, null);
                    return;
                }
                this.zzaDL.get(n2).zzxb();
                ++n2;
            } while (true);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void dump(String var1_1, FileDescriptor var2_2, PrintWriter var3_3, String[] var4_4) {
        var2_2 = this.zzrN;
        // MONITORENTER : var2_2
        var5_5 = this.zzaDN;
        var4_4 = this.zzaDK;
        // MONITOREXIT : var2_2
        var3_3.append((CharSequence)var1_1).append("mConnectState=");
        switch (var5_5) {
            default: {
                var2_2 = "UNKNOWN";
                break;
            }
            case 4: {
                var2_2 = "DISCONNECTING";
                break;
            }
            case 3: {
                var2_2 = "CONNECTED";
                break;
            }
            case 2: {
                var2_2 = "CONNECTING";
                break;
            }
            case 1: {
                var2_2 = "DISCONNECTED";
            }
        }
        var3_3.print((String)var2_2);
        ** break;
lbl24: // 1 sources:
        var3_3.append(" mService=");
        if (var4_4 == null) {
            var3_3.println("null");
        } else {
            var3_3.append(this.zzev()).append("@").println(Integer.toHexString(System.identityHashCode((Object)var4_4.asBinder())));
        }
        var4_4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzaDD > 0L) {
            var2_2 = var3_3.append((CharSequence)var1_1).append("lastConnectedTime=");
            var6_6 = this.zzaDD;
            var8_7 = String.valueOf(var4_4.format(new Date(this.zzaDD)));
            var9_8 = new StringBuilder(String.valueOf(var8_7).length() + 21);
            var9_8.append(var6_6);
            var9_8.append(" ");
            var9_8.append(var8_7);
            var2_2.println(var9_8.toString());
        }
        if (this.zzaDC > 0L) {
            var3_3.append((CharSequence)var1_1).append("lastSuspendedCause=");
            switch (this.zzaDB) {
                default: {
                    var2_2 = String.valueOf(this.zzaDB);
                    break;
                }
                case 2: {
                    var2_2 = "CAUSE_NETWORK_LOST";
                    break;
                }
                case 1: {
                    var2_2 = "CAUSE_SERVICE_DISCONNECTED";
                }
            }
            var3_3.append((CharSequence)var2_2);
            ** break;
lbl52: // 1 sources:
            var2_2 = var3_3.append(" lastSuspendedTime=");
            var6_6 = this.zzaDC;
            var8_7 = String.valueOf(var4_4.format(new Date(this.zzaDC)));
            var9_8 = new StringBuilder(String.valueOf(var8_7).length() + 21);
            var9_8.append(var6_6);
            var9_8.append(" ");
            var9_8.append(var8_7);
            var2_2.println(var9_8.toString());
        }
        if (this.zzaDF <= 0L) return;
        var3_3.append((CharSequence)var1_1).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.zzaDE));
        var1_1 = var3_3.append(" lastFailedTime=");
        var6_6 = this.zzaDF;
        var2_2 = String.valueOf(var4_4.format(new Date(this.zzaDF)));
        var3_3 = new StringBuilder(21 + String.valueOf(var2_2).length());
        var3_3.append(var6_6);
        var3_3.append(" ");
        var3_3.append((String)var2_2);
        var1_1.println(var3_3.toString());
    }

    public Account getAccount() {
        return null;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zzrx;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnected() {
        Object object = this.zzrN;
        synchronized (object) {
            if (this.zzaDN != 3) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isConnecting() {
        Object object = this.zzrN;
        synchronized (object) {
            if (this.zzaDN != 2) return false;
            return true;
        }
    }

    @CallSuper
    protected void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzaDE = connectionResult.getErrorCode();
        this.zzaDF = System.currentTimeMillis();
    }

    @CallSuper
    protected void onConnectionSuspended(int n) {
        this.zzaDB = n;
        this.zzaDC = System.currentTimeMillis();
    }

    protected void zza(int n, @Nullable Bundle bundle, int n2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(5, n2, -1, (Object)new zzk(n, bundle)));
    }

    @BinderThread
    protected void zza(int n, IBinder iBinder, Bundle bundle, int n2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, n2, -1, (Object)new zzj(n, iBinder, bundle)));
    }

    @CallSuper
    protected void zza(@NonNull T t) {
        this.zzaDD = System.currentTimeMillis();
    }

    public void zza(@NonNull zzf zzf2) {
        this.zzaDJ = zzac.zzb(zzf2, (Object)"Connection progress callbacks cannot be null.");
        this.zza(2, null);
    }

    public void zza(zzf zzf2, ConnectionResult connectionResult) {
        this.zzaDJ = zzac.zzb(zzf2, (Object)"Connection progress callbacks cannot be null.");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzaDS.get(), connectionResult.getErrorCode(), (Object)connectionResult.getResolution()));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @WorkerThread
    public void zza(zzr object, Set<Scope> set) {
        block12 : {
            Object object2 = this.zzql();
            object2 = new com.google.android.gms.common.internal.zzj(this.zzaDQ).zzdq(this.mContext.getPackageName()).zzp((Bundle)object2);
            if (set != null) {
                object2.zzf(set);
            }
            if (this.zzqD()) {
                object2.zze(this.zzwU()).zzb((zzr)object);
            } else if (this.zzwX()) {
                object2.zze(this.getAccount());
            }
            try {
                object = this.zzaDH;
                // MONITORENTER : object
                if (this.zzaDI == null) break block12;
            }
            catch (RuntimeException runtimeException) {
                Log.w((String)"GmsClient", (String)"IGmsServiceBroker.getService failed", (Throwable)runtimeException);
                this.zzm(new ConnectionResult(8, null, "IGmsServiceBroker.getService failed."));
                return;
            }
            catch (SecurityException securityException) {
                throw securityException;
            }
            catch (RemoteException remoteException) {
                Log.w((String)"GmsClient", (String)"Remote exception occurred", (Throwable)remoteException);
                return;
            }
            catch (DeadObjectException deadObjectException) {}
            this.zzaDI.zza((zzu)new zzg(this, this.zzaDS.get()), (com.google.android.gms.common.internal.zzj)object2);
            return;
        }
        Log.w((String)"GmsClient", (String)"mServiceBroker is null, client disconnected");
        // MONITOREXIT : object
        return;
        Log.w((String)"GmsClient", (String)"service died");
        this.zzcM(1);
    }

    public void zzcM(int n) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, this.zzaDS.get(), n));
    }

    @NonNull
    protected abstract String zzeu();

    @NonNull
    protected abstract String zzev();

    @Nullable
    protected abstract T zzh(IBinder var1);

    public boolean zzqD() {
        return false;
    }

    public boolean zzqS() {
        return false;
    }

    public Intent zzqT() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    protected Bundle zzql() {
        return new Bundle();
    }

    public boolean zzuI() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    public IBinder zzuJ() {
        Object object = this.zzaDH;
        synchronized (object) {
            if (this.zzaDI != null) return this.zzaDI.asBinder();
            return null;
        }
    }

    public Bundle zzud() {
        return null;
    }

    protected String zzwP() {
        return "com.google.android.gms";
    }

    @Nullable
    protected final String zzwQ() {
        if (this.zzaDR == null) {
            return this.mContext.getClass().getName();
        }
        return this.zzaDR;
    }

    public void zzwT() {
        int n = this.zzazw.isGooglePlayServicesAvailable(this.mContext);
        if (n != 0) {
            this.zza(1, null);
            this.zzaDJ = new zzi();
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzaDS.get(), n));
            return;
        }
        this.zza(new zzi());
    }

    public final Account zzwU() {
        if (this.getAccount() != null) {
            return this.getAccount();
        }
        return new Account("<<default account>>", "com.google");
    }

    protected final void zzwV() {
        if (!this.isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final T zzwW() throws DeadObjectException {
        Object object = this.zzrN;
        synchronized (object) {
            if (this.zzaDN == 4) {
                throw new DeadObjectException();
            }
            this.zzwV();
            boolean bl = this.zzaDK != null;
            zzac.zza(bl, (Object)"Client is connected but service is null");
            T t = this.zzaDK;
            return t;
        }
    }

    public boolean zzwX() {
        return false;
    }

    protected Set<Scope> zzwY() {
        return Collections.EMPTY_SET;
    }

    private abstract class zza
    extends zze<Boolean> {
        public final int statusCode;
        public final Bundle zzaDU;

        @BinderThread
        protected zza(int n, Bundle bundle) {
            super(true);
            this.statusCode = n;
            this.zzaDU = bundle;
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void zzc(Boolean object) {
            Object var3_2 = null;
            if (object == null) {
                zzf.this.zza(1, (T)null);
                return;
            }
            int n = this.statusCode;
            if (n != 0) {
                if (n == 10) {
                    zzf.this.zza(1, (T)null);
                    throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
                }
                zzf.this.zza(1, (T)null);
                object = var3_2;
                if (this.zzaDU != null) {
                    object = (PendingIntent)this.zzaDU.getParcelable("pendingIntent");
                }
                object = new ConnectionResult(this.statusCode, (PendingIntent)object);
            } else {
                if (this.zzwZ()) {
                    return;
                }
                zzf.this.zza(1, (T)null);
                object = new ConnectionResult(8, null);
            }
            this.zzn((ConnectionResult)object);
        }

        protected abstract void zzn(ConnectionResult var1);

        @Override
        protected /* synthetic */ void zzu(Object object) {
            this.zzc((Boolean)object);
        }

        protected abstract boolean zzwZ();
    }

    public static interface zzb {
        public void onConnected(@Nullable Bundle var1);

        public void onConnectionSuspended(int var1);
    }

    public static interface zzc {
        public void onConnectionFailed(@NonNull ConnectionResult var1);
    }

    final class zzd
    extends Handler {
        public zzd(Looper looper) {
            super(looper);
        }

        private void zza(Message message) {
            ((zze)message.obj).unregister();
        }

        private boolean zzb(Message message) {
            boolean bl;
            int n = message.what;
            boolean bl2 = bl = true;
            if (n != 2) {
                bl2 = bl;
                if (message.what != 1) {
                    if (message.what == 5) {
                        return true;
                    }
                    bl2 = false;
                }
            }
            return bl2;
        }

        public void handleMessage(Message object) {
            if (zzf.this.zzaDS.get() != object.arg1) {
                if (this.zzb((Message)object)) {
                    this.zza((Message)object);
                }
                return;
            }
            if (!(object.what != 1 && object.what != 5 || zzf.this.isConnecting())) {
                this.zza((Message)object);
                return;
            }
            int n = object.what;
            PendingIntent pendingIntent = null;
            if (n == 3) {
                if (object.obj instanceof PendingIntent) {
                    pendingIntent = (PendingIntent)object.obj;
                }
                object = new ConnectionResult(object.arg2, pendingIntent);
                zzf.this.zzaDJ.zzg((ConnectionResult)object);
                zzf.this.onConnectionFailed((ConnectionResult)object);
                return;
            }
            if (object.what == 4) {
                zzf.this.zza(4, (T)null);
                if (zzf.this.zzaDO != null) {
                    zzf.this.zzaDO.onConnectionSuspended(object.arg2);
                }
                zzf.this.onConnectionSuspended(object.arg2);
                zzf.this.zza(4, 1, (T)null);
                return;
            }
            if (object.what == 2 && !zzf.this.isConnected()) {
                this.zza((Message)object);
                return;
            }
            if (this.zzb((Message)object)) {
                ((zze)object.obj).zzxa();
                return;
            }
            n = object.what;
            object = new StringBuilder(45);
            object.append("Don't know how to handle message: ");
            object.append(n);
            Log.wtf((String)"GmsClient", (String)object.toString(), (Throwable)new Exception());
        }
    }

    protected abstract class zze<TListener> {
        private TListener mListener;
        private boolean zzaDW;

        public zze(TListener TListener) {
            this.mListener = TListener;
            this.zzaDW = false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void unregister() {
            this.zzxb();
            ArrayList arrayList = zzf.this.zzaDL;
            synchronized (arrayList) {
                zzf.this.zzaDL.remove(this);
                return;
            }
        }

        protected abstract void zzu(TListener var1);

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void zzxa() {
            // MONITORENTER : this
            TListener TListener = this.mListener;
            if (this.zzaDW) {
                String string = String.valueOf(this);
                StringBuilder stringBuilder = new StringBuilder(47 + String.valueOf(string).length());
                stringBuilder.append("Callback proxy ");
                stringBuilder.append(string);
                stringBuilder.append(" being reused. This is not safe.");
                Log.w((String)"GmsClient", (String)stringBuilder.toString());
            }
            // MONITOREXIT : this
            if (TListener != null) {
                this.zzu(TListener);
            }
            // MONITORENTER : this
            this.zzaDW = true;
            // MONITOREXIT : this
            this.unregister();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void zzxb() {
            synchronized (this) {
                this.mListener = null;
                return;
            }
        }
    }

    public static interface zzf {
        public void zzg(@NonNull ConnectionResult var1);
    }

    public static final class zzg
    extends zzu.zza {
        private zzf zzaDX;
        private final int zzaDY;

        public zzg(@NonNull zzf zzf2, int n) {
            this.zzaDX = zzf2;
            this.zzaDY = n;
        }

        private void zzxc() {
            this.zzaDX = null;
        }

        @BinderThread
        @Override
        public void zza(int n, @NonNull IBinder iBinder, @Nullable Bundle bundle) {
            zzac.zzb(this.zzaDX, (Object)"onPostInitComplete can be called only once per call to getRemoteService");
            this.zzaDX.zza(n, iBinder, bundle, this.zzaDY);
            this.zzxc();
        }

        @BinderThread
        @Override
        public void zzb(int n, @Nullable Bundle bundle) {
            Log.wtf((String)"GmsClient", (String)"received deprecated onAccountValidationComplete callback, ignoring", (Throwable)new Exception());
        }
    }

    public final class zzh
    implements ServiceConnection {
        private final int zzaDY;

        public zzh(int n) {
            this.zzaDY = n;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onServiceConnected(ComponentName object, IBinder iBinder) {
            if (iBinder == null) {
                zzf.this.zzm(new ConnectionResult(8, null, "ServiceBroker IBinder is null"));
                return;
            }
            object = zzf.this.zzaDH;
            synchronized (object) {
                zzf.this.zzaDI = zzv.zza.zzbu(iBinder);
            }
            zzf.this.zza(0, null, this.zzaDY);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onServiceDisconnected(ComponentName object) {
            object = zzf.this.zzaDH;
            synchronized (object) {
                zzf.this.zzaDI = null;
            }
            zzf.this.mHandler.sendMessage(zzf.this.mHandler.obtainMessage(4, this.zzaDY, 1));
        }
    }

    protected class zzi
    implements zzf {
        @Override
        public void zzg(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                zzf.this.zza(null, zzf.this.zzwY());
                return;
            }
            if (zzf.this.zzaDP != null) {
                zzf.this.zzaDP.onConnectionFailed(connectionResult);
            }
        }
    }

    protected final class zzj
    extends zza {
        public final IBinder zzaDZ;

        @BinderThread
        public zzj(int n, IBinder iBinder, Bundle bundle) {
            super(n, bundle);
            this.zzaDZ = iBinder;
        }

        @Override
        protected void zzn(ConnectionResult connectionResult) {
            if (zzf.this.zzaDP != null) {
                zzf.this.zzaDP.onConnectionFailed(connectionResult);
            }
            zzf.this.onConnectionFailed(connectionResult);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        protected boolean zzwZ() {
            String string;
            boolean bl = false;
            try {
                string = this.zzaDZ.getInterfaceDescriptor();
            }
            catch (RemoteException remoteException) {}
            if (!zzf.this.zzev().equals(string)) {
                String string2 = String.valueOf(zzf.this.zzev());
                StringBuilder stringBuilder = new StringBuilder(34 + String.valueOf(string2).length() + String.valueOf(string).length());
                stringBuilder.append("service descriptor mismatch: ");
                stringBuilder.append(string2);
                stringBuilder.append(" vs. ");
                stringBuilder.append(string);
                Log.e((String)"GmsClient", (String)stringBuilder.toString());
                return false;
            }
            Object t = zzf.this.zzh(this.zzaDZ);
            boolean bl2 = bl;
            if (t == null) return bl2;
            bl2 = bl;
            if (!zzf.this.zza(2, 3, (T)t)) return bl2;
            Bundle bundle = zzf.this.zzud();
            if (zzf.this.zzaDO == null) return true;
            zzf.this.zzaDO.onConnected(bundle);
            return true;
            Log.w((String)"GmsClient", (String)"service probably died");
            return false;
        }
    }

    protected final class zzk
    extends zza {
        @BinderThread
        public zzk(int n, @Nullable Bundle bundle) {
            super(n, bundle);
        }

        @Override
        protected void zzn(ConnectionResult connectionResult) {
            zzf.this.zzaDJ.zzg(connectionResult);
            zzf.this.onConnectionFailed(connectionResult);
        }

        @Override
        protected boolean zzwZ() {
            zzf.this.zzaDJ.zzg(ConnectionResult.zzawX);
            return true;
        }
    }

}
