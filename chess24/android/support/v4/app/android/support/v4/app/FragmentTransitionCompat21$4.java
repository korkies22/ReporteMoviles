/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.transition.Transition
 *  android.transition.Transition$EpicenterCallback
 */
package android.support.v4.app;

import android.graphics.Rect;
import android.transition.Transition;

class FragmentTransitionCompat21
extends Transition.EpicenterCallback {
    final /* synthetic */ Rect val$epicenter;

    FragmentTransitionCompat21(Rect rect) {
        this.val$epicenter = rect;
    }

    public Rect onGetEpicenter(Transition transition) {
        if (this.val$epicenter != null && !this.val$epicenter.isEmpty()) {
            return this.val$epicenter;
        }
        return null;
    }
}
