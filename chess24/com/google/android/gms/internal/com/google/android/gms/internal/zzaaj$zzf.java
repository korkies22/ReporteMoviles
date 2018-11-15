/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.internal.zzaaj;

private abstract class zzaaj.zzf
implements Runnable {
    private zzaaj.zzf() {
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @WorkerThread
    @Override
    public void run() {
        block7 : {
            block6 : {
                zzaaj.zzc(zzaaj.this).lock();
                var1_1 = Thread.interrupted();
                if (!var1_1) break block6;
                zzaaj.zzc(zzaaj.this).unlock();
                return;
            }
            this.zzvA();
lbl10: // 2 sources:
            do {
                zzaaj.zzc(zzaaj.this).unlock();
                return;
                break;
            } while (true);
            {
                catch (Throwable var2_2) {
                    break block7;
                }
                catch (RuntimeException var2_3) {}
                {
                    zzaaj.zzd(zzaaj.this).zza(var2_3);
                    ** continue;
                }
            }
        }
        zzaaj.zzc(zzaaj.this).unlock();
        throw var2_2;
    }

    @WorkerThread
    protected abstract void zzvA();
}
