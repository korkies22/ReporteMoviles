/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Looper
 *  android.util.Log
 */
package android.support.v4.app;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManagerImpl;
import android.support.v4.content.Loader;
import android.support.v4.util.DebugUtils;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public static class LoaderManagerImpl.LoaderInfo<D>
extends MutableLiveData<D>
implements Loader.OnLoadCompleteListener<D> {
    @Nullable
    private final Bundle mArgs;
    private final int mId;
    private LifecycleOwner mLifecycleOwner;
    @NonNull
    private final Loader<D> mLoader;
    private LoaderManagerImpl.LoaderObserver<D> mObserver;
    private Loader<D> mPriorLoader;

    LoaderManagerImpl.LoaderInfo(int n, @Nullable Bundle bundle, @NonNull Loader<D> loader, @Nullable Loader<D> loader2) {
        this.mId = n;
        this.mArgs = bundle;
        this.mLoader = loader;
        this.mPriorLoader = loader2;
        this.mLoader.registerListener(n, this);
    }

    @MainThread
    Loader<D> destroy(boolean bl) {
        Object object;
        if (LoaderManagerImpl.DEBUG) {
            object = new StringBuilder();
            object.append("  Destroying: ");
            object.append(this);
            Log.v((String)LoaderManagerImpl.TAG, (String)object.toString());
        }
        this.mLoader.cancelLoad();
        this.mLoader.abandon();
        object = this.mObserver;
        if (object != null) {
            this.removeObserver((Observer<D>)object);
            if (bl) {
                object.reset();
            }
        }
        this.mLoader.unregisterListener(this);
        if (object != null && !object.hasDeliveredData() || bl) {
            this.mLoader.reset();
            return this.mPriorLoader;
        }
        return this.mLoader;
    }

    public void dump(String string, FileDescriptor object, PrintWriter printWriter, String[] object2) {
        printWriter.print(string);
        printWriter.print("mId=");
        printWriter.print(this.mId);
        printWriter.print(" mArgs=");
        printWriter.println((Object)this.mArgs);
        printWriter.print(string);
        printWriter.print("mLoader=");
        printWriter.println(this.mLoader);
        Loader<D> loader = this.mLoader;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("  ");
        loader.dump(stringBuilder.toString(), (FileDescriptor)object, printWriter, (String[])object2);
        if (this.mObserver != null) {
            printWriter.print(string);
            printWriter.print("mCallbacks=");
            printWriter.println(this.mObserver);
            object = this.mObserver;
            object2 = new StringBuilder();
            object2.append(string);
            object2.append("  ");
            object.dump(object2.toString(), printWriter);
        }
        printWriter.print(string);
        printWriter.print("mData=");
        printWriter.println(this.getLoader().dataToString(this.getValue()));
        printWriter.print(string);
        printWriter.print("mStarted=");
        printWriter.println(this.hasActiveObservers());
    }

    @NonNull
    Loader<D> getLoader() {
        return this.mLoader;
    }

    boolean isCallbackWaitingForData() {
        boolean bl = this.hasActiveObservers();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        bl = bl2;
        if (this.mObserver != null) {
            bl = bl2;
            if (!this.mObserver.hasDeliveredData()) {
                bl = true;
            }
        }
        return bl;
    }

    void markForRedelivery() {
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        LoaderManagerImpl.LoaderObserver<D> loaderObserver = this.mObserver;
        if (lifecycleOwner != null && loaderObserver != null) {
            super.removeObserver(loaderObserver);
            this.observe(lifecycleOwner, loaderObserver);
        }
    }

    @Override
    protected void onActive() {
        if (LoaderManagerImpl.DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("  Starting: ");
            stringBuilder.append(this);
            Log.v((String)LoaderManagerImpl.TAG, (String)stringBuilder.toString());
        }
        this.mLoader.startLoading();
    }

    @Override
    protected void onInactive() {
        if (LoaderManagerImpl.DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("  Stopping: ");
            stringBuilder.append(this);
            Log.v((String)LoaderManagerImpl.TAG, (String)stringBuilder.toString());
        }
        this.mLoader.stopLoading();
    }

    @Override
    public void onLoadComplete(@NonNull Loader<D> object, @Nullable D d) {
        if (LoaderManagerImpl.DEBUG) {
            object = new StringBuilder();
            object.append("onLoadComplete: ");
            object.append(this);
            Log.v((String)LoaderManagerImpl.TAG, (String)object.toString());
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.setValue(d);
            return;
        }
        if (LoaderManagerImpl.DEBUG) {
            Log.w((String)LoaderManagerImpl.TAG, (String)"onLoadComplete was incorrectly called on a background thread");
        }
        this.postValue(d);
    }

    @Override
    public void removeObserver(@NonNull Observer<D> observer) {
        super.removeObserver(observer);
        this.mLifecycleOwner = null;
        this.mObserver = null;
    }

    @MainThread
    @NonNull
    Loader<D> setCallback(@NonNull LifecycleOwner lifecycleOwner, @NonNull LoaderManager.LoaderCallbacks<D> object) {
        object = new LoaderManagerImpl.LoaderObserver<D>(this.mLoader, (LoaderManager.LoaderCallbacks<D>)object);
        this.observe(lifecycleOwner, object);
        if (this.mObserver != null) {
            this.removeObserver(this.mObserver);
        }
        this.mLifecycleOwner = lifecycleOwner;
        this.mObserver = object;
        return this.mLoader;
    }

    @Override
    public void setValue(D d) {
        super.setValue(d);
        if (this.mPriorLoader != null) {
            this.mPriorLoader.reset();
            this.mPriorLoader = null;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(64);
        stringBuilder.append("LoaderInfo{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" #");
        stringBuilder.append(this.mId);
        stringBuilder.append(" : ");
        DebugUtils.buildShortClassTag(this.mLoader, stringBuilder);
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }
}
