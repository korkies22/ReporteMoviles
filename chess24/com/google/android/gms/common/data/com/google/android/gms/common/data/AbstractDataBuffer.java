/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzb;
import com.google.android.gms.common.data.zzg;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T>
implements DataBuffer<T> {
    protected final DataHolder zzazI;

    protected AbstractDataBuffer(DataHolder dataHolder) {
        this.zzazI = dataHolder;
    }

    @Deprecated
    @Override
    public final void close() {
        this.release();
    }

    @Override
    public abstract T get(int var1);

    @Override
    public int getCount() {
        if (this.zzazI == null) {
            return 0;
        }
        return this.zzazI.getCount();
    }

    @Deprecated
    @Override
    public boolean isClosed() {
        if (this.zzazI != null && !this.zzazI.isClosed()) {
            return false;
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new zzb<T>(this);
    }

    @Override
    public void release() {
        if (this.zzazI != null) {
            this.zzazI.close();
        }
    }

    @Override
    public Iterator<T> singleRefIterator() {
        return new zzg<T>(this);
    }

    @Override
    public Bundle zzwy() {
        return this.zzazI.zzwy();
    }
}
