/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Lifecycling;

static class LifecycleRegistry.ObserverWithState {
    GenericLifecycleObserver mLifecycleObserver;
    Lifecycle.State mState;

    LifecycleRegistry.ObserverWithState(LifecycleObserver lifecycleObserver, Lifecycle.State state) {
        this.mLifecycleObserver = Lifecycling.getCallback(lifecycleObserver);
        this.mState = state;
    }

    void dispatchEvent(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Lifecycle.State state = LifecycleRegistry.getStateAfter(event);
        this.mState = LifecycleRegistry.min(this.mState, state);
        this.mLifecycleObserver.onStateChanged(lifecycleOwner, event);
        this.mState = state;
    }
}
