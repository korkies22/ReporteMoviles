/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class zzu {
    protected static final Comparator<byte[]> zzau = new Comparator<byte[]>(){

        @Override
        public /* synthetic */ int compare(Object object, Object object2) {
            return this.zza((byte[])object, (byte[])object2);
        }

        public int zza(byte[] arrby, byte[] arrby2) {
            return arrby.length - arrby2.length;
        }
    };
    private List<byte[]> zzaq = new LinkedList<byte[]>();
    private List<byte[]> zzar = new ArrayList<byte[]>(64);
    private int zzas = 0;
    private final int zzat;

    public zzu(int n) {
        this.zzat = n;
    }

    private void zzt() {
        synchronized (this) {
            while (this.zzas > this.zzat) {
                byte[] arrby = this.zzaq.remove(0);
                this.zzar.remove(arrby);
                this.zzas -= arrby.length;
            }
            return;
        }
    }

    public void zza(byte[] arrby) {
        synchronized (this) {
            block5 : {
                if (arrby != null) {
                    int n;
                    block6 : {
                        int n2;
                        if (arrby.length > this.zzat) break block5;
                        this.zzaq.add(arrby);
                        n = n2 = Collections.binarySearch(this.zzar, arrby, zzau);
                        if (n2 >= 0) break block6;
                        n = - n2 - 1;
                    }
                    this.zzar.add(n, arrby);
                    this.zzas += arrby.length;
                    this.zzt();
                    return;
                }
            }
            return;
        }
    }

    public byte[] zzb(int n) {
        synchronized (this) {
            byte[] arrby;
            int n2 = 0;
            do {
                block5 : {
                    if (n2 >= this.zzar.size()) break;
                    arrby = this.zzar.get(n2);
                    if (arrby.length < n) break block5;
                    this.zzas -= arrby.length;
                    this.zzar.remove(n2);
                    this.zzaq.remove(arrby);
                    return arrby;
                }
                ++n2;
            } while (true);
            arrby = new byte[n];
            return arrby;
        }
    }

}
