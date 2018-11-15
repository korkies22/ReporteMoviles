/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.util.SparseIntArray
 */
package android.support.v7.util;

import android.support.v7.util.AsyncListUtil;
import android.support.v7.util.ThreadUtil;
import android.support.v7.util.TileList;
import android.util.Log;
import android.util.SparseIntArray;

class AsyncListUtil
implements ThreadUtil.MainThreadCallback<T> {
    AsyncListUtil() {
    }

    private boolean isRequestedGeneration(int n) {
        if (n == AsyncListUtil.this.mRequestedGeneration) {
            return true;
        }
        return false;
    }

    private void recycleAllTiles() {
        for (int i = 0; i < AsyncListUtil.this.mTileList.size(); ++i) {
            AsyncListUtil.this.mBackgroundProxy.recycleTile(AsyncListUtil.this.mTileList.getAtIndex(i));
        }
        AsyncListUtil.this.mTileList.clear();
    }

    @Override
    public void addTile(int n, TileList.Tile<T> tile) {
        if (!this.isRequestedGeneration(n)) {
            AsyncListUtil.this.mBackgroundProxy.recycleTile(tile);
            return;
        }
        TileList.Tile tile2 = AsyncListUtil.this.mTileList.addOrReplace(tile);
        if (tile2 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("duplicate tile @");
            stringBuilder.append(tile2.mStartPosition);
            Log.e((String)android.support.v7.util.AsyncListUtil.TAG, (String)stringBuilder.toString());
            AsyncListUtil.this.mBackgroundProxy.recycleTile(tile2);
        }
        int n2 = tile.mStartPosition;
        int n3 = tile.mItemCount;
        n = 0;
        while (n < AsyncListUtil.this.mMissingPositions.size()) {
            int n4 = AsyncListUtil.this.mMissingPositions.keyAt(n);
            if (tile.mStartPosition <= n4 && n4 < n2 + n3) {
                AsyncListUtil.this.mMissingPositions.removeAt(n);
                AsyncListUtil.this.mViewCallback.onItemLoaded(n4);
                continue;
            }
            ++n;
        }
    }

    @Override
    public void removeTile(int n, int n2) {
        if (!this.isRequestedGeneration(n)) {
            return;
        }
        TileList.Tile tile = AsyncListUtil.this.mTileList.removeAtPos(n2);
        if (tile == null) {
            tile = new StringBuilder();
            tile.append("tile not found @");
            tile.append(n2);
            Log.e((String)android.support.v7.util.AsyncListUtil.TAG, (String)tile.toString());
            return;
        }
        AsyncListUtil.this.mBackgroundProxy.recycleTile(tile);
    }

    @Override
    public void updateItemCount(int n, int n2) {
        if (!this.isRequestedGeneration(n)) {
            return;
        }
        AsyncListUtil.this.mItemCount = n2;
        AsyncListUtil.this.mViewCallback.onDataRefresh();
        AsyncListUtil.this.mDisplayedGeneration = AsyncListUtil.this.mRequestedGeneration;
        this.recycleAllTiles();
        AsyncListUtil.this.mAllowScrollHints = false;
        AsyncListUtil.this.updateRange();
    }
}
