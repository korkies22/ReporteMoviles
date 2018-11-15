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
    final /* synthetic */ CharSequence val$error;

    TextInputLayout(CharSequence charSequence) {
        this.val$error = charSequence;
    }

    public void onAnimationEnd(Animator animator) {
        TextInputLayout.this.mErrorView.setText(this.val$error);
        TextInputLayout.this.mErrorView.setVisibility(4);
    }
}
