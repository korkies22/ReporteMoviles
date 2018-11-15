/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package android.support.v4.app;

import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManagerImpl;
import android.support.v4.content.Loader;
import android.util.Log;
import java.io.PrintWriter;

static class LoaderManagerImpl.LoaderObserver<D>
implements Observer<D> {
    @NonNull
    private final LoaderManager.LoaderCallbacks<D> mCallback;
    private boolean mDeliveredData = false;
    @NonNull
    private final Loader<D> mLoader;

    LoaderManagerImpl.LoaderObserver(@NonNull Loader<D> loader, @NonNull LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        this.mLoader = loader;
        this.mCallback = loaderCallbacks;
    }

    public void dump(String string, PrintWriter printWriter) {
        printWriter.print(string);
        printWriter.print("mDeliveredData=");
        printWriter.println(this.mDeliveredData);
    }

    boolean hasDeliveredData() {
        return this.mDeliveredData;
    }

    @Override
    public void onChanged(@Nullable D d) {
        if (LoaderManagerImpl.DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("  onLoadFinished in ");
            stringBuilder.append(this.mLoader);
            stringBuilder.append(": ");
            stringBuilder.append(this.mLoader.dataToString(d));
            Log.v((String)LoaderManagerImpl.TAG, (String)stringBuilder.toString());
        }
        this.mCallback.onLoadFinished(this.mLoader, d);
        this.mDeliveredData = true;
    }

    @MainThread
    void reset() {
        if (this.mDeliveredData) {
            if (LoaderManagerImpl.DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("  Resetting: ");
                stringBuilder.append(this.mLoader);
                Log.v((String)LoaderManagerImpl.TAG, (String)stringBuilder.toString());
            }
            this.mCallback.onLoaderReset(this.mLoader);
        }
    }

    public String toString() {
        return this.mCallback.toString();
    }
}
