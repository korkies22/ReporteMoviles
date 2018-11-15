/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 */
package android.support.transition;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.transition.Transition;

class FragmentTransitionSupport
extends Transition.EpicenterCallback {
    final /* synthetic */ Rect val$epicenter;

    FragmentTransitionSupport(Rect rect) {
        this.val$epicenter = rect;
    }

    @Override
    public Rect onGetEpicenter(@NonNull Transition transition) {
        return this.val$epicenter;
    }
}
