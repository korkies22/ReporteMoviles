/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 */
package com.google.android.gms.auth.api.signin;

import android.accounts.Account;
import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzac;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public static final class GoogleSignInOptions.Builder {
    private Account zzagg;
    private boolean zzajh;
    private boolean zzaji;
    private boolean zzajj;
    private String zzajk;
    private String zzajl;
    private Set<Scope> zzajm = new HashSet<Scope>();

    public GoogleSignInOptions.Builder() {
    }

    public GoogleSignInOptions.Builder(@NonNull GoogleSignInOptions googleSignInOptions) {
        zzac.zzw(googleSignInOptions);
        this.zzajm = new HashSet<Scope>(googleSignInOptions.zzajg);
        this.zzaji = googleSignInOptions.zzaji;
        this.zzajj = googleSignInOptions.zzajj;
        this.zzajh = googleSignInOptions.zzajh;
        this.zzajk = googleSignInOptions.zzajk;
        this.zzagg = googleSignInOptions.zzagg;
        this.zzajl = googleSignInOptions.zzajl;
    }

    private String zzcx(String string) {
        zzac.zzdv(string);
        boolean bl = this.zzajk == null || this.zzajk.equals(string);
        zzac.zzb(bl, (Object)"two different server client ids provided");
        return string;
    }

    public GoogleSignInOptions build() {
        if (this.zzajh && (this.zzagg == null || !this.zzajm.isEmpty())) {
            this.requestId();
        }
        return new GoogleSignInOptions(this.zzajm, this.zzagg, this.zzajh, this.zzaji, this.zzajj, this.zzajk, this.zzajl, null);
    }

    public GoogleSignInOptions.Builder requestEmail() {
        this.zzajm.add(GoogleSignInOptions.zzaje);
        return this;
    }

    public GoogleSignInOptions.Builder requestId() {
        this.zzajm.add(GoogleSignInOptions.zzajf);
        return this;
    }

    public GoogleSignInOptions.Builder requestIdToken(String string) {
        this.zzajh = true;
        this.zzajk = this.zzcx(string);
        return this;
    }

    public GoogleSignInOptions.Builder requestProfile() {
        this.zzajm.add(GoogleSignInOptions.zzajd);
        return this;
    }

    public /* varargs */ GoogleSignInOptions.Builder requestScopes(Scope scope, Scope ... arrscope) {
        this.zzajm.add(scope);
        this.zzajm.addAll(Arrays.asList(arrscope));
        return this;
    }

    public GoogleSignInOptions.Builder requestServerAuthCode(String string) {
        return this.requestServerAuthCode(string, false);
    }

    public GoogleSignInOptions.Builder requestServerAuthCode(String string, boolean bl) {
        this.zzaji = true;
        this.zzajk = this.zzcx(string);
        this.zzajj = bl;
        return this;
    }

    public GoogleSignInOptions.Builder setAccountName(String string) {
        this.zzagg = new Account(zzac.zzdv(string), "com.google");
        return this;
    }

    public GoogleSignInOptions.Builder setHostedDomain(String string) {
        this.zzajl = zzac.zzdv(string);
        return this;
    }
}
