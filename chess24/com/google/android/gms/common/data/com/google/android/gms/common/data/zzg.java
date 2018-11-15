/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.data.zzb;
import com.google.android.gms.common.data.zzc;
import java.util.NoSuchElementException;

public class zzg<T>
extends zzb<T> {
    private T zzaCF;

    public zzg(DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            int n = this.zzaCk;
            StringBuilder stringBuilder = new StringBuilder(46);
            stringBuilder.append("Cannot advance the iterator beyond ");
            stringBuilder.append(n);
            throw new NoSuchElementException(stringBuilder.toString());
        }
        ++this.zzaCk;
        if (this.zzaCk == 0) {
            this.zzaCF = this.zzaCj.get(0);
            if (!(this.zzaCF instanceof zzc)) {
                String string = String.valueOf(this.zzaCF.getClass());
                StringBuilder stringBuilder = new StringBuilder(44 + String.valueOf(string).length());
                stringBuilder.append("DataBuffer reference of type ");
                stringBuilder.append(string);
                stringBuilder.append(" is not movable");
                throw new IllegalStateException(stringBuilder.toString());
            }
        } else {
            ((zzc)this.zzaCF).zzcA(this.zzaCk);
        }
        return this.zzaCF;
    }
}
