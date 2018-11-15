/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 */
package com.google.android.gms.dynamic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zza;

class zza
implements zza.zza {
    final /* synthetic */ FrameLayout zzaQj;
    final /* synthetic */ LayoutInflater zzaQk;
    final /* synthetic */ ViewGroup zzaQl;
    final /* synthetic */ Bundle zzxd;

    zza(FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.zzaQj = frameLayout;
        this.zzaQk = layoutInflater;
        this.zzaQl = viewGroup;
        this.zzxd = bundle;
    }

    @Override
    public int getState() {
        return 2;
    }

    @Override
    public void zzb(LifecycleDelegate lifecycleDelegate) {
        this.zzaQj.removeAllViews();
        this.zzaQj.addView(zza.this.zzaQd.onCreateView(this.zzaQk, this.zzaQl, this.zzxd));
    }
}
