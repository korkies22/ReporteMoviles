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
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

class LoaderManagerImpl
extends LoaderManager {
    static boolean DEBUG = false;
    static final String TAG = "LoaderManager";
    private boolean mCreatingLoader;
    @NonNull
    private final LifecycleOwner mLifecycleOwner;
    @NonNull
    private final LoaderViewModel mLoaderViewModel;

    LoaderManagerImpl(@NonNull LifecycleOwner lifecycleOwner, @NonNull ViewModelStore viewModelStore) {
        this.mLifecycleOwner = lifecycleOwner;
        this.mLoaderViewModel = LoaderViewModel.getInstance(viewModelStore);
    }

    @MainThread
    @NonNull
    private <D> Loader<D> createAndInstallLoader(int n, @Nullable Bundle object, @NonNull LoaderManager.LoaderCallbacks<D> loaderCallbacks, @Nullable Loader<D> object2) {
        try {
            this.mCreatingLoader = true;
            Loader<D> loader = loaderCallbacks.onCreateLoader(n, (Bundle)object);
            if (loader.getClass().isMemberClass() && !Modifier.isStatic(loader.getClass().getModifiers())) {
                object = new StringBuilder();
                object.append("Object returned from onCreateLoader must not be a non-static inner member class: ");
                object.append(loader);
                throw new IllegalArgumentException(object.toString());
            }
            object = new LoaderInfo<D>(n, (Bundle)object, loader, (Loader<D>)object2);
            if (DEBUG) {
                object2 = new StringBuilder();
                object2.append("  Created new loader ");
                object2.append(object);
                Log.v((String)TAG, (String)object2.toString());
            }
            this.mLoaderViewModel.putLoader(n, (LoaderInfo)object);
            return object.setCallback(this.mLifecycleOwner, loaderCallbacks);
        }
        finally {
            this.mCreatingLoader = false;
        }
    }

    @MainThread
    @Override
    public void destroyLoader(int n) {
        Object object;
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("destroyLoader must be called on the main thread");
        }
        if (DEBUG) {
            object = new StringBuilder();
            object.append("destroyLoader in ");
            object.append(this);
            object.append(" of ");
            object.append(n);
            Log.v((String)TAG, (String)object.toString());
        }
        if ((object = this.mLoaderViewModel.getLoader(n)) != null) {
            object.destroy(true);
            this.mLoaderViewModel.removeLoader(n);
        }
    }

    @Override
    public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        this.mLoaderViewModel.dump(string, fileDescriptor, printWriter, arrstring);
    }

    @Nullable
    @Override
    public <D> Loader<D> getLoader(int n) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderInfo loaderInfo = this.mLoaderViewModel.getLoader(n);
        if (loaderInfo != null) {
            return loaderInfo.getLoader();
        }
        return null;
    }

    @Override
    public boolean hasRunningLoaders() {
        return this.mLoaderViewModel.hasRunningLoaders();
    }

    @MainThread
    @NonNull
    @Override
    public <D> Loader<D> initLoader(int n, @Nullable Bundle object, @NonNull LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("initLoader must be called on the main thread");
        }
        LoaderInfo<D> loaderInfo = this.mLoaderViewModel.getLoader(n);
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("initLoader in ");
            stringBuilder.append(this);
            stringBuilder.append(": args=");
            stringBuilder.append(object);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        if (loaderInfo == null) {
            return this.createAndInstallLoader(n, (Bundle)object, loaderCallbacks, null);
        }
        if (DEBUG) {
            object = new StringBuilder();
            object.append("  Re-using existing loader ");
            object.append(loaderInfo);
            Log.v((String)TAG, (String)object.toString());
        }
        return loaderInfo.setCallback(this.mLifecycleOwner, loaderCallbacks);
    }

    void markForRedelivery() {
        this.mLoaderViewModel.markForRedelivery();
    }

    @MainThread
    @NonNull
    @Override
    public <D> Loader<D> restartLoader(int n, @Nullable Bundle bundle, @NonNull LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        Object object;
        if (this.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new IllegalStateException("restartLoader must be called on the main thread");
        }
        if (DEBUG) {
            object = new StringBuilder();
            object.append("restartLoader in ");
            object.append(this);
            object.append(": args=");
            object.append((Object)bundle);
            Log.v((String)TAG, (String)object.toString());
        }
        LoaderInfo loaderInfo = this.mLoaderViewModel.getLoader(n);
        object = null;
        if (loaderInfo != null) {
            object = loaderInfo.destroy(false);
        }
        return this.createAndInstallLoader(n, bundle, loaderCallbacks, (Loader<D>)object);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("LoaderManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        DebugUtils.buildShortClassTag(this.mLifecycleOwner, stringBuilder);
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    public static class LoaderInfo<D>
    extends MutableLiveData<D>
    implements Loader.OnLoadCompleteListener<D> {
        @Nullable
        private final Bundle mArgs;
        private final int mId;
        private LifecycleOwner mLifecycleOwner;
        @NonNull
        private final Loader<D> mLoader;
        private LoaderObserver<D> mObserver;
        private Loader<D> mPriorLoader;

        LoaderInfo(int n, @Nullable Bundle bundle, @NonNull Loader<D> loader, @Nullable Loader<D> loader2) {
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
            LoaderObserver<D> loaderObserver = this.mObserver;
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
            object = new LoaderObserver<D>(this.mLoader, (LoaderManager.LoaderCallbacks<D>)object);
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

    static class LoaderObserver<D>
    implements Observer<D> {
        @NonNull
        private final LoaderManager.LoaderCallbacks<D> mCallback;
        private boolean mDeliveredData = false;
        @NonNull
        private final Loader<D> mLoader;

        LoaderObserver(@NonNull Loader<D> loader, @NonNull LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
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

    static class LoaderViewModel
    extends ViewModel {
        private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory(){

            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> class_) {
                return (T)new LoaderViewModel();
            }
        };
        private SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat();

        LoaderViewModel() {
        }

        @NonNull
        static LoaderViewModel getInstance(ViewModelStore viewModelStore) {
            return new ViewModelProvider(viewModelStore, FACTORY).get(LoaderViewModel.class);
        }

        public void dump(String string, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            if (this.mLoaders.size() > 0) {
                printWriter.print(string);
                printWriter.println("Loaders:");
                CharSequence charSequence = new StringBuilder();
                charSequence.append(string);
                charSequence.append("    ");
                charSequence = charSequence.toString();
                for (int i = 0; i < this.mLoaders.size(); ++i) {
                    LoaderInfo loaderInfo = this.mLoaders.valueAt(i);
                    printWriter.print(string);
                    printWriter.print("  #");
                    printWriter.print(this.mLoaders.keyAt(i));
                    printWriter.print(": ");
                    printWriter.println(loaderInfo.toString());
                    loaderInfo.dump((String)charSequence, fileDescriptor, printWriter, arrstring);
                }
            }
        }

        <D> LoaderInfo<D> getLoader(int n) {
            return this.mLoaders.get(n);
        }

        boolean hasRunningLoaders() {
            int n = this.mLoaders.size();
            for (int i = 0; i < n; ++i) {
                if (!this.mLoaders.valueAt(i).isCallbackWaitingForData()) continue;
                return true;
            }
            return false;
        }

        void markForRedelivery() {
            int n = this.mLoaders.size();
            for (int i = 0; i < n; ++i) {
                this.mLoaders.valueAt(i).markForRedelivery();
            }
        }

        @Override
        protected void onCleared() {
            super.onCleared();
            int n = this.mLoaders.size();
            for (int i = 0; i < n; ++i) {
                this.mLoaders.valueAt(i).destroy(true);
            }
            this.mLoaders.clear();
        }

        void putLoader(int n, @NonNull LoaderInfo loaderInfo) {
            this.mLoaders.put(n, loaderInfo);
        }

        void removeLoader(int n) {
            this.mLoaders.remove(n);
        }

    }

}
