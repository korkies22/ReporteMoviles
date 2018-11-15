/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public static interface ViewModelProvider.Factory {
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> var1);
}
