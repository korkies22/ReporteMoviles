/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.internal.zzac;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class zzb<T>
implements Iterator<T> {
    protected final DataBuffer<T> zzaCj;
    protected int zzaCk;

    public zzb(DataBuffer<T> dataBuffer) {
        this.zzaCj = zzac.zzw(dataBuffer);
        this.zzaCk = -1;
    }

    @Override
    public boolean hasNext() {
        if (this.zzaCk < this.zzaCj.getCount() - 1) {
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        int n;
        if (!this.hasNext()) {
            int n2 = this.zzaCk;
            StringBuilder stringBuilder = new StringBuilder(46);
            stringBuilder.append("Cannot advance the iterator beyond ");
            stringBuilder.append(n2);
            throw new NoSuchElementException(stringBuilder.toString());
        }
        DataBuffer<T> dataBuffer = this.zzaCj;
        this.zzaCk = n = this.zzaCk + 1;
        return dataBuffer.get(n);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
