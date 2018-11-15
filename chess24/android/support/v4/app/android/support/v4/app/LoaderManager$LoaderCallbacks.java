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
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

public static interface LoaderManager.LoaderCallbacks<D> {
    @MainThread
    @NonNull
    public Loader<D> onCreateLoader(int var1, @Nullable Bundle var2);

    @MainThread
    public void onLoadFinished(@NonNull Loader<D> var1, D var2);

    @MainThread
    public void onLoaderReset(@NonNull Loader<D> var1);
}
