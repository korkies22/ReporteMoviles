/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 *  android.os.Bundle
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.internal.zzaav;
import com.google.android.gms.internal.zzaax;
import com.google.android.gms.internal.zzaay;
import com.google.android.gms.internal.zzabm;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zzaaw {
    protected final zzaax zzaBs;

    protected zzaaw(zzaax zzaax2) {
        this.zzaBs = zzaax2;
    }

    protected static zzaax zzc(zzaav zzaav2) {
        if (zzaav2.zzwl()) {
            return zzabm.zza(zzaav2.zzwn());
        }
        return zzaay.zzt(zzaav2.zzwm());
    }

    public static zzaax zzs(Activity activity) {
        return zzaaw.zzc(new zzaav(activity));
    }

    @MainThread
    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
    }

    public Activity getActivity() {
        return this.zzaBs.zzwo();
    }

    @MainThread
    public void onActivityResult(int n, int n2, Intent intent) {
    }

    @MainThread
    public void onCreate(Bundle bundle) {
    }

    @MainThread
    public void onDestroy() {
    }

    @MainThread
    public void onSaveInstanceState(Bundle bundle) {
    }

    @MainThread
    public void onStart() {
    }

    @MainThread
    public void onStop() {
    }
}
