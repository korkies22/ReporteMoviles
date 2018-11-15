/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.data;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataHolder;
import java.util.ArrayList;

public abstract class zzf<T>
extends AbstractDataBuffer<T> {
    private boolean zzaCD = false;
    private ArrayList<Integer> zzaCE;

    protected zzf(DataHolder dataHolder) {
        super(dataHolder);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void zzwH() {
        synchronized (this) {
            if (!this.zzaCD) {
                int n = this.zzazI.getCount();
                this.zzaCE = new ArrayList();
                if (n > 0) {
                    this.zzaCE.add(0);
                    String string = this.zzwG();
                    int n2 = this.zzazI.zzcC(0);
                    CharSequence charSequence = this.zzazI.zzd(string, 0, n2);
                    for (n2 = 1; n2 < n; ++n2) {
                        int n3 = this.zzazI.zzcC(n2);
                        String string2 = this.zzazI.zzd(string, n2, n3);
                        if (string2 == null) {
                            charSequence = new StringBuilder(78 + String.valueOf(string).length());
                            charSequence.append("Missing value for markerColumn: ");
                            charSequence.append(string);
                            charSequence.append(", at row: ");
                            charSequence.append(n2);
                            charSequence.append(", for window: ");
                            charSequence.append(n3);
                            throw new NullPointerException(charSequence.toString());
                        }
                        String string3 = charSequence;
                        if (!string2.equals(charSequence)) {
                            this.zzaCE.add(n2);
                            string3 = string2;
                        }
                        charSequence = string3;
                    }
                }
                this.zzaCD = true;
            }
            return;
        }
    }

    @Override
    public final T get(int n) {
        this.zzwH();
        return this.zzn(this.zzcG(n), this.zzcH(n));
    }

    @Override
    public int getCount() {
        this.zzwH();
        return this.zzaCE.size();
    }

    int zzcG(int n) {
        if (n >= 0 && n < this.zzaCE.size()) {
            return this.zzaCE.get(n);
        }
        StringBuilder stringBuilder = new StringBuilder(53);
        stringBuilder.append("Position ");
        stringBuilder.append(n);
        stringBuilder.append(" is out of bounds for this buffer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int zzcH(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == this.zzaCE.size()) {
            return 0;
        }
        int n2 = n == this.zzaCE.size() - 1 ? this.zzazI.getCount() : this.zzaCE.get(n + 1).intValue();
        if ((n2 -= this.zzaCE.get(n).intValue()) == 1) {
            n = this.zzcG(n);
            int n3 = this.zzazI.zzcC(n);
            String string = this.zzwI();
            if (string != null && this.zzazI.zzd(string, n, n3) == null) {
                return 0;
            }
        }
        return n2;
    }

    protected abstract T zzn(int var1, int var2);

    protected abstract String zzwG();

    protected String zzwI() {
        return null;
    }
}
