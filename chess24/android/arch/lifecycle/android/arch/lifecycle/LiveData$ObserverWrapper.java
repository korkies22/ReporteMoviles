/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;

private abstract class LiveData.ObserverWrapper {
    boolean mActive;
    int mLastVersion = -1;
    final Observer<T> mObserver;

    LiveData.ObserverWrapper(Observer<T> observer) {
        this.mObserver = observer;
    }

    void activeStateChanged(boolean bl) {
        if (bl == this.mActive) {
            return;
        }
        this.mActive = bl;
        int n = LiveData.this.mActiveCount;
        int n2 = 1;
        n = n == 0 ? 1 : 0;
        LiveData liveData = LiveData.this;
        int n3 = liveData.mActiveCount;
        if (!this.mActive) {
            n2 = -1;
        }
        liveData.mActiveCount = n3 + n2;
        if (n != 0 && this.mActive) {
            LiveData.this.onActive();
        }
        if (LiveData.this.mActiveCount == 0 && !this.mActive) {
            LiveData.this.onInactive();
        }
        if (this.mActive) {
            LiveData.this.dispatchingValue(this);
        }
    }

    void detachObserver() {
    }

    boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
        return false;
    }

    abstract boolean shouldBeActive();
}
