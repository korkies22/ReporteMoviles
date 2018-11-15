/*
 * Decompiled with CFR 0_134.
 */
package android.support.v4.app;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManagerImpl;

static final class LoaderManagerImpl.LoaderViewModel
implements ViewModelProvider.Factory {
    LoaderManagerImpl.LoaderViewModel() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> class_) {
        return (T)new LoaderManagerImpl.LoaderViewModel();
    }
}
