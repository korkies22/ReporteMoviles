/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 */
package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;
import com.google.android.gms.tagmanager.zzbo;

class zzo
implements ContainerHolder {
    private boolean zzaKt;
    private Status zzahq;
    private Container zzbDa;
    private Container zzbDb;
    private zzb zzbDc;
    private zza zzbDd;
    private TagManager zzbDe;
    private final Looper zzrx;

    public zzo(Status status) {
        this.zzahq = status;
        this.zzrx = null;
    }

    public zzo(TagManager tagManager, Looper looper, Container container, zza zza2) {
        this.zzbDe = tagManager;
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.zzrx = looper;
        this.zzbDa = container;
        this.zzbDd = zza2;
        this.zzahq = Status.zzayh;
        tagManager.zza(this);
    }

    private void zzOD() {
        if (this.zzbDc != null) {
            this.zzbDc.zzha(this.zzbDb.zzOA());
        }
    }

    @Override
    public Container getContainer() {
        synchronized (this) {
            block5 : {
                if (!this.zzaKt) break block5;
                zzbo.e("ContainerHolder is released.");
                return null;
            }
            if (this.zzbDb != null) {
                this.zzbDa = this.zzbDb;
                this.zzbDb = null;
            }
            Container container = this.zzbDa;
            return container;
        }
    }

    String getContainerId() {
        if (this.zzaKt) {
            zzbo.e("getContainerId called on a released ContainerHolder.");
            return "";
        }
        return this.zzbDa.getContainerId();
    }

    @Override
    public Status getStatus() {
        return this.zzahq;
    }

    @Override
    public void refresh() {
        synchronized (this) {
            if (this.zzaKt) {
                zzbo.e("Refreshing a released ContainerHolder.");
                return;
            }
            this.zzbDd.zzOE();
            return;
        }
    }

    @Override
    public void release() {
        synchronized (this) {
            if (this.zzaKt) {
                zzbo.e("Releasing a released ContainerHolder.");
                return;
            }
            this.zzaKt = true;
            this.zzbDe.zzb(this);
            this.zzbDa.release();
            this.zzbDa = null;
            this.zzbDb = null;
            this.zzbDd = null;
            this.zzbDc = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setContainerAvailableListener(ContainerHolder.ContainerAvailableListener containerAvailableListener) {
        synchronized (this) {
            if (this.zzaKt) {
                zzbo.e("ContainerHolder is released.");
                return;
            }
            if (containerAvailableListener == null) {
                this.zzbDc = null;
            } else {
                this.zzbDc = new zzb(containerAvailableListener, this.zzrx);
                if (this.zzbDb != null) {
                    this.zzOD();
                }
            }
            return;
        }
    }

    String zzOC() {
        if (this.zzaKt) {
            zzbo.e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
            return "";
        }
        return this.zzbDd.zzOC();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void zza(Container container) {
        synchronized (this) {
            boolean bl = this.zzaKt;
            if (bl) {
                return;
            }
            if (container == null) {
                zzbo.e("Unexpected null container.");
                return;
            }
            this.zzbDb = container;
            this.zzOD();
            return;
        }
    }

    public void zzgX(String string) {
        synchronized (this) {
            block4 : {
                boolean bl = this.zzaKt;
                if (!bl) break block4;
                return;
            }
            this.zzbDa.zzgX(string);
            return;
        }
    }

    void zzgZ(String string) {
        if (this.zzaKt) {
            zzbo.e("setCtfeUrlPathAndQuery called on a released ContainerHolder.");
            return;
        }
        this.zzbDd.zzgZ(string);
    }

    public static interface zza {
        public String zzOC();

        public void zzOE();

        public void zzgZ(String var1);
    }

    private class zzb
    extends Handler {
        private final ContainerHolder.ContainerAvailableListener zzbDf;

        public zzb(ContainerHolder.ContainerAvailableListener containerAvailableListener, Looper looper) {
            super(looper);
            this.zzbDf = containerAvailableListener;
        }

        public void handleMessage(Message message) {
            if (message.what != 1) {
                zzbo.e("Don't know how to handle this message.");
                return;
            }
            this.zzhb((String)message.obj);
        }

        public void zzha(String string) {
            this.sendMessage(this.obtainMessage(1, (Object)string));
        }

        protected void zzhb(String string) {
            this.zzbDf.onContainerAvailable(zzo.this, string);
        }
    }

}
