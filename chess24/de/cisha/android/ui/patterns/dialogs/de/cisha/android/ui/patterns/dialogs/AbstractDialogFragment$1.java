/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package de.cisha.android.ui.patterns.dialogs;

import android.view.View;
import android.view.animation.Animation;

class AbstractDialogFragment
implements Animation.AnimationListener {
    final /* synthetic */ boolean val$visible;

    AbstractDialogFragment(boolean bl) {
        this.val$visible = bl;
    }

    public void onAnimationEnd(Animation animation) {
        AbstractDialogFragment.this.setHardwareLayerType(AbstractDialogFragment.this.getView(), false);
        if (!this.val$visible) {
            if (AbstractDialogFragment.this._showing) {
                de.cisha.android.ui.patterns.dialogs.AbstractDialogFragment.super.dismiss();
                return;
            }
            AbstractDialogFragment.this._dismiss = true;
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
