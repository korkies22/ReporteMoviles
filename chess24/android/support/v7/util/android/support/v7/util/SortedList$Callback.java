/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.annotation.Nullable;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;
import java.util.Comparator;

public static abstract class SortedList.Callback<T2>
implements Comparator<T2>,
ListUpdateCallback {
    public abstract boolean areContentsTheSame(T2 var1, T2 var2);

    public abstract boolean areItemsTheSame(T2 var1, T2 var2);

    @Override
    public abstract int compare(T2 var1, T2 var2);

    @Nullable
    public Object getChangePayload(T2 T2, T2 T22) {
        return null;
    }

    public abstract void onChanged(int var1, int var2);

    @Override
    public void onChanged(int n, int n2, Object object) {
        this.onChanged(n, n2);
    }
}
