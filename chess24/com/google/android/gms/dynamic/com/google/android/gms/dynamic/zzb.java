/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.View
 */
package com.google.android.gms.dynamic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.dynamic.zzc;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;

@SuppressLint(value={"NewApi"})
public final class zzb
extends zzc.zza {
    private Fragment zzaQn;

    private zzb(Fragment fragment) {
        this.zzaQn = fragment;
    }

    public static zzb zza(Fragment fragment) {
        if (fragment != null) {
            return new zzb(fragment);
        }
        return null;
    }

    @Override
    public Bundle getArguments() {
        return this.zzaQn.getArguments();
    }

    @Override
    public int getId() {
        return this.zzaQn.getId();
    }

    @Override
    public boolean getRetainInstance() {
        return this.zzaQn.getRetainInstance();
    }

    @Override
    public String getTag() {
        return this.zzaQn.getTag();
    }

    @Override
    public int getTargetRequestCode() {
        return this.zzaQn.getTargetRequestCode();
    }

    @Override
    public boolean getUserVisibleHint() {
        return this.zzaQn.getUserVisibleHint();
    }

    @Override
    public zzd getView() {
        return zze.zzA(this.zzaQn.getView());
    }

    @Override
    public boolean isAdded() {
        return this.zzaQn.isAdded();
    }

    @Override
    public boolean isDetached() {
        return this.zzaQn.isDetached();
    }

    @Override
    public boolean isHidden() {
        return this.zzaQn.isHidden();
    }

    @Override
    public boolean isInLayout() {
        return this.zzaQn.isInLayout();
    }

    @Override
    public boolean isRemoving() {
        return this.zzaQn.isRemoving();
    }

    @Override
    public boolean isResumed() {
        return this.zzaQn.isResumed();
    }

    @Override
    public boolean isVisible() {
        return this.zzaQn.isVisible();
    }

    @Override
    public void setHasOptionsMenu(boolean bl) {
        this.zzaQn.setHasOptionsMenu(bl);
    }

    @Override
    public void setMenuVisibility(boolean bl) {
        this.zzaQn.setMenuVisibility(bl);
    }

    @Override
    public void setRetainInstance(boolean bl) {
        this.zzaQn.setRetainInstance(bl);
    }

    @Override
    public void setUserVisibleHint(boolean bl) {
        this.zzaQn.setUserVisibleHint(bl);
    }

    @Override
    public void startActivity(Intent intent) {
        this.zzaQn.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int n) {
        this.zzaQn.startActivityForResult(intent, n);
    }

    @Override
    public zzd zzAZ() {
        return zze.zzA(this.zzaQn.getActivity());
    }

    @Override
    public zzc zzBa() {
        return zzb.zza(this.zzaQn.getParentFragment());
    }

    @Override
    public zzd zzBb() {
        return zze.zzA(this.zzaQn.getResources());
    }

    @Override
    public zzc zzBc() {
        return zzb.zza(this.zzaQn.getTargetFragment());
    }

    @Override
    public void zzC(zzd zzd2) {
        zzd2 = (View)zze.zzE(zzd2);
        this.zzaQn.registerForContextMenu((View)zzd2);
    }

    @Override
    public void zzD(zzd zzd2) {
        zzd2 = (View)zze.zzE(zzd2);
        this.zzaQn.unregisterForContextMenu((View)zzd2);
    }
}
