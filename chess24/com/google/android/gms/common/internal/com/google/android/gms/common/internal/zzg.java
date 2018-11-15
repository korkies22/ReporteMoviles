/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.content.Context
 *  android.view.View
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzaxo;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zzg {
    private final Set<Scope> zzaEb;
    private final Map<Api<?>, zza> zzaEc;
    private final zzaxo zzaEd;
    private Integer zzaEe;
    private final Account zzagg;
    private final String zzahp;
    private final Set<Scope> zzaxN;
    private final int zzaxP;
    private final View zzaxQ;
    private final String zzaxR;

    public zzg(Account object, Set<Scope> object2, Map<Api<?>, zza> map, int n, View view, String string, String string2, zzaxo zzaxo2) {
        this.zzagg = object;
        object = object2 == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(object2);
        this.zzaxN = object;
        object = map;
        if (map == null) {
            object = Collections.EMPTY_MAP;
        }
        this.zzaEc = object;
        this.zzaxQ = view;
        this.zzaxP = n;
        this.zzahp = string;
        this.zzaxR = string2;
        this.zzaEd = zzaxo2;
        object = new HashSet<Scope>(this.zzaxN);
        object2 = this.zzaEc.values().iterator();
        while (object2.hasNext()) {
            object.addAll(((zza)object2.next()).zzajm);
        }
        this.zzaEb = Collections.unmodifiableSet(object);
    }

    public static zzg zzaA(Context context) {
        return new GoogleApiClient.Builder(context).zzuP();
    }

    public Account getAccount() {
        return this.zzagg;
    }

    @Deprecated
    public String getAccountName() {
        if (this.zzagg != null) {
            return this.zzagg.name;
        }
        return null;
    }

    public Set<Scope> zzc(Api<?> object) {
        if ((object = this.zzaEc.get(object)) != null && !object.zzajm.isEmpty()) {
            HashSet<Scope> hashSet = new HashSet<Scope>(this.zzaxN);
            hashSet.addAll(object.zzajm);
            return hashSet;
        }
        return this.zzaxN;
    }

    public void zzc(Integer n) {
        this.zzaEe = n;
    }

    public Account zzwU() {
        if (this.zzagg != null) {
            return this.zzagg;
        }
        return new Account("<<default account>>", "com.google");
    }

    public int zzxd() {
        return this.zzaxP;
    }

    public Set<Scope> zzxe() {
        return this.zzaxN;
    }

    public Set<Scope> zzxf() {
        return this.zzaEb;
    }

    public Map<Api<?>, zza> zzxg() {
        return this.zzaEc;
    }

    public String zzxh() {
        return this.zzahp;
    }

    public String zzxi() {
        return this.zzaxR;
    }

    public View zzxj() {
        return this.zzaxQ;
    }

    public zzaxo zzxk() {
        return this.zzaEd;
    }

    public Integer zzxl() {
        return this.zzaEe;
    }

    public static final class zza {
        public final boolean zzaEf;
        public final Set<Scope> zzajm;

        public zza(Set<Scope> set, boolean bl) {
            zzac.zzw(set);
            this.zzajm = Collections.unmodifiableSet(set);
            this.zzaEf = bl;
        }
    }

}
