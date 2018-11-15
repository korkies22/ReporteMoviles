/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.AnimatorListenerAdapter
 */
package android.support.transition;

import android.animation.AnimatorListenerAdapter;
import android.support.transition.ChangeBounds;

class ChangeBounds
extends AnimatorListenerAdapter {
    private ChangeBounds.ViewBounds mViewBounds;
    final /* synthetic */ ChangeBounds.ViewBounds val$viewBounds;

    ChangeBounds(ChangeBounds.ViewBounds viewBounds) {
        this.val$viewBounds = viewBounds;
        this.mViewBounds = this.val$viewBounds;
    }
}
