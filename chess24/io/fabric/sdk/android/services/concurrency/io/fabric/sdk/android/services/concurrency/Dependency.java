/*
 * Decompiled with CFR 0_134.
 */
package io.fabric.sdk.android.services.concurrency;

import java.util.Collection;

public interface Dependency<T> {
    public void addDependency(T var1);

    public boolean areDependenciesMet();

    public Collection<T> getDependencies();
}
