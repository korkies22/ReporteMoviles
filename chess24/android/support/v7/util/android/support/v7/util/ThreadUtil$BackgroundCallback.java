/*
 * Decompiled with CFR 0_134.
 */
package android.support.v7.util;

import android.support.v7.util.ThreadUtil;
import android.support.v7.util.TileList;

public static interface ThreadUtil.BackgroundCallback<T> {
    public void loadTile(int var1, int var2);

    public void recycleTile(TileList.Tile<T> var1);

    public void refresh(int var1);

    public void updateRange(int var1, int var2, int var3, int var4, int var5);
}
