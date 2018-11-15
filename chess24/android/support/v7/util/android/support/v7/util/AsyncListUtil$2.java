/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.util.SparseBooleanArray
 */
package android.support.v7.util;

import android.support.v7.util.AsyncListUtil;
import android.support.v7.util.ThreadUtil;
import android.support.v7.util.TileList;
import android.util.Log;
import android.util.SparseBooleanArray;

class AsyncListUtil
implements ThreadUtil.BackgroundCallback<T> {
    private int mFirstRequiredTileStart;
    private int mGeneration;
    private int mItemCount;
    private int mLastRequiredTileStart;
    final SparseBooleanArray mLoadedTiles = new SparseBooleanArray();
    private TileList.Tile<T> mRecycledRoot;

    AsyncListUtil() {
    }

    private TileList.Tile<T> acquireTile() {
        if (this.mRecycledRoot != null) {
            TileList.Tile<T> tile = this.mRecycledRoot;
            this.mRecycledRoot = this.mRecycledRoot.mNext;
            return tile;
        }
        return new TileList.Tile(AsyncListUtil.this.mTClass, AsyncListUtil.this.mTileSize);
    }

    private void addTile(TileList.Tile<T> tile) {
        this.mLoadedTiles.put(tile.mStartPosition, true);
        AsyncListUtil.this.mMainThreadProxy.addTile(this.mGeneration, tile);
    }

    private void flushTileCache(int n) {
        int n2 = AsyncListUtil.this.mDataCallback.getMaxCachedTiles();
        while (this.mLoadedTiles.size() >= n2) {
            int n3 = this.mLoadedTiles.keyAt(0);
            int n4 = this.mLoadedTiles.keyAt(this.mLoadedTiles.size() - 1);
            int n5 = this.mFirstRequiredTileStart - n3;
            int n6 = n4 - this.mLastRequiredTileStart;
            if (n5 > 0 && (n5 >= n6 || n == 2)) {
                this.removeTile(n3);
                continue;
            }
            if (n6 > 0 && (n5 < n6 || n == 1)) {
                this.removeTile(n4);
                continue;
            }
            return;
        }
    }

    private int getTileStart(int n) {
        return n - n % AsyncListUtil.this.mTileSize;
    }

    private boolean isTileLoaded(int n) {
        return this.mLoadedTiles.get(n);
    }

    private /* varargs */ void log(String string, Object ... arrobject) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[BKGR] ");
        stringBuilder.append(String.format(string, arrobject));
        Log.d((String)android.support.v7.util.AsyncListUtil.TAG, (String)stringBuilder.toString());
    }

    private void removeTile(int n) {
        this.mLoadedTiles.delete(n);
        AsyncListUtil.this.mMainThreadProxy.removeTile(this.mGeneration, n);
    }

    private void requestTiles(int n, int n2, int n3, boolean bl) {
        for (int i = n; i <= n2; i += AsyncListUtil.this.mTileSize) {
            int n4 = bl ? n2 + n - i : i;
            AsyncListUtil.this.mBackgroundProxy.loadTile(n4, n3);
        }
    }

    @Override
    public void loadTile(int n, int n2) {
        if (this.isTileLoaded(n)) {
            return;
        }
        TileList.Tile<T> tile = this.acquireTile();
        tile.mStartPosition = n;
        tile.mItemCount = Math.min(AsyncListUtil.this.mTileSize, this.mItemCount - tile.mStartPosition);
        AsyncListUtil.this.mDataCallback.fillData(tile.mItems, tile.mStartPosition, tile.mItemCount);
        this.flushTileCache(n2);
        this.addTile(tile);
    }

    @Override
    public void recycleTile(TileList.Tile<T> tile) {
        AsyncListUtil.this.mDataCallback.recycleData(tile.mItems, tile.mItemCount);
        tile.mNext = this.mRecycledRoot;
        this.mRecycledRoot = tile;
    }

    @Override
    public void refresh(int n) {
        this.mGeneration = n;
        this.mLoadedTiles.clear();
        this.mItemCount = AsyncListUtil.this.mDataCallback.refreshData();
        AsyncListUtil.this.mMainThreadProxy.updateItemCount(this.mGeneration, this.mItemCount);
    }

    @Override
    public void updateRange(int n, int n2, int n3, int n4, int n5) {
        if (n > n2) {
            return;
        }
        n = this.getTileStart(n);
        n2 = this.getTileStart(n2);
        this.mFirstRequiredTileStart = this.getTileStart(n3);
        this.mLastRequiredTileStart = this.getTileStart(n4);
        if (n5 == 1) {
            this.requestTiles(this.mFirstRequiredTileStart, n2, n5, true);
            this.requestTiles(n2 + AsyncListUtil.this.mTileSize, this.mLastRequiredTileStart, n5, false);
            return;
        }
        this.requestTiles(n, this.mLastRequiredTileStart, n5, false);
        this.requestTiles(this.mFirstRequiredTileStart, n - AsyncListUtil.this.mTileSize, n5, true);
    }
}
