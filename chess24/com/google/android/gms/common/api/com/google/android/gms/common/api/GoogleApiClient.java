/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.app.Activity
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.View
 */
package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.internal.zzaal;
import com.google.android.gms.internal.zzaav;
import com.google.android.gms.internal.zzaaz;
import com.google.android.gms.internal.zzabi;
import com.google.android.gms.internal.zzabp;
import com.google.android.gms.internal.zzaxm;
import com.google.android.gms.internal.zzaxn;
import com.google.android.gms.internal.zzaxo;
import com.google.android.gms.internal.zzaxy;
import com.google.android.gms.internal.zzzt;
import com.google.android.gms.internal.zzzv;
import com.google.android.gms.internal.zzzy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GoogleApiClient {
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;
    private static final Set<GoogleApiClient> zzaxM = Collections.newSetFromMap(new WeakHashMap());

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void dumpAll(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        Set<GoogleApiClient> set = zzaxM;
        synchronized (set) {
            int n = 0;
            String string2 = String.valueOf(string).concat("  ");
            Iterator<GoogleApiClient> iterator = zzaxM.iterator();
            while (iterator.hasNext()) {
                GoogleApiClient googleApiClient = iterator.next();
                printWriter.append(string).append("GoogleApiClient#").println(n);
                googleApiClient.dump(string2, fileDescriptor, printWriter, arrstring);
                ++n;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Set<GoogleApiClient> zzuM() {
        Set<GoogleApiClient> set = zzaxM;
        synchronized (set) {
            return zzaxM;
        }
    }

    public abstract ConnectionResult blockingConnect();

    public abstract ConnectionResult blockingConnect(long var1, @NonNull TimeUnit var3);

    public abstract PendingResult<Status> clearDefaultAccountAndReconnect();

    public abstract void connect();

    public void connect(int n) {
        throw new UnsupportedOperationException();
    }

    public abstract void disconnect();

    public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    @NonNull
    public abstract ConnectionResult getConnectionResult(@NonNull Api<?> var1);

    public Context getContext() {
        throw new UnsupportedOperationException();
    }

    public Looper getLooper() {
        throw new UnsupportedOperationException();
    }

    public abstract boolean hasConnectedApi(@NonNull Api<?> var1);

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks var1);

    public abstract boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener var1);

    public abstract void reconnect();

    public abstract void registerConnectionCallbacks(@NonNull ConnectionCallbacks var1);

    public abstract void registerConnectionFailedListener(@NonNull OnConnectionFailedListener var1);

    public abstract void stopAutoManage(@NonNull FragmentActivity var1);

    public abstract void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks var1);

    public abstract void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener var1);

    @NonNull
    public <C extends Api.zze> C zza(@NonNull Api.zzc<C> zzc2) {
        throw new UnsupportedOperationException();
    }

    public <A extends Api.zzb, R extends Result, T extends zzzv.zza<R, A>> T zza(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    public void zza(zzabp zzabp2) {
        throw new UnsupportedOperationException();
    }

    public boolean zza(@NonNull Api<?> api) {
        throw new UnsupportedOperationException();
    }

    public boolean zza(zzabi zzabi2) {
        throw new UnsupportedOperationException();
    }

    public <A extends Api.zzb, T extends zzzv.zza<? extends Result, A>> T zzb(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    public void zzb(zzabp zzabp2) {
        throw new UnsupportedOperationException();
    }

    public <L> zzaaz<L> zzr(@NonNull L l) {
        throw new UnsupportedOperationException();
    }

    public void zzuN() {
        throw new UnsupportedOperationException();
    }

    public static final class Builder {
        private final Context mContext;
        private Account zzagg;
        private String zzahp;
        private final Set<Scope> zzaxN = new HashSet<Scope>();
        private final Set<Scope> zzaxO = new HashSet<Scope>();
        private int zzaxP;
        private View zzaxQ;
        private String zzaxR;
        private final Map<Api<?>, zzg.zza> zzaxS = new ArrayMap();
        private final Map<Api<?>, Api.ApiOptions> zzaxT = new ArrayMap();
        private zzaav zzaxU;
        private int zzaxV = -1;
        private OnConnectionFailedListener zzaxW;
        private GoogleApiAvailability zzaxX = GoogleApiAvailability.getInstance();
        private Api.zza<? extends zzaxn, zzaxo> zzaxY = zzaxm.zzahd;
        private final ArrayList<ConnectionCallbacks> zzaxZ = new ArrayList();
        private final ArrayList<OnConnectionFailedListener> zzaya = new ArrayList();
        private boolean zzayb = false;
        private Looper zzrx;

        public Builder(@NonNull Context context) {
            this.mContext = context;
            this.zzrx = context.getMainLooper();
            this.zzahp = context.getPackageName();
            this.zzaxR = context.getClass().getName();
        }

        public Builder(@NonNull Context context, @NonNull ConnectionCallbacks connectionCallbacks, @NonNull OnConnectionFailedListener onConnectionFailedListener) {
            this(context);
            zzac.zzb(connectionCallbacks, (Object)"Must provide a connected listener");
            this.zzaxZ.add(connectionCallbacks);
            zzac.zzb(onConnectionFailedListener, (Object)"Must provide a connection failed listener");
            this.zzaya.add(onConnectionFailedListener);
        }

        private static <C extends Api.zze, O> C zza(Api.zza<C, O> zza2, Object object, Context context, Looper looper, zzg zzg2, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zza2.zza(context, looper, zzg2, object, connectionCallbacks, onConnectionFailedListener);
        }

        private Builder zza(@NonNull zzaav zzaav2, int n, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            boolean bl = n >= 0;
            zzac.zzb(bl, (Object)"clientId must be non-negative");
            this.zzaxV = n;
            this.zzaxW = onConnectionFailedListener;
            this.zzaxU = zzaav2;
            return this;
        }

        private /* varargs */ <O extends Api.ApiOptions> void zza(Api<O> object, O object2, int n, Scope ... arrscope) {
            block4 : {
                boolean bl;
                block3 : {
                    int n2 = 0;
                    bl = true;
                    if (n == 1) break block3;
                    if (n != 2) break block4;
                    bl = false;
                }
                object2 = new HashSet<Scope>(object.zzuF().zzp(object2));
                int n3 = arrscope.length;
                for (n = n2; n < n3; ++n) {
                    object2.add((Scope)arrscope[n]);
                }
                this.zzaxS.put((Api<?>)object, new zzg.zza((Set<Scope>)object2, bl));
                return;
            }
            object = new StringBuilder(90);
            object.append("Invalid resolution mode: '");
            object.append(n);
            object.append("', use a constant from GoogleApiClient.ResolutionMode");
            throw new IllegalArgumentException(object.toString());
        }

        private void zzf(GoogleApiClient googleApiClient) {
            zzzt.zza(this.zzaxU).zza(this.zzaxV, googleApiClient, this.zzaxW);
        }

        private GoogleApiClient zzuQ() {
            int n;
            boolean bl;
            Object object;
            zzg zzg2 = this.zzuP();
            Map<Api<?>, zzg.zza> map = zzg2.zzxg();
            ArrayMap arrayMap = new ArrayMap();
            ArrayMap arrayMap2 = new ArrayMap();
            ArrayList<zzzy> arrayList = new ArrayList<zzzy>();
            Iterator<Api<?>> iterator = this.zzaxT.keySet().iterator();
            Object object2 = null;
            Object object3 = null;
            do {
                bl = iterator.hasNext();
                n = 0;
                if (!bl) break;
                object = iterator.next();
                Object object4 = this.zzaxT.get(object);
                if (map.get(object) != null) {
                    n = map.get(object).zzaEf ? 1 : 2;
                }
                arrayMap.put((Api<?>)object, n);
                zzzy zzzy2 = new zzzy((Api<?>)object, n);
                arrayList.add(zzzy2);
                Api.zza zza2 = object.zzuG();
                if (zza2.getPriority() == 1) {
                    object2 = object;
                }
                zzzy2 = Builder.zza(zza2, object4, this.mContext, this.zzrx, zzg2, zzzy2, zzzy2);
                arrayMap2.put(object.zzuH(), (Api.zze)((Object)zzzy2));
                object4 = object3;
                if (zzzy2.zzqS()) {
                    if (object3 != null) {
                        object2 = String.valueOf(object.getName());
                        object3 = String.valueOf(object3.getName());
                        object = new StringBuilder(21 + String.valueOf(object2).length() + String.valueOf(object3).length());
                        object.append((String)object2);
                        object.append(" cannot be used with ");
                        object.append((String)object3);
                        throw new IllegalStateException(object.toString());
                    }
                    object4 = object;
                }
                object3 = object4;
            } while (true);
            if (object3 != null) {
                if (object2 != null) {
                    object3 = String.valueOf(object3.getName());
                    object2 = String.valueOf(object2.getName());
                    object = new StringBuilder(21 + String.valueOf(object3).length() + String.valueOf(object2).length());
                    object.append((String)object3);
                    object.append(" cannot be used with ");
                    object.append((String)object2);
                    throw new IllegalStateException(object.toString());
                }
                bl = this.zzagg == null;
                zzac.zza(bl, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", object3.getName());
                zzac.zza(this.zzaxN.equals(this.zzaxO), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", object3.getName());
            }
            n = zzaal.zza(arrayMap2.values(), true);
            return new zzaal(this.mContext, new ReentrantLock(), this.zzrx, zzg2, this.zzaxX, this.zzaxY, arrayMap, this.zzaxZ, this.zzaya, arrayMap2, this.zzaxV, n, arrayList, false);
        }

        public Builder addApi(@NonNull Api<? extends Api.ApiOptions.NotRequiredOptions> object) {
            zzac.zzb(object, (Object)"Api must not be null");
            this.zzaxT.put((Api<?>)object, (Api.ApiOptions)null);
            object = object.zzuF().zzp(null);
            this.zzaxO.addAll((Collection<Scope>)object);
            this.zzaxN.addAll((Collection<Scope>)object);
            return this;
        }

        public <O extends Api.ApiOptions.HasOptions> Builder addApi(@NonNull Api<O> object, @NonNull O o) {
            zzac.zzb(object, (Object)"Api must not be null");
            zzac.zzb(o, (Object)"Null options are not permitted for this Api");
            this.zzaxT.put((Api<?>)object, o);
            object = object.zzuF().zzp(o);
            this.zzaxO.addAll((Collection<Scope>)object);
            this.zzaxN.addAll((Collection<Scope>)object);
            return this;
        }

        public /* varargs */ <O extends Api.ApiOptions.HasOptions> Builder addApiIfAvailable(@NonNull Api<O> api, @NonNull O o, Scope ... arrscope) {
            zzac.zzb(api, (Object)"Api must not be null");
            zzac.zzb(o, (Object)"Null options are not permitted for this Api");
            this.zzaxT.put(api, o);
            this.zza(api, o, 1, arrscope);
            return this;
        }

        public /* varargs */ Builder addApiIfAvailable(@NonNull Api<? extends Api.ApiOptions.NotRequiredOptions> api, Scope ... arrscope) {
            zzac.zzb(api, (Object)"Api must not be null");
            this.zzaxT.put(api, null);
            this.zza(api, null, 1, arrscope);
            return this;
        }

        public Builder addConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
            zzac.zzb(connectionCallbacks, (Object)"Listener must not be null");
            this.zzaxZ.add(connectionCallbacks);
            return this;
        }

        public Builder addOnConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
            zzac.zzb(onConnectionFailedListener, (Object)"Listener must not be null");
            this.zzaya.add(onConnectionFailedListener);
            return this;
        }

        public Builder addScope(@NonNull Scope scope) {
            zzac.zzb(scope, (Object)"Scope must not be null");
            this.zzaxN.add(scope);
            return this;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public GoogleApiClient build() {
            zzac.zzb(this.zzaxT.isEmpty() ^ true, (Object)"must call addApi() to add at least one API");
            GoogleApiClient googleApiClient = this.zzuQ();
            Set set = zzaxM;
            // MONITORENTER : set
            zzaxM.add(googleApiClient);
            // MONITOREXIT : set
            if (this.zzaxV < 0) return googleApiClient;
            this.zzf(googleApiClient);
            return googleApiClient;
        }

        public Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, int n, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            return this.zza(new zzaav(fragmentActivity), n, onConnectionFailedListener);
        }

        public Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            return this.enableAutoManage(fragmentActivity, 0, onConnectionFailedListener);
        }

        public Builder setAccountName(String string) {
            string = string == null ? null : new Account(string, "com.google");
            this.zzagg = string;
            return this;
        }

        public Builder setGravityForPopups(int n) {
            this.zzaxP = n;
            return this;
        }

        public Builder setHandler(@NonNull Handler handler) {
            zzac.zzb(handler, (Object)"Handler must not be null");
            this.zzrx = handler.getLooper();
            return this;
        }

        public Builder setViewForPopups(@NonNull View view) {
            zzac.zzb(view, (Object)"View must not be null");
            this.zzaxQ = view;
            return this;
        }

        public Builder useDefaultAccount() {
            return this.setAccountName("<<default account>>");
        }

        public zzg zzuP() {
            zzaxo zzaxo2 = zzaxo.zzbCg;
            if (this.zzaxT.containsKey(zzaxm.API)) {
                zzaxo2 = (zzaxo)this.zzaxT.get(zzaxm.API);
            }
            return new zzg(this.zzagg, this.zzaxN, this.zzaxS, this.zzaxP, this.zzaxQ, this.zzahp, this.zzaxR, zzaxo2);
        }
    }

    public static interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        public void onConnected(@Nullable Bundle var1);

        public void onConnectionSuspended(int var1);
    }

    public static interface OnConnectionFailedListener {
        public void onConnectionFailed(@NonNull ConnectionResult var1);
    }

}
