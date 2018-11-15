/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api;

public final class zzaxo
implements Api.ApiOptions.Optional {
    public static final zzaxo zzbCg = new zza().zzOj();
    private final boolean zzajh;
    private final boolean zzajj;
    private final String zzajk;
    private final String zzajl;
    private final boolean zzbCh;
    private final boolean zzbCi;
    private final Long zzbCj;
    private final Long zzbCk;

    private zzaxo(boolean bl, boolean bl2, String string, boolean bl3, String string2, boolean bl4, Long l, Long l2) {
        this.zzbCh = bl;
        this.zzajh = bl2;
        this.zzajk = string;
        this.zzajj = bl3;
        this.zzbCi = bl4;
        this.zzajl = string2;
        this.zzbCj = l;
        this.zzbCk = l2;
    }

    public boolean zzOf() {
        return this.zzbCh;
    }

    public boolean zzOg() {
        return this.zzbCi;
    }

    @Nullable
    public Long zzOh() {
        return this.zzbCj;
    }

    @Nullable
    public Long zzOi() {
        return this.zzbCk;
    }

    public boolean zzqK() {
        return this.zzajh;
    }

    public boolean zzqM() {
        return this.zzajj;
    }

    public String zzqN() {
        return this.zzajk;
    }

    @Nullable
    public String zzqO() {
        return this.zzajl;
    }

    public static final class zza {
        public zzaxo zzOj() {
            return new zzaxo(false, false, null, false, null, false, null, null);
        }
    }

}
