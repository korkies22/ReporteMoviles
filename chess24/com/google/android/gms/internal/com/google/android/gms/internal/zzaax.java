/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 */
package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.google.android.gms.internal.zzaaw;

public interface zzaax {
    public void startActivityForResult(Intent var1, int var2);

    public <T extends zzaaw> T zza(String var1, Class<T> var2);

    public void zza(String var1, @NonNull zzaaw var2);

    public Activity zzwo();
}
