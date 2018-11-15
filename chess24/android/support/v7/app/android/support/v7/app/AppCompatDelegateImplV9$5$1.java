/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.v7.app;

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.widget.ActionBarContextView;
import android.view.View;

class AppCompatDelegateImplV9
extends ViewPropertyAnimatorListenerAdapter {
    AppCompatDelegateImplV9() {
    }

    @Override
    public void onAnimationEnd(View view) {
        5.this.this$0.mActionModeView.setAlpha(1.0f);
        5.this.this$0.mFadeAnim.setListener(null);
        5.this.this$0.mFadeAnim = null;
    }

    @Override
    public void onAnimationStart(View view) {
        5.this.this$0.mActionModeView.setVisibility(0);
    }
}
