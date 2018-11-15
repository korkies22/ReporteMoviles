/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.v7.util.DiffUtil;

public static abstract class DiffUtil.ItemCallback<T> {
    public abstract boolean areContentsTheSame(T var1, T var2);

    public abstract boolean areItemsTheSame(T var1, T var2);

    public Object getChangePayload(T t, T t2) {
        return null;
    }
}
