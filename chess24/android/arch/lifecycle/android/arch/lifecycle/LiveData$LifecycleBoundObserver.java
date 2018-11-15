/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

class LiveData.LifecycleBoundObserver
extends LiveData<T>
implements GenericLifecycleObserver {
    @NonNull
    final LifecycleOwner mOwner;

    LiveData.LifecycleBoundObserver(LifecycleOwner lifecycleOwner, Observer<T> observer) {
        super(LiveData.this, observer);
        this.mOwner = lifecycleOwner;
    }

    void detachObserver() {
        this.mOwner.getLifecycle().removeObserver(this);
    }

    boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
        if (this.mOwner == lifecycleOwner) {
            return true;
        }
        return false;
    }

    @Override
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (this.mOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            LiveData.this.removeObserver(this.mObserver);
            return;
        }
        this.activeStateChanged(this.shouldBeActive());
    }

    boolean shouldBeActive() {
        return this.mOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }
}
