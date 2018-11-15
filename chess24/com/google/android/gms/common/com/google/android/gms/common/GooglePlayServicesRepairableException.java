/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 */
package com.google.android.gms.common;

import android.content.Intent;
import com.google.android.gms.common.UserRecoverableException;

public class GooglePlayServicesRepairableException
extends UserRecoverableException {
    private final int zzahH;

    GooglePlayServicesRepairableException(int n, String string, Intent intent) {
        super(string, intent);
        this.zzahH = n;
    }

    public int getConnectionStatusCode() {
        return this.zzahH;
    }
}
