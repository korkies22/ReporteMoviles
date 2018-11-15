/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.security;

import android.content.Intent;
import com.google.android.gms.security.ProviderInstaller;

public static interface ProviderInstaller.ProviderInstallListener {
    public void onProviderInstallFailed(int var1, Intent var2);

    public void onProviderInstalled();
}
