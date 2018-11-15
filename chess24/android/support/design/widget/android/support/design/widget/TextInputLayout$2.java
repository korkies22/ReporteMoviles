/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorListenerAdapter
 *  android.widget.TextView
 */
package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.widget.TextView;

class TextInputLayout
extends AnimatorListenerAdapter {
    TextInputLayout() {
    }

    public void onAnimationStart(Animator animator) {
        TextInputLayout.this.mErrorView.setVisibility(0);
    }
}
