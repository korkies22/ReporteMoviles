/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 */
package com.google.android.gms.common.data;

import android.content.ContentValues;
import com.google.android.gms.common.data.DataHolder;
import java.util.HashMap;

final class DataHolder
extends DataHolder.zza {
    DataHolder(String[] arrstring, String string) {
        super(arrstring, string, null);
    }

    @Override
    public DataHolder.zza zza(ContentValues contentValues) {
        throw new UnsupportedOperationException("Cannot add data to empty builder");
    }

    @Override
    public DataHolder.zza zzb(HashMap<String, Object> hashMap) {
        throw new UnsupportedOperationException("Cannot add data to empty builder");
    }
}
