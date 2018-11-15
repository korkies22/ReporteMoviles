/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.database.ContentObserver
 *  android.os.Handler
 */
package com.google.android.gms.internal;

import android.database.ContentObserver;
import android.os.Handler;
import java.util.concurrent.atomic.AtomicBoolean;

final class zzbii
extends ContentObserver {
    zzbii(Handler handler) {
        super(handler);
    }

    public void onChange(boolean bl) {
        zzbTO.set(true);
    }
}
