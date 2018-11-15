/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.zzb;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzaac;
import com.google.android.gms.internal.zzaal;
import com.google.android.gms.internal.zzzs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

private class zzaac.zza
implements OnFailureListener,
OnSuccessListener<Void> {
    private zzaac.zza() {
    }

    @Nullable
    private ConnectionResult zzvs() {
        Iterator iterator = zzaac.this.zzazu.keySet().iterator();
        ConnectionResult connectionResult = null;
        int n = 0;
        while (iterator.hasNext()) {
            int n2;
            Api api = (Api)iterator.next();
            ConnectionResult connectionResult2 = (ConnectionResult)zzaac.this.zzazz.get(((com.google.android.gms.common.api.zzc)zzaac.this.zzazt.get(api.zzuH())).getApiKey());
            if (connectionResult2.isSuccess() || (n2 = ((Integer)zzaac.this.zzazu.get(api)).intValue()) == 2 || n2 == 1 && !connectionResult2.hasResolution() && !zzaac.this.zzazw.isUserResolvableError(connectionResult2.getErrorCode())) continue;
            n2 = api.zzuF().getPriority();
            if (connectionResult != null && n <= n2) continue;
            n = n2;
            connectionResult = connectionResult2;
        }
        return connectionResult;
    }

    private void zzvt() {
        if (zzaac.this.zzazs == null) {
            zzaac.zzd((zzaac)zzaac.this).zzaAs = Collections.emptySet();
            return;
        }
        HashSet<Scope> hashSet = new HashSet<Scope>(zzaac.this.zzazs.zzxe());
        Map<Api<?>, zzg.zza> map = zzaac.this.zzazs.zzxg();
        for (Api<?> api : map.keySet()) {
            ConnectionResult connectionResult = (ConnectionResult)zzaac.this.zzazz.get(((com.google.android.gms.common.api.zzc)zzaac.this.zzazt.get(api.zzuH())).getApiKey());
            if (connectionResult == null || !connectionResult.isSuccess()) continue;
            hashSet.addAll(map.get(api).zzajm);
        }
        zzaac.zzd((zzaac)zzaac.this).zzaAs = hashSet;
    }

    @Override
    public void onFailure(@NonNull Exception exception) {
        exception = (zzb)exception;
        zzaac.this.zzazn.lock();
        try {
            zzaac.this.zzazz = exception.zzuK();
            zzaac.this.zzazA = this.zzvs();
            if (zzaac.this.zzazA == null) {
                this.zzvt();
                zzaac.this.zzazv.zzo(null);
            } else {
                zzaac.this.zzazy = false;
                zzaac.this.zzazv.zzc(zzaac.this.zzazA);
            }
            zzaac.this.zzazx.signalAll();
            return;
        }
        finally {
            zzaac.this.zzazn.unlock();
        }
    }

    @Override
    public /* synthetic */ void onSuccess(Object object) {
        this.zza((Void)object);
    }

    public void zza(Void object) {
        zzaac.this.zzazn.lock();
        try {
            zzaac.this.zzazz = new ArrayMap(zzaac.this.zzazt.size());
            for (Api.zzc zzc2 : zzaac.this.zzazt.keySet()) {
                zzaac.this.zzazz.put(((com.google.android.gms.common.api.zzc)zzaac.this.zzazt.get(zzc2)).getApiKey(), ConnectionResult.zzawX);
            }
            this.zzvt();
            zzaac.this.zzazv.zzo(null);
            zzaac.this.zzazx.signalAll();
            return;
        }
        finally {
            zzaac.this.zzazn.unlock();
        }
    }
}
