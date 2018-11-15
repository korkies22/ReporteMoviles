/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.View
 */
package com.google.android.gms.dynamic;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.google.android.gms.dynamic.zzc;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;

public final class zzh
extends zzc.zza {
    private Fragment zzaQq;

    private zzh(Fragment fragment) {
        this.zzaQq = fragment;
    }

    public static zzh zza(Fragment fragment) {
        if (fragment != null) {
            return new zzh(fragment);
        }
        return null;
    }

    @Override
    public Bundle getArguments() {
        return this.zzaQq.getArguments();
    }

    @Override
    public int getId() {
        return this.zzaQq.getId();
    }

    @Override
    public boolean getRetainInstance() {
        return this.zzaQq.getRetainInstance();
    }

    @Override
    public String getTag() {
        return this.zzaQq.getTag();
    }

    @Override
    public int getTargetRequestCode() {
        return this.zzaQq.getTargetRequestCode();
    }

    @Override
    public boolean getUserVisibleHint() {
        return this.zzaQq.getUserVisibleHint();
    }

    @Override
    public zzd getView() {
        return zze.zzA(this.zzaQq.getView());
    }

    @Override
    public boolean isAdded() {
        return this.zzaQq.isAdded();
    }

    @Override
    public boolean isDetached() {
        return this.zzaQq.isDetached();
    }

    @Override
    public boolean isHidden() {
        return this.zzaQq.isHidden();
    }

    @Override
    public boolean isInLayout() {
        return this.zzaQq.isInLayout();
    }

    @Override
    public boolean isRemoving() {
        return this.zzaQq.isRemoving();
    }

    @Override
    public boolean isResumed() {
        return this.zzaQq.isResumed();
    }

    @Override
    public boolean isVisible() {
        return this.zzaQq.isVisible();
    }

    @Override
    public void setHasOptionsMenu(boolean bl) {
        this.zzaQq.setHasOptionsMenu(bl);
    }

    @Override
    public void setMenuVisibility(boolean bl) {
        this.zzaQq.setMenuVisibility(bl);
    }

    @Override
    public void setRetainInstance(boolean bl) {
        this.zzaQq.setRetainInstance(bl);
    }

    @Override
    public void setUserVisibleHint(boolean bl) {
        this.zzaQq.setUserVisibleHint(bl);
    }

    @Override
    public void startActivity(Intent intent) {
        this.zzaQq.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int n) {
        this.zzaQq.startActivityForResult(intent, n);
    }

    @Override
    public zzd zzAZ() {
        return zze.zzA(this.zzaQq.getActivity());
    }

    @Override
    public zzc zzBa() {
        return zzh.zza(this.zzaQq.getParentFragment());
    }

    @Override
    public zzd zzBb() {
        return zze.zzA(this.zzaQq.getResources());
    }

    @Override
    public zzc zzBc() {
        return zzh.zza(this.zzaQq.getTargetFragment());
    }

    @Override
    public void zzC(zzd zzd2) {
        zzd2 = (View)zze.zzE(zzd2);
        this.zzaQq.registerForContextMenu((View)zzd2);
    }

    @Override
    public void zzD(zzd zzd2) {
        zzd2 = (View)zze.zzE(zzd2);
        this.zzaQq.unregisterForContextMenu((View)zzd2);
    }
}
