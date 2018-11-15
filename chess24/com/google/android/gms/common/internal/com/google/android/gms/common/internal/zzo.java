/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.common.internal;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzn;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class zzo
extends zzn
implements Handler.Callback {
    private final Handler mHandler;
    private final HashMap<zza, zzb> zzaEF = new HashMap();
    private final com.google.android.gms.common.stats.zza zzaEG;
    private final long zzaEH;
    private final Context zzvZ;

    zzo(Context context) {
        this.zzvZ = context.getApplicationContext();
        this.mHandler = new Handler(context.getMainLooper(), (Handler.Callback)this);
        this.zzaEG = com.google.android.gms.common.stats.zza.zzyc();
        this.zzaEH = 5000L;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean zza(zza object, ServiceConnection object2, String string) {
        zzac.zzb(object2, (Object)"ServiceConnection must not be null");
        HashMap<zza, zzb> hashMap = this.zzaEF;
        synchronized (hashMap) {
            zzb zzb2 = this.zzaEF.get(object);
            if (zzb2 == null) {
                zzb2 = new zzb((zza)object);
                zzb2.zza((ServiceConnection)object2, string);
                zzb2.zzdr(string);
                this.zzaEF.put((zza)object, zzb2);
                object = zzb2;
                return object.isBound();
            } else {
                this.mHandler.removeMessages(0, object);
                if (zzb2.zza((ServiceConnection)object2)) {
                    object = String.valueOf(object);
                    object2 = new StringBuilder(81 + String.valueOf(object).length());
                    object2.append("Trying to bind a GmsServiceConnection that was already connected before.  config=");
                    object2.append((String)object);
                    throw new IllegalStateException(object2.toString());
                }
                zzb2.zza((ServiceConnection)object2, string);
                switch (zzb2.getState()) {
                    case 2: {
                        zzb2.zzdr(string);
                        object = zzb2;
                        return object.isBound();
                    }
                    case 1: {
                        object2.onServiceConnected(zzb2.getComponentName(), zzb2.getBinder());
                        object = zzb2;
                        return object.isBound();
                    }
                    default: {
                        object = zzb2;
                    }
                }
            }
            return object.isBound();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzb(zza object, ServiceConnection object2, String string) {
        zzac.zzb(object2, (Object)"ServiceConnection must not be null");
        HashMap<zza, zzb> hashMap = this.zzaEF;
        synchronized (hashMap) {
            zzb zzb2 = this.zzaEF.get(object);
            if (zzb2 == null) {
                object = String.valueOf(object);
                object2 = new StringBuilder(50 + String.valueOf(object).length());
                object2.append("Nonexistent connection status for service config: ");
                object2.append((String)object);
                throw new IllegalStateException(object2.toString());
            }
            if (!zzb2.zza((ServiceConnection)object2)) {
                object = String.valueOf(object);
                object2 = new StringBuilder(76 + String.valueOf(object).length());
                object2.append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=");
                object2.append((String)object);
                throw new IllegalStateException(object2.toString());
            }
            zzb2.zzb((ServiceConnection)object2, string);
            if (zzb2.zzxt()) {
                object = this.mHandler.obtainMessage(0, object);
                this.mHandler.sendMessageDelayed((Message)object, this.zzaEH);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean handleMessage(Message object) {
        if (object.what != 0) {
            return false;
        }
        zza zza2 = (zza)object.obj;
        object = this.zzaEF;
        synchronized (object) {
            zzb zzb2 = this.zzaEF.get(zza2);
            if (zzb2 != null && zzb2.zzxt()) {
                if (zzb2.isBound()) {
                    zzb2.zzds("GmsClientSupervisor");
                }
                this.zzaEF.remove(zza2);
            }
            return true;
        }
    }

    @Override
    public boolean zza(ComponentName componentName, ServiceConnection serviceConnection, String string) {
        return this.zza(new zza(componentName), serviceConnection, string);
    }

    @Override
    public boolean zza(String string, String string2, ServiceConnection serviceConnection, String string3) {
        return this.zza(new zza(string, string2), serviceConnection, string3);
    }

    @Override
    public void zzb(ComponentName componentName, ServiceConnection serviceConnection, String string) {
        this.zzb(new zza(componentName), serviceConnection, string);
    }

    @Override
    public void zzb(String string, String string2, ServiceConnection serviceConnection, String string3) {
        this.zzb(new zza(string, string2), serviceConnection, string3);
    }

    private static final class zza {
        private final String zzaEI;
        private final ComponentName zzaEJ;
        private final String zzaca;

        public zza(ComponentName componentName) {
            this.zzaca = null;
            this.zzaEI = null;
            this.zzaEJ = zzac.zzw(componentName);
        }

        public zza(String string, String string2) {
            this.zzaca = zzac.zzdv(string);
            this.zzaEI = zzac.zzdv(string2);
            this.zzaEJ = null;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof zza)) {
                return false;
            }
            object = (zza)object;
            if (zzaa.equal(this.zzaca, object.zzaca) && zzaa.equal((Object)this.zzaEJ, (Object)object.zzaEJ)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return zzaa.hashCode(new Object[]{this.zzaca, this.zzaEJ});
        }

        public String toString() {
            if (this.zzaca == null) {
                return this.zzaEJ.flattenToString();
            }
            return this.zzaca;
        }

        public Intent zzxs() {
            if (this.zzaca != null) {
                return new Intent(this.zzaca).setPackage(this.zzaEI);
            }
            return new Intent().setComponent(this.zzaEJ);
        }
    }

    private final class zzb {
        private int mState;
        private ComponentName zzaEJ;
        private final zzb$zza zzaEK;
        private final Set<ServiceConnection> zzaEL;
        private boolean zzaEM;
        private final zza zzaEN;
        private IBinder zzaEa;

        public zzb(zza zza2) {
            this.zzaEN = zza2;
            this.zzaEK = new zzb$zza();
            this.zzaEL = new HashSet<ServiceConnection>();
            this.mState = 2;
        }

        public IBinder getBinder() {
            return this.zzaEa;
        }

        public ComponentName getComponentName() {
            return this.zzaEJ;
        }

        public int getState() {
            return this.mState;
        }

        public boolean isBound() {
            return this.zzaEM;
        }

        public void zza(ServiceConnection serviceConnection, String string) {
            zzo.this.zzaEG.zza(zzo.this.zzvZ, serviceConnection, string, this.zzaEN.zzxs());
            this.zzaEL.add(serviceConnection);
        }

        public boolean zza(ServiceConnection serviceConnection) {
            return this.zzaEL.contains((Object)serviceConnection);
        }

        public void zzb(ServiceConnection serviceConnection, String string) {
            zzo.this.zzaEG.zzb(zzo.this.zzvZ, serviceConnection);
            this.zzaEL.remove((Object)serviceConnection);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @TargetApi(value=14)
        public void zzdr(String string) {
            this.mState = 3;
            this.zzaEM = zzo.this.zzaEG.zza(zzo.this.zzvZ, string, this.zzaEN.zzxs(), this.zzaEK, 129);
            if (this.zzaEM) return;
            this.mState = 2;
            try {
                zzo.this.zzaEG.zza(zzo.this.zzvZ, this.zzaEK);
                return;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return;
            }
        }

        public void zzds(String string) {
            zzo.this.zzaEG.zza(zzo.this.zzvZ, this.zzaEK);
            this.zzaEM = false;
            this.mState = 2;
        }

        public boolean zzxt() {
            return this.zzaEL.isEmpty();
        }
    }

    public class zzb$zza
    implements ServiceConnection {
        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            HashMap hashMap = zzo.this.zzaEF;
            synchronized (hashMap) {
                zzb.this.zzaEa = iBinder;
                zzb.this.zzaEJ = componentName;
                Iterator iterator = zzb.this.zzaEL.iterator();
                do {
                    if (!iterator.hasNext()) {
                        zzb.this.mState = 1;
                        return;
                    }
                    ((ServiceConnection)iterator.next()).onServiceConnected(componentName, iBinder);
                } while (true);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onServiceDisconnected(ComponentName componentName) {
            HashMap hashMap = zzo.this.zzaEF;
            synchronized (hashMap) {
                zzb.this.zzaEa = null;
                zzb.this.zzaEJ = componentName;
                Iterator iterator = zzb.this.zzaEL.iterator();
                do {
                    if (!iterator.hasNext()) {
                        zzb.this.mState = 2;
                        return;
                    }
                    ((ServiceConnection)iterator.next()).onServiceDisconnected(componentName);
                } while (true);
            }
        }
    }

}
