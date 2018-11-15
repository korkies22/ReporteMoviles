/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.v7.util.ThreadUtil;
import android.support.v7.util.TileList;

public static interface ThreadUtil.MainThreadCallback<T> {
    public void addTile(int var1, TileList.Tile<T> var2);

    public void removeTile(int var1, int var2);

    public void updateItemCount(int var1, int var2);
}
