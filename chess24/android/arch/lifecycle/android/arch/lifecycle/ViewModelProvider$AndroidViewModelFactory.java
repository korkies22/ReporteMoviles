/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.app.Application
 */
package android.arch.lifecycle;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public static class ViewModelProvider.AndroidViewModelFactory
extends ViewModelProvider.NewInstanceFactory {
    private static ViewModelProvider.AndroidViewModelFactory sInstance;
    private Application mApplication;

    public ViewModelProvider.AndroidViewModelFactory(@NonNull Application application) {
        this.mApplication = application;
    }

    public static ViewModelProvider.AndroidViewModelFactory getInstance(@NonNull Application application) {
        if (sInstance == null) {
            sInstance = new ViewModelProvider.AndroidViewModelFactory(application);
        }
        return sInstance;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> class_) {
        if (AndroidViewModel.class.isAssignableFrom(class_)) {
            ViewModel viewModel;
            try {
                viewModel = (ViewModel)class_.getConstructor(Application.class).newInstance(new Object[]{this.mApplication});
            }
            catch (InvocationTargetException invocationTargetException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot create an instance of ");
                stringBuilder.append(class_);
                throw new RuntimeException(stringBuilder.toString(), invocationTargetException);
            }
            catch (InstantiationException instantiationException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot create an instance of ");
                stringBuilder.append(class_);
                throw new RuntimeException(stringBuilder.toString(), instantiationException);
            }
            catch (IllegalAccessException illegalAccessException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot create an instance of ");
                stringBuilder.append(class_);
                throw new RuntimeException(stringBuilder.toString(), illegalAccessException);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot create an instance of ");
                stringBuilder.append(class_);
                throw new RuntimeException(stringBuilder.toString(), noSuchMethodException);
            }
            return (T)viewModel;
        }
        return super.create(class_);
    }
}
