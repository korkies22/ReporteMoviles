/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

public static abstract class DiffUtil.Callback {
    public abstract boolean areContentsTheSame(int var1, int var2);

    public abstract boolean areItemsTheSame(int var1, int var2);

    @Nullable
    public Object getChangePayload(int n, int n2) {
        return null;
    }

    public abstract int getNewListSize();

    public abstract int getOldListSize();
}
