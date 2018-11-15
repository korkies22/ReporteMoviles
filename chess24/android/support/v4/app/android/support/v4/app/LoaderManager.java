/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManagerImpl;
import android.support.v4.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class LoaderManager {
    public static void enableDebugLogging(boolean bl) {
        LoaderManagerImpl.DEBUG = bl;
    }

    @MainThread
    public abstract void destroyLoader(int var1);

    public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

    @Nullable
    public abstract <D> Loader<D> getLoader(int var1);

    public boolean hasRunningLoaders() {
        return false;
    }

    @MainThread
    @NonNull
    public abstract <D> Loader<D> initLoader(int var1, @Nullable Bundle var2, @NonNull LoaderCallbacks<D> var3);

    @MainThread
    @NonNull
    public abstract <D> Loader<D> restartLoader(int var1, @Nullable Bundle var2, @NonNull LoaderCallbacks<D> var3);

    public static interface LoaderCallbacks<D> {
        @MainThread
        @NonNull
        public Loader<D> onCreateLoader(int var1, @Nullable Bundle var2);

        @MainThread
        public void onLoadFinished(@NonNull Loader<D> var1, D var2);

        @MainThread
        public void onLoaderReset(@NonNull Loader<D> var1);
    }

}
