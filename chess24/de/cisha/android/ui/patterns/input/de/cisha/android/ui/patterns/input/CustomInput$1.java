/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 */
package de.cisha.android.ui.patterns.input;

import android.view.animation.Animation;

class CustomInput
implements Animation.AnimationListener {
    final /* synthetic */ boolean val$show;

    CustomInput(boolean bl) {
        this.val$show = bl;
    }

    public void onAnimationEnd(Animation animation) {
        CustomInput.this.showHint(this.val$show);
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
        CustomInput.this.showHint(true);
    }
}
