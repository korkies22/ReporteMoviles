/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.google.android.gms.common.internal;

import android.util.Log;
import com.google.android.gms.common.internal.zzf;
import java.util.ArrayList;

protected abstract class zzf.zze<TListener> {
    private TListener mListener;
    private boolean zzaDW;

    public zzf.zze(TListener TListener) {
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
