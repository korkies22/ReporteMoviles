/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzbul;
import com.google.android.gms.internal.zzbum;
import com.google.android.gms.internal.zzbun;
import com.google.android.gms.internal.zzbut;
import com.google.android.gms.internal.zzbuw;
import java.io.IOException;

public static final class zzaf.zzb
extends zzbun<zzaf.zzb> {
    public Long zzcn = null;
    public Integer zzco = null;
    public Boolean zzcp = null;
    public int[] zzcq = zzbuw.zzcsi;
    public Long zzcr = null;

    public zzaf.zzb() {
        this.zzcsg = -1;
    }

    @Override
    public void zza(zzbum zzbum2) throws IOException {
        if (this.zzcn != null) {
            zzbum2.zzb(1, this.zzcn);
        }
        if (this.zzco != null) {
            zzbum2.zzF(2, this.zzco);
        }
        if (this.zzcp != null) {
            zzbum2.zzg(3, this.zzcp);
        }
        if (this.zzcq != null && this.zzcq.length > 0) {
            for (int i = 0; i < this.zzcq.length; ++i) {
                zzbum2.zzF(4, this.zzcq[i]);
            }
        }
        if (this.zzcr != null) {
            zzbum2.zza(5, this.zzcr);
        }
        super.zza(zzbum2);
    }

    @Override
    public /* synthetic */ zzbut zzb(zzbul zzbul2) throws IOException {
        return this.zzi(zzbul2);
    }

    public zzaf.zzb zzi(zzbul zzbul2) throws IOException {
        int n;
        while ((n = zzbul2.zzacu()) != 0) {
            if (n != 8) {
                if (n != 16) {
                    if (n != 24) {
                        int n2;
                        int[] arrn;
                        if (n != 32) {
                            if (n != 34) {
                                if (n != 40) {
                                    if (super.zza(zzbul2, n)) continue;
                                    return this;
                                }
                                this.zzcr = zzbul2.zzacw();
                                continue;
                            }
                            int n3 = zzbul2.zzqj(zzbul2.zzacD());
                            n = zzbul2.getPosition();
                            n2 = 0;
                            while (zzbul2.zzacI() > 0) {
                                zzbul2.zzacy();
                                ++n2;
                            }
                            zzbul2.zzql(n);
                            n = this.zzcq == null ? 0 : this.zzcq.length;
                            arrn = new int[n2 + n];
                            n2 = n;
                            if (n != 0) {
                                System.arraycopy(this.zzcq, 0, arrn, 0, n);
                                n2 = n;
                            }
                            while (n2 < arrn.length) {
                                arrn[n2] = zzbul2.zzacy();
                                ++n2;
                            }
                            this.zzcq = arrn;
                            zzbul2.zzqk(n3);
                            continue;
                        }
                        n2 = zzbuw.zzc(zzbul2, 32);
                        n = this.zzcq == null ? 0 : this.zzcq.length;
                        arrn = new int[n2 + n];
                        n2 = n;
                        if (n != 0) {
                            System.arraycopy(this.zzcq, 0, arrn, 0, n);
                            n2 = n;
                        }
                        while (n2 < arrn.length - 1) {
                            arrn[n2] = zzbul2.zzacy();
                            zzbul2.zzacu();
                            ++n2;
                        }
                        arrn[n2] = zzbul2.zzacy();
                        this.zzcq = arrn;
                        continue;
                    }
                    this.zzcp = zzbul2.zzacA();
                    continue;
                }
                this.zzco = zzbul2.zzacy();
                continue;
            }
            this.zzcn = zzbul2.zzacx();
        }
        return this;
    }

    @Override
    protected int zzv() {
        int n;
        int n2 = n = super.zzv();
        if (this.zzcn != null) {
            n2 = n + zzbum.zzf(1, this.zzcn);
        }
        n = n2;
        if (this.zzco != null) {
            n = n2 + zzbum.zzH(2, this.zzco);
        }
        n2 = n;
        if (this.zzcp != null) {
            n2 = n + zzbum.zzh(3, this.zzcp);
        }
        n = n2;
        if (this.zzcq != null) {
            n = n2;
            if (this.zzcq.length > 0) {
                int n3 = 0;
                for (n = 0; n < this.zzcq.length; ++n) {
                    n3 += zzbum.zzqp(this.zzcq[n]);
                }
                n = n2 + n3 + 1 * this.zzcq.length;
            }
        }
        n2 = n;
        if (this.zzcr != null) {
            n2 = n + zzbum.zze(5, this.zzcr);
        }
        return n2;
    }
}
