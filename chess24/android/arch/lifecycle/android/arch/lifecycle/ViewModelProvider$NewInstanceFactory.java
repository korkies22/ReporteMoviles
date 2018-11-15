/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public static class ViewModelProvider.NewInstanceFactory
implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> class_) {
        ViewModel viewModel;
        try {
            viewModel = (ViewModel)class_.newInstance();
        }
        catch (IllegalAccessException illegalAccessException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot create an instance of ");
            stringBuilder.append(class_);
            throw new RuntimeException(stringBuilder.toString(), illegalAccessException);
        }
        catch (InstantiationException instantiationException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot create an instance of ");
            stringBuilder.append(class_);
            throw new RuntimeException(stringBuilder.toString(), instantiationException);
        }
        return (T)viewModel;
    }
}
