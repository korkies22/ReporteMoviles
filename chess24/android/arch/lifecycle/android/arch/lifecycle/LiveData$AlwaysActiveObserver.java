/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;

private class LiveData.AlwaysActiveObserver
extends LiveData<T> {
    LiveData.AlwaysActiveObserver(Observer<T> observer) {
        super(LiveData.this, observer);
    }

    boolean shouldBeActive() {
        return true;
    }
}
