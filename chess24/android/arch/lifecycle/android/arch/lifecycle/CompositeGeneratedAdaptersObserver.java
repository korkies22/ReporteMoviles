/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.GeneratedAdapter;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MethodCallsLogger;
import android.support.annotation.RestrictTo;

@RestrictTo(value={RestrictTo.Scope.LIBRARY_GROUP})
public class CompositeGeneratedAdaptersObserver
implements GenericLifecycleObserver {
    private final GeneratedAdapter[] mGeneratedAdapters;

    CompositeGeneratedAdaptersObserver(GeneratedAdapter[] arrgeneratedAdapter) {
        this.mGeneratedAdapters = arrgeneratedAdapter;
    }

    @Override
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        int n;
        MethodCallsLogger methodCallsLogger = new MethodCallsLogger();
        GeneratedAdapter[] arrgeneratedAdapter = this.mGeneratedAdapters;
        int n2 = 0;
        int n3 = arrgeneratedAdapter.length;
        for (n = 0; n < n3; ++n) {
            arrgeneratedAdapter[n].callMethods(lifecycleOwner, event, false, methodCallsLogger);
        }
        arrgeneratedAdapter = this.mGeneratedAdapters;
        n3 = arrgeneratedAdapter.length;
        for (n = n2; n < n3; ++n) {
            arrgeneratedAdapter[n].callMethods(lifecycleOwner, event, true, methodCallsLogger);
        }
    }
}
