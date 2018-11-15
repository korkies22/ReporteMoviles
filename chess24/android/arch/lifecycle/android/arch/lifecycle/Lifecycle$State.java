/*
 * Decompiled with CFR 0_134.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.NonNull;

public static enum Lifecycle.State {
    DESTROYED,
    INITIALIZED,
    CREATED,
    STARTED,
    RESUMED;
    

    private Lifecycle.State() {
    }

    public boolean isAtLeast(@NonNull Lifecycle.State state) {
        if (this.compareTo(state) >= 0) {
            return true;
        }
        return false;
    }
}
