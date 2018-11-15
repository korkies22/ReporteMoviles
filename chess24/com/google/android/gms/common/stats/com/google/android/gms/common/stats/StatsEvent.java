/*
 * Decompiled with CFR 0_134.
 */
package com.google.android.gms.common.stats;

import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

public abstract class StatsEvent
extends zza
implements ReflectedParcelable {
    public abstract int getEventType();

    public abstract long getTimeMillis();

    public String toString() {
        long l = this.getTimeMillis();
        String string = String.valueOf("\t");
        int n = this.getEventType();
        String string2 = String.valueOf("\t");
        long l2 = this.zzye();
        String string3 = String.valueOf(this.zzyf());
        StringBuilder stringBuilder = new StringBuilder(51 + String.valueOf(string).length() + String.valueOf(string2).length() + String.valueOf(string3).length());
        stringBuilder.append(l);
        stringBuilder.append(string);
        stringBuilder.append(n);
        stringBuilder.append(string2);
        stringBuilder.append(l2);
        stringBuilder.append(string3);
        return stringBuilder.toString();
    }

    public abstract long zzye();

    public abstract String zzyf();
}
