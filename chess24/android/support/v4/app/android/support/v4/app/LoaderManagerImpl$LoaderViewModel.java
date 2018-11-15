/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.app;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManagerImpl;
import android.support.v4.content.Loader;
import android.support.v4.util.SparseArrayCompat;
import java.io.FileDescriptor;
import java.io.PrintWriter;

static class LoaderManagerImpl.LoaderViewModel
extends ViewModel {
    private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory(){

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> class_) {
            return (T)new LoaderManagerImpl.LoaderViewModel();
        }
    };
    private SparseArrayCompat<LoaderManagerImpl.LoaderInfo> mLoaders = new SparseArrayCompat();

    LoaderManagerImpl.LoaderViewModel() {
    }

    @NonNull
    static LoaderManagerImpl.LoaderViewModel getInstance(ViewModelStore viewModelStore) {
        return new ViewModelProvider(viewModelStore, FACTORY).get(LoaderManagerImpl.LoaderViewModel.class);
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
                LoaderManagerImpl.LoaderInfo loaderInfo = this.mLoaders.valueAt(i);
                printWriter.print(string);
                printWriter.print("  #");
                printWriter.print(this.mLoaders.keyAt(i));
                printWriter.print(": ");
                printWriter.println(loaderInfo.toString());
                loaderInfo.dump((String)charSequence, fileDescriptor, printWriter, arrstring);
            }
        }
    }

    <D> LoaderManagerImpl.LoaderInfo<D> getLoader(int n) {
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

    void putLoader(int n, @NonNull LoaderManagerImpl.LoaderInfo loaderInfo) {
        this.mLoaders.put(n, loaderInfo);
    }

    void removeLoader(int n) {
        this.mLoaders.remove(n);
    }

}
