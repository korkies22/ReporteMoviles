/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.support.annotation.NonNull;

@Deprecated
public interface LifecycleRegistryOwner
extends LifecycleOwner {
    @NonNull
    @Override
    public LifecycleRegistry getLifecycle();
}
