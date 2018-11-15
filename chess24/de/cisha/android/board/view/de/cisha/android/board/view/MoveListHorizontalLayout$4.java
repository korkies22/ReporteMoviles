/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.animation.Animation
 *  android.view.animation.Animation$AnimationListener
 *  android.widget.TextView
 */
package de.cisha.android.board.view;

import android.view.animation.Animation;
import android.widget.TextView;

class MoveListHorizontalLayout
implements Animation.AnimationListener {
    final /* synthetic */ TextView val$textView;

    MoveListHorizontalLayout(TextView textView) {
        this.val$textView = textView;
    }

    public void onAnimationEnd(Animation animation) {
        this.val$textView.clearAnimation();
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationStart(Animation animation) {
    }
}
