/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.PopupWindow
 */
package android.support.v7.app;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.widget.ActionBarContextView;
import android.view.View;
import android.widget.PopupWindow;

class AppCompatDelegateImplV9
implements Runnable {
    AppCompatDelegateImplV9() {
    }

    @Override
    public void run() {
        AppCompatDelegateImplV9.this.mActionModePopup.showAtLocation((View)AppCompatDelegateImplV9.this.mActionModeView, 55, 0, 0);
        AppCompatDelegateImplV9.this.endOnGoingFadeAnimation();
        if (AppCompatDelegateImplV9.this.shouldAnimateActionModeView()) {
            AppCompatDelegateImplV9.this.mActionModeView.setAlpha(0.0f);
            AppCompatDelegateImplV9.this.mFadeAnim = ViewCompat.animate((View)AppCompatDelegateImplV9.this.mActionModeView).alpha(1.0f);
            AppCompatDelegateImplV9.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(View view) {
                    AppCompatDelegateImplV9.this.mActionModeView.setAlpha(1.0f);
                    AppCompatDelegateImplV9.this.mFadeAnim.setListener(null);
                    AppCompatDelegateImplV9.this.mFadeAnim = null;
                }

                @Override
                public void onAnimationStart(View view) {
                    AppCompatDelegateImplV9.this.mActionModeView.setVisibility(0);
                }
            });
            return;
        }
        AppCompatDelegateImplV9.this.mActionModeView.setAlpha(1.0f);
        AppCompatDelegateImplV9.this.mActionModeView.setVisibility(0);
    }

}
